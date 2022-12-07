package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "FinancialInitial")
public class FinancialInitial implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FinancialInitialID")
    private Integer financialInitialId;
    @Column(name = "CustomerID")
    private Integer customerId;
    @Column(name = "InitialState")
    private Integer initialState;
    @Column(name = "OfficalFeeAmount")
    private String officalFeeAmount;
    @Column(name = "OfficalFeeAmountDetails")
    private String officalFeeAmountDetails;
    @Column(name = "AgencyFeeAmount")
    private String agencyFeeAmount;
    @Column(name = "AgencyFeeAmountDetails")
    private String agencyFeeAmountDetails;
    @Column(name = "UserID")
    private Integer userId;
    @Column(name = "AddTime")
    private Date addTime;
    @Transient
    private String khName;

    public Integer getFinancialInitialId() {
        return financialInitialId;
    }

    public void setFinancialInitialId(Integer financialInitialId) {
        this.financialInitialId = financialInitialId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getInitialState() {
        return initialState;
    }

    public void setInitialState(Integer initialState) {
        this.initialState = initialState;
    }

    public String getOfficalFeeAmount() {
        return officalFeeAmount;
    }

    public void setOfficalFeeAmount(String officalFeeAmount) {
        this.officalFeeAmount = officalFeeAmount;
    }

    public String getOfficalFeeAmountDetails() {
        return officalFeeAmountDetails;
    }

    public void setOfficalFeeAmountDetails(String officalFeeAmountDetails) {
        this.officalFeeAmountDetails = officalFeeAmountDetails;
    }

    public String getAgencyFeeAmount() {
        return agencyFeeAmount;
    }

    public void setAgencyFeeAmount(String agencyFeeAmount) {
        this.agencyFeeAmount = agencyFeeAmount;
    }

    public String getAgencyFeeAmountDetails() {
        return agencyFeeAmountDetails;
    }

    public void setAgencyFeeAmountDetails(String agencyFeeAmountDetails) {
        this.agencyFeeAmountDetails = agencyFeeAmountDetails;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getKhName() {
        return khName;
    }

    public void setKhName(String khName) {
        this.khName = khName;
    }
}
