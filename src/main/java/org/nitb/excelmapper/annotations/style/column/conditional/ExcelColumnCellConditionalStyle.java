package org.nitb.excelmapper.annotations.style.column.conditional;

import org.nitb.excelmapper.annotations.style.column.ExcelColumnStyle;

import java.lang.annotation.*;

/**
 * Special cell style applied to all cells that meet a condition. Condition is specified by
 * org.mozilla.rhino-based JavaScript code, which is executed by each cell to check condition. Depending on the
 * complexity of the condition, generation may take longer than expected.
 *
 * In JavaScript code, user has some variables to use inside code:
 *
 * <ul>
 *   <li><strong>cell:</strong> Value of current cell, used to check condition</li>
 *   <li><strong>column:</strong> Rest of cell values, used to compare current value with rest of cells (if needed)</li>
 *   <li><strong>i:</strong> Current cell position in column</li>
 * </ul>
 *
 * JavaScript's expression must return a boolean result. If result is true for a cell, style is applied to it.
 *
 * To learn about Rhino:
 * @link https://github.com/mozilla/rhino
 */
@Repeatable(ExcelColumnCellConditionalStyles.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelColumnCellConditionalStyle {

  /**
   * Style applied to cell if checks condition
   */
  ExcelColumnStyle style() default @ExcelColumnStyle;

  /**
   * JavaScript's expression used to check condition
   */
  String condition();
}
