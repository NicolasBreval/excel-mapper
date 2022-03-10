package org.nitb.excelmapper.operative.transformation;

public class ExcelColumnDummyTransformer extends ExcelColumnTransformer<Object, Object> {

  @Override
  public Object transform(Object input) {
    return input;
  }
}
