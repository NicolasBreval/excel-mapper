package org.nitb.excelmapper.enums;

/**
 * Defines type of document mapping
 */
public enum ExcelDocumentType {
  /**
   * In BASIC_MAPPING, a class is defined as a xlsx workbook, each property on it is recognised as a valid worksheet
   * if is a child of Collection class and is annotated with {@link org.nitb.excelmapper.annotations.basic.ExcelBasicSheet}.
   * At the same time, sheet class' properties are recognised as columns only if are annotated with
   * {@link org.nitb.excelmapper.annotations.basic.ExcelBasicColumn}
   */
  BASIC_MAPPING,

  /**
   * TODO: COMPLEX_MAPPING is not implemented yet
   */
  COMPLEX_MAPPING;
}
