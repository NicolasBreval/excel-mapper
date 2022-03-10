package org.nitb.excelmapper.annotations;

import org.nitb.excelmapper.annotations.basic.ExcelBasicSheet;
import org.nitb.excelmapper.enums.ExcelDocumentType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a class as an Excel document
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExcelDocument {

  /**
   * Defines type of document.
   *
   * If is BASIC_MAPPING, mapper transforms annotated variables with @ExcelBasicSheet annotation
   * to sheets automatically. It's important for BASIC_MAPPING that properties annotated with
   * @ExcelBasicSheet that they are collections.
   *
   * In other hand, if is COMPLEX_MAPPING, each class contains some annotations to allocate data inside sheets.
   *
   * @see ExcelBasicSheet
   */
  ExcelDocumentType type() default ExcelDocumentType.BASIC_MAPPING;

  /**
   * Defines name of file to produce. This annotation is only important when you use mapper for transform objects into files, else
   * is not needed to override the value
   */
  String name() default "ExcelMappingOutput.xlsx";

  boolean useClassname() default false;
}
