package com.zhide.dtsystem.repositorys;


import com.zhide.dtsystem.models.tbCustomerRefund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface tbCustomerRefundRepository extends JpaRepository<tbCustomerRefund,Integer> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbCustomerRefund SET ReasonForReturn=:ReasonForReturn," +
            "RefundRequestNumber=:RefundRequestNumber," +
            "Applicant=:Applicant," +
            "RefundType=:RefundType," +
            "AgencyFeeAmount=:AgencyFeeAmount," +
            "OfficalFeeAmount=:OfficalFeeAmount," +
            "DocumentNumber=:DocumentNumber," +
            "RefundMethod=:RefundMethod," +
            "Bank=:Bank," +
            "AccountNumber=:AccountNumber," +
            "AccountName=:AccountName," +
            "AddTime=:AddTime," +
            "UserID=:UserID WHERE CustomerRefundRequestID=:CustomerRefundRequestID",nativeQuery = true)
    int UPDATE(@Param("ReasonForReturn") String ReasonForReturn,
               @Param("RefundRequestNumber") String RefundRequestNumber,
               @Param("Applicant") Integer Applicant,
               @Param("RefundType") Integer RefundType,
               @Param("AgencyFeeAmount") String AgencyFeeAmount,
               @Param("OfficalFeeAmount") String OfficalFeeAmount,
               @Param("DocumentNumber") String DocumentNumber,
               @Param("RefundMethod") Integer RefundMethod,
               @Param("Bank") String Bank,
               @Param("AccountNumber") String AccountNumber,
               @Param("AccountName") String AccountName,
               @Param("AddTime") Date AddTime,
               @Param("UserID") Integer UserID,
               @Param("CustomerRefundRequestID") Integer CustomerRefundRequestID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbCustomerRefund SET Approver=:Approver," +
            "ApproverDate=:ApproverDate," +
            "ApproverResult=:ApproverResult," +
            "ApproverDescription=:ApproverDescription WHERE CustomerRefundRequestID=:CustomerRefundRequestID",nativeQuery = true)
    int updateJinLi(@Param("Approver") Integer Approver,
                   @Param("ApproverDate") Date ApproverDate,
                   @Param("ApproverResult") Integer ApproverResult,
                   @Param("ApproverDescription") String ApproverDescription,
                   @Param("CustomerRefundRequestID") Integer CustomerRefundRequestID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbCustomerRefund SET Reviewer=:Reviewer," +
            "DateOfReview=:DateOfReview," +
            "AuditResult=:AuditResult," +
            "ReviewerDescription=:ReviewerDescription WHERE CustomerRefundRequestID=:CustomerRefundRequestID",nativeQuery = true)
    int updateCaiWu(@Param("Reviewer") Integer Reviewer,
                    @Param("DateOfReview") Date DateOfReview,
                    @Param("AuditResult") Integer AuditResult,
                    @Param("ReviewerDescription") String ReviewerDescription,
                    @Param("CustomerRefundRequestID") Integer CustomerRefundRequestID);


    List<tbCustomerRefund> findAllByDocumentNumber(String DocumentNumber);
}
