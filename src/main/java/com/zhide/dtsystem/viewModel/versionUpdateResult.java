package com.zhide.dtsystem.viewModel;

import java.util.Date;

public class versionUpdateResult {

    private Integer verId;
    private Boolean ProcessResult;
    private String sqlText;
    private Date createTime;
    private Boolean needLogin;
    private String account;

    public Boolean getNeedLogin() {
        return needLogin;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setNeedLogin(Boolean needLogin) {
        this.needLogin = needLogin;
    }

    public Integer getVerId() {
        return verId;
    }

    public void setVerId(Integer verId) {
        this.verId = verId;
    }

    public Boolean getProcessResult() {
        return ProcessResult;
    }

    public void setProcessResult(Boolean processResult) {
        ProcessResult = processResult;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
