package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CasesYwItems")
public class casesYwItems implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "CasesID")
    private String casesId;
    @Column(name = "ClientID")
    private Integer clientId;
    @Column(name = "SignDate")
    private Date signDate;
    @Column(name = "YName")
    private String yName;
    @Column(name = "YID")
    private String yid;
    @Column(name = "YType")
    private String yType;
    @Column(name = "Price")
    private Double price;
    @Column(name = "Num")
    private Integer num;
    @Column(name = "Dai")
    private Double dai;
    @Column(name = "Guan")
    private Double guan;
    @Column(name = "Total")
    private Double total;
    @Column(name = "CreateTime")
    private Date createTime;
    @Column(name = "CreateMan")
    private Integer createMan;
    @Column(name = "TechMan")
    private Integer techMan;
    @Column(name = "TechManager")
    private String techManager;
    @Column(name = "TradeName")
    private String tradeName;
    @Column(name = "FilingTime")
    private Date filingTime;
    @Column(name = "SHENQINGH")
    private String shenqingh;
    @Column(name = "Whether")
    private Integer whether;
    @Column(name = "WhetherTime")
    private Date whetherTime;
    @Column(name = "WhetherReject")
    private Integer whetherReject;
    @Column(name = "RejectionDate")
    private Date rejectionDate;
    @Column(name = "Category")
    private Integer category;
    @Column(name = "CanUse")
    private Boolean canUse;
    @Column(name = "ProcessState")
    private Integer processState;
    @Column(name = "SubNo")
    private String subNo;
    @Column(name = "SubID")
    private String subId;
    @Column(name = "ClientRequiredDate")
    private Date clientRequiredDate;
    @Column(name = "TCName")
    private String tcName;
    @Column(name = "TCCategory")
    private String tcCategory;
    @Column(name = "TechFiles")
    private String techFiles;
    @Column(name = "TechSBMemo")
    private String techSbMemo;
    @Column(name = "TechSBResult")
    private Integer techSbResult;
    @Column(name = "SQText")
    private String sqText;
    @Column(name = "CGText")
    private String cgText;
    @Column(name = "Memo")
    private String memo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getCasesId() {
        return casesId;
    }

    public void setCasesId(String casesId) {
        this.casesId = casesId;
    }


    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }


    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }


    public String getYName() {
        return yName;
    }

    public void setYName(String yName) {
        this.yName = yName;
    }


    public String getYid(){return yid;}

    public void setYid(String yid){this.yid=yid;}


    public String getYType() {
        return yType;
    }

    public void setYType(String yType) {
        this.yType = yType;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }


    public Double getDai() {
        return dai;
    }

    public void setDai(Double dai) {
        this.dai = dai;
    }


    public Double getGuan() {
        return guan;
    }

    public void setGuan(Double guan) {
        this.guan = guan;
    }


    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
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


    public Integer getTechMan() {
        return techMan;
    }

    public void setTechMan(Integer techMan) {
        this.techMan = techMan;
    }

    public String getTechManager() {
        return techManager;
    }

    public void setTechManager(String techManager) {
        this.techManager = techManager;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public Date getFilingTime() {
        return filingTime;
    }

    public void setFilingTime(Date filingTime) {
        this.filingTime = filingTime;
    }

    public String getShenqingh() {
        return shenqingh;
    }

    public void setShenqingh(String shenqingh) {
        this.shenqingh = shenqingh;
    }

    public Integer getWhether() {
        return whether;
    }

    public void setWhether(Integer whether) {
        this.whether = whether;
    }

    public Date getWhetherTime() {
        return whetherTime;
    }

    public void setWhetherTime(Date whetherTime) {
        this.whetherTime = whetherTime;
    }

    public Integer getWhetherReject() {
        return whetherReject;
    }

    public void setWhetherReject(Integer whetherReject) {
        this.whetherReject = whetherReject;
    }

    public Date getRejectionDate() {
        return rejectionDate;
    }

    public void setRejectionDate(Date rejectionDate) {
        this.rejectionDate = rejectionDate;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }


    public Boolean getCanUse() {
        return canUse;
    }

    public void setCanUse(Boolean canUse) {
        this.canUse = canUse;
    }


    public Integer getProcessState(){return processState;}

    public void setProcessState(Integer processState){this.processState=processState;}


    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }


    public String getSubNo(){return subNo;}

    public void setSubNo(String subNo){this.subNo=subNo;}

    public Date getClientRequiredDate() {
        return clientRequiredDate;
    }

    public void setClientRequiredDate(Date clientRequiredDate) {
        this.clientRequiredDate = clientRequiredDate;
    }

    public String getTcName(){return tcName;}

    public void setTcName(String tcName){this.tcName=tcName;}

    public String getTcCategory(){return tcCategory;}

    public void setTcCategory(String tcCategory){this.tcCategory=tcCategory;}

    public String getTechFiles(){return techFiles;}

    public void setTechFiles(String techFiles){this.techFiles=techFiles;}

    public String getTechSbMemo() {
        return techSbMemo;
    }

    public void setTechSbMemo(String techSbMemo) {
        this.techSbMemo = techSbMemo;
    }


    public Integer getTechSbResult() {
        return techSbResult;
    }

    public void setTechSbResult(Integer techSbResult) {
        this.techSbResult = techSbResult;
    }

    public String getSqText() {
        return sqText;
    }

    public void setSqText(String sqText) {
        this.sqText = sqText;
    }

    public String getCgText() {
        return cgText;
    }

    public void setCgText(String cgText) {
        this.cgText = cgText;
    }

    public String getMemo(){return memo;}

    public void setMemo(String memo){this.memo=memo;}
}
