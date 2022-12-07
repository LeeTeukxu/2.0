package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CPCApplyMan")
public class cpcApplyMan   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;
  @Column(name = "MainID")
  private String mainId;
  @Column(name = "SubID")
  private String subId;
  @Column(name = "Name")
  private String name;
  @Column(name = "Code")
  private String code;
  @Column(name = "Type")
  private String type;
  @Column(name = "IDCode")
  private String idCode;
  @Column(name = "Email")
  private String email;
  @Column(name = "RequestFJ")
  private Boolean requestFj;
  @Column(name = "Country")
  private String country;
  @Column(name = "Province")
  private String province;
  @Column(name = "City")
  private String city;
  @Column(name = "Street")
  private String street;
  @Column(name = "Address")
  private String address;
  @Column(name = "PostCode")
  private String postCode;
  @Column(name = "Phone")
  private String phone;
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


  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }


  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }


  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }


  public String getIdCode() {
    return idCode;
  }
  public void setIdCode(String idCode) {
    this.idCode = idCode;
  }


  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }


  public Boolean getRequestFj() {
    return requestFj;
  }
  public void setRequestFj(Boolean requestFj) {
    this.requestFj = requestFj;
  }


  public String getCountry() {
    return country;
  }
  public void setCountry(String country) {
    this.country = country;
  }


  public String getProvince() {
    return province;
  }
  public void setProvince(String province) {
    this.province = province;
  }


  public String getCity() {
    return city;
  }
  public void setCity(String city) {
    this.city = city;
  }


  public String getStreet() {
    return street;
  }
  public void setStreet(String street) {
    this.street = street;
  }


  public String getAddress() {
    return address;
  }
  public void setAddress(String address) {
    this.address = address;
  }


  public String getPostCode() {
    return postCode;
  }
  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }


  public String getPhone() {
    return phone;
  }
  public void setPhone(String phone) {
    this.phone = phone;
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

}
