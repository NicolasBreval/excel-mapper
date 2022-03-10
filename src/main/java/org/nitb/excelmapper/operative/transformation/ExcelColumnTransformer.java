package org.nitb.excelmapper.operative.transformation;

/**
 * Defines a transformer object to map value of cells in a column
 * @param <I> Type of original cell value
 * @param <O> Type of mapped value
 */
public abstract class ExcelColumnTransformer<I, O> {

  /**
   * Method to be overwritten by used which defines how to map cell value
   * @param input Original cell value
   * @return Mapped value from original cell value
   */
  public abstract O transform(I input);

}
