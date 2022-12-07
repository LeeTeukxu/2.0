package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CancelCasesMain")
public class cancelCasesMain   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;
  @Column(name = "State")
  private Integer state;
  @Column(name = "Type")
  private String type;
  @Column(name = "CasesID")
  private String casesId;
  @Column(name = "Memo")
  private String memo;
  @Column(name = "TotalMoney")
  private Double totalMoney;
  @Column(name = "CreateMan")
  private Integer createMan;
  @Column(name = "CreateTime")
  private Date createTime;
  @Column(name = "CreateText")
  private String createText;
  @Column(name = "AuditMan")
  private Integer auditMan;
  @Column(name = "AuditTime")
  private Date auditTime;
  @Column(name = "AuditText")
  private String auditText;
  @Column(name = "SetMan")
  private Integer setMan;
  @Column(name = "SetTime")
  private Date setTime;
  @Column(name = "SetText")
  private String setText;
  @Column(name="RefundMoney")
  private Boolean refundMoney;
  @Column(name = "GuanMoney")
  private Double guanMoney;
  @Column(name = "DaiMoney")
  private Double daiMoney;
  @Column(name = "RefundMethod")
  private Integer refundMethod;
  @Column(name = "AccountName")
  private String accountName;
  @Column(name = "AccountNumber")
  private String accountNumber;
  @Column(name = "BankName")
  private String bankName;

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


  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }


  public String getCasesId() {
    return casesId;
  }
  public void setCasesId(String casesId) {
    this.casesId = casesId;
  }


  public String getMemo() {
    return memo;
  }
  public void setMemo(String memo) {
    this.memo = memo;
  }


  public Double getTotalMoney() {
    if(totalMoney==null) totalMoney=0.0;
    return totalMoney;
  }
  public void setTotalMoney(Double totalMoney) {
    this.totalMoney = totalMoney;
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


  public String getCreateText() {
    return createText;
  }
  public void setCreateText(String createText) {
    this.createText = createText;
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
  public Boolean getRefundMoney() {
    if(refundMoney==null) refundMoney=false;
    return refundMoney;
  }

  public void setRefundMoney(Boolean refundMoney) {
    this.refundMoney = refundMoney;
  }

  public Double getGuanMoney() {
    return guanMoney;
  }

  public void setGuanMoney(Double guanMoney) {
    this.guanMoney = guanMoney;
  }

  public Double getDaiMoney() {
    return daiMoney;
  }

  public void setDaiMoney(Double daiMoney) {
    this.daiMoney = daiMoney;
  }

  public Integer getRefundMethod() {
    return refundMethod;
  }

  public void setRefundMethod(Integer refundMethod) {
    this.refundMethod = refundMethod;
  }

  public String getAccountName() {
    return accountName;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }
}
