package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbTradeCaseFollowRecord")
public class tbTradeCaseFollowRecord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TradeCaseFollowRecordID")
    public Integer tradeCaseFollowRecordId;

    @Column(name = "Record")
    public String record;

    @Column(name = "CreateMan")
    public Integer createMan;

    @Column(name = "CreateTime")
    public Date createTime;

    @Column(name = "TradeCaseID")
    public Integer tradeCaseId;

    public Integer getTradeCaseFollowRecordId() {
        return tradeCaseFollowRecordId;
    }

    public void setTradeCaseFollowRecordId(Integer tradeCaseFollowRecordId) {
        this.tradeCaseFollowRecordId = tradeCaseFollowRecordId;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public Integer getCreateMan() {
        return createMan;
    }

    public void setCreateMan(Integer createMan) {
        this.createMan = createMan;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getTradeCaseId() {
        return tradeCaseId;
    }

    public void setTradeCaseId(Integer tradeCaseId) {
        this.tradeCaseId = tradeCaseId;
    }
}
