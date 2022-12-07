package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TZSEmailRecord")
public class tzsEmailRecord   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;

  @Column(name = "TID")
  private String tid;
  @Column(name = "SHENQINGH")
  private String shenqingh;
  @Column(name = "TONGZHISBH")
  private String tongzhisbh;
  @Column(name = "Client")
  private String client;
  @Column(name = "Email")
  private String email;
  @Column(name = "SendUser")
  private Integer sendUser;
  @Column(name = "SendUserName")
  private String sendUserName;
  @Column(name = "SendTime")
  private Date sendTime;
  @Column(name = "IsOld")
  private Boolean isOld;

  public String getTid() {
    return tid;
  }
  public void setTid(String tid) {
    this.tid = tid;
  }


  public String getShenqingh() {
    return shenqingh;
  }
  public void setShenqingh(String shenqingh) {
    this.shenqingh = shenqingh;
  }


  public String getTongzhisbh() {
    return tongzhisbh;
  }
  public void setTongzhisbh(String tongzhisbh) {
    this.tongzhisbh = tongzhisbh;
  }


  public String getClient() {
    return client;
  }
  public void setClient(String client) {
    this.client = client;
  }


  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }


  public Integer getSendUser() {
    return sendUser;
  }
  public void setSendUser(Integer sendUser) {
    this.sendUser = sendUser;
  }


  public String getSendUserName() {
    return sendUserName;
  }
  public void setSendUserName(String sendUserName) {
    this.sendUserName = sendUserName;
  }


  public Date getSendTime() {
    return sendTime;
  }
  public void setSendTime(Date sendTime) {
    this.sendTime = sendTime;
  }


  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }


  public Boolean getIsOld() {
    return isOld;
  }
  public void setIsOld(Boolean isOld) {
    this.isOld = isOld;
  }

}
