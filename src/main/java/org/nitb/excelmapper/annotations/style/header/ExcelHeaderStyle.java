package org.nitb.excelmapper.annotations.style.header;

import org.nitb.excelmapper.annotations.style.ExcelCellStyle;
import org.nitb.excelmapper.annotations.style.ExcelFontStyle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines cell and font styles applied to first cell of column, treated as header
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelHeaderStyle {

  /**
   * Defines font to apply to cell
   * @see ExcelFontStyle
   */
  ExcelFontStyle font() default @ExcelFontStyle;

  /**
   * Defines style (color, alignment, position, indentation, ...) to apply to cell
   * @see ExcelCellStyle
   */
  ExcelCellStyle style() default @ExcelCellStyle;
}
