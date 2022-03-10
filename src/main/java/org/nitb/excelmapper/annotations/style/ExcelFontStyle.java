package org.nitb.excelmapper.annotations.style;

import org.apache.poi.common.usermodel.fonts.FontCharset;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines font style of a cell
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD })
public @interface ExcelFontStyle {

  /**
   * Height of font, default value is -1, which indicates mapper will use the default value used by apache poi library
   */
  short height() default -1;

  /**
   * Name of font used in cell, default value 'Arial'
   */
  String fontName() default "Arial";

  /**
   * Font color, default value {@link IndexedColors#AUTOMATIC} (not set)
   */
  IndexedColors color() default IndexedColors.AUTOMATIC;

  /**
   * If is true, applies bold style to text
   */
  boolean bold() default false;

  /**
   * If is true, applies italic style to text
   */
  boolean italic() default false;

  /**
   * Used to specify underline of text. Allowable values:
   * <ul>
   *   <li>{@link org.apache.poi.ss.usermodel.Font#U_NONE}</li>
   *   <li>{@link org.apache.poi.ss.usermodel.Font#U_SINGLE}</li>
   *   <li>{@link org.apache.poi.ss.usermodel.Font#U_DOUBLE}</li>
   *   <li>{@link org.apache.poi.ss.usermodel.Font#U_SINGLE_ACCOUNTING}</li>
   *   <li>{@link org.apache.poi.ss.usermodel.Font#U_DOUBLE_ACCOUNTING}</li>
   * </ul>
   */
  byte underline() default 0;

  /**
   * If is true, cell text is struck
   */
  boolean strikedOut() default false;

  /**
   * Sets subscript ({@link org.apache.poi.ss.usermodel.Font#SS_SUB}),
   * superscript ({@link org.apache.poi.ss.usermodel.Font#SS_SUPER}),
   * or normal text ({@link org.apache.poi.ss.usermodel.Font#SS_NONE})
   */
  short typeOffset() default 0;

  /**
   * Allows changing font charset in cell. Allowable values in enum class {@link FontCharset}
   */
  FontCharset charset() default FontCharset.DEFAULT;
}
