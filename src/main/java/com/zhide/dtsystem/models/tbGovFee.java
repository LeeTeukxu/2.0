package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbGovFee")
public class tbGovFee   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;
  @Column(name = "AID")
  private Integer aid;
  @Column(name = "AppNo")
  private String appNo;
  @Column(name = "CostName")
  private String costName;
  @Column(name = "Payer")
  private String payer;
  @Column(name = "GovPayDate")
  private Date govPayDate;
  @Column(name = "ReceiptCode")
  private String receiptCode;
  @Column(name = "ReceiptNo")
  private String receiptNo;
  @Column(name = "Amount")
  private Integer amount;
  @Column(name = "PayState")
  private String payState;
  @Column(name = "LimitDate")
  private Date limitDate;
  @Column(name = "CreateTime")
  private Date createTime;

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }


  public Integer getAid() {
    return aid;
  }
  public void setAid(Integer aid) {
    this.aid = aid;
  }


  public String getAppNo() {
    return appNo;
  }
  public void setAppNo(String appNo) {
    this.appNo = appNo;
  }


  public String getCostName() {
    return costName;
  }
  public void setCostName(String costName) {
    this.costName = costName;
  }


  public String getPayer() {
    return payer;
  }
  public void setPayer(String payer) {
    this.payer = payer;
  }


  public Date getGovPayDate() {
    return govPayDate;
  }
  public void setGovPayDate(Date govPayDate) {
    this.govPayDate = govPayDate;
  }


  public String getReceiptCode() {
    return receiptCode;
  }
  public void setReceiptCode(String receiptCode) {
    this.receiptCode = receiptCode;
  }


  public String getReceiptNo() {
    return receiptNo;
  }
  public void setReceiptNo(String receiptNo) {
    this.receiptNo = receiptNo;
  }


  public Integer getAmount() {
    return amount;
  }
  public void setAmount(Integer amount) {
    this.amount = amount;
  }


  public String getPayState() {
    return payState;
  }
  public void setPayState(String payState) {
    this.payState = payState;
  }


  public Date getLimitDate() {
    return limitDate;
  }
  public void setLimitDate(Date limitDate) {
    this.limitDate = limitDate;
  }


  public Date getCreateTime() {
    return createTime;
  }
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

}
