package org.nitb.excelmapper.annotations.style.column;

import org.nitb.excelmapper.annotations.style.ExcelCellStyle;
import org.nitb.excelmapper.annotations.style.ExcelFontStyle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines cell style applied to all cells in a column
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelColumnStyle {

  /**
   * Defines font to apply to each cell in column
   * @see ExcelFontStyle
   */
  ExcelFontStyle font() default @ExcelFontStyle;

  /**
   * Defines style (color, alignment, position, indentation, ...) to apply to each cell in column
   * @see ExcelCellStyle
   */
  ExcelCellStyle style() default @ExcelCellStyle;
}
