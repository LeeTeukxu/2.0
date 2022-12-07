package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CPCPackageMain")
public class cpcPackageMain   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;
  @Column(name = "PID")
  private String pid;
  @Column(name = "SubID")
  private String subId;
  @Column(name = "DocSN")
  private String docSn;
  @Column(name = "SubNo")
  private String subNo;
  @Column(name = "YName")
  private String yName;
  @Column(name = "Famingmc")
  private String famingmc;
  @Column(name = "Shenqinglx")
  private Integer shenqinglx;
  @Column(name = "NBBH")
  private String nbbh;
  @Column(name = "FirstInventor")
  private String firstInventor;
  @Column(name = "FirstInventCountry")
  private String firstInventCountry;
  @Column(name = "FirstInventIDCode")
  private String firstInventIdCode;
  @Column(name = "SameApply")
  private Boolean sameApply;
  @Column(name = "AdvanceApply")
  private Boolean advanceApply;

  @Column(name = "AddSZSC")
  private Boolean addSZSC;

  @Column(name = "DigistImage")
  private String digistImage;
  @Column(name = "AbandonChangeRights")
  private Boolean abandonChangeRights;
  @Column(name = "RealMessage")
  private Boolean realMessage;
  @Column(name = "CreateMan")
  private Integer createMan;
  @Column(name = "CreateTime")
  private Date createTime;
  @Column(name = "AgentName")
  private String agentName;
  @Column(name = "AgentCode")
  private String agentCode;
  @Column(name = "ItemCount")
  private Integer itemCount;
  @Column(name = "PackageFilePath")
  private String packageFilePath;
  @Column(name = "PackageDirPath")
  private String packageDirPath;
  @Column(name = "PackageCreateTime")
  private Date packageCreateTime;
  @Column(name="Agents")
  private String agents;
  @Column(name = "PackageCreateMan")
  private Integer packageCreateMan;
  @Column(name="CreateType")
  private Integer createType;

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }

  public String getAgents() {
    return agents;
  }

  public void setAgents(String agents) {
    this.agents = agents;
  }

  public String getPid() {
    return pid;
  }
  public void setPid(String pid) {
    this.pid = pid;
  }


  public String getSubId() {
    return subId;
  }
  public void setSubId(String subId) {
    this.subId = subId;
  }


  public String getDocSn() {
    return docSn;
  }
  public void setDocSn(String docSn) {
    this.docSn = docSn;
  }


  public String getSubNo() {
    return subNo;
  }
  public void setSubNo(String subNo) {
    this.subNo = subNo;
  }


  public String getYName() {
    return yName;
  }
  public void setYName(String yName) {
    this.yName = yName;
  }


  public String getFamingmc() {
    return famingmc;
  }
  public void setFamingmc(String famingmc) {
    this.famingmc = famingmc;
  }


  public Integer getShenqinglx() {
    return shenqinglx;
  }
  public void setShenqinglx(Integer shenqinglx) {
    this.shenqinglx = shenqinglx;
  }


  public String getNbbh() {
    return nbbh;
  }
  public void setNbbh(String nbbh) {
    this.nbbh = nbbh;
  }


  public String getFirstInventor() {
    return firstInventor;
  }
  public void setFirstInventor(String firstInventor) {
    this.firstInventor = firstInventor;
  }


  public String getFirstInventCountry() {
    return firstInventCountry;
  }
  public void setFirstInventCountry(String firstInventCountry) {
    this.firstInventCountry = firstInventCountry;
  }


  public String getFirstInventIdCode() {
    return firstInventIdCode;
  }
  public void setFirstInventIdCode(String firstInventIdCode) {
    this.firstInventIdCode = firstInventIdCode;
  }


  public Boolean getSameApply() {
    return sameApply;
  }
  public void setSameApply(Boolean sameApply) {
    this.sameApply = sameApply;
  }


  public Boolean getAdvanceApply() {
    if(advanceApply==null)advanceApply=true;
    return advanceApply;
  }
  public void setAdvanceApply(Boolean advanceApply) {
    this.advanceApply = advanceApply;
  }


  public String getDigistImage() {
    return digistImage;
  }
  public void setDigistImage(String digistImage) {
    this.digistImage = digistImage;
  }


  public Boolean getAbandonChangeRights() {
    return abandonChangeRights;
  }
  public void setAbandonChangeRights(Boolean abandonChangeRights) {
    this.abandonChangeRights = abandonChangeRights;
  }


  public Boolean getRealMessage() {
    if(realMessage==null)realMessage=true;
    return realMessage;
  }
  public void setRealMessage(Boolean realMessage) {
    this.realMessage = realMessage;
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


  public String getAgentName() {
    return agentName;
  }
  public void setAgentName(String agentName) {
    this.agentName = agentName;
  }


  public String getAgentCode() {
    return agentCode;
  }
  public void setAgentCode(String agentCode) {
    this.agentCode = agentCode;
  }


  public Integer getItemCount() {
    return itemCount;
  }
  public void setItemCount(Integer itemCount) {
    this.itemCount = itemCount;
  }


  public String getPackageFilePath() {
    return packageFilePath;
  }
  public void setPackageFilePath(String packageFilePath) {
    this.packageFilePath = packageFilePath;
  }


  public String getPackageDirPath() {
    return packageDirPath;
  }
  public void setPackageDirPath(String packageDirPath) {
    this.packageDirPath = packageDirPath;
  }


  public Date getPackageCreateTime() {
    return packageCreateTime;
  }
  public void setPackageCreateTime(Date packageCreateTime) {
    this.packageCreateTime = packageCreateTime;
  }


  public Integer getPackageCreateMan() {
    return packageCreateMan;
  }
  public void setPackageCreateMan(Integer packageCreateMan) {
    this.packageCreateMan = packageCreateMan;
  }

  public Boolean getAddSZSC() {
    if(addSZSC==null)addSZSC=false;
    return addSZSC;
  }

  public void setAddSZSC(Boolean addSZSC) {
    this.addSZSC = addSZSC;
  }

  public Integer getCreateType() {
    return createType;
  }

  public void setCreateType(Integer createType) {
    this.createType = createType;
  }
}
