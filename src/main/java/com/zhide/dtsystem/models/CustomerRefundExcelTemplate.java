package com.zhide.dtsystem.models;

@ExcelTitle(value = "财务退款信息表", end = 11)
public class CustomerRefundExcelTemplate implements IExcelExportTemplate {
    @ExcelColumn(title = "退款申请编号", columnIndex = 0)
    private String RefundRequestNumber;
    @ExcelColumn(title = "申请人", columnIndex = 1)
    private String SQR;
    @ExcelColumn(title = "退款类型", columnIndex = 2, dataSource = "{'1':'代理费','2':'官费','3':'代理费+官费'}")
    private int RefundType;
    @ExcelColumn(title = "客户名称", columnIndex = 3)
    private String KHName;
    @ExcelColumn(title = "代理费金额", columnIndex = 4)
    private String AgencyFeeAmount;
    @ExcelColumn(title = "官费金额", columnIndex = 5)
    private String OfficalFeeAmount;
    @ExcelColumn(columnIndex = 6, title = "开户行")
    private String Bank;
    @ExcelColumn(title = "账号", columnIndex = 7)
    private String AccountNumber;
    @ExcelColumn(title = "户名", columnIndex = 8)
    private String AccountName;
    @ExcelColumn(title = "退款方式", columnIndex = 9, dataSource = "{'1':'现金','2':'转账','3':'支付宝','4':'微信'}")
    private int RefundMethod;
    @ExcelColumn(title = "经理审批意见", columnIndex = 10, dataSource = "{'1':'同意','2':'拒绝'}")
    private int ApproverResult;
    @ExcelColumn(title = "财务审批意见", columnIndex = 11, dataSource = "{'1':'同意','2':'拒绝'}")
    private int AuditResult;

    public String getRefundRequestNumber() {
        return RefundRequestNumber;
    }

    public void setRefundRequestNumber(String RefundRequestNumber) {
        this.RefundRequestNumber = RefundRequestNumber;
    }

    public String getSQR() {
        return SQR;
    }

    public void setSQR(String SQR) {
        this.SQR = SQR;
    }

    public int getRefundType() {
        return RefundType;
    }

    public void setRefundType(int RefundType) {
        this.RefundType = RefundType;
    }

    public String getKHName() {
        return KHName;
    }

    public void setKHName(String KHName) {
        this.KHName = KHName;
    }

    public String getAgencyFeeAmount() {
        return AgencyFeeAmount;
    }

    public void setAgencyFeeAmount(String AgencyFeeAmount) {
        this.AgencyFeeAmount = AgencyFeeAmount;
    }

    public String getOfficalFeeAmount() {
        return OfficalFeeAmount;
    }

    public void setOfficalFeeAmount(String OfficalFeeAmount) {
        this.OfficalFeeAmount = OfficalFeeAmount;
    }

    public String getBank() {
        return Bank;
    }

    public void setBank(String Bank) {
        this.Bank = Bank;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String AccountNumber) {
        this.AccountNumber = AccountNumber;
    }

    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String AccountName) {
        this.AccountName = AccountName;
    }

    public int getRefundMethod() {
        return RefundMethod;
    }

    public void setRefundMethod(int RefundMethod) {
        this.RefundMethod = RefundMethod;
    }

    public int getApproverResult() {
        return ApproverResult;
    }

    public void setApproverResult(int ApproverResult) {
        this.ApproverResult = ApproverResult;
    }

    public int getAuditResult() {
        return AuditResult;
    }

    public void setAuditResult(int AuditResult) {
        this.AuditResult = AuditResult;
    }
}
