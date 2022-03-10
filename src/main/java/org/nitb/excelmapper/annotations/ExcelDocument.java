package org.nitb.excelmapper.annotations;

import org.nitb.excelmapper.enums.ExcelDocumentType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a class as an object serializable to a xlsx file
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExcelDocument {

  /**
   * Used to specify mapper how to serialize object.
   *
   * If type is BASIC_MAPPING, all Collection-based object with annotation {@link org.nitb.excelmapper.annotations.basic.ExcelBasicSheet}
   * are recognised as sheets and their properties are recognised as sheet columns
   */
  ExcelDocumentType type() default ExcelDocumentType.BASIC_MAPPING;

  /**
   * Default file name if user doesn't specify one. If this property is an empty String, mapper puts a generic name
   * like ExcelMappingObject_%d.xlsx, being %d the creation timestamp
   */
  String name() default "";

  /**
   * If this property is true, filename will be the name of serialized class
   */
  boolean useClassname() default false;
}
