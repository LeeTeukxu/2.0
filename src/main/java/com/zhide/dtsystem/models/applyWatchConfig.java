package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ApplyWatchConfig")
public class applyWatchConfig implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "shenqingh")
    private String shenqingh;
    @Column(name = "FeeType")
    private int feeType;
    @Column(name = "FeePercent")
    private double feePercent;
    @Column(name = "ApplyDate")
    private Date applyDate;
    @Column(name = "CreateTime")
    private Date createTime;
    @Column(name = "CreateMan")
    private int createMan;
    @Column(name = "UpdateMan")
    private int updateMan;
    @Column(name = "UpdateTime")
    private Date updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getShenqingh() {
        return shenqingh;
    }

    public void setShenqingh(String shenqingh) {
        this.shenqingh = shenqingh;
    }


    public int getFeeType() {
        return feeType;
    }

    public void setFeeType(int feeType) {
        this.feeType = feeType;
    }


    public double getFeePercent() {
        return feePercent;
    }

    public void setFeePercent(double feePercent) {
        this.feePercent = feePercent;
    }


    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public int getCreateMan() {
        return createMan;
    }

    public void setCreateMan(int createMan) {
        this.createMan = createMan;
    }


    public int getUpdateMan() {
        return updateMan;
    }

    public void setUpdateMan(int updateMan) {
        this.updateMan = updateMan;
    }


    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
