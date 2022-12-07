package com.zhide.dtsystem.models;

import java.util.Date;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TechSupportSub")
public class techSupportSub   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;
  @Column(name = "SubID")
  private String subId;
  @Column(name = "CasesID")
  private String casesId;
  @Column(name = "ProcessState")
  private Integer processState;
  @Column(name = "Memo")
  private String memo;
  @Column(name = "SubNo")
  private String subNo;
  @Column(name = "ClientRequiredDate")
  private Date clientRequiredDate;
  @Column(name = "TechFiles")
  private String techFiles;
  @Column(name = "ZLFiles")
  private String zlFiles;
  @Column(name = "TechMan")
  private Integer techMan;
  @Column(name = "TechManager")
  private String techManager;
  @Column(name = "CanUse")
  private Boolean canUse;
  @Column(name = "IsComplete")
  private Boolean isComplete;
  @Column(name = "CreateMan")
  private Integer createMan;
  @Column(name = "CreateTime")
  private Date createTime;
  @Column(name = "ProcessText")
  private String processText;

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


  public String getZlFiles() {
    return zlFiles;
  }
  public void setZlFiles(String zlFiles) {
    this.zlFiles = zlFiles;
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


  public String getProcessText() {
    return processText;
  }
  public void setProcessText(String processText) {
    this.processText = processText;
  }

}
