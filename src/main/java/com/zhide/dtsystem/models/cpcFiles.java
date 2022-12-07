package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CPCFiles")
public class cpcFiles   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;
  @Column(name = "MainID")
  private String mainId;
  @Column(name = "SubID")
  private String subId;
  @Column(name = "AttID")
  private String attId;
  @Column(name = "Type")
  private String type;
  @Column(name = "Code")
  private String code;
  @Column(name="Pages")
  private Integer pages;
  @Column(name="ExtName")
  private String extName;

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }


  public String getMainId() {
    return mainId;
  }
  public void setMainId(String mainId) {
    this.mainId = mainId;
  }


  public String getSubId() {
    return subId;
  }
  public void setSubId(String subId) {
    this.subId = subId;
  }


  public String getAttId() {
    return attId;
  }
  public void setAttId(String attId) {
    this.attId = attId;
  }


  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }


  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }

  public Integer getPages() {
    return pages;
  }

  public void setPages(Integer pages) {
    this.pages = pages;
  }

  public String getExtName() {
    return extName;
  }

  public void setExtName(String extName) {
    this.extName = extName;
  }
}
