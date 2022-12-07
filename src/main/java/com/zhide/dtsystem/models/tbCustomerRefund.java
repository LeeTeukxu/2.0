package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbCustomerRefund")
public class tbCustomerRefund implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerRefundRequestID")
    private Integer customerRefundRequestId;
    @Column(name = "RefundRequestNumber")
    private String refundRequestNumber;
    @Column(name = "Applicant")
    private Integer applicant;
    @Column(name = "RefundType")
    private Integer refundType;
    @Column(name = "AgencyFeeAmount")
    private String agencyFeeAmount;
    @Column(name = "OfficalFeeAmount")
    private String officalFeeAmount;
    @Column(name = "DocumentNumber")
    private String documentNumber;
    @Column(name = "RefundMethod")
    private Integer refundMethod;
    @Column(name = "Bank")
    private String bank;
    @Column(name = "AccountNumber")
    private String accountNumber;
    @Column(name = "AccountName")
    private String accountName;
    @Column(name = "ReasonForReturn")
    private String reasonForReturn;
    @Column(name = "Approver")
    private Integer approver;
    @Column(name = "ApproverDate")
    private Date approverDate;
    @Column(name = "ApproverResult")
    private Integer approverResult;
    @Column(name = "ApproverDescription")
    private String approverDescription;
    @Column(name = "Reviewer")
    private Integer reviewer;
    @Column(name = "DateOfReview")
    private Date dateOfReview;
    @Column(name = "AuditResult")
    private Integer auditResult;
    @Column(name = "ReviewerDescription")
    private String reviewerDescription;
    @Column(name = "UserID")
    private Integer userId;
    @Column(name = "AddTime")
    private Date addTime;
    @Column(name="KHName")
    String khName;

    public Integer getCustomerRefundRequestId() {
        return customerRefundRequestId;
    }

    public void setCustomerRefundRequestId(Integer customerRefundRequestId) {
        this.customerRefundRequestId = customerRefundRequestId;
    }

    public String getRefundRequestNumber() {
        return refundRequestNumber;
    }

    public void setRefundRequestNumber(String refundRequestNumber) {
        this.refundRequestNumber = refundRequestNumber;
    }

    public Integer getApplicant() {
        return applicant;
    }

    public void setApplicant(Integer applicant) {
        this.applicant = applicant;
    }

    public Integer getRefundType() {
        return refundType;
    }

    public void setRefundType(Integer refundType) {
        this.refundType = refundType;
    }

    public String getAgencyFeeAmount() {
        return agencyFeeAmount;
    }

    public void setAgencyFeeAmount(String agencyFeeAmount) {
        this.agencyFeeAmount = agencyFeeAmount;
    }

    public String getOfficalFeeAmount() {
        return officalFeeAmount;
    }

    public void setOfficalFeeAmount(String officalFeeAmount) {
        this.officalFeeAmount = officalFeeAmount;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Integer getRefundMethod() {
        return refundMethod;
    }

    public void setRefundMethod(Integer refundMethod) {
        this.refundMethod = refundMethod;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getReasonForReturn() {
        return reasonForReturn;
    }

    public void setReasonForReturn(String reasonForReturn) {
        this.reasonForReturn = reasonForReturn;
    }

    public Integer getApprover() {
        return approver;
    }

    public void setApprover(Integer approver) {
        this.approver = approver;
    }

    public Date getApproverDate() {
        return approverDate;
    }

    public void setApproverDate(Date approverDate) {
        this.approverDate = approverDate;
    }

    public Integer getApproverResult() {
        return approverResult;
    }

    public void setApproverResult(Integer approverResult) {
        this.approverResult = approverResult;
    }

    public String getApproverDescription() {
        return approverDescription;
    }

    public void setApproverDescription(String approverDescription) {
        this.approverDescription = approverDescription;
    }

    public Integer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Integer reviewer) {
        this.reviewer = reviewer;
    }

    public Date getDateOfReview() {
        return dateOfReview;
    }

    public void setDateOfReview(Date dateOfReview) {
        this.dateOfReview = dateOfReview;
    }

    public Integer getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(Integer auditResult) {
        this.auditResult = auditResult;
    }

    public String getReviewerDescription() {
        return reviewerDescription;
    }

    public void setReviewerDescription(String reviewerDescription) {
        this.reviewerDescription = reviewerDescription;
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
