package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbCompany")
public class tbCompany   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;
  @Column(name = "OrgCode")
  private String orgCode;
  @Column(name = "CompanyName")
  private String companyName;
  @Column(name = "Phone")
  private String phone;
  @Column(name = "PostCode")
  private String postCode;

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }


  public String getOrgCode() {
    return orgCode;
  }
  public void setOrgCode(String orgCode) {
    this.orgCode = orgCode;
  }


  public String getCompanyName() {
    return companyName;
  }
  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }


  public String getPhone() {
    return phone;
  }
  public void setPhone(String phone) {
    this.phone = phone;
  }


  public String getPostCode() {
    return postCode;
  }
  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

}
