package com.zhide.dtsystem.models;

import java.util.Date;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbInvoiceApplication")
public class tbInvoiceApplication   implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "InvoiceApplicationID")
  private Integer invoiceApplicationId;
  @Column(name = "State")
  private Integer state;
  @Column(name = "DateOfApplication")
  private Date dateOfApplication;
  @Column(name = "Applicant")
  private Integer applicant;
  @Column(name = "ContractNo")
  private String contractNo;
  @Column(name = "BankOfArrival")
  private Integer bankOfArrival;
  @Column(name = "InvoiceType")
  private Integer invoiceType;
  @Column(name = "InvoiceTT")
  private String invoiceTt;
  @Column(name = "TaxpayerIdentificationNumber")
  private String taxpayerIdentificationNumber;
  @Column(name = "ProjectName")
  private String projectName;
  @Column(name = "PurchaserAddress")
  private String purchaserAddress;

  @Column(name = "PurchaserPhoneNumber")
  private String purchaserPhoneNumber;

  @Column(name = "PurchaserBank")
  private String purchaserBank;

  @Column(name = "PurchaserAccount")
  private String purchaserAccount;

  @Column(name = "Amount")
  private String amount;
  @Column(name = "PaymentToPayment")
  private String paymentToPayment;
  @Column(name = "EmailAddress")
  private String emailAddress;
  @Column(name = "ReceiptNumber")
  private String receiptNumber;
  @Column(name = "CourierCompany")
  private String courierCompany;
  @Column(name = "ExpressNumber")
  private String expressNumber;
  @Column(name = "UploadTime")
  private Date uploadTime;
  @Column(name = "IsSend")
  private Integer isSend;
  @Column(name = "Remarks")
  private String remarks;
  @Column(name = "UserID")
  private Integer userId;
  @Column(name = "AddTime")
  private Date addTime;
  @Column(name = "ClientID")
  private Integer clientId;
  @Column(name = "InvoiceForm")
  private Integer invoiceForm;
  @Column(name = "TickCompany")
  private Integer tickCompany;
  @Column(name = "AuditMan")
  private Integer auditMan;
  @Column(name = "AuditTime")
  private Date auditTime;
  @Column(name = "AuditResult")
  private Integer auditResult;
  @Column(name = "AuditText")
  private String auditText;
  @Column(name="ReceiveTime")
  Date receiveTime;
  @Column(name="CompanyOfArrival")
  String companyOfArrival;

  public Integer getState() {
    return state;
  }
  public void setState(Integer state) {
    this.state = state;
  }


  public Integer getInvoiceApplicationId() {
    return invoiceApplicationId;
  }
  public void setInvoiceApplicationId(Integer invoiceApplicationId) {
    this.invoiceApplicationId = invoiceApplicationId;
  }


  public Date getDateOfApplication() {
    return dateOfApplication;
  }
  public void setDateOfApplication(Date dateOfApplication) {
    this.dateOfApplication = dateOfApplication;
  }


  public Integer getApplicant() {
    return applicant;
  }
  public void setApplicant(Integer applicant) {
    this.applicant = applicant;
  }


  public String getContractNo() {
    return contractNo;
  }
  public void setContractNo(String contractNo) {
    this.contractNo = contractNo;
  }


  public Integer getBankOfArrival() {
    return bankOfArrival;
  }
  public void setBankOfArrival(Integer bankOfArrival) {
    this.bankOfArrival = bankOfArrival;
  }


  public Integer getInvoiceType() {
    return invoiceType;
  }
  public void setInvoiceType(Integer invoiceType) {
    this.invoiceType = invoiceType;
  }


  public String getInvoiceTt() {
    return invoiceTt;
  }
  public void setInvoiceTt(String invoiceTt) {
    this.invoiceTt = invoiceTt;
  }


  public String getTaxpayerIdentificationNumber() {
    return taxpayerIdentificationNumber;
  }
  public void setTaxpayerIdentificationNumber(String taxpayerIdentificationNumber) {
    this.taxpayerIdentificationNumber = taxpayerIdentificationNumber;
  }


  public String getProjectName() {
    return projectName;
  }
  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getPurchaserAddress() {
    return purchaserAddress;
  }

  public void setPurchaserAddress(String purchaserAddress) {
    this.purchaserAddress = purchaserAddress;
  }

  public String getPurchaserPhoneNumber() {
    return purchaserPhoneNumber;
  }

  public void setPurchaserPhoneNumber(String purchaserPhoneNumber) {
    this.purchaserPhoneNumber = purchaserPhoneNumber;
  }

  public String getPurchaserBank() {
    return purchaserBank;
  }

  public void setPurchaserBank(String purchaserBank) {
    this.purchaserBank = purchaserBank;
  }

  public String getPurchaserAccount() {
    return purchaserAccount;
  }

  public void setPurchaserAccount(String purchaserAccount) {
    this.purchaserAccount = purchaserAccount;
  }

  public String getAmount() {
    return amount;
  }
  public void setAmount(String amount) {
    this.amount = amount;
  }


  public String getPaymentToPayment() {
    return paymentToPayment;
  }
  public void setPaymentToPayment(String paymentToPayment) {
    this.paymentToPayment = paymentToPayment;
  }


  public String getEmailAddress() {
    return emailAddress;
  }
  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }


  public String getReceiptNumber() {
    return receiptNumber;
  }
  public void setReceiptNumber(String receiptNumber) {
    this.receiptNumber = receiptNumber;
  }


  public String getCourierCompany() {
    return courierCompany;
  }
  public void setCourierCompany(String courierCompany) {
    this.courierCompany = courierCompany;
  }


  public String getExpressNumber() {
    return expressNumber;
  }
  public void setExpressNumber(String expressNumber) {
    this.expressNumber = expressNumber;
  }


  public Date getUploadTime() {
    return uploadTime;
  }
  public void setUploadTime(Date uploadTime) {
    this.uploadTime = uploadTime;
  }


  public Integer getIsSend() {
    return isSend;
  }
  public void setIsSend(Integer isSend) {
    this.isSend = isSend;
  }


  public String getRemarks() {
    return remarks;
  }
  public void setRemarks(String remarks) {
    this.remarks = remarks;
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


  public Integer getClientId() {
    return clientId;
  }
  public void setClientId(Integer clientId) {
    this.clientId = clientId;
  }


  public Integer getInvoiceForm() {
    return invoiceForm;
  }
  public void setInvoiceForm(Integer invoiceForm) {
    this.invoiceForm = invoiceForm;
  }


  public Integer getTickCompany() {
    return tickCompany;
  }
  public void setTickCompany(Integer tickCompany) {
    this.tickCompany = tickCompany;
  }


  public Integer getAuditMan() {
    return auditMan;
  }
  public void setAuditMan(Integer auditMan) {
    this.auditMan = auditMan;
  }


  public Date getAuditTime() {
    return auditTime;
  }
  public void setAuditTime(Date auditTime) {
    this.auditTime = auditTime;
  }


  public Integer getAuditResult() {
    return auditResult;
  }
  public void setAuditResult(Integer auditResult) {
    this.auditResult = auditResult;
  }


  public String getAuditText() {
    return auditText;
  }
  public void setAuditText(String auditText) {
    this.auditText = auditText;
  }

  public Date getReceiveTime() {
    return receiveTime;
  }

  public void setReceiveTime(Date receiveTime) {
    this.receiveTime = receiveTime;
  }

  public String getCompanyOfArrival() {
    return companyOfArrival;
  }

  public void setCompanyOfArrival(String companyOfArrival) {
    this.companyOfArrival = companyOfArrival;
  }
}
