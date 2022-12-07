package com.zhide.dtsystem.models;

import java.util.Date;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ExpenseItem")
public class expenseItem   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "FID")
  private Integer fid;
  @Column(name = "PID")
  private Integer pid;
  @Column(name = "SN")
  private Integer sn;
  @Column(name = "Name")
  private String name;
  @Column(name = "CreateTime")
  private Date createTime;
  @Column(name = "CreateMan")
  private Integer createMan;
  @Transient
  private Double money;

  public Integer getFid() {
    return fid;
  }
  public void setFid(Integer fid) {
    this.fid = fid;
  }


  public Integer getPid() {
    return pid;
  }
  public void setPid(Integer pid) {
    this.pid = pid;
  }


  public Integer getSn() {
    return sn;
  }
  public void setSn(Integer sn) {
    this.sn = sn;
  }


  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
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

  public Double getMoney() {
    if(money==null)money=0.0;
    return money;
  }

  public void setMoney(Double money) {
    this.money = money;
  }
}
