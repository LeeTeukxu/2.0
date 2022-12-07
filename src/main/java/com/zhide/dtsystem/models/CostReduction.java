package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CostReduction")
public class CostReduction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CostReductionID")
    private Integer costReductionId;
    @Column(name = "UUID")
    private String uUId;
    @Column(name = "UserID")
    private Integer userId;
    @Column(name = "AddTime")
    private Date addTime;
    @Column(name = "CustomerID")
    private Integer customerId;
    @Column(name = "ReductionRequest")
    private String reductionRequest;
    @Column(name = "ReductionRequestNumber")
    private String reductionRequestNumber;
    @Column(name = "InternalPeople")
    private Integer internalPeople;
    @Column(name = "InternalResult")
    private Integer internalResult;
    @Column(name = "InternalDate")
    private Date internalDate;
    @Column(name = "Transactor")
    private Integer transactor;
    @Column(name = "DealTime")
    private Date dealTime;
    @Column(name = "GzjZt")
    private Integer gzjZt;
    @Column(name = "TheSuccess")
    private Integer theSuccess;
    @Column(name = "ReductionTheYear")
    private String reductionTheYear;

    @Transient
    private String clientIdName;

    public String getClientIdName() {
        return clientIdName;
    }

    public void setClientIdName(String clientIdName) {
        this.clientIdName = clientIdName;
    }

    public Integer getCostReductionId() {
        return costReductionId;
    }

    public void setCostReductionId(Integer costReductionId) {
        this.costReductionId = costReductionId;
    }

    public String getuUId() {
        return uUId;
    }

    public void setUUId(String uUId) {
        this.uUId = uUId;
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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getReductionRequest() {
        return reductionRequest;
    }

    public void setReductionRequest(String reductionRequest) {
        this.reductionRequest = reductionRequest;
    }

    public String getReductionRequestNumber() {
        return reductionRequestNumber;
    }

    public void setReductionRequestNumber(String reductionRequestNumber) {
        this.reductionRequestNumber = reductionRequestNumber;
    }

    public Integer getInternalPeople() {
        return internalPeople;
    }

    public void setInternalPeople(Integer internalPeople) {
        this.internalPeople = internalPeople;
    }

    public Integer getInternalResult() {
        return internalResult;
    }

    public void setInternalResult(Integer internalResult) {
        this.internalResult = internalResult;
    }

    public Date getInternalDate() {
        return internalDate;
    }

    public void setInternalDate(Date internalDate) {
        this.internalDate = internalDate;
    }

    public Integer getTransactor() {
        return transactor;
    }

    public void setTransactor(Integer transactor) {
        this.transactor = transactor;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public Integer getGzjZt() {
        return gzjZt;
    }

    public void setGzjZt(Integer gzjZt) {
        this.gzjZt = gzjZt;
    }

    public Integer getTheSuccess() {
        return theSuccess;
    }

    public void setTheSuccess(Integer theSuccess) {
        this.theSuccess = theSuccess;
    }

    public String getReductionTheYear() {
        return reductionTheYear;
    }

    public void setReductionTheYear(String reductionTheYear) {
        this.reductionTheYear = reductionTheYear;
    }
}
