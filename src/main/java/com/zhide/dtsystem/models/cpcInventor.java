package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CPCInventor")
public class cpcInventor   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;
  @Column(name = "MainID")
  private String mainId;
  @Column(name = "SubID")
  private String subId;
  @Column(name = "Name")
  private String name;
  @Column(name = "NotOpen")
  private Boolean notOpen;
  @Column(name = "First")
  private Boolean first;
  @Column(name = "CreateTime")
  private Date createTime;
  @Column(name = "CreateMan")
  private Integer createMan;

  @Column(name="Code")
  String code;
  @Column(name="Country")
  String country;

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }


  public String getMainId() {
    return mainId;
  }
  public void setMainId(String mainId) {
    this.mainId = mainId;
  }


  public String getSubId() {
    return subId;
  }
  public void setSubId(String subId) {
    this.subId = subId;
  }


  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }


  public Boolean getNotOpen() {
    return notOpen;
  }
  public void setNotOpen(Boolean notOpen) {
    this.notOpen = notOpen;
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

  public Boolean getFirst() {
    if(first==null)first=false;
    return first;
  }

  public void setFirst(Boolean first) {
    this.first = first;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }
}
