package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "OtherOfficeFee")
public class otherOfficeFee   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;
  @Column(name = "JIAOFEIR")
  private Date jiaofeir;
  @Column(name = "ExpenseItem")
  private String expenseItem;
  @Column(name = "Amount")
  private float amount;
  @Column(name = "SHENQINGH")
  private String shenqingh;
  @Column(name = "FAMINGMC")
  private String famingmc;
  @Column(name = "SHENQINGLX")
  private String shenqinglx;
  @Column(name = "STATE")
  private Integer state;
  @Column(name = "XMONEY")
  private Double xmoney;
  @Column(name = "SHOW")
  private Boolean show;
    @Column(name = "YEAR")
    private Integer year;
    @Column(name = "AddPayFor")
    private Boolean addPayFor;
    @Column(name = "NeedPayFor")
    private Boolean needPayFor;
    @Column(name = "SXMONEY")
    private Double sxmoney;
    @Column(name = "ExpenseItemID")
    private Integer expenseItemId;
    @Column(name = "Type")
    private Integer type;
    @Column(name = "OtherOfficeStates")
    private Integer otherOfficeStates;
    @Column(name = "ImportData")
    private Boolean importData;
    @Column(name = "PayState")
    private Integer payState;
    @Column(name = "UpdateTime")
    private Date updateTime;
    private Integer payMan;
    @Column(name = "PayTime")
    private Date payTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Date getJiaofeir() {
        return jiaofeir;
    }

    public void setJiaofeir(Date jiaofeir) {
        this.jiaofeir = jiaofeir;
    }


  public String getExpenseItem() {
    return expenseItem;
  }
  public void setExpenseItem(String expenseItem) {
    this.expenseItem = expenseItem;
  }


  public float getAmount() {
    return amount;
  }
  public void setAmount(float amount) {
    this.amount = amount;
  }


  public String getShenqingh() {
    return shenqingh;
  }
  public void setShenqingh(String shenqingh) {
    this.shenqingh = shenqingh;
  }


  public String getFamingmc() {
    return famingmc;
  }
  public void setFamingmc(String famingmc) {
    this.famingmc = famingmc;
  }


  public String getShenqinglx() {
    return shenqinglx;
  }
  public void setShenqinglx(String shenqinglx) {
    this.shenqinglx = shenqinglx;
  }


  public Integer getState() {
    return state;
  }
  public void setState(Integer state) {
    this.state = state;
  }


  public Double getXmoney() {
    return xmoney;
  }
  public void setXmoney(Double xmoney) {
    this.xmoney = xmoney;
  }


  public Boolean getShow() {
    return show;
  }
  public void setShow(Boolean show) {
    this.show = show;
  }


  public Integer getYear() {
    return year;
  }
  public void setYear(Integer year) {
    this.year = year;
  }


  public Boolean getAddPayFor() {
    return addPayFor;
  }
  public void setAddPayFor(Boolean addPayFor) {
    this.addPayFor = addPayFor;
  }


  public Boolean getNeedPayFor() {
    return needPayFor;
  }
  public void setNeedPayFor(Boolean needPayFor) {
    this.needPayFor = needPayFor;
  }


  public Double getSxmoney() {
    return sxmoney;
  }
  public void setSxmoney(Double sxmoney) {
    this.sxmoney = sxmoney;
  }


  public Integer getExpenseItemId() {
    return expenseItemId;
  }
  public void setExpenseItemId(Integer expenseItemId) {
    this.expenseItemId = expenseItemId;
  }


  public Integer getType() {
    return type;
  }
  public void setType(Integer type) {
    this.type = type;
  }


  public Integer getOtherOfficeStates() {
    return otherOfficeStates;
  }
  public void setOtherOfficeStates(Integer otherOfficeStates) {
    this.otherOfficeStates = otherOfficeStates;
  }


  public Boolean getImportData() {
    return importData;
  }
  public void setImportData(Boolean importData) {
    this.importData = importData;
  }


    public Integer getPayState() {
        return payState;
    }

    public void setPayState(Integer payState) {
        this.payState = payState;
    }


    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getPayMan() {
        return payMan;
    }

    public void setPayMan(Integer payMan) {
        this.payMan = payMan;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
}
