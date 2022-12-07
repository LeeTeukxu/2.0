package com.zhide.dtsystem.models;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: highCreateInfo
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年11月24日 15:33
 **/
public class highCreateInfo implements Serializable {
    private Date signTime;
    private Date clientRequiredDate;
    private String yId;
    private double sjfMoney;
    private Integer containSJF;
    private String area;
    private String sbYear;
    private double daiMoney;
    private String swsName;
    private int num;
    private String casesId;
    private String supportMan;
    private String rptType;

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Date getClientRequiredDate() {
        return clientRequiredDate;
    }

    public void setClientRequiredDate(Date clientRequiredDate) {
        this.clientRequiredDate = clientRequiredDate;
    }

    public String getyId() {
        return yId;
    }

    public void setyId(String yId) {
        this.yId = yId;
    }

    public double getSjfMoney() {
        return sjfMoney;
    }

    public void setSjfMoney(double sjfMoney) {
        this.sjfMoney = sjfMoney;
    }

    public Integer getContainSJF() {
        return containSJF;
    }

    public void setContainSJF(Integer containSJF) {
        if (containSJF == null) containSJF = 0;
        this.containSJF = containSJF;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSbYear() {
        return sbYear;
    }

    public void setSbYear(String sbYear) {
        this.sbYear = sbYear;
    }

    public double getDaiMoney() {
        return daiMoney;
    }

    public void setDaiMoney(double daiMoney) {
        this.daiMoney = daiMoney;
    }

    public String getSwsName() {
        return swsName;
    }

    public void setSwsName(String swsName) {
        this.swsName = swsName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCasesId() {
        return casesId;
    }

    public void setCasesId(String casesId) {
        this.casesId = casesId;
    }

    public String getSupportMan() {
        return supportMan;
    }

    public void setSupportMan(String supportMan) {
        this.supportMan = supportMan;
    }

    public String getRptType() {
        return rptType;
    }

    public void setRptType(String rptType) {
        this.rptType = rptType;
    }
}
