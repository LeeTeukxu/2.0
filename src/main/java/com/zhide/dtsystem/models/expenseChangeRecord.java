package com.zhide.dtsystem.models;

import java.util.Date;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ExpenseChangeRecord")
public class expenseChangeRecord   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;
  @Column(name = "MainID")
  private Integer mainId;
  @Column(name = "Mode")
  private String mode;
  @Column(name = "ChangeText")
  private String changeText;
  @Column(name = "UserID")
  private Integer userId;
  @Column(name = "CreateTime")
  private Date createTime;

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


  public String getMode() {
    return mode;
  }
  public void setMode(String mode) {
    this.mode = mode;
  }


  public String getChangeText() {
    return changeText;
  }
  public void setChangeText(String changeText) {
    this.changeText = changeText;
  }


  public Integer getUserId() {
    return userId;
  }
  public void setUserId(Integer userId) {
    this.userId = userId;
  }


  public Date getCreateTime() {
    return createTime;
  }
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

}
