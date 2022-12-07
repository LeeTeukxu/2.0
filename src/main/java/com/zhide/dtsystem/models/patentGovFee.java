package com.zhide.dtsystem.models;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: patentGovFee
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年02月03日 9:43
 **/
public class patentGovFee  implements Serializable {
    private Integer AID;
    private String AppNo;
    private String CostName;
    private String Payer;
    private Date GovPayDate;
    private String ReceiptCode;
    private String ReceiptNo;
    private Integer Amount;
    private String PayState;
    private Date LimitDate;
    private Date CreateTime;
    private Date ProcessTime;

    public Integer getAID() {
        return AID;
    }

    public void setAID(Integer AID) {
        this.AID = AID;
    }

    public String getAppNo() {
        return AppNo;
    }

    public void setAppNo(String appNo) {
        AppNo = appNo;
    }

    public String getCostName() {
        return CostName;
    }

    public void setCostName(String costName) {
        CostName = costName;
    }

    public String getPayer() {
        return Payer;
    }

    public void setPayer(String payer) {
        Payer = payer;
    }

    public Date getGovPayDate() {
        return GovPayDate;
    }

    public void setGovPayDate(Date govPayDate) {
        GovPayDate = govPayDate;
    }

    public String getReceiptCode() {
        return ReceiptCode;
    }

    public void setReceiptCode(String receiptCode) {
        ReceiptCode = receiptCode;
    }

    public String getReceiptNo() {
        return ReceiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        ReceiptNo = receiptNo;
    }

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
    }

    public String getPayState() {
        return PayState;
    }

    public void setPayState(String payState) {
        PayState = payState;
    }

    public Date getLimitDate() {
        return LimitDate;
    }

    public void setLimitDate(Date limitDate) {
        LimitDate = limitDate;
    }

    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }

    public Date getProcessTime() {
        return ProcessTime;
    }

    public void setProcessTime(Date processTime) {
        ProcessTime = processTime;
    }
}
