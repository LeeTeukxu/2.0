package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CancelCasesSub")
public class cancelCasesSub   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;
  @Column(name = "MainID")
  private Integer mainId;
  @Column(name = "SubID")
  private String subId;
  @Column(name = "CasesID")
  private String casesId;
  @Column(name = "KouGuan")
  private Double kouGuan;
  @Column(name = "KouDai")
  private Double kouDai;
  @Column(name = "KouTotal")
  private Double kouTotal;
  @Column(name = "Memo")
  private String memo;
  @Column(name = "CreateTime")
  private Date createTime;
  @Column(name = "CreateMan")
  private Integer createMan;


  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }


  public Integer getMainId() {
    return mainId;
  }
  public void setMainId(Integer mainId) {
    this.mainId = mainId;
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


  public Double getKouGuan() {
    return kouGuan;
  }
  public void setKouGuan(Double kouGuan) {
    this.kouGuan = kouGuan;
  }


  public Double getKouDai() {
    return kouDai;
  }
  public void setKouDai(Double kouDai) {
    this.kouDai = kouDai;
  }


  public Double getKouTotal() {
    return kouTotal;
  }
  public void setKouTotal(Double kouTotal) {
    this.kouTotal = kouTotal;
  }


  public String getMemo() {
    return memo;
  }
  public void setMemo(String memo) {
    this.memo = memo;
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

}
