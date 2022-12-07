package com.zhide.dtsystem.models;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.zhide.dtsystem.listeners.casesMainChangeListener;
import com.zhide.dtsystem.services.define.IChangeEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CasesMain")
@EntityListeners({casesMainChangeListener.class})
public class casesMain implements Serializable, IChangeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "DocSN")
    private String docSn;
    @Column(name = "CasesID")
    private String casesId;
    @Column(name = "State")
    private Integer state;
    @Column(name = "Nums")
    private String nums;
    @Column(name = "AllMoney")
    private Double allMoney;
    @Column(name = "AllMoneySet")
    private Boolean allMoneySet;
    @Column(name = "Memo")
    private String memo;
    @Column(name = "ContractNo")
    private String contractNo;
    @Column(name = "ClientID")
    private Integer clientId;
    @Column(name = "ZuanLiFei")
    private Integer zuanLiFei;
    @Column(name = "FeiJian")
    private Integer feiJian;
    @Column(name = "SignTime")
    private Date signTime;
    @Column(name = "CreateMan")
    private Integer createMan;
    @Column(name = "CreateManager")
    private String createManager;
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
    @Column(name = "CWMan")
    private String cwMan;
    @Column(name = "CWManager")
    private String cwManager;
    @Column(name = "FormText")
    private String formText;
    @Column(name = "PreFormText")
    private String preFormText;
    @Transient
    private Double DMoney;
    @Column(name = "CompleteMan")
    private Integer completeMan;
    @Column(name = "CompleteTime")
    private Date completeTime;
    @Column(name = "CreateTime")
    private Date createTime;
    @Transient
    private String clientIdName;

    public Double getDMoney() {
        return DMoney;
    }

    public void setDMoney(Double DMoney) {
        this.DMoney = DMoney;
    }

    public Boolean isAllMoneySet() {
        if(allMoneySet==null)allMoneySet=false;
        return allMoneySet;
    }

    public void setAllMoneySet(Boolean allMoneySet) {
        this.allMoneySet = allMoneySet;
    }


    public String getClientIdName() {
        return clientIdName;
    }

    public void setClientIdName(String clientIdName) {
        this.clientIdName = clientIdName;
    }

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


    public String getCasesId() {
        return casesId;
    }

    public void setCasesId(String casesId) {
        this.casesId = casesId;
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


    public Double getAllMoney() {
        if(allMoney==null)allMoney=0.0;
        return allMoney;
    }

    public void setAllMoney(Double allMoney) {
        this.allMoney = allMoney;
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


    public Integer getZuanLiFei() {
        return zuanLiFei;
    }

    public void setZuanLiFei(Integer zuanLiFei) {
        this.zuanLiFei = zuanLiFei;
    }


    public Integer getFeiJian() {
        return feiJian;
    }

    public void setFeiJian(Integer feiJian) {
        this.feiJian = feiJian;
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


    public String getCwMan() {
        return cwMan;
    }

    public void setCwMan(String cwMan) {
        this.cwMan = cwMan;
    }


    public String getCwManager() {
        return cwManager;
    }

    public void setCwManager(String cwManager) {
        this.cwManager = cwManager;
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


    public Integer getCompleteMan() {
        return completeMan;
    }

    public void setCompleteMan(Integer completeMan) {
        this.completeMan = completeMan;
    }


    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
