package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbFeeItemError")
public class tbFeeItemError   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;
  @Column(name = "Shenqingh")
  private String shenqingh;
  @Column(name = "TZSID")
  private String tzsid;
  @Column(name = "Type")
  private String type;
  @Column(name = "FilePath")
  private String filePath;
  @Column(name = "Error")
  private String error;
  @Column(name = "CreateTime")
  private Date createTime;

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }


  public String getShenqingh() {
    return shenqingh;
  }
  public void setShenqingh(String shenqingh) {
    this.shenqingh = shenqingh;
  }


  public String getTzsid() {
    return tzsid;
  }
  public void setTzsid(String tzsid) {
    this.tzsid = tzsid;
  }


  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }


  public String getFilePath() {
    return filePath;
  }
  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }


  public String getError() {
    return error;
  }
  public void setError(String error) {
    this.error = error;
  }


  public Date getCreateTime() {
    return createTime;
  }
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

}
