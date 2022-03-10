package org.nitb.excelmapper.annotations.style.column.conditional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to allow put multiple annotations of type {@link ExcelColumnCellConditionalStyle} in same class
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelColumnCellConditionalStyles {
  ExcelColumnCellConditionalStyle[] value();
}
