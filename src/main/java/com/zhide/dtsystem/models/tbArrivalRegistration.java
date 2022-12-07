package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbArrivalRegistration")
public class tbArrivalRegistration implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ArrivalRegistrationID")
    private Integer arrivalRegistrationId;
    @Column(name = "DocumentNumber")
    private String documentNumber;
    @Column(name = "DateOfPayment")
    private Date dateOfPayment;
    @Column(name = "PaymentMethod")
    private Integer paymentMethod;
    @Column(name = "PaymentAccount")
    private String paymentAccount;
    @Column(name = "PaymentAmount")
    private String paymentAmount;
    @Column(name = "Payer")
    private String payer;
    @Column(name = "ReturnBank")
    private String returnBank;
    @Column(name = "Description")
    private String description;
    @Column(name = "SignMan")
    private Integer signMan;
    @Column(name = "Claimant")
    private Integer claimant;
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
    private Integer claimStatus;
    @Column(name = "Reviewer")
    private Integer reviewer;
    @Column(name = "ReviewerDate")
    private Date reviewerDate;
    @Column(name = "Note")
    private String note;
    @Column(name = "ReviewerStatus")
    private Integer reviewerStatus;
    @Column(name = "UserID")
    private Integer userId;
    @Column(name = "AddTime")
    private Date addTime;

    private String khName;
    @Column(name="AttIDS")
    private String attIDS;
    public Integer getArrivalRegistrationId() {
        return arrivalRegistrationId;
    }

    public void setArrivalRegistrationId(Integer arrivalRegistrationId) {
        this.arrivalRegistrationId = arrivalRegistrationId;
    }


    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }


    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }


    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    public String getPaymentAccount() {
        return paymentAccount;
    }

    public void setPaymentAccount(String paymentAccount) {
        this.paymentAccount = paymentAccount;
    }


    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }


    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }


    public String getReturnBank() {
        return returnBank;
    }

    public void setReturnBank(String returnBank) {
        this.returnBank = returnBank;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Integer getSignMan() {
        return signMan;
    }

    public void setSignMan(Integer signMan) {
        this.signMan = signMan;
    }


    public Integer getClaimant() {
        if(claimant==null)claimant=0;
        return claimant;
    }

    public void setClaimant(Integer claimant) {
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


    public Integer getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(Integer claimStatus) {
        this.claimStatus = claimStatus;
    }


    public Integer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Integer reviewer) {
        this.reviewer = reviewer;
    }


    public Date getReviewerDate() {
        return reviewerDate;
    }

    public void setReviewerDate(Date reviewerDate) {
        this.reviewerDate = reviewerDate;
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public Integer getReviewerStatus() {
        if(reviewerStatus==null) reviewerStatus=0;
        return reviewerStatus;
    }

    public void setReviewerStatus(Integer reviewerStatus) {
        this.reviewerStatus = reviewerStatus;
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

    public String getAttIDS() {
        return attIDS;
    }

    public void setAttIDS(String attIDS) {
        this.attIDS = attIDS;
    }
}
