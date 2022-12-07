package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbExcelTemplate")
public class tbExcelTemplate   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;
  @Column(name = "Code")
  private String code;
  @Column(name = "Name")
  private String name;
  @Column(name = "TemplatePath")
  private String templatePath;
  @Column(name = "CreateTime")
  private Date createTime;
  @Column(name = "UpdateTime")
  private Date updateTime;

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }


  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }


  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }


  public String getTemplatePath() {
    return templatePath;
  }
  public void setTemplatePath(String templatePath) {
    this.templatePath = templatePath;
  }


  public Date getCreateTime() {
    return createTime;
  }
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }


  public Date getUpdateTime() {
    return updateTime;
  }
  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

}
