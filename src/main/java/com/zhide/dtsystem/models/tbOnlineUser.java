package com.zhide.dtsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbOnlineUser")
public class tbOnlineUser implements Serializable {
    @Id
    @Column(name = "UID")
    private int uid;
    @Column(name = "LoginTime")
    private Date loginTime;
    @Column(name = "RefTime")
    private Date refTime;
    @Column(name = "IPAddress")
    private String ipAddress;
    @Column(name = "Project")
    private int project;
    @Column(name = "ClearCache")
    private boolean clearCache;
    @Column(name = "Status")
    private String status;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }


    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }


    public Date getRefTime() {
        return refTime;
    }

    public void setRefTime(Date refTime) {
        this.refTime = refTime;
    }


    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }


    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }


    public boolean getClearCache() {
        return clearCache;
    }

    public void setClearCache(boolean clearCache) {
        this.clearCache = clearCache;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
