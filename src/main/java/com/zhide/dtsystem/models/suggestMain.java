package com.zhide.dtsystem.models;

import java.util.Date;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SuggestMain")
public class suggestMain   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;
  @Column(name = "DocSN")
  private String docSn;
  @Column(name = "CasesID")
  private String casesId;
  @Column(name = "SuggestMan")
  private String suggestMan;
  @Column(name = "Title")
  private String title;
  @Column(name = "Memo")
  private String memo;
  @Column(name = "Type")
  private Integer type;
  @Column(name = "CreateMan")
  private Integer createMan;
  @Column(name = "CreateTime")
  private Date createTime;
  @Column(name = "SubmitTime")
  private Date submitTime;
  @Column(name = "AuditMan")
  private String auditMan;
  @Column(name = "ActAuditMan")
  private Integer actAuditMan;
  @Column(name = "AuditResult")
  private Integer auditResult;
  @Column(name = "AuditText")
  private String auditText;
  @Column(name = "AuditTime")
  private Date auditTime;
  @Column(name = "State")
  private Integer state;
  @Column(name = "FormText")
  private String formText;
  @Column(name = "PreFormText")
  private String preFormText;
  @Column(name = "ChangeMan")
  private Integer changeMan;
  @Column(name = "ChangeTime")
  private Date changeTime;
  @Column(name = "ChangeText")
  private String changeText;

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


  public String getSuggestMan() {
    return suggestMan;
  }
  public void setSuggestMan(String suggestMan) {
    this.suggestMan = suggestMan;
  }


  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }


  public String getMemo() {
    return memo;
  }
  public void setMemo(String memo) {
    this.memo = memo;
  }


  public Integer getType() {
    return type;
  }
  public void setType(Integer type) {
    this.type = type;
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


  public Date getSubmitTime() {
    return submitTime;
  }
  public void setSubmitTime(Date submitTime) {
    this.submitTime = submitTime;
  }


  public String getAuditMan() {
    return auditMan;
  }
  public void setAuditMan(String auditMan) {
    this.auditMan = auditMan;
  }


  public Integer getActAuditMan() {
    return actAuditMan;
  }
  public void setActAuditMan(Integer actAuditMan) {
    this.actAuditMan = actAuditMan;
  }


  public Integer getAuditResult() {
    return auditResult;
  }
  public void setAuditResult(Integer auditResult) {
    this.auditResult = auditResult;
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


  public Integer getState() {
    return state;
  }
  public void setState(Integer state) {
    this.state = state;
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


  public Integer getChangeMan() {
    return changeMan;
  }
  public void setChangeMan(Integer changeMan) {
    this.changeMan = changeMan;
  }


  public Date getChangeTime() {
    return changeTime;
  }
  public void setChangeTime(Date changeTime) {
    this.changeTime = changeTime;
  }


  public String getChangeText() {
    return changeText;
  }
  public void setChangeText(String changeText) {
    this.changeText = changeText;
  }

}
