package com.zhide.dtsystem.viewModel;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: ProcessInfo
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年11月10日 11:07
 **/
public class ProcessInfo implements Serializable {
    Long usedTime;
    String url;
    String args;
    String userName;
    String companyName;
    String companyId;
    Date curTime;

    public Long getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Long usedTime) {
        this.usedTime = usedTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
