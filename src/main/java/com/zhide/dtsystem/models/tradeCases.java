package com.zhide.dtsystem.models;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TradeCase")
public class tradeCases implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "DocSN")
    private String docSn;
    @Column(name = "CASESID")
    private String casesid;
    @Column(name = "State")
    private Integer state;
    @Column(name = "Nums")
    private String nums;
    @Column(name = "Memo")
    private String memo;
    @Column(name = "ContractNo")
    private String contractNo;
    @Column(name = "ClientID")
    private Integer clientId;
    @Column(name = "AllMoney")
    private Double allMoney;
    @Column(name = "AllMoneySet")
    private Boolean allMoneySet;

    public String getClientIdName() {
        return clientIdName;
    }

    public void setClientIdName(String clientIdName) {
        this.clientIdName = clientIdName;
    }

    @Transient
    private String clientIdName;
    @Column(name = "SignTime")
    private Date signTime;
    @Column(name = "CreateMan")
    private Integer createMan;
    @Column(name = "CreateManager")
    private String createManager;
    @Column(name = "CreateTime")
    private Date createTime;
    @Column(name = "AuditMan")
    private Integer auditMan;
    @Column(name = "AuditManager")
    private String auditManager;
    @Column(name = "AuditTime")
    private Date auditTime;
    @Column(name = "AuditText")
    private String auditText;
    @Column(name = "TechMan")
    private String techMan;
    @Column(name = "TechManager")
    private String techManager;
    @Column(name = "TechTime")
    private String techTime;
    @Column(name = "TechManNames")
    private String techManNames;
    @Column(name = "FormText")
    private String formText;
    @Column(name = "PreFormText")
    private String preFormText;
    @Column(name = "TechNums")
    private String techNums;
    @Column(name = "CommitFileStatus")
    private String commitFileStatus;
    @Column(name = "ShenBaoStatus")
    private String shenBaoStatus;
    @Column(name = "FinishTime")
    private Date finishTime;
    @Column(name = "FinishMan")
    private Integer finishMan;
    @Column(name = "UpdateTime")
    private Date updateTime;
    @Transient
    private Double DMoney;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getDocSn() {
        return docSn;
    }

    public void setDocSn(String docSn) {
        this.docSn = docSn;
    }


    public String getCasesid() {
        return casesid;
    }

    public void setCasesid(String casesid) {
        this.casesid = casesid;
    }


    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }


    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }


    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }


    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }


    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }


    public Integer getCreateMan() {
        return createMan;
    }

    public void setCreateMan(Integer createMan) {
        this.createMan = createMan;
    }


    public String getCreateManager() {
        return createManager;
    }

    public void setCreateManager(String createManager) {
        this.createManager = createManager;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public Integer getAuditMan() {
        return auditMan;
    }

    public void setAuditMan(Integer auditMan) {
        this.auditMan = auditMan;
    }


    public String getAuditManager() {
        return auditManager;
    }

    public void setAuditManager(String auditManager) {
        this.auditManager = auditManager;
    }


    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }


    public String getAuditText() {
        return auditText;
    }

    public void setAuditText(String auditText) {
        this.auditText = auditText;
    }


    public String getTechMan() {
        return techMan;
    }

    public void setTechMan(String techMan) {
        this.techMan = techMan;
    }


    public String getTechManager() {
        return techManager;
    }

    public void setTechManager(String techManager) {
        this.techManager = techManager;
    }


    public String getTechTime() {
        return techTime;
    }

    public void setTechTime(String techTime) {
        this.techTime = techTime;
    }


    public String getTechManNames() {
        return techManNames;
    }

    public void setTechManNames(String techManNames) {
        this.techManNames = techManNames;
    }


    public String getFormText() {
        return formText;
    }

    public void setFormText(String formText) {
        this.formText = formText;
    }


    public String getPreFormText() {
        return preFormText;
    }

    public void setPreFormText(String preFormText) {
        this.preFormText = preFormText;
    }


    public String getTechNums() {
        return techNums;
    }

    public void setTechNums(String techNums) {
        this.techNums = techNums;
    }


    public String getCommitFileStatus() {
        return commitFileStatus;
    }

    public void setCommitFileStatus(String commitFileStatus) {
        this.commitFileStatus = commitFileStatus;
    }


    public String getShenBaoStatus() {
        return shenBaoStatus;
    }

    public void setShenBaoStatus(String shenBaoStatus) {
        this.shenBaoStatus = shenBaoStatus;
    }


    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }


    public Integer getFinishMan() {
        return finishMan;
    }

    public void setFinishMan(Integer finishMan) {
        this.finishMan = finishMan;
    }


    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    public Double getAllMoney() {
        return allMoney;
    }

    public void setAllMoney(Double allMoney) {
        this.allMoney = allMoney;
    }

    public Boolean isAllMoneySet() {
        if(allMoneySet==null) allMoneySet=false;
        return allMoneySet;
    }

    public void setAllMoneySet(Boolean allMoneySet) {
        this.allMoneySet = allMoneySet;
    }
}
