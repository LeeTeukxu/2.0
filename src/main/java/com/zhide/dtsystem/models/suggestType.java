package com.zhide.dtsystem.models;

import java.util.Date;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SuggestType")
public class suggestType   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;
  @Column(name = "Type")
  private Integer type;
  @Column(name = "SN")
  private String sn;
  @Column(name = "Name")
  private String name;

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }


  public Integer getType() {
    return type;
  }
  public void setType(Integer type) {
    this.type = type;
  }


  public String getSn() {
    return sn;
  }
  public void setSn(String sn) {
    this.sn = sn;
  }


  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

}
