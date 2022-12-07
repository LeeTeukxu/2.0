package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "f_1")
public class f1   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;
  @Column(name = "ExpenseItem")
  private String expenseItem;
  @Column(name = "Amount")
  private String amount;
  @Column(name = "OfficialExplanation")
  private String officialExplanation;
  @Column(name = "Type")
  private Integer type;

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }


  public String getExpenseItem() {
    return expenseItem;
  }
  public void setExpenseItem(String expenseItem) {
    this.expenseItem = expenseItem;
  }


  public String getAmount() {
    return amount;
  }
  public void setAmount(String amount) {
    this.amount = amount;
  }


  public String getOfficialExplanation() {
    return officialExplanation;
  }
  public void setOfficialExplanation(String officialExplanation) {
    this.officialExplanation = officialExplanation;
  }


  public Integer getType() {
    return type;
  }
  public void setType(Integer type) {
    this.type = type;
  }

}
