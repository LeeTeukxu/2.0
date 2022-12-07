package com.zhide.dtsystem.models;

import java.util.Date;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ExpenseMoney")
public class expenseMoney   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;
  @Column(name = "FID")
  private Integer fid;
  @Column(name = "Year")
  private Integer year;
  @Column(name = "Money")
  private Double money;
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


  public Integer getFid() {
    return fid;
  }
  public void setFid(Integer fid) {
    this.fid = fid;
  }


  public Integer getYear() {
    return year;
  }
  public void setYear(Integer year) {
    this.year = year;
  }


  public Double getMoney() {
    return money;
  }
  public void setMoney(Double money) {
    this.money = money;
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
