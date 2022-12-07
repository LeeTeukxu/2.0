package com.zhide.dtsystem.models;

import java.util.Date;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbFileUpload")
public class tbFileUpload   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;
  @Column(name = "UploadTime")
  private Date uploadTime;
  @Column(name = "UploadMan")
  private Integer uploadMan;
  @Column(name = "AttID")
  private String attId;
  @Column(name = "Memo2")
  private String memo2;
  @Column(name = "Memo1")
  private String memo1;
  @Column(name = "Required")
  private String required;
  @Column(name = "Name")
  private String name;
  @Column(name = "TypeID")
  private Integer typeId;

  public Date getUploadTime() {
    return uploadTime;
  }
  public void setUploadTime(Date uploadTime) {
    this.uploadTime = uploadTime;
  }


  public Integer getUploadMan() {
    return uploadMan;
  }
  public void setUploadMan(Integer uploadMan) {
    this.uploadMan = uploadMan;
  }


  public String getAttId() {
    return attId;
  }
  public void setAttId(String attId) {
    this.attId = attId;
  }


  public String getMemo2() {
    return memo2;
  }
  public void setMemo2(String memo2) {
    this.memo2 = memo2;
  }


  public String getMemo1() {
    return memo1;
  }
  public void setMemo1(String memo1) {
    this.memo1 = memo1;
  }


  public String getRequired() {
    return required;
  }
  public void setRequired(String required) {
    this.required = required;
  }


  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }


  public Integer getTypeId() {
    return typeId;
  }
  public void setTypeId(Integer typeId) {
    this.typeId = typeId;
  }


  public Integer getId() {
    if(id==null)id=0;
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }

}
