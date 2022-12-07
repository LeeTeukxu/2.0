package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "TZS")
public class TZS implements Serializable {
    @Id
    @Column(name = "TONGZHISBH")
    private String tongzhisbh;
    @Column(name = "TONGZHISDM")
    private String tongzhisdm;
    @Column(name = "FAMINGMC")
    private String famingmc;
    @Column(name = "FAWENXLH")
    private String fawenxlh;
    @Column(name = "TONGZHISMC")
    private String tongzhismc;
    @Column(name = "SHENQINGBH")
    private String shenqingbh;
    @Column(name = "FAWENRQ")
    private Date fawenrq;
    @Column(name = "DAFURQ")
    private Date dafurq;
    @Column(name = "DATEFROM")
    private int datefrom;
    @Column(name = "ZHUANGTAI")
    private int zhuangtai;
    @Column(name = "TZSPATH")
    private String tzspath;
    @Column(name = "XIAZAIRQ")
    private Date xiazairq;
    @Column(name = "SHENQINGH")
    private String shenqingh;
    @Column(name = "TUploadTime")
    private Date tUploadTime;
    @Column(name = "Original")
    private String original;
    @Column(name = "OUploadTime")
    private Date oUploadTime;
    @Column(name = "FeeType")
    private Integer feeType;
    @Column(name = "FeePercent")
    private Double feePercent;
    @Column(name = "BeginTimes")
    private Integer BeginTimes;

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public List<tbFeeItem> getItems() {
        if (items == null) items = new ArrayList<>();
        return items;
    }

    public void setItems(List<tbFeeItem> items) {
        this.items = items;
    }

    @Column(name = "lastDate")
    private Date lastDate;
    @Transient
    private List<tbFeeItem> items;

    public Date gettUploadTime() {
        return tUploadTime;
    }

    public void settUploadTime(Date tUploadTime) {
        this.tUploadTime = tUploadTime;
    }

    public Date getoUploadTime() {
        return oUploadTime;
    }

    public void setoUploadTime(Date oUploadTime) {
        this.oUploadTime = oUploadTime;
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
        return BeginTimes;
    }

    public void setBeginTimes(Integer beginTimes) {
        BeginTimes = beginTimes;
    }


    public String getTongzhisbh() {
        return tongzhisbh;
    }

    public void setTongzhisbh(String tongzhisbh) {
        this.tongzhisbh = tongzhisbh;
    }


    public String getTongzhisdm() {
        return tongzhisdm;
    }

    public void setTongzhisdm(String tongzhisdm) {
        this.tongzhisdm = tongzhisdm;
    }


    public String getFamingmc() {
        return famingmc;
    }

    public void setFamingmc(String famingmc) {
        this.famingmc = famingmc;
    }


    public String getFawenxlh() {
        return fawenxlh;
    }

    public void setFawenxlh(String fawenxlh) {
        this.fawenxlh = fawenxlh;
    }


    public String getTongzhismc() {
        return tongzhismc;
    }

    public void setTongzhismc(String tongzhismc) {
        this.tongzhismc = tongzhismc;
    }


    public String getShenqingbh() {
        return shenqingbh;
    }

    public void setShenqingbh(String shenqingbh) {
        this.shenqingbh = shenqingbh;
    }


    public Date getFawenrq() {
        return fawenrq;
    }

    public void setFawenrq(Date fawenrq) {
        this.fawenrq = fawenrq;
    }


    public Date getDafurq() {
        return dafurq;
    }

    public void setDafurq(Date dafurq) {
        this.dafurq = dafurq;
    }


    public int getDatefrom() {
        return datefrom;
    }

    public void setDatefrom(int datefrom) {
        this.datefrom = datefrom;
    }


    public int getZhuangtai() {
        return zhuangtai;
    }

    public void setZhuangtai(int zhuangtai) {
        this.zhuangtai = zhuangtai;
    }


    public String getTzspath() {
        return tzspath;
    }

    public void setTzspath(String tzspath) {
        this.tzspath = tzspath;
    }


    public Date getXiazairq() {
        return xiazairq;
    }

    public void setXiazairq(Date xiazairq) {
        this.xiazairq = xiazairq;
    }


    public String getShenqingh() {
        return shenqingh;
    }

    public void setShenqingh(String shenqingh) {
        this.shenqingh = shenqingh;
    }


    public Date getTUploadTime() {
        return tUploadTime;
    }

    public void setTUploadTime(Date tUploadTime) {
        this.tUploadTime = tUploadTime;
    }


    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }


    public Date getOUploadTime() {
        return oUploadTime;
    }

    public void setOUploadTime(Date oUploadTime) {
        this.oUploadTime = oUploadTime;
    }

}
