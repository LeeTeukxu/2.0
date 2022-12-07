package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CompanyConfig")
public class companyConfig implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private String id;
    @Column(name = "CompanyCode")
    private String companyCode;
    @Column(name = "Name")
    private String name;
    @Column(name = "Phone")
    private String phone;
    @Column(name = "Address")
    private String address;
    @Column(name = "Memo")
    private String memo;
    @Column(name = "Range")
    private String range;
    @Column(name = "BeginTime")
    private Date beginTime;
    @Column(name = "EndTime")
    private Date endTime;
    @Column(name = "UserNumbers")
    private Integer userNumbers;
    @Column(name = "UserRoles")
    private String userRoles;
    @Column(name = "CanUse")
    private Boolean canUse;
    @Column(name = "Image")
    private String image;
    @Column(name = "BankNo")
    private String bankNo;
    @Column(name = "BankName")
    private String bankName;
    @Column(name = "BankMan")
    private String bankMan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }


    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }


    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    public Integer getUserNumbers() {
        return userNumbers;
    }

    public void setUserNumbers(Integer userNumbers) {
        this.userNumbers = userNumbers;
    }


    public String getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(String userRoles) {
        this.userRoles = userRoles;
    }


    public Boolean getCanUse() {
        return canUse;
    }

    public void setCanUse(Boolean canUse) {
        this.canUse = canUse;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }


    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }


    public String getBankMan() {
        return bankMan;
    }

    public void setBankMan(String bankMan) {
        this.bankMan = bankMan;
    }

}
