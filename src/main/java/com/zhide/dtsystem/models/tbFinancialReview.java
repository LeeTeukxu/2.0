package com.zhide.dtsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbFinancialReview")
public class tbFinancialReview implements Serializable {
    @Id
    @Column(name = "FinancialReviewID")
    private int financialReviewId;
    @Column(name = "ArrivalRegistrationID")
    private int arrivalRegistrationId;
    @Column(name = "Reviewer")
    private int reviewer;
    @Column(name = "ReviewerDate")
    private Date reviewerDate;
    @Column(name = "Note")
    private String note;
    @Column(name = "ReviewerStatus")
    private int reviewerStatus;
    @Column(name = "UserID")
    private int userId;
    @Column(name = "AddTime")
    private Date addTime;

    public int getFinancialReviewId() {
        return financialReviewId;
    }

    public void setFinancialReviewId(int financialReviewId) {
        this.financialReviewId = financialReviewId;
    }


    public int getArrivalRegistrationId() {
        return arrivalRegistrationId;
    }

    public void setArrivalRegistrationId(int arrivalRegistrationId) {
        this.arrivalRegistrationId = arrivalRegistrationId;
    }


    public int getReviewer() {
        return reviewer;
    }

    public void setReviewer(int reviewer) {
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


    public int getReviewerStatus() {
        return reviewerStatus;
    }

    public void setReviewerStatus(int reviewerStatus) {
        this.reviewerStatus = reviewerStatus;
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
