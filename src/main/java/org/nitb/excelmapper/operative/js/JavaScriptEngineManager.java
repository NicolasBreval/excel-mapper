package org.nitb.excelmapper.operative.js;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

/**
 * Util class used to run JavaScript code and get their return value as boolean
 */
public class JavaScriptEngineManager {

  /**
   * Evaluates {@code condition} string as JavaScript code and returns their returned value as a boolean. If condition
   * produces an exception or returned value cannot be transformed to boolean, returns false
   * @param context Rhino Context to run code
   * @param scope Scope object used to define variables and some properties of execution
   * @param condition JavaScript's expression used to check cell value
   * @return Boolean value of evaluated JavaScript code, or false if evaluation fails
   */
  public static boolean checkCondition(Context context, Scriptable scope, String condition) {
    try {
      Object object = context.evaluateString(scope, condition, "sourceName", 1, null);
      return object instanceof Boolean ? (Boolean) object : false;
    } catch (Exception e) {
      return false;
    }
  }
}
