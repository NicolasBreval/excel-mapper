package org.nitb.excelmapper.annotations.style;

import org.apache.poi.common.usermodel.fonts.FontCharset;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD })
public @interface ExcelFontStyle {

  short height() default -1;

  String fontName() default "Arial";

  IndexedColors color() default IndexedColors.AUTOMATIC;

  boolean bold() default false;

  boolean italic() default false;

  byte underline() default 0;

  boolean strikedOut() default false;

  short typeOffset() default 0;

  FontCharset charset() default FontCharset.DEFAULT;
}
