package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ArrivalUseDetail")
public class arrivalUseDetail   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;
  @Column(name = "ArrID")
  private Integer arrId;
  @Column(name = "CasesID")
  private String casesId;
  @Column(name = "CanUse")
  private Boolean canUse;
  @Column(name = "CreateTime")
  private Date createTime;
  @Column(name = "CreateMan")
  private Integer createMan;
  @Column(name = "ClientID")
  private Integer clientId;
  @Column(name = "ClientName")
  private String clientName;
  @Column(name = "Memo")
  private String memo;
  @Column(name = "State")
  private Integer state;
  @Column(name = "AuditMan")
  private Integer auditMan;
  @Column(name = "AuditTime")
  private Date auditTime;
  @Column(name = "AuditMemo")
  private String auditMemo;
  @Column(name = "Dai")
  private Double dai;
  @Column(name = "Guan")
  private Double guan;
  @Column(name = "Total")
  private Double total;
  @Column(name="MoneyType")
  private Integer moneyType;
  @Column(name="Files")
  private String files;

  public Integer getId() {
    if(id==null) id=0;
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }


  public Integer getArrId() {
    return arrId;
  }
  public void setArrId(Integer arrId) {
    this.arrId = arrId;
  }


  public String getCasesId() {
    return casesId;
  }
  public void setCasesId(String casesId) {
    this.casesId = casesId;
  }


  public Boolean getCanUse() {
    if(canUse==null) canUse=true;
    return canUse;
  }
  public void setCanUse(Boolean canUse) {
    this.canUse = canUse;
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


  public Integer getClientId() {
    return clientId;
  }
  public void setClientId(Integer clientId) {
    this.clientId = clientId;
  }


  public String getClientName() {
    return clientName;
  }
  public void setClientName(String clientName) {
    this.clientName = clientName;
  }


  public String getMemo() {
    return memo;
  }
  public void setMemo(String memo) {
    this.memo = memo;
  }


  public Integer getState() {
    if(state==null)state=0;
    return state;
  }
  public void setState(Integer state) {
    this.state = state;
  }


  public Integer getAuditMan() {
    if(auditMan==null) auditMan=0;
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


  public String getAuditMemo() {
    return auditMemo;
  }
  public void setAuditMemo(String auditMemo) {
    this.auditMemo = auditMemo;
  }


  public Double getDai() {
    if(dai==null)dai=0.0;
    return dai;
  }
  public void setDai(Double dai) {
    this.dai = dai;
  }


  public Double getGuan() {
    if(guan==null)guan=0.0;
    return guan;
  }
  public void setGuan(Double guan) {
    this.guan = guan;
  }


  public Double getTotal() {
    return total;
  }
  public void setTotal(Double total) {
    this.total = total;
  }

  public Integer getMoneyType() {
    return moneyType;
  }

  public void setMoneyType(Integer moneyType) {
    this.moneyType = moneyType;
  }

  public String getFiles() {
    return files;
  }

  public void setFiles(String files) {
    this.files = files;
  }
}
