package com.zhide.dtsystem.common;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Date;

public class pageObject implements Serializable {
    Integer total;
    Object data;
    Logger logger;
    Boolean success;
    String errorMsg;
    Object sum;
    String className;
    String methodName;

    public pageObject() {
        logger = LoggerFactory.getLogger(pageObject.class);
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void setTotal(long total) {
        this.total = Integer.parseInt(Long.toString(total));
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getSum() {
        return sum;
    }

    public void setSum(Object sum) {
        this.sum = sum;
    }

    public void raiseException(Exception ax) {
        success = false;
        errorMsg = ax.getMessage();
        if (StringUtils.isEmpty(errorMsg)) errorMsg = ExceptionUtils.getStrackTrace(ax);
        logger.info(errorMsg);
    }
}
