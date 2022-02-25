package com.caf.yeb.common.exception;


import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;

public class LogUtils {

    private static final String SEPARATOR = "\r\n";

    public static String printTop8StackTrace(Throwable e) {
        return printStackTrace(e, 8);
    }

    public static String printStackTrace(Throwable e, int maxLineCount) {
        StringBuilder sb = new StringBuilder(maxLineCount * 5);
        sb.append(e.toString());
        sb.append(SEPARATOR);
        StackTraceElement[] trace = e.getStackTrace();
        if (trace == null) {
            return e.toString();
        }
        int count = Math.min(maxLineCount, trace.length);
        for (int i = 0; i < count; i++) {
            sb.append("\tat ").append(trace[i]).append(SEPARATOR);
        }
        // Print suppressed exceptions, if any
        Throwable[] suppressedExceptions = e.getSuppressed();
        if (ArrayUtils.isNotEmpty(suppressedExceptions)) {
            sb.append("\tSuppressed: ");
            for (Throwable suppressedException : suppressedExceptions) {
                sb.append(printStackTrace(suppressedException, maxLineCount));
            }
        }
        // Print cause, if any
        Throwable cause = e.getCause();
        if (cause != null) {
            sb.append("Caused by: ");
            sb.append(printStackTrace(cause, maxLineCount));
        }
        return sb.toString();
    }

}
