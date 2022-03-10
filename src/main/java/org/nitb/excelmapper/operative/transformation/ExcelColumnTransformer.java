package org.nitb.excelmapper.operative.transformation;

public abstract class ExcelColumnTransformer<I, O> {

  public abstract O transform(I input);

}
