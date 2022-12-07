package com.zhide.dtsystem.common;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils {
    public static String getStrackTrace(Exception ax) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ax.printStackTrace(pw);
        return sw.toString();
    }
}
