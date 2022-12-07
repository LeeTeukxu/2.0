package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TZSConfig")
public class tzsConfig   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;
  @Column(name = "TZSBH")
  private String tzsbh;
  @Column(name = "Type")
  private String type;
  @Column(name = "ISREAD")
  private Boolean isread;
  @Column(name = "ISREADMAN")
  private Integer isreadman;
  @Column(name = "ISREADDATE")
  private Date isreaddate;
  @Column(name = "REPLY")
  private Boolean reply;
  @Column(name = "REPLYMAN")
  private Integer replyman;
  @Column(name = "REPLYDATE")
  private Date replydate;
  @Column(name = "ISCOMMIT")
  private Boolean iscommit;
  @Column(name = "ISCOMMITMAN")
  private Integer iscommitman;
  @Column(name = "ISCOMMITDATE")
  private Date iscommitdate;
  @Column(name = "JA")
  private Boolean ja;
  @Column(name = "JAMAN")
  private Integer jaman;
  @Column(name = "JADATE")
  private Date jadate;
  @Column(name = "ABORT")
  private Boolean abort;
  @Column(name = "ABORTMAN")
  private Integer abortman;
  @Column(name = "ABORTDATE")
  private Date abortdate;
  @Column(name = "MEMO")
  private String memo;

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }


  public String getTzsbh() {
    return tzsbh;
  }
  public void setTzsbh(String tzsbh) {
    this.tzsbh = tzsbh;
  }


  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }


  public Boolean getIsread() {
    return isread;
  }
  public void setIsread(Boolean isread) {
    this.isread = isread;
  }


  public Integer getIsreadman() {
    return isreadman;
  }
  public void setIsreadman(Integer isreadman) {
    this.isreadman = isreadman;
  }


  public Date getIsreaddate() {
    return isreaddate;
  }
  public void setIsreaddate(Date isreaddate) {
    this.isreaddate = isreaddate;
  }


  public Boolean getReply() {
    return reply;
  }
  public void setReply(Boolean reply) {
    this.reply = reply;
  }


  public Integer getReplyman() {
    return replyman;
  }
  public void setReplyman(Integer replyman) {
    this.replyman = replyman;
  }


  public Date getReplydate() {
    return replydate;
  }
  public void setReplydate(Date replydate) {
    this.replydate = replydate;
  }


  public Boolean getIscommit() {
    return iscommit;
  }
  public void setIscommit(Boolean iscommit) {
    this.iscommit = iscommit;
  }


  public Integer getIscommitman() {
    return iscommitman;
  }
  public void setIscommitman(Integer iscommitman) {
    this.iscommitman = iscommitman;
  }


  public Date getIscommitdate() {
    return iscommitdate;
  }
  public void setIscommitdate(Date iscommitdate) {
    this.iscommitdate = iscommitdate;
  }


  public Boolean getJa() {
    return ja;
  }
  public void setJa(Boolean ja) {
    this.ja = ja;
  }


  public Integer getJaman() {
    return jaman;
  }
  public void setJaman(Integer jaman) {
    this.jaman = jaman;
  }


  public Date getJadate() {
    return jadate;
  }
  public void setJadate(Date jadate) {
    this.jadate = jadate;
  }


  public Boolean getAbort() {
    return abort;
  }
  public void setAbort(Boolean abort) {
    this.abort = abort;
  }


  public Integer getAbortman() {
    return abortman;
  }
  public void setAbortman(Integer abortman) {
    this.abortman = abortman;
  }


  public Date getAbortdate() {
    return abortdate;
  }
  public void setAbortdate(Date abortdate) {
    this.abortdate = abortdate;
  }


  public String getMemo() {
    return memo;
  }
  public void setMemo(String memo) {
    this.memo = memo;
  }

}
