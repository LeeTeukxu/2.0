package com.zhide.dtsystem.models;

import java.util.Date;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbFileClass")
public class tbFileClass   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;
  @Column(name="PID")
  private Integer pid;
  @Column(name = "SN")
  private Integer sn;
  @Column(name = "TypeName")
  private String typeName;
  @Column(name = "CanUse")
  private Boolean canUse;
  @Column(name = "Memo")
  private String memo;
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


  public Integer getSn() {
    return sn;
  }
  public void setSn(Integer sn) {
    this.sn = sn;
  }


  public String getTypeName() {
    return typeName;
  }
  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }


  public Boolean getCanUse() {
    return canUse;
  }
  public void setCanUse(Boolean canUse) {
    this.canUse = canUse;
  }


  public String getMemo() {
    return memo;
  }
  public void setMemo(String memo) {
    this.memo = memo;
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

  public Integer getPid() {
    return pid;
  }

  public void setPid(Integer pid) {
    this.pid = pid;
  }
}
