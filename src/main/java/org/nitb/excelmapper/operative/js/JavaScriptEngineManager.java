package org.nitb.excelmapper.operative.js;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class JavaScriptEngineManager {

  public static boolean checkCondition(Context context, Scriptable scope, String condition) {
    try {
      Object object = context.evaluateString(scope, condition, "sourceName", 1, null);
      return object instanceof Boolean ? (Boolean) object : false;
    } catch (Exception e) {
      return false;
    }
  }
}
