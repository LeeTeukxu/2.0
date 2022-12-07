package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CasesYwAccept")
public class casesYwAccept implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "CasesID")
    private String casesId;
    @Column(name = "YID")
    private String yid;
    @Column(name = "YType")
    private String yType;
    @Column(name = "YName")
    private String yName;
    @Column(name = "Num")
    private Integer num;
    @Column(name = "Progress")
    private String progress;
    @Column(name = "TechMan")
    private Integer techMan;
    @Column(name = "CreateTime")
    private Date createTime;
    @Column(name = "FinishTime")
    private Date finishTime;
    @Column(name = "ShenBao")
    private String shenBao;
    @Column(name = "ShenBaoMan")
    private Integer shenBaoMan;
    @Column(name = "ShenBaoTime")
    private Date shenBaoTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getCasesId() {
        return casesId;
    }

    public void setCasesId(String casesId) {
        this.casesId = casesId;
    }


    public String getYid() {
        return yid;
    }

    public void setYid(String yid) {
        this.yid = yid;
    }


    public String getYType() {
        return yType;
    }

    public void setYType(String yType) {
        this.yType = yType;
    }


    public String getYName() {
        return yName;
    }

    public void setYName(String yName) {
        this.yName = yName;
    }


    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }


    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }


    public Integer getTechMan() {
        return techMan;
    }

    public void setTechMan(Integer techMan) {
        this.techMan = techMan;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }


    public String getShenBao() {
        return shenBao;
    }

    public void setShenBao(String shenBao) {
        this.shenBao = shenBao;
    }


    public Integer getShenBaoMan() {
        return shenBaoMan;
    }

    public void setShenBaoMan(Integer shenBaoMan) {
        this.shenBaoMan = shenBaoMan;
    }


    public Date getShenBaoTime() {
        return shenBaoTime;
    }

    public void setShenBaoTime(Date shenBaoTime) {
        this.shenBaoTime = shenBaoTime;
    }

}
