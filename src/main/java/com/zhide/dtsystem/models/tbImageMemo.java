package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbImageMemo")
public class tbImageMemo   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;
  @Column(name = "PID")
  private String pid;
  @Column(name = "AttID")
  private String attId;
  @Column(name = "Memo")
  private String memo;
  @Column(name = "CreateManName")
  private String createManName;
  @Column(name = "CreateTime")
  private Date createTime;

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }


  public String getPid() {
    return pid;
  }
  public void setPid(String pid) {
    this.pid = pid;
  }


  public String getAttId() {
    return attId;
  }
  public void setAttId(String attId) {
    this.attId = attId;
  }


  public String getMemo() {
    return memo;
  }
  public void setMemo(String memo) {
    this.memo = memo;
  }


  public String getCreateManName() {
    return createManName;
  }
  public void setCreateManName(String createManName) {
    this.createManName = createManName;
  }


  public Date getCreateTime() {
    return createTime;
  }
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

}
