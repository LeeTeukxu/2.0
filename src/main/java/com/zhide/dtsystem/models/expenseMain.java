package com.zhide.dtsystem.models;

import java.util.Date;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ExpenseMain")
public class expenseMain   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;
  @Column(name = "DocSN")
  private String docSn;
  @Column(name = "State")
  private Integer state;
  @Column(name = "DepID")
  private Integer depId;
  @Column(name = "ProjectID")
  private Integer projectId;
  @Column(name = "AllocAuditMan")
  private String allocAuditMan;
  @Column(name = "Memo")
  private String memo;
  @Column(name = "AuditResult")
  private Integer auditResult;
  @Column(name = "AuditMan")
  private Integer auditMan;
  @Column(name = "AuditTime")
  private Date auditTime;
  @Column(name = "AuditText")
  private String auditText;
  @Column(name = "SetResult")
  private Integer setResult;
  @Column(name = "SetMan")
  private Integer setMan;
  @Column(name = "SetTime")
  private Date setTime;
  @Column(name = "SetText")
  private String setText;
  @Column(name = "CreateTime")
  private Date createTime;
  @Column(name = "CreateMan")
  private Integer createMan;
  @Column(name = "SubmitTime")
  private Date submitTime;
  @Column(name = "ChangeMan")
  private Integer changeMan;
  @Column(name = "ChangeText")
  private String changeText;
  @Column(name = "ChangeTime")
  private Date changeTime;

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


  public Integer getState() {
    return state;
  }
  public void setState(Integer state) {
    this.state = state;
  }


  public Integer getDepId() {
    return depId;
  }
  public void setDepId(Integer depId) {
    this.depId = depId;
  }


  public Integer getProjectId() {
    return projectId;
  }
  public void setProjectId(Integer projectId) {
    this.projectId = projectId;
  }


  public String getAllocAuditMan() {
    return allocAuditMan;
  }
  public void setAllocAuditMan(String allocAuditMan) {
    this.allocAuditMan = allocAuditMan;
  }


  public String getMemo() {
    return memo;
  }
  public void setMemo(String memo) {
    this.memo = memo;
  }


  public Integer getAuditResult() {
    return auditResult;
  }
  public void setAuditResult(Integer auditResult) {
    this.auditResult = auditResult;
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


  public String getAuditText() {
    return auditText;
  }
  public void setAuditText(String auditText) {
    this.auditText = auditText;
  }


  public Integer getSetResult() {
    return setResult;
  }
  public void setSetResult(Integer setResult) {
    this.setResult = setResult;
  }


  public Integer getSetMan() {
    return setMan;
  }
  public void setSetMan(Integer setMan) {
    this.setMan = setMan;
  }


  public Date getSetTime() {
    return setTime;
  }
  public void setSetTime(Date setTime) {
    this.setTime = setTime;
  }


  public String getSetText() {
    return setText;
  }
  public void setSetText(String setText) {
    this.setText = setText;
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


  public Date getSubmitTime() {
    return submitTime;
  }
  public void setSubmitTime(Date submitTime) {
    this.submitTime = submitTime;
  }


  public Integer getChangeMan() {
    return changeMan;
  }
  public void setChangeMan(Integer changeMan) {
    this.changeMan = changeMan;
  }


  public String getChangeText() {
    return changeText;
  }
  public void setChangeText(String changeText) {
    this.changeText = changeText;
  }


  public Date getChangeTime() {
    return changeTime;
  }
  public void setChangeTime(Date changeTime) {
    this.changeTime = changeTime;
  }

}
