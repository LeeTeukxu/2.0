package com.zhide.dtsystem.models;

import java.util.Date;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbProject")
public class tbProject   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "Name")
  private String name;
  @Column(name = "CreatTime")
  private Date creatTime;
  @Column(name = "Memo")
  private String memo;
  @Column(name = "SN")
  private String sn;
  @Column(name = "PID")
  private Integer pid;
  @Column(name = "FID")
  private Integer fid;

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }


  public Date getCreatTime() {
    return creatTime;
  }
  public void setCreatTime(Date creatTime) {
    this.creatTime = creatTime;
  }


  public String getMemo() {
    return memo;
  }
  public void setMemo(String memo) {
    this.memo = memo;
  }


  public String getSn() {
    return sn;
  }
  public void setSn(String sn) {
    this.sn = sn;
  }


  public Integer getPid() {
    return pid;
  }
  public void setPid(Integer pid) {
    this.pid = pid;
  }


  public Integer getFid() {
    return fid;
  }
  public void setFid(Integer fid) {
    this.fid = fid;
  }

}
