package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbResignationRecord")
public class tbResignationRecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ResignationRecordID")
    public Integer resignationRecordId;

    @Column(name = "Resignation")
    public int resignation;

    @Column(name = "Transfer")
    public int transfer;

    @Column(name = "ResignationLeaving")
    public String resignationLeaving;

    @Column(name = "ResignationTime")
    public Date resignationTime;

    @Column(name = "CreateMan")
    public int createMan;

    @Column(name = "CreateTime")
    public Date createTime;

    public Integer getResignationRecordId() {
        return resignationRecordId;
    }

    public void setResignationRecordId(Integer resignationRecordId) {
        this.resignationRecordId = resignationRecordId;
    }

    public int getResignation() {
        return resignation;
    }

    public void setResignation(int resignation) {
        this.resignation = resignation;
    }

    public int getTransfer() {
        return transfer;
    }

    public void setTransfer(int transfer) {
        this.transfer = transfer;
    }

    public String getResignationLeaving() {
        return resignationLeaving;
    }

    public void setResignationLeaving(String resignationLeaving) {
        this.resignationLeaving = resignationLeaving;
    }

    public Date getResignationTime() {
        return resignationTime;
    }

    public void setResignationTime(Date resignationTime) {
        this.resignationTime = resignationTime;
    }

    public int getCreateMan() {
        return createMan;
    }

    public void setCreateMan(int createMan) {
        this.createMan = createMan;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
