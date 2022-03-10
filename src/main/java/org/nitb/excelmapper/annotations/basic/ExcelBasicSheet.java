package org.nitb.excelmapper.annotations.basic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExcelBasicSheet {

  String name() default "Sheet1";

  boolean useClassname() default false;

}
