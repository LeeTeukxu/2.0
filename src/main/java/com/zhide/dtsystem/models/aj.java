package com.zhide.dtsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "AJ")
public class aj implements Serializable {
    @Id
    @Column(name = "ANJUANBH")
    private String anjuanbh;
    @Column(name = "ANJUANH")
    private String anjuanh;
    @Column(name = "ANJUANMC")
    private String anjuanmc;
    @Column(name = "TIANXIEMS")
    private Integer tianxiems;
    @Column(name = "ANJUANLX")
    private Integer anjuanlx;
    @Column(name = "SHENQINGBH")
    private String shenqingbh;
    @Column(name = "CHUANGJIANRQ")
    private Date chuangjianrq;
    @Column(name = "ANJUANZT")
    private Integer anjuanzt;
    @Column(name = "NEIBUBH")
    private String neibubh;
    @Column(name = "CreateTime")
    private Date createTime;
    @Column(name = "UpdateTime")
    private Date updateTime;

    public String getAnjuanbh() {
        return anjuanbh;
    }

    public void setAnjuanbh(String anjuanbh) {
        this.anjuanbh = anjuanbh;
    }


    public String getAnjuanh() {
        return anjuanh;
    }

    public void setAnjuanh(String anjuanh) {
        this.anjuanh = anjuanh;
    }


    public String getAnjuanmc() {
        return anjuanmc;
    }

    public void setAnjuanmc(String anjuanmc) {
        this.anjuanmc = anjuanmc;
    }


    public Integer getTianxiems() {
        return tianxiems;
    }

    public void setTianxiems(Integer tianxiems) {
        this.tianxiems = tianxiems;
    }


    public Integer getAnjuanlx() {
        return anjuanlx;
    }

    public void setAnjuanlx(Integer anjuanlx) {
        this.anjuanlx = anjuanlx;
    }


    public String getShenqingbh() {
        return shenqingbh;
    }

    public void setShenqingbh(String shenqingbh) {
        this.shenqingbh = shenqingbh;
    }


    public Date getChuangjianrq() {
        return chuangjianrq;
    }

    public void setChuangjianrq(Date chuangjianrq) {
        this.chuangjianrq = chuangjianrq;
    }


    public Integer getAnjuanzt() {
        return anjuanzt;
    }

    public void setAnjuanzt(Integer anjuanzt) {
        this.anjuanzt = anjuanzt;
    }


    public String getNeibubh() {
        return neibubh;
    }

    public void setNeibubh(String neibubh) {
        this.neibubh = neibubh;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
