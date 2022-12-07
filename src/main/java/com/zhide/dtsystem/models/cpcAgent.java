package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CPCAgent")
public class cpcAgent   implements Serializable {
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
  @Column(name = "Code")
  private String code;
  @Column(name = "Phone")
  private String phone;
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


  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }


  public String getPhone() {
    return phone;
  }
  public void setPhone(String phone) {
    this.phone = phone;
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
