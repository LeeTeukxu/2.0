package com.zhide.dtsystem.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * String className = Thread.currentThread().getStackTrace()[2].getClassName();//调用的类名
 * String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();//调用的方法名
 * int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();//调用的行数
 */
public class successResult implements Serializable {
    Logger logger = LoggerFactory.getLogger(successResult.class);
    private String className;
    private String methodName;
    private String controllerName;
    Date begin;
    Date end;
    boolean showLog=true;

    public successResult() {
        success = true;
        try {
            className = Thread.currentThread().getStackTrace()[2].getClassName();//调用的类名
            logger = LoggerFactory.getLogger(Class.forName(className));
            methodName = Thread.currentThread().getStackTrace()[2].getMethodName();//调用的方法名
            String[] cs=className.split("\\.");
            controllerName=cs[cs.length-1];
            begin = new Date();
            logger.info("----[" + methodName + "]开始执行----");
        } catch (Exception ax) {
            logger.info("success在获取当前方法和当前类名时发生了错误:" + ax.getMessage());
        }
    }
    public successResult(boolean showLog){
        this.showLog=showLog;
        success = true;
        try {
            className = Thread.currentThread().getStackTrace()[2].getClassName();//调用的类名
            logger = LoggerFactory.getLogger(Class.forName(className));
            methodName = Thread.currentThread().getStackTrace()[2].getMethodName();//调用的方法名
            String[] cs=className.split("\\.");
            controllerName=cs[cs.length-1];
            begin = new Date();
            //logger.info("----[" + methodName + "]开始执行----");
        } catch (Exception ax) {
            //logger.info("success在获取当前方法和当前类名时发生了错误:" + ax.getMessage());
        }
    }

    private String message;
    private Object data;
    private boolean success;

    public void raiseException(Exception ax) {
        setMessage(ax.getMessage());
        setSuccess(false);
        String message = ax.getMessage();
        List<String> sb=new ArrayList<>();
        sb.add("\r\n");
        sb.add("X------------------------------------------------X");
        sb.add("控制器:"+controllerName+"中的"+methodName + "方法执行出现异常:");
        sb.add("【"+message+"】");
        StackTraceElement[] elements= ax.getStackTrace();
        for(int i=0;i< elements.length;i++){
            StackTraceElement t=elements[i];
            if(t.getClassName().startsWith("com.zhide") && t.getLineNumber()>=1){
                String f="file:%s,type:%s,method:%s,line:%d";
                sb.add(String.format(f,t.getFileName(),t.getClassName(),t.getMethodName(),t.getLineNumber()));
            }
        }
        sb.add("X------------------------------------------------X");
        logger.error(String.join("\r\n",sb));
    }

    public String getMessage() {
        end = new Date();
        if(showLog==true) {
            logger.info("累计用时:" + Long.toString(end.getTime() - begin.getTime()) + "毫秒。");
            logger.info("[" + methodName + "]执行结束");
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
