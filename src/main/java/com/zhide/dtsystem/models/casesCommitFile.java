package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CasesCommitFile")
public class casesCommitFile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "State")
    private Integer state;
    @Column(name = "CasesID")
    private String casesId;
    @Column(name = "AttID")
    private String attId;
    @Column(name = "Type")
    private String type;
    @Column(name = "AuditResult")
    private String auditResult;
    @Column(name = "AuditText")
    private String auditText;
    @Column(name = "AuditMan")
    private Integer auditMan;
    @Column(name = "AuditTime")
    private Date auditTime;
    @Column(name = "ShenBaoResult")
    private String shenBaoResult;
    @Column(name = "ShenBaoText")
    private String shenBaoText;
    @Column(name = "ShenBaoMan")
    private Integer shenBaoMan;
    @Column(name = "ShenBaoTime")
    private Date shenBaoTime;
    @Column(name = "CreateMan")
    private Integer createMan;
    @Column(name = "CreateTime")
    private Date createTime;
    @Column(name = "Memo")
    private String memo;
    @Column(name = "LastDate")
    private Date lastDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }


    public String getCasesId() {
        return casesId;
    }

    public void setCasesId(String casesId) {
        this.casesId = casesId;
    }


    public String getAttId() {
        return attId;
    }

    public void setAttId(String attId) {
        this.attId = attId;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
    }


    public String getAuditText() {
        return auditText;
    }

    public void setAuditText(String auditText) {
        this.auditText = auditText;
    }


    public Integer getAuditMan() {
        return auditMan;
    }

    public void setAuditMan(Integer auditMan) {
        this.auditMan = auditMan;
    }


    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }


    public String getShenBaoResult() {
        return shenBaoResult;
    }

    public void setShenBaoResult(String shenBaoResult) {
        this.shenBaoResult = shenBaoResult;
    }


    public String getShenBaoText() {
        return shenBaoText;
    }

    public void setShenBaoText(String shenBaoText) {
        this.shenBaoText = shenBaoText;
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


    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

}
