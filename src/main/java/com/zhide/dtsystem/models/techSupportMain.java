package com.zhide.dtsystem.models;

import java.util.Date;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TechSupportMain")
public class techSupportMain   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;

  @Column(name = "CreateManager")
  private String createManager;
  @Column(name = "CreateMan")
  private Integer createMan;
  @Column(name = "CreateTime")
  private Date createTime;
  @Column(name = "PreFormText")
  private String preFormText;
  @Column(name = "FormText")
  private String formText;
  @Column(name = "TechManager")
  private String techManager;
  @Column(name = "TechMan")
  private String techMan;
  @Column(name = "AuditText")
  private String auditText;
  @Column(name = "AuditTime")
  private Date auditTime;
  @Column(name = "AuditManager")
  private String auditManager;
  @Column(name = "AuditMan")
  private Integer auditMan;
  @Column(name = "Address")
  private String address;
  @Column(name = "EndTime")
  private Date endTime;
  @Column(name = "BeginTime")
  private Date beginTime;
  @Column(name = "TechType")
  private Integer techType;
  @Column(name = "ClientID")
  private Integer clientId;
  @Column(name = "Memo")
  private String memo;
  @Column(name = "State")
  private Integer state;
  @Column(name = "CasesID")
  private String casesId;
  @Column(name = "DocSN")
  private String docSn;

  @Transient
  private String clientIdName;
  @Transient
  private String techManName;

  public String getClientIdName() {
    return clientIdName;
  }

  public void setClientIdName(String clientIdName) {
    this.clientIdName = clientIdName;
  }

  public String getCreateManager() {
    return createManager;
  }
  public void setCreateManager(String createManager) {
    this.createManager = createManager;
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


  public String getPreFormText() {
    return preFormText;
  }
  public void setPreFormText(String preFormText) {
    this.preFormText = preFormText;
  }


  public String getFormText() {
    return formText;
  }
  public void setFormText(String formText) {
    this.formText = formText;
  }


  public String getTechManager() {
    return techManager;
  }
  public void setTechManager(String techManager) {
    this.techManager = techManager;
  }


  public String getTechMan() {
    return techMan;
  }
  public void setTechMan(String techMan) {
    this.techMan = techMan;
  }


  public String getAuditText() {
    return auditText;
  }
  public void setAuditText(String auditText) {
    this.auditText = auditText;
  }


  public Date getAuditTime() {
    return auditTime;
  }
  public void setAuditTime(Date auditTime) {
    this.auditTime = auditTime;
  }


  public String getAuditManager() {
    return auditManager;
  }
  public void setAuditManager(String auditManager) {
    this.auditManager = auditManager;
  }


  public Integer getAuditMan() {
    return auditMan;
  }
  public void setAuditMan(Integer auditMan) {
    this.auditMan = auditMan;
  }


  public String getAddress() {
    return address;
  }
  public void setAddress(String address) {
    this.address = address;
  }


  public Date getEndTime() {
    return endTime;
  }
  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }


  public Date getBeginTime() {
    return beginTime;
  }
  public void setBeginTime(Date beginTime) {
    this.beginTime = beginTime;
  }


  public Integer getTechType() {
    return techType;
  }
  public void setTechType(Integer techType) {
    this.techType = techType;
  }


  public Integer getClientId() {
    return clientId;
  }
  public void setClientId(Integer clientId) {
    this.clientId = clientId;
  }


  public String getMemo() {
    return memo;
  }
  public void setMemo(String memo) {
    this.memo = memo;
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


  public String getDocSn() {
    return docSn;
  }
  public void setDocSn(String docSn) {
    this.docSn = docSn;
  }


  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }

  public String getTechManName() {
    return techManName;
  }

  public void setTechManName(String techManName) {
    this.techManName = techManName;
  }
}
