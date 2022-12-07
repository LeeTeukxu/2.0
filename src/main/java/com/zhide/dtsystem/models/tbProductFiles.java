package com.zhide.dtsystem.models;

import java.util.Date;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbProductFiles")
public class tbProductFiles   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "SubID")
  private Integer subId;
  @Column(name = "MainID")
  private String mainId;
  @Column(name = "AttID")
  private String attId;
  @Column(name = "AttName")
  private String attName;
  @Column(name = "Type")
  private String type;
  @Column(name = "CreateTime")
  private Date createTime;
  @Column(name = "CreateMan")
  private Integer createMan;

  public Integer getSubId() {
    return subId;
  }
  public void setSubId(Integer subId) {
    this.subId = subId;
  }


  public String getMainId() {
    return mainId;
  }
  public void setMainId(String mainId) {
    this.mainId = mainId;
  }


  public String getAttId() {
    return attId;
  }
  public void setAttId(String attId) {
    this.attId = attId;
  }


  public String getAttName() {
    return attName;
  }
  public void setAttName(String attName) {
    this.attName = attName;
  }


  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
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
