package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbArrivalRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface tbArrivalRegistrationRepository  extends  JpaRepository<tbArrivalRegistration,Integer>  {

    List<tbArrivalRegistration> findAllByPayer(String payer);
    @Transactional
    @Modifying
    @Query(value = "UPDATE tbArrivalRegistration SET DocumentNumber=:DocumentNumber," +
            "DateOfPayment=:DateOfPayment," +
            "PaymentMethod=:PaymentMethod," +
            "PaymentAccount=:PaymentAccount," +
            "PaymentAmount=:PaymentAmount," +
            "Payer=:Payer," +
            "ReturnBank=:ReturnBank," +
            "AttIDS=:AttIDS,"+
            "Description=:Description WHERE ArrivalRegistrationID=:ArrivalRegistrationID",nativeQuery = true)
    int Update(@Param("DocumentNumber") String DocumentNumber,
               @Param("DateOfPayment") Date DateOfPayment,
               @Param("PaymentMethod") Integer PaymentMethod,
               @Param("PaymentAccount") String PaymentAccount,
               @Param("PaymentAmount") String PaymentAmount,
               @Param("Payer") String Payer,
               @Param("ReturnBank") String ReturnBank,
               @Param("Description") String Description,
               @Param("AttIDS") String AttIDS,
               @Param("ArrivalRegistrationID") Integer ArrivalRegistrationID);
    @Transactional
    @Modifying
    @Query(value = "UPDATE tbArrivalRegistration SET ClaimStatus=:ClaimStatus,AgencyFee='',OfficalFee='',CustomerID='',Remark='',ClaimDate=NULL,Claimant='' WHERE ArrivalRegistrationID=:ArrivalRegistrationID",nativeQuery = true)
    int UnUpdateReviewerStatus(@Param("ClaimStatus") Integer ClaimStatus,@Param("ArrivalRegistrationID") Integer ArrivalRegistrationID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbArrivalRegistration SET ClaimStatus=:ClaimStatus WHERE ArrivalRegistrationID=:ArrivalRegistrationID",nativeQuery = true)
    int UpdateReviewerStatus(@Param("ClaimStatus") Integer ClaimStatus,@Param("ArrivalRegistrationID") Integer ArrivalRegistrationID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbArrivalRegistration SET Claimant=:Claimant," +
            "ClaimDate=:ClaimDate," +
            "CustomerID=:CustomerID," +
            "AgencyFee=:AgencyFee," +
            "OfficalFee=:OfficalFee," +
            "Remark=:Remark," +
            "ClaimStatus=:ClaimStatus WHERE ArrivalRegistrationID=:ArrivalRegistrationID",nativeQuery = true)
    int updateRenLin(@Param("Claimant") Integer Claimant,
                     @Param("ClaimDate") Date ClaimDate,
                     @Param("CustomerID") String CustomerID,
                     @Param("AgencyFee") String AgencyFee,
                     @Param("OfficalFee") String OfficalFee,
                     @Param("Remark") String Remark,
                     @Param("ClaimStatus") Integer ClaimStatus,
                     @Param("ArrivalRegistrationID") Integer ArrivalRegistrationID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbClient SET cootype=:cootype WHERE ClientID=:ClientID",nativeQuery = true)
    int RenLinUpdateClientCooType(@Param("cootype") Integer cootype,
                                  @Param("ClientID") Integer ClientID);
    @Transactional
    @Modifying
    @Query(value = "UPDATE tbArrivalRegistration SET ReviewerStatus=0,Note='' WHERE ArrivalRegistrationID=:ArrivalRegistrationID",nativeQuery = true)
    int UpdateReviewerStatus(@Param("ArrivalRegistrationID") Integer ArrivalRegistrationID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbArrivalRegistration SET Reviewer=:Reviewer," +
            "ReviewerDate=:ReviewerDate," +
            "Note=:Note," +
            "ReviewerStatus=:ReviewerStatus WHERE ArrivalRegistrationID=:ArrivalRegistrationID",nativeQuery = true)
    int updateFuHe(@Param("Reviewer") Integer Reviewer,
                     @Param("ReviewerDate") Date ReviewerDate,
                     @Param("Note") String Note,
                     @Param("ReviewerStatus") Integer ReviewerStatus,
                     @Param("ArrivalRegistrationID") Integer ArrivalRegistrationID);
}
