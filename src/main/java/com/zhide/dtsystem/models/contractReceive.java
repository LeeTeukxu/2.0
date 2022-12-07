package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ContractReceive")
public class contractReceive   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;
  @Column(name = "ContractType")
  private Integer contractType;
  @Column(name = "ContractName")
  private String contractName;
  @Column(name = "ContractNo")
  private String contractNo;
  @Column(name = "NeadSeal")
  private Integer neadSeal;
  @Column(name = "DrawEmp")
  private Integer drawEmp;
  @Column(name = "DrawTime")
  private Date drawTime;
  @Column(name = "NeedSubmit")
  private Integer needSubmit;
  @Column(name = "ClientID")
  private Integer clientId;
  @Column(name = "CreateMan")
  private Integer createMan;
  @Column(name = "CreateTime")
  private Date createTime;
  @Column(name = "Remark")
  private String remark;
  @Column(name = "AttID")
  private String attId;

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }


  public Integer getContractType() {
    return contractType;
  }
  public void setContractType(Integer contractType) {
    this.contractType = contractType;
  }


  public String getContractName() {
    return contractName;
  }
  public void setContractName(String contractName) {
    this.contractName = contractName;
  }


  public String getContractNo() {
    return contractNo;
  }
  public void setContractNo(String contractNo) {
    this.contractNo = contractNo;
  }


  public Integer getNeadSeal() {
    return neadSeal;
  }
  public void setNeadSeal(Integer neadSeal) {
    this.neadSeal = neadSeal;
  }


  public Integer getDrawEmp() {
    return drawEmp;
  }
  public void setDrawEmp(Integer drawEmp) {
    this.drawEmp = drawEmp;
  }


  public Date getDrawTime() {
    return drawTime;
  }
  public void setDrawTime(Date drawTime) {
    this.drawTime = drawTime;
  }


  public Integer getNeedSubmit() {
    return needSubmit;
  }
  public void setNeedSubmit(Integer needSubmit) {
    this.needSubmit = needSubmit;
  }


  public Integer getClientId() {
    return clientId;
  }
  public void setClientId(Integer clientId) {
    this.clientId = clientId;
  }


  public Integer getCreateMan() {
    return createMan;
  }
  public void setCreateMan(Integer createMan) {
    this.createMan = createMan;
  }


  public Date getCreateTime() {
    return createTime;
  }
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }


  public String getRemark() {
    return remark;
  }
  public void setRemark(String remark) {
    this.remark = remark;
  }


  public String getAttId() {
    return attId;
  }
  public void setAttId(String attId) {
    this.attId = attId;
  }

}
