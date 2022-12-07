package com.zhide.dtsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbFinModLog")
public class tbFinModLog implements Serializable {
    @Id
    @Column(name = "FinModID")
    private int finModId;
    @Column(name = "FeaturesID")
    private int featuresId;
    @Column(name = "PID")
    private int pid;
    @Column(name = "OUserID")
    private int oUserId;
    @Column(name = "Content")
    private String content;
    @Column(name = "OperationTime")
    private Date operationTime;

    public int getFinModId() {
        return finModId;
    }

    public void setFinModId(int finModId) {
        this.finModId = finModId;
    }


    public int getFeaturesId() {
        return featuresId;
    }

    public void setFeaturesId(int featuresId) {
        this.featuresId = featuresId;
    }


    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }


    public int getOUserId() {
        return oUserId;
    }

    public void setOUserId(int oUserId) {
        this.oUserId = oUserId;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

}
