package com.zhide.dtsystem.viewModel;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: MethodWatchInfo
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年07月09日 10:09
 **/
public class MethodWatchInfo implements Serializable {
    String methodName;
    String methodFunction;
    String argsValue;
    String userName;
    String roleName;
    Date  curTime;
    String companyName;
    String companyID;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodFunction() {
        return methodFunction;
    }

    public void setMethodFunction(String methodFunction) {
        this.methodFunction = methodFunction;
    }

    public String getArgsValue() {
        return argsValue;
    }

    public void setArgsValue(String argsValue) {
        this.argsValue = argsValue;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getCurTime() {
        return curTime;
    }

    public void setCurTime(Date curTime) {
        this.curTime = curTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }
}
