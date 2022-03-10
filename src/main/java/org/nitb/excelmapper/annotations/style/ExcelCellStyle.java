package org.nitb.excelmapper.annotations.style;

import org.apache.poi.ss.usermodel.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines some properties used to change appareance of a cell in xlsx file
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD })
public @interface ExcelCellStyle {

  /**
   * Fill pattern used to colour cell. Available values in {@link FillPatternType}
   */
  FillPatternType fillPattern() default FillPatternType.NO_FILL;

  /**
   * Foreground color of cell. Available values in {@link IndexedColors}
   */
  IndexedColors fgColor() default IndexedColors.AUTOMATIC;

  /**
   * Background color of cell. Available values in {@link IndexedColors}
   */
  IndexedColors bgColor() default IndexedColors.AUTOMATIC;

  /**
   * Defines horizontal alignment of cell. Available values in {@link HorizontalAlignment}
   */
  HorizontalAlignment horizontalAlignment() default HorizontalAlignment.LEFT;

  /**
   * Defines vertical alignment of cell. Available values in {@link VerticalAlignment}
   */
  VerticalAlignment verticalAlignment() default VerticalAlignment.BOTTOM;

  /**
   * Defines style of cell border, only if {@link #globalBorder()} is set to true. Available values in {@link BorderStyle}
   */
  BorderStyle border() default BorderStyle.NONE;

  /**
   * If is true, {@link #border()} property applies to all borders of cell
   */
  boolean globalBorder() default true;

  /**
   * Defines style of top cell border, only if {@link #globalBorder()} is set to false
   */
  BorderStyle borderTop() default BorderStyle.NONE;

  /**
   * Defines style of bottom cell border, only if {@link #globalBorder()} is set to false
   */
  BorderStyle borderBottom() default BorderStyle.NONE;

  /**
   * Defines style of left cell border, only if {@link #globalBorder()} is set to false
   */
  BorderStyle borderLeft() default BorderStyle.NONE;

  /**
   * Defines style of right cell border, only if {@link #globalBorder()} is set to false
   */
  BorderStyle borderRight() default BorderStyle.NONE;

  /**
   * Defines color of all borders in cell, only if {@link #globalBorderColor()} is set to true. Available values in {@link IndexedColors}
   */
  IndexedColors borderColor() default IndexedColors.AUTOMATIC;

  /**
   * If is true, {@link #borderColor()} applies to all borders in cell
   */
  boolean globalBorderColor() default true;

  /**
   * Defines color of top border in cell, only if {@link #borderColor()} is set to false
   */
  IndexedColors borderTopColor() default IndexedColors.AUTOMATIC;

  /**
   * Defines color of bottom border in cell, only if {@link #borderColor()} is set to false
   */
  IndexedColors borderBottomColor() default IndexedColors.AUTOMATIC;

  /**
   * Defines color of left border in cell, only if {@link #borderColor()} is set to false
   */
  IndexedColors borderLeftColor() default IndexedColors.AUTOMATIC;

  /**
   * Defines color of right border in cell, only if {@link #borderColor()} is set to false
   */
  IndexedColors borderRightColor() default IndexedColors.AUTOMATIC;

  /**
   * Format used to represent data in cell. Available values in {@link BuiltinFormats}
   */
  String format() default "";

  /**
   * Indentation (number of spaces at start of text) applied to text
   */
  short indent() default 0;

  /**
   * Rotation applied to text. Available values: from 0 to 180
   */
  short rotation() default 0;

  /**
   * If is true, if text exceeds cell width, words are placed in another lines below
   */
  boolean wrap() default false;

  /**
   * Uses to turn on (if is true) quote prefix, used in xlsx files to mark cell values similar to number or formulas
   * which should not be treated as such
   */
  boolean quotePrefixed() default false;

  /**
   * If is true, cell value is hidden
   */
  boolean hidden() default false;

  /**
   * If is true, cell cannot be modified by user
   */
  boolean locked() default false;

  /**
   * If is true, cell text font size is modified to fit inside cell
   */
  boolean fitContent() default false;

}
