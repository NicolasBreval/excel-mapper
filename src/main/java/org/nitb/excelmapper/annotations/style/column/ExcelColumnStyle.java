package org.nitb.excelmapper.annotations.style.column;

import org.nitb.excelmapper.annotations.style.ExcelCellStyle;
import org.nitb.excelmapper.annotations.style.ExcelFontStyle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelColumnStyle {
  ExcelFontStyle font() default @ExcelFontStyle;

  ExcelCellStyle style() default @ExcelCellStyle;
}
