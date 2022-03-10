package org.nitb.excelmapper.exceptions;

public class ExcelMapperException extends Throwable {

  public ExcelMapperException(String msg) {
    super(msg);
  }

  public ExcelMapperException(String msg, Throwable e) {
    super(msg, e);
  }
}
