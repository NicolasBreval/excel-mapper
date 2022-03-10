package org.nitb.excelmapper.annotations.basic;

import org.nitb.excelmapper.operative.transformation.ExcelColumnDummyTransformer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a column inside a sheet
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelBasicColumn {

  /**
   * Name of column in sheet. If this property is empty, default name is COLUMN %d, being %d the number of column
   */
  String name() default "";

  /**
   * If is true, the name of column is the name of class which represents the column
   */
  boolean useClassname() default false;

  /**
   * Specifies position where column starts to put their values
   */
  int startsAt() default 0;

  /**
   * Custom class to transform column class values to another
   */
  Class<?> transformer() default ExcelColumnDummyTransformer.class;
}
