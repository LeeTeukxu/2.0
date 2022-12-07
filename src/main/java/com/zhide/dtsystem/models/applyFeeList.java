package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ApplyFeeList")
public class applyFeeList   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;
  @Column(name = "STATE")
  private Integer state;
  @Column(name = "SHENQINGH")
  private String shenqingh;
  @Column(name = "YEAR")
  private Integer year;
  @Column(name = "YEARS")
  private Integer years;
  @Column(name = "FEENAME")
  private String feename;
  @Column(name = "SHOW")
  private Boolean show;
  @Column(name = "JIAOFEIR")
  private Date jiaofeir;
  @Column(name = "MONEY")
  private Double money;
  @Column(name = "XMONEY")
  private Double xmoney;
  @Column(name = "SXMONEY")
  private Double sxmoney;
  @Column(name = "CREATETIME")
  private Date createtime;
  @Column(name = "CREATEMAN")
  private Integer createman;
    @Column(name = "NeedPayFor")
    private Boolean needPayFor;
    @Column(name = "AddPayFor")
    private Boolean addPayFor;
    @Column(name = "JkStatus")
    private Integer jkStatus;
    @Column(name = "ImportData")
    private Boolean importData;
    @Column(name = "PayState")
    private Integer payState;
    @Column(name = "UpdateTime")
    private Date updateTime;
    @Column(name = "CancelMan")
    private Integer cancelMan;
    @Column(name = "CancelTime")
    private Date cancelTime;
    @Column(name = "UpMan")
    private Integer upMan;
    @Column(name = "UpTime")
    private Date upTime;
    @Column(name = "PayMan")
    private Integer payMan;
    @Column(name = "PayTime")
    private Date payTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }


    public String getShenqingh() {
        return shenqingh;
    }

    public void setShenqingh(String shenqingh) {
    this.shenqingh = shenqingh;
  }


  public Integer getYear() {
    return year;
  }
  public void setYear(Integer year) {
    this.year = year;
  }


  public Integer getYears() {
    return years;
  }
  public void setYears(Integer years) {
    this.years = years;
  }


  public String getFeename() {
    return feename;
  }
  public void setFeename(String feename) {
    this.feename = feename;
  }


  public Boolean getShow() {
    return show;
  }
  public void setShow(Boolean show) {
    this.show = show;
  }


  public Date getJiaofeir() {
    return jiaofeir;
  }
  public void setJiaofeir(Date jiaofeir) {
    this.jiaofeir = jiaofeir;
  }


  public Double getMoney() {
    return money;
  }
  public void setMoney(Double money) {
    this.money = money;
  }


  public Double getXmoney() {
    return xmoney;
  }
  public void setXmoney(Double xmoney) {
    this.xmoney = xmoney;
  }


  public Double getSxmoney() {
    return sxmoney;
  }
  public void setSxmoney(Double sxmoney) {
    this.sxmoney = sxmoney;
  }


  public Date getCreatetime() {
    return createtime;
  }
  public void setCreatetime(Date createtime) {
    this.createtime = createtime;
  }


  public Integer getCreateman() {
    return createman;
  }
  public void setCreateman(Integer createman) {
    this.createman = createman;
  }


  public Boolean getNeedPayFor() {
    return needPayFor;
  }
  public void setNeedPayFor(Boolean needPayFor) {
    this.needPayFor = needPayFor;
  }


  public Boolean getAddPayFor() {
    return addPayFor;
  }
  public void setAddPayFor(Boolean addPayFor) {
    this.addPayFor = addPayFor;
  }


  public Integer getJkStatus() {
    return jkStatus;
  }
  public void setJkStatus(Integer jkStatus) {
    this.jkStatus = jkStatus;
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


  public Integer getCancelMan() {
    return cancelMan;
  }
  public void setCancelMan(Integer cancelMan) {
    this.cancelMan = cancelMan;
  }


  public Date getCancelTime() {
    return cancelTime;
  }
  public void setCancelTime(Date cancelTime) {
    this.cancelTime = cancelTime;
  }


    public Integer getUpMan() {
        return upMan;
    }

    public void setUpMan(Integer upMan) {
        this.upMan = upMan;
    }


    public Date getUpTime() {
        return upTime;
    }

    public void setUpTime(Date upTime) {
        this.upTime = upTime;
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
