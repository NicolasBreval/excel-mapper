package org.nitb.excelmapper.annotations.basic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a sheet inside a xlsx file
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExcelBasicSheet {

  /**
   * Name of sheet. If this property is empty, mapper puts a generic name like Sheet %d, being %d the number
   * of not-named sheets at moment
   */
  String name() default "";

  /**
   * If this property is true, mapper puts class name to sheet
   */
  boolean useClassname() default false;

}
