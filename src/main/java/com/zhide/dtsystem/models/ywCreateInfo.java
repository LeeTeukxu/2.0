package com.zhide.dtsystem.models;

import java.util.Date;

/**
 * @ClassName ajCreateInfo
 * @Author xiao
 * @CreateTime 2020-07-16 21:14
 **/
public class ywCreateInfo {
    private String yId;
    private String casesId;
    private Date clientRequiredDate;
    private int num;
    private Date signTime;
    private String ytype;
    private double guan;
    private double dai;
    private double price;
    private double total;


    public String getCasesId() {
        return casesId;
    }

    public void setCasesId(String casesId) {
        this.casesId = casesId;
    }

    public Date getClientRequiredDate() {
        return clientRequiredDate;
    }

    public void setClientRequiredDate(Date clientRequiredDate) {
        this.clientRequiredDate = clientRequiredDate;
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

    public double getGuan() {
        return guan;
    }

    public void setGuan(double guan) {
        this.guan = guan;
    }

    public double getDai() {
        return dai;
    }

    public void setDai(double dai) {
        this.dai = dai;
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

    public String getYtype(){return ytype;}

    public void setYtype(String ytype){this.ytype=ytype;}

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
