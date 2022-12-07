package com.zhide.dtsystem.models;


import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: ClientInfo
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年03月25日 10:25
 **/
public class ClientInfo implements Serializable {
    int clientID;
    String name;
    String companyName;
    String companyID;
    String orgCode;
    Boolean canUse;
    String passWord;
    String type;
    String tSource;
    Date createTime;

    public String gettSource() {
        return tSource;
    }

    public void settSource(String tSource) {
        this.tSource = tSource;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Boolean getCanUse() {
        if(canUse==null)canUse=true;
        return canUse;
    }

    public void setCanUse(Boolean canUse) {
        this.canUse = canUse;
    }
}
