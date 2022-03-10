package org.nitb.excelmapper.exceptions;

/**
 * Exception used to throw some errors during xlsx file creation
 */
public class ExcelMapperException extends Throwable {

  public ExcelMapperException(String msg) {
    super(msg);
  }

  public ExcelMapperException(String msg, Throwable e) {
    super(msg, e);
  }
}
