package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "YearWatchConfig")
public class yearWatchConfig implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "ShenQingh")
    private String shenQingh;
    @Column(name = "FeeType")
    private Integer feeType;
    @Column(name = "FeePercent")
    private Double feePercent;
    @Column(name = "BeginTimes")
    private Integer beginTimes;
    @Column(name = "BeginJiaoFei")
    private Integer beginJiaoFei;
    @Column(name = "FaWenDate")
    private Date faWenDate;
    @Column(name = "CreateTime")
    private Date createTime;
    @Column(name = "CreateMan")
    private Integer createMan;
    @Column(name = "UpdateTime")
    private Date updateTime;
    @Column(name = "UpdateMan")
    private Integer updateMan;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getShenQingh() {
        return shenQingh;
    }

    public void setShenQingh(String shenQingh) {
        this.shenQingh = shenQingh;
    }


    public Integer getFeeType() {
        return feeType;
    }

    public void setFeeType(Integer feeType) {
        this.feeType = feeType;
    }


    public Double getFeePercent() {
        return feePercent;
    }

    public void setFeePercent(Double feePercent) {
        this.feePercent = feePercent;
    }


    public Integer getBeginTimes() {
        return beginTimes;
    }

    public void setBeginTimes(Integer beginTimes) {
        this.beginTimes = beginTimes;
    }


    public Integer getBeginJiaoFei() {
        return beginJiaoFei;
    }

    public void setBeginJiaoFei(Integer beginJiaoFei) {
        this.beginJiaoFei = beginJiaoFei;
    }


    public Date getFaWenDate() {
        return faWenDate;
    }

    public void setFaWenDate(Date faWenDate) {
        this.faWenDate = faWenDate;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public Integer getCreateMan() {
        return createMan;
    }

    public void setCreateMan(Integer createMan) {
        this.createMan = createMan;
    }


    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    public Integer getUpdateMan() {
        return updateMan;
    }

    public void setUpdateMan(Integer updateMan) {
        this.updateMan = updateMan;
    }

}
