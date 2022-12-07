package com.zhide.dtsystem.models;

import java.util.Date;

@ExcelTitle(value = "开票申请信息表", end = 10)
public class InvoiceApplicationExcelTemplate implements IExcelExportTemplate {
    @ExcelColumn(title = "申请人", columnIndex = 0)
    private String SQR;
    @ExcelColumn(dataType = "Date", format = "yyyy-MM-dd", title = "申请日期", columnIndex = 1)
    private Date AddTime;
    @ExcelColumn(title = "发票抬头", columnIndex = 2)
    private String InvoiceTT;
    @ExcelColumn(title = "纳税人识别号", columnIndex = 3)
    private String TaxpayerIdentificationNumber;
    @ExcelColumn(title = "金额", columnIndex = 4)
    private String Amount;
    @ExcelColumn(columnIndex = 5, title = "款项到款情况", dataSource = "{'1':'未到账','2':'已到账','3':'部分到账'}")
    private int PaymentToPayment;
    @ExcelColumn(title = "联系人信息", columnIndex = 6)
    private String EmailAddress;
    @ExcelColumn(title = "发票号", columnIndex = 7)
    private String ReceiptNumber;
    @ExcelColumn(title = "快递公司", columnIndex = 8)
    private String CourierCompany;
    @ExcelColumn(title = "快递号", columnIndex = 9)
    private String ExpressNumber;
    @ExcelColumn(title = "财务审批意见", columnIndex = 10)
    private String Remarks;

    public String getSQR() {
        return SQR;
    }

    public void setSQR(String SQR) {
        this.SQR = SQR;
    }

    public Date getAddTime() {
        return AddTime;
    }

    public void setAddTime(Date AddTime) {
        this.AddTime = AddTime;
    }

    public String getInvoiceTT() {
        return InvoiceTT;
    }

    public void setInvoiceTT(String InvoiceTT) {
        this.InvoiceTT = InvoiceTT;
    }

    public String getTaxpayerIdentificationNumber() {
        return TaxpayerIdentificationNumber;
    }

    public void setTaxpayerIdentificationNumber(String TaxpayerIdentificationNumber) {
        this.TaxpayerIdentificationNumber = TaxpayerIdentificationNumber;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String Amount) {
        this.Amount = Amount;
    }

    public int getPaymentToPayment() {
        return PaymentToPayment;
    }

    public void setPaymentToPayment(int PaymentToPayment) {
        this.PaymentToPayment = PaymentToPayment;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String EmailAddress) {
        this.EmailAddress = EmailAddress;
    }

    public String getReceiptNumber() {
        return ReceiptNumber;
    }

    public void setReceiptNumber(String ReceiptNumber) {
        this.ReceiptNumber = ReceiptNumber;
    }

    public String getCourierCompany() {
        return CourierCompany;
    }

    public void setCourierCompany(String CourierCompany) {
        this.CourierCompany = CourierCompany;
    }

    public String getExpressNumber() {
        return ExpressNumber;
    }

    public void setExpressNumber(String ExpressNumber) {
        this.ExpressNumber = ExpressNumber;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String Remarks) {
        this.Remarks = Remarks;
    }
}
