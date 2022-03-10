package org.nitb.excelmapper.operative;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.tools.shell.Global;
import org.nitb.excelmapper.annotations.ExcelDocument;
import org.nitb.excelmapper.annotations.basic.ExcelBasicColumn;
import org.nitb.excelmapper.annotations.basic.ExcelBasicSheet;
import org.nitb.excelmapper.annotations.style.ExcelCellStyle;
import org.nitb.excelmapper.annotations.style.ExcelFontStyle;
import org.nitb.excelmapper.annotations.style.column.conditional.ExcelColumnCellConditionalStyle;
import org.nitb.excelmapper.annotations.style.column.ExcelColumnStyle;
import org.nitb.excelmapper.annotations.style.header.ExcelHeaderStyle;
import org.nitb.excelmapper.enums.ExcelDocumentType;
import org.nitb.excelmapper.exceptions.ExcelMapperException;
import org.nitb.excelmapper.operative.js.JavaScriptEngineManager;
import org.nitb.excelmapper.operative.transformation.ExcelColumnTransformer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelMapper {

  public static Workbook toExcel(Object documentObject) throws ExcelMapperException {
    final Workbook workbook = new XSSFWorkbook();

    final Class<?> documentClass = documentObject.getClass();

    // Check if passed object is a valid Excel document checking if exists @ExcelDocument in their annotations
    if (!documentClass.isAnnotationPresent(ExcelDocument.class)) {
      throw new ExcelMapperException("Invalid parameter, object is not a valid ExcelDocument object, please check your class contains @ExcelDocument annotation");
    }

    final ExcelDocument documentAnnotation = documentClass.getAnnotation(ExcelDocument.class);
    final ExcelDocumentType type = documentAnnotation.type();

    if (type.equals(ExcelDocumentType.BASIC_MAPPING)) {
      fromBasicToFile(workbook, documentObject, documentClass);
    }

    return workbook;
  }

  public static void toExcelFile(Object documentObject, String folderPath, String filename) throws ExcelMapperException, IOException {
    final String workBookName;

    if (filename == null) {
      final Class<?> documentClass = documentObject.getClass();
      final ExcelDocument documentAnnotation = documentClass.getAnnotation(ExcelDocument.class);
      final boolean useClassname = documentAnnotation.useClassname();
      workBookName = useClassname ? documentClass.getTypeName() : documentAnnotation.name();
    } else {
      workBookName = filename;
    }

    Workbook workbook = toExcel(documentObject);

    try (FileOutputStream fos = new FileOutputStream(Paths.get(folderPath == null ? "." : folderPath, workBookName).toFile())) {
      workbook.write(fos);
    }
  }

  public static void toExcelFile(Object documentObject, String folderPath) throws ExcelMapperException, IOException {
    toExcelFile(documentObject, folderPath, null);
  }

  public static void toExcelFile(Object documentObject) throws ExcelMapperException, IOException {
    toExcelFile(documentObject, null, null);
  }

  @SuppressWarnings("unchecked")
  private static void fromBasicToFile(Workbook workbook, Object documentObject, Class<?> documentClass) throws ExcelMapperException {
    // Filter all fields that are annotated as @ExcelBasicSheet inside document
    final List<Field> sheets = Arrays.stream(documentClass.getDeclaredFields())
            .filter(x -> x.getType().isAnnotationPresent(ExcelBasicSheet.class)).collect(Collectors.toList());

    try {
      for (final Field sheet : sheets) {
        final Object sheetObject = sheet.get(documentObject);
        final Class<?> fieldClass = sheet.getType();
        final ExcelBasicSheet sheetAnnotation = fieldClass.getAnnotation(ExcelBasicSheet.class);
        final boolean useClassname = sheetAnnotation.useClassname();
        final String sheetName = useClassname ? fieldClass.getTypeName() : sheetAnnotation.name();

        final Sheet workSheet = workbook.createSheet(sheetName);

        final List<Field> columns = Arrays.stream(fieldClass.getDeclaredFields())
                .filter(x -> x.isAnnotationPresent(ExcelBasicColumn.class)).collect(Collectors.toList());

        int col = 0;

        for (final Field column : columns) {
          if (!Collection.class.isAssignableFrom(column.getType()) && !column.getType().isArray()) {
            throw new ExcelMapperException("Invalid parameter, object with @ExcelBasicColumn only can be array or Collection types");
          }

          final ExcelBasicColumn columnAnnotation = column.getAnnotation(ExcelBasicColumn.class);
          final int startsAt = columnAnnotation.startsAt();

          final ExcelHeaderStyle columnHeaderStyle = column.getAnnotation(ExcelHeaderStyle.class);
          final CellStyle headerStyle = columnHeaderStyle == null ? null : getCellStyle(workbook, columnHeaderStyle.style(), columnHeaderStyle.font());

          final ExcelColumnStyle columnStyle = column.getAnnotation(ExcelColumnStyle.class);
          final CellStyle colStyle = columnStyle == null ? null : getCellStyle(workbook, columnStyle.style(), columnStyle.font());

          final ExcelColumnCellConditionalStyle[] cellConditions = column.getAnnotationsByType(ExcelColumnCellConditionalStyle.class);
          final Map<String, CellStyle> cellConditionalStyles = Arrays.stream(cellConditions).map(x -> new AbstractMap.SimpleEntry<>(x.condition(), getCellStyle(workbook, x.style().style(), x.style().font())))
                  .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

          final Class<?> cellTransformerClass = columnAnnotation.transformer();

          if (!ExcelColumnTransformer.class.isAssignableFrom(cellTransformerClass)) {
            throw new ExcelMapperException("Error mapping column with invalid column cell transformer class. A cell transformer class must be child of ExcelColumnTransformer");
          }

          final ExcelColumnTransformer<Object, Object> cellTransformer = (ExcelColumnTransformer<Object, Object>)cellTransformerClass.newInstance();

          if (workSheet.getLastRowNum() < startsAt) {
            for (int i = workSheet.getLastRowNum() + 1; i < startsAt + 1; i++) {
              workSheet.createRow(i);
            }
          }

          final Cell columnCell = workSheet.getRow(startsAt).createCell(col);
          columnCell.setCellValue(columnAnnotation.useClassname() ? column.getName() : columnAnnotation.name().isEmpty() ? String.format("COLUMN %d", col + 1) : columnAnnotation.name());

          if (headerStyle != null)
            columnCell.setCellStyle(headerStyle);

          final Object[] data = ((Collection<?>) column.get(sheetObject)).stream().map(cellTransformer::transform).toArray();

          for (int i = startsAt + 1; i < startsAt + data.length + 1; i++) {
            Object value = data[i - startsAt - 1];

            if (workSheet.getRow(i) == null) {
              workSheet.createRow(i);
            }

            final Cell valueCell = workSheet.getRow(i).createCell(col);

            boolean setByCondition = false;

            if (cellConditions.length > 0) {
              try (Context ctx = Context.enter()) {
                final Global global = new Global(ctx);
                global.defineProperty(column.getName(), value, 0);
                global.defineProperty("i", i, 0);
                global.defineProperty("columns", data, 0);

                for (Map.Entry<String, CellStyle> condition : cellConditionalStyles.entrySet()) {
                  if (JavaScriptEngineManager.checkCondition(ctx, global, condition.getKey())) {
                    valueCell.setCellStyle(condition.getValue());
                    setByCondition = true;
                  }
                }
              }
            }

            if (colStyle != null && !setByCondition)
              valueCell.setCellStyle(colStyle);

            if (value instanceof String) {
              valueCell.setCellValue((String) value);
            } else if (value instanceof Number) {
              valueCell.setCellValue(((Number) value).doubleValue());
            } else if (value instanceof Date) {
              valueCell.setCellValue((Date) value);
            } else if (value instanceof Calendar) {
              valueCell.setCellValue((Calendar) value);
            } else if (value instanceof LocalDateTime) {
              valueCell.setCellValue((LocalDateTime) value);
            } else if (value instanceof LocalDate) {
              valueCell.setCellValue((LocalDate) value);
            } else if (value instanceof RichTextString) {
              valueCell.setCellValue((RichTextString) value);
            }
          }

          col++;
        }
      }
    } catch (IllegalAccessException | InstantiationException e) {
      throw new ExcelMapperException("Error obtaining column data", e);
    }
  }

  private static CellStyle getCellStyle(Workbook workbook, ExcelCellStyle cellStyle, ExcelFontStyle fontStyle) {
    final CellStyle style = workbook.createCellStyle();
    final Font font = workbook.createFont();

    // region STYLE OPTIONS

    // region CONTENT OPTIONS

    if (!cellStyle.format().isEmpty()) {
      style.setDataFormat(workbook.createDataFormat().getFormat(cellStyle.format()));
    }
    style.setIndention(cellStyle.indent());
    style.setRotation(cellStyle.rotation());
    style.setWrapText(cellStyle.wrap());
    style.setHidden(cellStyle.hidden());
    style.setLocked(cellStyle.locked());
    style.setQuotePrefixed(cellStyle.quotePrefixed());
    style.setShrinkToFit(cellStyle.fitContent());

    // endregion

    // region ALIGNMENT OPTIONS

    style.setAlignment(cellStyle.horizontalAlignment());
    style.setVerticalAlignment(cellStyle.verticalAlignment());

    // endregion

    // region CELL COLOR AND STYLE OPTIONS

    style.setFillBackgroundColor(cellStyle.bgColor().getIndex());
    style.setFillPattern(cellStyle.fillPattern());
    style.setFillForegroundColor(cellStyle.fgColor().getIndex());

    // endregion

    // region BORDER OPTIONS

    // region BORDER STYLE OPTIONS

    if (cellStyle.globalBorder()) {
      style.setBorderLeft(cellStyle.border());
      style.setBorderTop(cellStyle.border());
      style.setBorderRight(cellStyle.border());
      style.setBorderBottom(cellStyle.border());
    } else {
      style.setBorderLeft(cellStyle.borderLeft());
      style.setBorderTop(cellStyle.borderTop());
      style.setBorderRight(cellStyle.borderRight());
      style.setBorderBottom(cellStyle.borderBottom());
    }

    // endregion

    // region BORDER COLOR OPTIONS

    if (cellStyle.globalBorderColor()) {
      style.setLeftBorderColor(cellStyle.borderColor().getIndex());
      style.setTopBorderColor(cellStyle.borderColor().getIndex());
      style.setRightBorderColor(cellStyle.borderColor().getIndex());
      style.setBottomBorderColor(cellStyle.borderColor().getIndex());
    } else {
      style.setLeftBorderColor(cellStyle.borderLeftColor().getIndex());
      style.setTopBorderColor(cellStyle.borderTopColor().getIndex());
      style.setRightBorderColor(cellStyle.borderRightColor().getIndex());
      style.setBottomBorderColor(cellStyle.borderBottomColor().getIndex());
    }

    // endregion

    // endregion

    // endregion

    // region FONT OPTIONS

    if (fontStyle.height() != -1)
      font.setFontHeightInPoints(fontStyle.height());

    font.setFontName(fontStyle.fontName());
    font.setColor(fontStyle.color().getIndex());
    font.setBold(fontStyle.bold());
    font.setItalic(fontStyle.italic());
    font.setUnderline(fontStyle.underline());
    font.setStrikeout(fontStyle.strikedOut());
    font.setTypeOffset(fontStyle.typeOffset());
    font.setCharSet(fontStyle.charset().getNativeId());
    style.setFont(font);

    // endregion

    return style;
  }

}
