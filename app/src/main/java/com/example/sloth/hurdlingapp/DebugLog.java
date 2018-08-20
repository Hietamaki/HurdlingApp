package com.example.sloth.hurdlingapp;

import android.util.Log;
public class DebugLog {
    public final static boolean DEBUG = true;
    public static void log(String message,  StackTraceElement stackTrace) {
        if (DEBUG) {
            String fullClassName = stackTrace.getClassName();
            String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
            String methodName = stackTrace.getMethodName();
            int lineNumber = stackTrace.getLineNumber();

            Log.d(className + "." + methodName + "():" + lineNumber, message);
        }
    }
}