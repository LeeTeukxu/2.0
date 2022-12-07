package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbInvoiceApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

@Repository
public interface tbInvoiceApplicationRepository  extends  JpaRepository<tbInvoiceApplication,Integer>  {
    //Page<tbInvoiceApplication> findAllByDtID(int DtID, Pageable page);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbInvoiceApplication SET Remarks=:Remarks," +
            "ContractNo=:ContractNo," +
            "PaymentToPayment=:PaymentToPayment," +
            "BankOfArrival=:BankOfArrival," +
            "InvoiceType=:InvoiceType," +
            "InvoiceTT=:InvoiceTT," +
            "TaxpayerIdentificationNumber=:TaxpayerIdentificationNumber," +
            "ProjectName=:ProjectName," +
            "PurchaserAddressAndPhoneNumber=:PurchaserAddressAndPhoneNumber," +
            "PurchaserBankAndAccount=:PurchaserBankAndAccount," +
            "Amount=:Amount," +
            "InvoiceForm=:InvoiceForm,"+
            "EmailAddress=:EmailAddress," +
            "AddTime=:AddTime," +
            "UserID=:UserID," +
            "ClientID=:ClientID WHERE InvoiceApplicationID=:InvoiceApplicationID",nativeQuery = true)
    int UPDATE(@Param("Remarks") String Remarks,
               @Param("ContractNo") String ContractNo,
               @Param("PaymentToPayment") String PaymentToPayment,
               @Param("BankOfArrival") Integer BankOffArrival,
               @Param("InvoiceType") Integer InvoiceType,
               @Param("InvoiceTT") String InvoiceTT,
               @Param("TaxpayerIdentificationNumber") String TaxpayerIdentificationNumber,
               @Param("ProjectName") String ProjectName,
               @Param("PurchaserAddressAndPhoneNumber") String PurchaserAddressAndPhoneNumber,
               @Param("PurchaserBankAndAccount") String PurchaserBankAndAccount,
               @Param("Amount") String Amount,
               @Param("InvoiceForm") Integer InvoiceForm,
               @Param("EmailAddress") String EmailAddress,
               @Param("AddTime") Date AddTime,
               @Param("UserID") Integer UserID,
               @Param("ClientID") Integer ClientID,
               @Param("InvoiceApplicationID") Integer InvoiceApplicationID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbInvoiceApplication SET ReceiptNumber=:ReceiptNumber," +
            "CourierCompany=:CourierCompany," +
            "ExpressNumber=:ExpressNumber," +
            "IsSend=:IsSend WHERE InvoiceApplicationID=:InvoiceApplicationID",nativeQuery = true)
    int UPDATEExpressInfo(@Param("ReceiptNumber") String ReceiptNumber,
               @Param("CourierCompany") String CourierCompany,
               @Param("ExpressNumber") String ExpressNumber,
               @Param("IsSend") Integer IsSend,
               @Param("InvoiceApplicationID") Integer InvoiceApplicationID);
}
