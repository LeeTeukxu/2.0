package com.zhide.dtsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "SHENQINGXX")
public class shenqingxx implements Serializable {
    @Id
    @Column(name = "SHENQINGBH")
    private String shenqingbh;
    @Column(name = "SHENQINGLX")
    private Integer shenqinglx;
    @Column(name = "ZHUANLIMC")
    private String zhuanlimc;
    @Column(name = "SHENQINGH")
    private String shenqingh;
    @Column(name = "SHENQINGR")
    private Date shenqingr;
    @Column(name = "CHUANGJIANR")
    private Date chuangjianr;
    @Column(name = "ZHUANGTAI")
    private Integer zhuangtai;
    @Column(name = "NEIBUBH")
    private String neibubh;
    @Column(name = "CHULIRY")
    private Integer chuliry;
    @Column(name = "CHULIRQ")
    private Date chulirq;
    @Column(name = "CHULIZT")
    private Integer chulizt;
    @Column(name = "UPLOADMAN")
    private Integer uploadman;
    @Column(name = "UPLOADTIME")
    private Date uploadtime;

    public String getShenqingbh() {
        return shenqingbh;
    }

    public void setShenqingbh(String shenqingbh) {
        this.shenqingbh = shenqingbh;
    }


    public Integer getShenqinglx() {
        return shenqinglx;
    }

    public void setShenqinglx(int shenqinglx) {
        this.shenqinglx = shenqinglx;
    }


    public String getZhuanlimc() {
        return zhuanlimc;
    }

    public void setZhuanlimc(String zhuanlimc) {
        this.zhuanlimc = zhuanlimc;
    }


    public String getShenqingh() {
        return shenqingh;
    }

    public void setShenqingh(String shenqingh) {
        this.shenqingh = shenqingh;
    }


    public Date getShenqingr() {
        return shenqingr;
    }

    public void setShenqingr(Date shenqingr) {
        this.shenqingr = shenqingr;
    }


    public Date getChuangjianr() {
        return chuangjianr;
    }

    public void setChuangjianr(Date chuangjianr) {
        this.chuangjianr = chuangjianr;
    }


    public Integer getZhuangtai() {
        return zhuangtai;
    }

    public void setZhuangtai(int zhuangtai) {
        this.zhuangtai = zhuangtai;
    }


    public String getNeibubh() {
        return neibubh;
    }

    public void setNeibubh(String neibubh) {
        this.neibubh = neibubh;
    }


    public Integer getChuliry() {
        return chuliry;
    }

    public void setChuliry(int chuliry) {
        this.chuliry = chuliry;
    }


    public Date getChulirq() {
        return chulirq;
    }

    public void setChulirq(Date chulirq) {
        this.chulirq = chulirq;
    }


    public Integer getChulizt() {
        return chulizt;
    }

    public void setChulizt(int chulizt) {
        this.chulizt = chulizt;
    }


    public Integer getUploadman() {
        return uploadman;
    }

    public void setUploadman(int uploadman) {
        this.uploadman = uploadman;
    }


    public Date getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(Date uploadtime) {
        this.uploadtime = uploadtime;
    }

}
