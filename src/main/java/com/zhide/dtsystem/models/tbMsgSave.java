package com.zhide.dtsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbMsgSave")
public class tbMsgSave implements Serializable {
    @Id
    @Column(name = "FID")
    private int fid;
    @Column(name = "UID")
    private String uid;
    @Column(name = "SendUID")
    private String sendUid;
    @Column(name = "SendName")
    private String sendName;
    @Column(name = "MsgText")
    private String msgText;
    @Column(name = "SendTime")
    private Date sendTime;
    @Column(name = "LimitDay")
    private int limitDay;
    @Column(name = "HasRead")
    private boolean hasRead;
    @Column(name = "Project")
    private int project;
    @Column(name = "HasReceive")
    private int hasReceive;

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getSendUid() {
        return sendUid;
    }

    public void setSendUid(String sendUid) {
        this.sendUid = sendUid;
    }


    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }


    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }


    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }


    public int getLimitDay() {
        return limitDay;
    }

    public void setLimitDay(int limitDay) {
        this.limitDay = limitDay;
    }


    public boolean getHasRead() {
        return hasRead;
    }

    public void setHasRead(boolean hasRead) {
        this.hasRead = hasRead;
    }


    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }


    public int getHasReceive() {
        return hasReceive;
    }

    public void setHasReceive(int hasReceive) {
        this.hasReceive = hasReceive;
    }

}
