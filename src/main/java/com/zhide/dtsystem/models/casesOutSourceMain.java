package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CasesOutSourceMain")
public class casesOutSourceMain implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "OutID")
    private String outId;
    @Column(name = "CasesID")
    private String casesId;
    @Column(name = "SubID")
    private String subId;
    @Column(name = "State")
    private Integer state;
    @Column(name = "Memo")
    private String memo;
    @Column(name = "ProcessText")
    private String processText;
    @Column(name = "TechMan")
    private Integer techMan;
    @Column(name = "TechTime")
    private Date techTime;
    @Column(name = "AuditTime")
    private Date auditTime;
    @Column(name = "AuditMan")
    private Integer auditMan;
    @Column(name = "AuditResult")
    private String auditResult;
    @Column(name = "AuditText")
    private String auditText;
    @Column(name = "AuditTechFiles")
    private String auditTechFiles;
    @Column(name = "CreateTime")
    private Date createTime;
    @Column(name = "CreateMan")
    private Integer createMan;
    @Column(name = "RejectTime")
    private Date rejectTime;
    @Column(name = "RejectMan")
    private Integer rejectMan;
    @Column(name = "CommitMan")
    private Integer commitMan;
    @Column(name = "CommitTime")
    private Date commitTime;
    @Column(name = "OutTechFiles")
    private String outTechFiles;
    @Column(name = "OutTechFileUploadTime")
    private Date outTechFileUploadTime;
    @Column(name = "CanUse")
    private Boolean canUse;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }


    public String getCasesId() {
        return casesId;
    }

    public void setCasesId(String casesId) {
        this.casesId = casesId;
    }


    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }


    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }


    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


    public String getProcessText() {
        return processText;
    }

    public void setProcessText(String processText) {
        this.processText = processText;
    }


    public Integer getTechMan() {
        return techMan;
    }

    public void setTechMan(Integer techMan) {
        this.techMan = techMan;
    }


    public Date getTechTime() {
        return techTime;
    }

    public void setTechTime(Date techTime) {
        this.techTime = techTime;
    }


    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }


    public Integer getAuditMan() {
        return auditMan;
    }

    public void setAuditMan(Integer auditMan) {
        this.auditMan = auditMan;
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


    public String getAuditTechFiles() {
        return auditTechFiles;
    }

    public void setAuditTechFiles(String auditTechFiles) {
        this.auditTechFiles = auditTechFiles;
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


    public Date getRejectTime() {
        return rejectTime;
    }

    public void setRejectTime(Date rejectTime) {
        this.rejectTime = rejectTime;
    }


    public Integer getRejectMan() {
        return rejectMan;
    }

    public void setRejectMan(Integer rejectMan) {
        this.rejectMan = rejectMan;
    }


    public Integer getCommitMan() {
        return commitMan;
    }

    public void setCommitMan(Integer commitMan) {
        this.commitMan = commitMan;
    }


    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }


    public String getOutTechFiles() {
        return outTechFiles;
    }

    public void setOutTechFiles(String outTechFiles) {
        this.outTechFiles = outTechFiles;
    }


    public Date getOutTechFileUploadTime() {
        return outTechFileUploadTime;
    }

    public void setOutTechFileUploadTime(Date outTechFileUploadTime) {
        this.outTechFileUploadTime = outTechFileUploadTime;
    }


    public Boolean getCanUse() {
        return canUse;
    }

    public void setCanUse(Boolean canUse) {
        this.canUse = canUse;
    }

}
