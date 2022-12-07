package com.zhide.dtsystem.models;

import java.util.Date;

@ExcelTitle(value = "销售回款信息表", end = 16)
public class ArrivalRegistrationExcelTemplate implements IExcelExportTemplate {
    @ExcelColumn(title = "单据编号", columnIndex = 0)
    private String DocumentNumber;
    @ExcelColumn(dataType = "Date", format = "yyyy-MM-dd", title = "回款日期", columnIndex = 1)
    private Date DateOfPayment;
    @ExcelColumn(dataType = "Date", format = "yyyy-MM-dd", title = "登记日期", columnIndex = 2)
    private Date AddTime;
    @ExcelColumn(title = "登记人", columnIndex = 3)
    private String DJR;
    @ExcelColumn(title = "回款方式", columnIndex = 4, dataSource = "{'1':'现金','2':'转账','3':'支付宝','4':'微信'}")
    private int PaymentMethod;
    @ExcelColumn(title = "付款账户", columnIndex = 5)
    private String PaymentAccount;
    @ExcelColumn(columnIndex = 6, title = "回款金额")
    private String PaymentAmount;
    @ExcelColumn(title = "付款人", columnIndex = 7)
    private String Payer;
    @ExcelColumn(title = "回款银行", columnIndex = 8)
    private String ReturnBank;
    @ExcelColumn(title = "款项描述", columnIndex = 9)
    private String Description;
    @ExcelColumn(title = "认领状态", columnIndex = 10, dataSource = "{'1':'未认领','2':'已认领','3':'已拒绝,重认领'}")
    private int ClaimStatus;
    @ExcelColumn(title = "认领人", columnIndex = 11)
    private String RLR;
    @ExcelColumn(dataType = "Date", format = "yyyy-MM-dd", title = "认领日期", columnIndex = 12)
    private Date ClaimDate;
    @ExcelColumn(title = "客户名称", columnIndex = 13)
    private String KHName;
    @ExcelColumn(title = "复核状态", columnIndex = 14, dataSource = "{'1':'拒绝','2':'同意'}")
    private int ReviewerStatus;
    @ExcelColumn(title = "备注", columnIndex = 15)
    private String Note;
    @ExcelColumn(title = "复核人", columnIndex = 16)
    private String FHR;

    public String getDocumentNumber() {
        return DocumentNumber;
    }

    public void setDocumentNumber(String DocumentNumber) {
        this.DocumentNumber = DocumentNumber;
    }

    public Date getDateOfPayment() {
        return DateOfPayment;
    }

    public void setDateOfPayment(Date DateOfPayment) {
        this.DateOfPayment = DateOfPayment;
    }

    public Date getAddTime() {
        return AddTime;
    }

    public void setAddTime(Date AddTime) {
        this.AddTime = AddTime;
    }

    public String getDJR() {
        return DJR;
    }

    public void setDJR(String DJR) {
        this.DJR = DJR;
    }

    public int getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(int PaymentMethod) {
        this.PaymentMethod = PaymentMethod;
    }

    public String getPaymentAccount() {
        return PaymentAccount;
    }

    public void setPaymentAccount(String PaymentAccount) {
        this.PaymentAccount = PaymentAccount;
    }

    public String getPaymentAmount() {
        return PaymentAmount;
    }

    public void setPaymentAmount(String PaymentAmount) {
        this.PaymentAmount = PaymentAmount;
    }

    public String getPayer() {
        return Payer;
    }

    public void setPayer(String Payer) {
        this.Payer = Payer;
    }

    public String getReturnBank() {
        return ReturnBank;
    }

    public void setReturnBank(String ReturnBank) {
        this.ReturnBank = ReturnBank;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getClaimStatus() {
        return ClaimStatus;
    }

    public void setClaimStatus(int ClaimStatus) {
        this.ClaimStatus = ClaimStatus;
    }

    public String getRLR() {
        return RLR;
    }

    public void setRLR(String RLR) {
        this.RLR = RLR;
    }

    public Date getClaimDate() {
        return ClaimDate;
    }

    public void setClaimDate(Date ClaimDate) {
        this.ClaimDate = ClaimDate;
    }

    public String getKHName() {
        return KHName;
    }

    public void setKHName(String KHName) {
        this.KHName = KHName;
    }

    public int getReviewerStatus() {
        return ReviewerStatus;
    }

    public void setReviewerStatus(int ReviewerStatus) {
        this.ReviewerStatus = ReviewerStatus;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String Note) {
        this.Note = Note;
    }

    public String getFHR() {
        return FHR;
    }

    public void setFHR(String FHR) {
        this.FHR = FHR;
    }
}

