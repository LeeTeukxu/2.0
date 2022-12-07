package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbOriginalExpress")
public class tbOriginalExpress implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OriginalExpressID")
    private Integer originalExpressId;
    @Column(name = "OriginalStates")
    private Integer originalStates;
    @Column(name = "CheckInTime")
    private Date checkInTime;
    @Column(name = "RegisterPerson")
    private Integer registerPerson;
    @Column(name = "Coding")
    private String coding;
    @Column(name = "PickUpNumber")
    private String pickUpNumber;
    @Column(name = "Action")
    private String action;
    @Column(name = "PackageNum")
    private String packageNum;
    @Column(name = "PickUp")
    private String pickUp;
    @Column(name = "PickUpTime")
    private Date pickUpTime;
    @Column(name = "PickUpApplicant")
    private Integer pickUpApplicant;
    @Column(name = "PickUpApplicationTime")
    private Date pickUpApplicationTime;

    public Integer getOriginalExpressId() {
        return originalExpressId;
    }

    public void setOriginalExpressId(Integer originalExpressId) {
        this.originalExpressId = originalExpressId;
    }

    public Integer getOriginalStates() {
        return originalStates;
    }

    public void setOriginalStates(Integer originalStates) {
        this.originalStates = originalStates;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Integer getRegisterPerson() {
        return registerPerson;
    }

    public void setRegisterPerson(Integer registerPerson) {
        this.registerPerson = registerPerson;
    }

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
    }

    public String getPickUpNumber() {
        return pickUpNumber;
    }

    public void setPickUpNumber(String pickUpNumber) {
        this.pickUpNumber = pickUpNumber;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPackageNum() {
        return packageNum;
    }

    public void setPackageNum(String packageNum) {
        this.packageNum = packageNum;
    }

    public String getPickUp() {
        return pickUp;
    }

    public void setPickUp(String pickUp) {
        this.pickUp = pickUp;
    }

    public Date getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(Date pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public Integer getPickUpApplicant() {
        return pickUpApplicant;
    }

    public void setPickUpApplicant(Integer pickUpApplicant) {
        this.pickUpApplicant = pickUpApplicant;
    }

    public Date getPickUpApplicationTime() {
        return pickUpApplicationTime;
    }

    public void setPickUpApplicationTime(Date pickUpApplicationTime) {
        this.pickUpApplicationTime = pickUpApplicationTime;
    }
}
