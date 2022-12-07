package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "VersionUpdate")
public class versionUpdate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "VerID")
    private Integer verId;
    @Column(name = "CanUse")
    private Boolean canUse;
    @Column(name = "SqlText")
    private String sqlText;
    @Column(name = "CreateTime")
    private Date createTime;
    @Column(name = "ExceptCompany")
    private String exceptCompany;
    @Column(name = "HasProcess")
    private Boolean hasProcess;
    @Column(name = "ProcessTime")
    private Date processTime;
    @Column(name = "Memo")
    private String memo;
    @Column(name = "NeedLogin")
    private Boolean needLogin;
    @Column(name = "Account")
    private String account;
    @Column(name = "Result")
    private String result;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getVerId() {
        return verId;
    }

    public void setVerId(Integer verId) {
        this.verId = verId;
    }


    public Boolean getCanUse() {
        return canUse;
    }

    public void setCanUse(Boolean canUse) {
        this.canUse = canUse;
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


    public String getExceptCompany() {
        return exceptCompany;
    }

    public void setExceptCompany(String exceptCompany) {
        this.exceptCompany = exceptCompany;
    }


    public Boolean getHasProcess() {
        return hasProcess;
    }

    public void setHasProcess(Boolean hasProcess) {
        this.hasProcess = hasProcess;
    }


    public Date getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Date processTime) {
        this.processTime = processTime;
    }


    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


    public Boolean getNeedLogin() {
        return needLogin;
    }

    public void setNeedLogin(Boolean needLogin) {
        this.needLogin = needLogin;
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
