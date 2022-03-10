package org.nitb.excelmapper.annotations.style;

import org.apache.poi.ss.usermodel.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines some properties used to make a CellStyle object used in a header or column cell
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD })
public @interface ExcelCellStyle {

  FillPatternType fillPattern() default FillPatternType.NO_FILL;

  IndexedColors fgColor() default IndexedColors.AUTOMATIC;

  IndexedColors bgColor() default IndexedColors.AUTOMATIC;

  HorizontalAlignment horizontalAlignment() default HorizontalAlignment.LEFT;

  VerticalAlignment verticalAlignment() default VerticalAlignment.BOTTOM;

  BorderStyle border() default BorderStyle.NONE;

  boolean globalBorder() default true;

  BorderStyle borderTop() default BorderStyle.NONE;

  BorderStyle borderBottom() default BorderStyle.NONE;

  BorderStyle borderLeft() default BorderStyle.NONE;

  BorderStyle borderRight() default BorderStyle.NONE;

  IndexedColors borderColor() default IndexedColors.AUTOMATIC;

  boolean globalBorderColor() default true;

  IndexedColors borderTopColor() default IndexedColors.AUTOMATIC;

  IndexedColors borderBottomColor() default IndexedColors.AUTOMATIC;

  IndexedColors borderLeftColor() default IndexedColors.AUTOMATIC;

  IndexedColors borderRightColor() default IndexedColors.AUTOMATIC;

  String format() default "";

  short indent() default 0;

  short rotation() default 0;

  boolean wrap() default false;

  boolean quotePrefixed() default false;

  boolean hidden() default false;

  boolean locked() default false;

  boolean fitContent() default false;

}
