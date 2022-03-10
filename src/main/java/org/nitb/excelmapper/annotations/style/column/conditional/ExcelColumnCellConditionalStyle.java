package org.nitb.excelmapper.annotations.style.column.conditional;

import org.nitb.excelmapper.annotations.style.column.ExcelColumnStyle;

import java.lang.annotation.*;

@Repeatable(ExcelColumnCellConditionalStyles.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelColumnCellConditionalStyle {

  ExcelColumnStyle style() default @ExcelColumnStyle;

  String condition();
}
