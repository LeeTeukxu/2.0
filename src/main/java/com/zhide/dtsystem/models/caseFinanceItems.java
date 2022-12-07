package com.zhide.dtsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CaseFinanceItems")
public class caseFinanceItems implements Serializable {
    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "CasesID")
    private String casesId;
    @Column(name = "ClientID")
    private int clientId;
    @Column(name = "Name")
    private String name;
    @Column(name = "Price")
    private String price;
    @Column(name = "Num")
    private int num;
    @Column(name = "CreateTime")
    private Date createTime;
    @Column(name = "HasConfirm")
    private boolean hasConfirm;
    @Column(name = "ConfirmDate")
    private Date confirmDate;
    @Column(name = "CreateMan")
    private int createMan;
    @Column(name = "ConfirmMan")
    private int confirmMan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getCasesId() {
        return casesId;
    }

    public void setCasesId(String casesId) {
        this.casesId = casesId;
    }


    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public boolean getHasConfirm() {
        return hasConfirm;
    }

    public void setHasConfirm(boolean hasConfirm) {
        this.hasConfirm = hasConfirm;
    }


    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }


    public int getCreateMan() {
        return createMan;
    }

    public void setCreateMan(int createMan) {
        this.createMan = createMan;
    }


    public int getConfirmMan() {
        return confirmMan;
    }

    public void setConfirmMan(int confirmMan) {
        this.confirmMan = confirmMan;
    }

}
