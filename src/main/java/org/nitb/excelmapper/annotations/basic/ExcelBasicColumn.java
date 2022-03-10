package org.nitb.excelmapper.annotations.basic;

import org.nitb.excelmapper.operative.transformation.ExcelColumnDummyTransformer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelBasicColumn {
  String name() default "";

  boolean useClassname() default false;

  int startsAt() default 0;

  Class<?> transformer() default ExcelColumnDummyTransformer.class;
}
