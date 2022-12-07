package com.zhide.dtsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbLinkersUpdateRecord")
public class tbLinkersUpdateRecord implements Serializable {
    @Id
    @Column(name = "UpID")
    private String upID;
    @Column(name = "ClientID")
    private int clientID;
    @Column(name = "ActionType")
    private String actionType;
    @Column(name = "ActionTime")
    private Date actionTime;
    @Column(name = "ActionMan")
    private int actionMan;
    @Column(name = "BObj")
    private String bObj;
    @Column(name = "AObj")
    private String aObj;

    public String getUpID() {
        return upID;
    }

    public void setUpID(String upId) {
        this.upID = upId;
    }


    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }


    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }


    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }


    public int getActionMan() {
        return actionMan;
    }

    public void setActionMan(int actionMan) {
        this.actionMan = actionMan;
    }


    public String getBObj() {
        return bObj;
    }

    public void setBObj(String bObj) {
        this.bObj = bObj;
    }


    public String getAObj() {
        return aObj;
    }

    public void setAObj(String aObj) {
        this.aObj = aObj;
    }

}
