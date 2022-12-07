package com.zhide.dtsystem.models;

import java.util.Date;

/**
 * @ClassName ajCreateInfo
 * @Author xiao
 * @CreateTime 2020-07-16 21:14
 **/
public class ajCreateInfo {
    private Date signTime;
    //private Date clientRequiredDate;
    private String yId;
    private double guanMoney;
    private double daiMoney;
    private double price;
    private int num;
    private int cLevel;
    private String casesId;
    private String supportMan;
    private  Integer hasTech;
    private Integer requiredDays;

    public String getSupportMan() {
        return supportMan;
    }

    public void setSupportMan(String supportMan) {
        this.supportMan = supportMan;
    }

//    public Date getClientRequiredDate() {
//        return clientRequiredDate;
//    }
//
//    public void setClientRequiredDate(Date clientRequiredDate) {
//        this.clientRequiredDate = clientRequiredDate;
//    }

    public String getCasesId() {
        return casesId;
    }

    public void setCasesId(String casesId) {
        this.casesId = casesId;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public String getyId() {
        return yId;
    }

    public void setyId(String yId) {
        this.yId = yId;
    }

    public double getGuanMoney() {
        return guanMoney;
    }

    public void setGuanMoney(double guanMoney) {
        this.guanMoney = guanMoney;
    }

    public double getDaiMoney() {
        return daiMoney;
    }

    public void setDaiMoney(double daiMoney) {
        this.daiMoney = daiMoney;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getcLevel() {
        return cLevel;
    }

    public void setcLevel(int cLevel) {
        this.cLevel = cLevel;
    }

    public Integer getHasTech() {
        return hasTech;
    }

    public void setHasTech(Integer hasTech) {
        this.hasTech = hasTech;
    }

    public Integer getRequiredDays() {
        return requiredDays;
    }

    public void setRequiredDays(Integer requiredDays) {
        this.requiredDays = requiredDays;
    }
}
