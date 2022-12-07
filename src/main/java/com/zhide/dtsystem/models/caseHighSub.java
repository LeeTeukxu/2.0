package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CaseHighSub")
public class caseHighSub implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "SubID")
    private String subId;
    @Column(name = "CasesID")
    private String casesId;
    @Column(name = "ProcessState")
    private Integer processState;
    @Column(name = "ProcessText")
    private String processText;
    @Column(name = "Memo")
    private String memo;
    @Column(name = "SubNo")
    private String subNo;
    @Column(name = "YID")
    private String yid;
    @Column(name = "YName")
    private String yName;
    @Column(name = "SBYear")
    private String sbYear;
    @Column(name = "Area")
    private String area;
    @Column(name = "ContainSJF")
    private Integer containSjf;
    @Column(name = "SJFMoney")
    private Double sjfMoney;
    @Column(name = "SWSName")
    private String swsName;
    @Column(name = "DaiMoney")
    private Double daiMoney;
    @Column(name = "Num")
    private Integer num;
    @Column(name = "TotalMoney")
    private Double totalMoney;
    @Column(name = "ClientRequiredDate")
    private Date clientRequiredDate;
    @Column(name = "TechFiles")
    private String techFiles;
    @Column(name = "ClientLinkMan")
    private String clientLinkMan;
    @Column(name = "ClientLinkPhone")
    private String clientLinkPhone;
    @Column(name = "ClientLinkMail")
    private String clientLinkMail;
    @Column(name = "AssignTechMan")
    private String assignTechMan;
    @Column(name = "AssignTechManNames")
    private String assignTechManNames;
    @Column(name = "TechMan")
    private Integer techMan;
    @Column(name = "TechManager")
    private String techManager;
    @Column(name = "TechAuditMan")
    private Integer techAuditMan;
    @Column(name = "TechAuditManager")
    private String techAuditManager;
    @Column(name = "AcceptTechTime")
    private Date acceptTechTime;
    @Column(name = "AcceptTechFiles")
    private String acceptTechFiles;
    @Column(name = "CommitTechMan")
    private Integer commitTechMan;
    @Column(name = "CommitTechMemo")
    private String commitTechMemo;
    @Column(name = "CommitTechTime")
    private Date commitTechTime;
    @Column(name = "AuditTechMan")
    private Integer auditTechMan;
    @Column(name = "AuditManager")
    private String auditManager;
    @Column(name = "AuditTechMemo")
    private String auditTechMemo;
    @Column(name = "AuditTechTime")
    private Date auditTechTime;
    @Column(name = "AuditTechFiles")
    private String auditTechFiles;
    @Column(name = "ClientSetMan")
    private Integer clientSetMan;
    @Column(name = "ClientSetTime")
    private Date clientSetTime;
    @Column(name = "ClientSetResult")
    private Integer clientSetResult;
    @Column(name = "ClientSetMemo")
    private String clientSetMemo;
    @Column(name = "TechSBMan")
    private Integer techSbMan;
    @Column(name = "TechSBTime")
    private Date techSbTime;
    @Column(name = "TechSBMemo")
    private String techSbMemo;
    @Column(name = "TechSBResult")
    private Integer techSbResult;
    @Column(name = "SettleMan")
    private Integer settleMan;
    @Column(name = "SettleTime")
    private Date settleTime;
    @Column(name = "SetttleManager")
    private String setttleManager;
    @Column(name = "SettleMemo")
    private String settleMemo;
    @Column(name = "SettleResult")
    private Integer settleResult;
    @Column(name = "CanUse")
    private Boolean canUse;
    @Column(name = "IsComplete")
    private Boolean isComplete;
    @Column(name = "CreateMan")
    private Integer createMan;
    @Column(name = "CreateTime")
    private Date createTime;
    @Column(name = "LXText")
    private String lxText;
    @Column(name = "ExpFiles")
    private String expFiles;
    @Column(name="SupportMan")
    private String supportMan;
    @Column(name="RptType")
    private String rptType;
    @Column(name="Cancel")
    private Boolean cancel;
    @Column(name="CancelMan")
    private Integer cancelMan;
    @Column(name="CancelTime")
    private Date    cancelTime;

    public Boolean getCancel() {
        return cancel;
    }

    public void setCancel(Boolean cancel) {
        this.cancel = cancel;
    }

    public Integer getCancelMan() {
        return cancelMan;
    }

    public void setCancelMan(Integer cancelMan) {
        this.cancelMan = cancelMan;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }


    public String getCasesId() {
        return casesId;
    }

    public void setCasesId(String casesId) {
        this.casesId = casesId;
    }


    public Integer getProcessState() {
        return processState;
    }

    public void setProcessState(Integer processState) {
        this.processState = processState;
    }


    public String getProcessText() {
        return processText;
    }

    public void setProcessText(String processText) {
        this.processText = processText;
    }


    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


    public String getSubNo() {
        return subNo;
    }

    public void setSubNo(String subNo) {
        this.subNo = subNo;
    }


    public String getYid() {
        return yid;
    }

    public void setYid(String yid) {
        this.yid = yid;
    }


    public String getYName() {
        return yName;
    }

    public void setYName(String yName) {
        this.yName = yName;
    }


    public String getSbYear() {
        return sbYear;
    }

    public void setSbYear(String sbYear) {
        this.sbYear = sbYear;
    }


    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }


    public Integer getContainSjf() {
        if (containSjf == null) containSjf = 0;
        return containSjf;
    }

    public void setContainSjf(Integer containSjf) {
        this.containSjf = containSjf;
    }

    public Double getSjfMoney() {
        if(sjfMoney==null)sjfMoney=0.0;
        return sjfMoney;
    }

    public void setSjfMoney(Double sjfMoney) {
        this.sjfMoney = sjfMoney;
    }


    public String getSwsName() {
        return swsName;
    }

    public void setSwsName(String swsName) {
        this.swsName = swsName;
    }


    public Double getDaiMoney() {
        if(daiMoney==null)daiMoney=0.0;
        return daiMoney;
    }

    public void setDaiMoney(Double daiMoney) {
        this.daiMoney = daiMoney;
    }


    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }


    public Double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }


    public Date getClientRequiredDate() {
        return clientRequiredDate;
    }

    public void setClientRequiredDate(Date clientRequiredDate) {
        this.clientRequiredDate = clientRequiredDate;
    }


    public String getTechFiles() {
        return techFiles;
    }

    public void setTechFiles(String techFiles) {
        this.techFiles = techFiles;
    }


    public String getClientLinkMan() {
        return clientLinkMan;
    }

    public void setClientLinkMan(String clientLinkMan) {
        this.clientLinkMan = clientLinkMan;
    }


    public String getClientLinkPhone() {
        return clientLinkPhone;
    }

    public void setClientLinkPhone(String clientLinkPhone) {
        this.clientLinkPhone = clientLinkPhone;
    }


    public String getClientLinkMail() {
        return clientLinkMail;
    }

    public void setClientLinkMail(String clientLinkMail) {
        this.clientLinkMail = clientLinkMail;
    }


    public String getAssignTechMan() {
        return assignTechMan;
    }

    public void setAssignTechMan(String assignTechMan) {
        this.assignTechMan = assignTechMan;
    }


    public String getAssignTechManNames() {
        return assignTechManNames;
    }

    public void setAssignTechManNames(String assignTechManNames) {
        this.assignTechManNames = assignTechManNames;
    }


    public Integer getTechMan() {
        return techMan;
    }

    public void setTechMan(Integer techMan) {
        this.techMan = techMan;
    }


    public String getTechManager() {
        return techManager;
    }

    public void setTechManager(String techManager) {
        this.techManager = techManager;
    }


    public Integer getTechAuditMan() {
        return techAuditMan;
    }

    public void setTechAuditMan(Integer techAuditMan) {
        this.techAuditMan = techAuditMan;
    }


    public String getTechAuditManager() {
        return techAuditManager;
    }

    public void setTechAuditManager(String techAuditManager) {
        this.techAuditManager = techAuditManager;
    }


    public Date getAcceptTechTime() {
        return acceptTechTime;
    }

    public void setAcceptTechTime(Date acceptTechTime) {
        this.acceptTechTime = acceptTechTime;
    }


    public String getAcceptTechFiles() {
        return acceptTechFiles;
    }

    public void setAcceptTechFiles(String acceptTechFiles) {
        this.acceptTechFiles = acceptTechFiles;
    }


    public Integer getCommitTechMan() {
        return commitTechMan;
    }

    public void setCommitTechMan(Integer commitTechMan) {
        this.commitTechMan = commitTechMan;
    }


    public String getCommitTechMemo() {
        return commitTechMemo;
    }

    public void setCommitTechMemo(String commitTechMemo) {
        this.commitTechMemo = commitTechMemo;
    }


    public Date getCommitTechTime() {
        return commitTechTime;
    }

    public void setCommitTechTime(Date commitTechTime) {
        this.commitTechTime = commitTechTime;
    }


    public Integer getAuditTechMan() {
        return auditTechMan;
    }

    public void setAuditTechMan(Integer auditTechMan) {
        this.auditTechMan = auditTechMan;
    }


    public String getAuditManager() {
        return auditManager;
    }

    public void setAuditManager(String auditManager) {
        this.auditManager = auditManager;
    }


    public String getAuditTechMemo() {
        return auditTechMemo;
    }

    public void setAuditTechMemo(String auditTechMemo) {
        this.auditTechMemo = auditTechMemo;
    }


    public Date getAuditTechTime() {
        return auditTechTime;
    }

    public void setAuditTechTime(Date auditTechTime) {
        this.auditTechTime = auditTechTime;
    }


    public String getAuditTechFiles() {
        return auditTechFiles;
    }

    public void setAuditTechFiles(String auditTechFiles) {
        this.auditTechFiles = auditTechFiles;
    }


    public Integer getClientSetMan() {
        return clientSetMan;
    }

    public void setClientSetMan(Integer clientSetMan) {
        this.clientSetMan = clientSetMan;
    }


    public Date getClientSetTime() {
        return clientSetTime;
    }

    public void setClientSetTime(Date clientSetTime) {
        this.clientSetTime = clientSetTime;
    }


    public Integer getClientSetResult() {
        return clientSetResult;
    }

    public void setClientSetResult(Integer clientSetResult) {
        this.clientSetResult = clientSetResult;
    }


    public String getClientSetMemo() {
        return clientSetMemo;
    }

    public void setClientSetMemo(String clientSetMemo) {
        this.clientSetMemo = clientSetMemo;
    }


    public Integer getTechSbMan() {
        return techSbMan;
    }

    public void setTechSbMan(Integer techSbMan) {
        this.techSbMan = techSbMan;
    }


    public Date getTechSbTime() {
        return techSbTime;
    }

    public void setTechSbTime(Date techSbTime) {
        this.techSbTime = techSbTime;
    }


    public String getTechSbMemo() {
        return techSbMemo;
    }

    public void setTechSbMemo(String techSbMemo) {
        this.techSbMemo = techSbMemo;
    }


    public Integer getTechSbResult() {
        return techSbResult;
    }

    public void setTechSbResult(Integer techSbResult) {
        this.techSbResult = techSbResult;
    }


    public Integer getSettleMan() {
        return settleMan;
    }

    public void setSettleMan(Integer settleMan) {
        this.settleMan = settleMan;
    }


    public Date getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(Date settleTime) {
        this.settleTime = settleTime;
    }


    public String getSetttleManager() {
        return setttleManager;
    }

    public void setSetttleManager(String setttleManager) {
        this.setttleManager = setttleManager;
    }


    public String getSettleMemo() {
        return settleMemo;
    }

    public void setSettleMemo(String settleMemo) {
        this.settleMemo = settleMemo;
    }


    public Integer getSettleResult() {
        return settleResult;
    }

    public void setSettleResult(Integer settleResult) {
        this.settleResult = settleResult;
    }


    public Boolean getCanUse() {
        return canUse;
    }

    public void setCanUse(Boolean canUse) {
        this.canUse = canUse;
    }


    public Boolean getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Boolean isComplete) {
        this.isComplete = isComplete;
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


    public String getExpFiles() {
        return expFiles;
    }

    public void setExpFiles(String expFiles) {
        this.expFiles = expFiles;
    }
    public String getSupportMan() {
        return supportMan;
    }

    public void setSupportMan(String supportMan) {
        this.supportMan = supportMan;
    }
    public String getLxText() {
        return lxText;
    }

    public void setLxText(String lxText) {
        this.lxText = lxText;
    }

    public String getRptType() {
        return rptType;
    }

    public void setRptType(String rptType) {
        this.rptType = rptType;
    }
}
