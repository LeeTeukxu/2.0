package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CasesAJDetail")
public class casesAjDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "AJID")
    private String ajid;
    @Column(name = "CasesID")
    private String casesId;
    @Column(name = "AJCode")
    private String ajCode;
    @Column(name = "AJName")
    private String ajName;
    @Column(name = "AJType")
    private String ajType;
    @Column(name = "ClientID")
    private Integer clientId;
    @Column(name = "AJLinkMan")
    private String ajLinkMan;
    @Column(name = "AJLinkPhone")
    private String ajLinkPhone;
    @Column(name = "AJMemo")
    private String ajMemo;
    @Column(name = "AJWriteMan")
    private String ajWriteMan;
    @Column(name = "CreateTime")
    private Date createTime;
    @Column(name = "CreateMan")
    private Integer createMan;
    @Column(name = "AttIDS")
    private String attIds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getAjid() {
        return ajid;
    }

    public void setAjid(String ajid) {
        this.ajid = ajid;
    }


    public String getCasesId() {
        return casesId;
    }

    public void setCasesId(String casesId) {
        this.casesId = casesId;
    }


    public String getAjCode() {
        return ajCode;
    }

    public void setAjCode(String ajCode) {
        this.ajCode = ajCode;
    }


    public String getAjName() {
        return ajName;
    }

    public void setAjName(String ajName) {
        this.ajName = ajName;
    }


    public String getAjType() {
        return ajType;
    }

    public void setAjType(String ajType) {
        this.ajType = ajType;
    }


    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }


    public String getAjLinkMan() {
        return ajLinkMan;
    }

    public void setAjLinkMan(String ajLinkMan) {
        this.ajLinkMan = ajLinkMan;
    }


    public String getAjLinkPhone() {
        return ajLinkPhone;
    }

    public void setAjLinkPhone(String ajLinkPhone) {
        this.ajLinkPhone = ajLinkPhone;
    }


    public String getAjMemo() {
        return ajMemo;
    }

    public void setAjMemo(String ajMemo) {
        this.ajMemo = ajMemo;
    }


    public String getAjWriteMan() {
        return ajWriteMan;
    }

    public void setAjWriteMan(String ajWriteMan) {
        this.ajWriteMan = ajWriteMan;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public Integer getCreateMan() {
        return createMan;
    }

    public void setCreateMan(Integer createMan) {
        this.createMan = createMan;
    }


    public String getAttIds() {
        return attIds;
    }

    public void setAttIds(String attIds) {
        this.attIds = attIds;
    }

}
