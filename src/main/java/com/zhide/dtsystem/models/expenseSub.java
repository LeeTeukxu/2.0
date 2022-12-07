package com.zhide.dtsystem.models;

import java.util.Date;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ExpenseSub")
public class expenseSub   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "SubID")
  private Integer subId;
  @Column(name = "MainID")
  private Integer mainId;
  @Column(name = "Memo")
  private String memo;
  @Column(name = "ItemID")
  private Integer itemId;
  @Column(name = "Price")
  private Double price;
  @Column(name = "Num")
  private Integer num;
  @Column(name = "Money")
  private Double money;
  @Column(name = "AttID")
  private String attId;
  @Column(name = "BeginTime")
  private Date beginTime;
  @Column(name = "EndTime")
  private Date endTime;
  @Column(name = "CreateTime")
  private Date createTime;
  @Column(name = "CreateMan")
  private Integer createMan;

  public Integer getMainId() {
    return mainId;
  }
  public void setMainId(Integer mainId) {
    this.mainId = mainId;
  }


  public Integer getSubId() {
    return subId;
  }
  public void setSubId(Integer subId) {
    this.subId = subId;
  }


  public String getMemo() {
    return memo;
  }
  public void setMemo(String memo) {
    this.memo = memo;
  }


  public Integer getItemId() {
    return itemId;
  }
  public void setItemId(Integer itemId) {
    this.itemId = itemId;
  }


  public Double getPrice() {
    return price;
  }
  public void setPrice(Double price) {
    this.price = price;
  }


  public Integer getNum() {
    return num;
  }
  public void setNum(Integer num) {
    this.num = num;
  }


  public Double getMoney() {
    return money;
  }
  public void setMoney(Double money) {
    this.money = money;
  }


  public String getAttId() {
    return attId;
  }
  public void setAttId(String attId) {
    this.attId = attId;
  }


  public Date getBeginTime() {
    return beginTime;
  }
  public void setBeginTime(Date beginTime) {
    this.beginTime = beginTime;
  }


  public Date getEndTime() {
    return endTime;
  }
  public void setEndTime(Date endTime) {
    this.endTime = endTime;
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
