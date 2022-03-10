package org.nitb.excelmapper.operative.transformation;

/**
 * Basic transformer used when user doesn't define any transformer. This transformer doesn't map original value
 */
public class ExcelColumnDummyTransformer extends ExcelColumnTransformer<Object, Object> {

  @Override
  public Object transform(Object input) {
    return input;
  }
}
