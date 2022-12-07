package com.zhide.dtsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbClaim")
public class tbClaim implements Serializable {
    @Id
    @Column(name = "ClaimID")
    private int claimId;
    @Column(name = "ArrivalRegistrationID")
    private int arrivalRegistrationId;
    @Column(name = "Claimant")
    private int claimant;
    @Column(name = "ClaimDate")
    private Date claimDate;
    @Column(name = "CustomerID")
    private String customerId;
    @Column(name = "AgencyFee")
    private String agencyFee;
    @Column(name = "OfficalFee")
    private String officalFee;
    @Column(name = "Remark")
    private String remark;
    @Column(name = "ClaimStatus")
    private int claimStatus;
    @Column(name = "UserID")
    private int userId;
    @Column(name = "AddTime")
    private Date addTime;

    public int getClaimId() {
        return claimId;
    }

    public void setClaimId(int claimId) {
        this.claimId = claimId;
    }


    public int getArrivalRegistrationId() {
        return arrivalRegistrationId;
    }

    public void setArrivalRegistrationId(int arrivalRegistrationId) {
        this.arrivalRegistrationId = arrivalRegistrationId;
    }


    public int getClaimant() {
        return claimant;
    }

    public void setClaimant(int claimant) {
        this.claimant = claimant;
    }


    public Date getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(Date claimDate) {
        this.claimDate = claimDate;
    }


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


    public String getAgencyFee() {
        return agencyFee;
    }

    public void setAgencyFee(String agencyFee) {
        this.agencyFee = agencyFee;
    }


    public String getOfficalFee() {
        return officalFee;
    }

    public void setOfficalFee(String officalFee) {
        this.officalFee = officalFee;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public int getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(int claimStatus) {
        this.claimStatus = claimStatus;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

}
