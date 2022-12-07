package com.zhide.dtsystem.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "AllFeePayForResult")
public class allFeePayForResult implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "FlowCode")
    private String flowCode;
    @Column(name = "FeeItemID")
    private String feeItemId;
    @Column(name = "FeeName")
    private String feeName;
    @Column(name = "ShenQingh")
    private String shenQingh;
    @Column(name = "PayState")
    private Integer payState;
    @Column(name = "Money")
    private Double money;
    @Column(name = "XMoney")
    private Double xMoney;
    @Column(name = "JiaoFeiDate")
    private Date jiaoFeiDate;
    @Column(name = "TickHeader")
    private String tickHeader;
    @Column(name = "TickReceiver")
    private Integer tickReceiver;
    @Column(name = "PostCode")
    private String postCode;
    @Column(name = "Address")
    private String address;
    @Column(name = "LinkMan")
    private String linkMan;
    @Column(name = "LinkPhone")
    private String linkPhone;
    @Column(name = "Memo")
    private String memo;
    @Column(name = "Type")
    private String type;
    @Column(name = "CreateMan")
    private Integer createMan;
    @Column(name = "CreateTime")
    private Date createTime;
    @Column(name = "AuditMan")
    private Integer auditMan;
    @Column(name = "AuditTime")
    private Date auditTime;
    @Column(name = "AuditText")
    private String auditText;
    @Autowired
    private String creditCode;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getFlowCode() {
        return flowCode;
    }

    public void setFlowCode(String flowCode) {
        this.flowCode = flowCode;
    }


    public String getFeeItemId() {
        return feeItemId;
    }

    public void setFeeItemId(String feeItemId) {
        this.feeItemId = feeItemId;
    }


    public String getFeeName() {
        return feeName;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }


    public String getShenQingh() {
        return shenQingh;
    }

    public void setShenQingh(String shenQingh) {
        this.shenQingh = shenQingh;
    }


    public Integer getPayState() {
        if(payState==null)payState=0;
        return payState;
    }

    public void setPayState(Integer payState) {
        this.payState = payState;
    }


    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }


    public Double getXMoney() {
        return xMoney;
    }

    public void setXMoney(Double xMoney) {
        this.xMoney = xMoney;
    }


    public Date getJiaoFeiDate() {
        return jiaoFeiDate;
    }

    public void setJiaoFeiDate(Date jiaoFeiDate) {
        this.jiaoFeiDate = jiaoFeiDate;
    }


    public String getTickHeader() {
        return tickHeader;
    }

    public void setTickHeader(String tickHeader) {
        this.tickHeader = tickHeader;
    }


    public Integer getTickReceiver() {
        return tickReceiver;
    }

    public void setTickReceiver(Integer tickReceiver) {
        this.tickReceiver = tickReceiver;
    }


    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }


    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }


    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Integer getCreateMan() {
        return createMan;
    }

    public void setCreateMan(Integer createMan) {
        this.createMan = createMan;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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


    public String getAuditText() {
        return auditText;
    }

    public void setAuditText(String auditText) {
        this.auditText = auditText;
    }

    public String getCreditCode() {
        if(StringUtils.isEmpty(creditCode))creditCode="";
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }
}
