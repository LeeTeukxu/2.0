package com.zhide.dtsystem.models;

import java.util.Date;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TechSupportAttachment")
public class techSupportAttachment   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;
  @Column(name = "DocSN")
  private String docSn;
  @Column(name = "CasesID")
  private String casesId;
  @Column(name = "AttID")
  private String attId;
  @Column(name = "RefID")
  private String refId;
  @Column(name = "CreateTime")
  private Date createTime;
  @Column(name = "Account")
  private Boolean account;
  @Column(name = "AccountMan")
  private Integer accountMan;
  @Column(name = "AccountTime")
  private Date accountTime;
  @Column(name = "TechMan")
  private Integer techMan;
  @Column(name = "ClientID")
  private Integer clientId;
  @Column(name = "TechManName")
  private String techManName;
  @Column(name = "ClientName")
  private String clientName;
  @Column(name="TechTime")
  private Date techTime;
  @Column(name="RefMan")
  private Integer refMan;
  @Column(name="refTime")
  private Date  refTime;
  @Column(name="CreateMan")
  private Integer createMan;

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


  public String getAttId() {
    return attId;
  }
  public void setAttId(String attId) {
    this.attId = attId;
  }


  public String getRefId() {
    return refId;
  }
  public void setRefId(String refId) {
    this.refId = refId;
  }


  public Date getCreateTime() {
    return createTime;
  }
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }


  public Boolean getAccount() {
    return account;
  }
  public void setAccount(Boolean account) {
    this.account = account;
  }


  public Integer getAccountMan() {
    return accountMan;
  }
  public void setAccountMan(Integer accountMan) {
    this.accountMan = accountMan;
  }


  public Date getAccountTime() {
    return accountTime;
  }
  public void setAccountTime(Date accountTime) {
    this.accountTime = accountTime;
  }


  public Integer getTechMan() {
    return techMan;
  }
  public void setTechMan(Integer techMan) {
    this.techMan = techMan;
  }


  public Integer getClientId() {
    return clientId;
  }
  public void setClientId(Integer clientId) {
    this.clientId = clientId;
  }


  public String getTechManName() {
    return techManName;
  }
  public void setTechManName(String techManName) {
    this.techManName = techManName;
  }


  public String getClientName() {
    return clientName;
  }
  public void setClientName(String clientName) {
    this.clientName = clientName;
  }

  public Date getTechTime() {
    return techTime;
  }

  public void setTechTime(Date techTime) {
    this.techTime = techTime;
  }

  public Integer getRefMan() {
    return refMan;
  }

  public void setRefMan(Integer refMan) {
    this.refMan = refMan;
  }

  public Date getRefTime() {
    return refTime;
  }

  public void setRefTime(Date refTime) {
    this.refTime = refTime;
  }

  public Integer getCreateMan() {
    return createMan;
  }

  public void setCreateMan(Integer createMan) {
    this.createMan = createMan;
  }
}
