package com.zhide.dtsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "SQWJ")
public class sqwj implements Serializable {
    @Column(name = "SHENQINGBH")
    private String shenqingbh;

    @Id
    @Column(name = "WENJIANBH")
    private int wenjianbh;
    @Column(name = "WENJIANMC")
    private String wenjianmc;
    @Column(name = "BIAOGEDM")
    private String biaogedm;
    @Column(name = "WENJIANLX")
    private int wenjianlx;
    @Column(name = "ANJUANBH")
    private String anjuanbh;
    @Column(name = "CHUANGJIANRQ")
    private Date chuangjianrq;
    @Column(name = "CUNCHULJ")
    private String cunchulj;

    public String getShenqingbh() {
        return shenqingbh;
    }

    public void setShenqingbh(String shenqingbh) {
        this.shenqingbh = shenqingbh;
    }


    public int getWenjianbh() {
        return wenjianbh;
    }

    public void setWenjianbh(int wenjianbh) {
        this.wenjianbh = wenjianbh;
    }


    public String getWenjianmc() {
        return wenjianmc;
    }

    public void setWenjianmc(String wenjianmc) {
        this.wenjianmc = wenjianmc;
    }


    public String getBiaogedm() {
        return biaogedm;
    }

    public void setBiaogedm(String biaogedm) {
        this.biaogedm = biaogedm;
    }


    public int getWenjianlx() {
        return wenjianlx;
    }

    public void setWenjianlx(int wenjianlx) {
        this.wenjianlx = wenjianlx;
    }


    public String getAnjuanbh() {
        return anjuanbh;
    }

    public void setAnjuanbh(String anjuanbh) {
        this.anjuanbh = anjuanbh;
    }


    public Date getChuangjianrq() {
        return chuangjianrq;
    }

    public void setChuangjianrq(Date chuangjianrq) {
        this.chuangjianrq = chuangjianrq;
    }


    public String getCunchulj() {
        return cunchulj;
    }

    public void setCunchulj(String cunchulj) {
        this.cunchulj = cunchulj;
    }

}
