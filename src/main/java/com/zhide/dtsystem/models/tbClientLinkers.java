package com.zhide.dtsystem.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbClientLinkers")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class tbClientLinkers implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LinkID")
    private Integer linkID;
    @Column(name = "ClientID")
    private Integer clientID;
    @Column(name = "CType")
    private String cType;
    @Column(name = "LinkMan")
    private String linkMan;
    @Column(name = "Mobile")
    private String mobile;
    @Column(name = "LinkPhone")
    private String linkPhone;
    @Column(name = "Address")
    private String address;
    @Column(name = "Email")
    private String email;
    @Column(name = "PostCode")
    private String postCode;
    @Column(name = "QQ")
    private String qq;
    @Column(name = "WX")
    private String wx;
    @Column(name = "Tele")
    private String tele;
    @Column(name = "Fax")
    private String fax;
    @Column(name = "Memo")
    private String memo;
    @Column(name = "CreateTime")
    private Date createTime;
    @Column(name="Position")
    private Integer position;

    public Integer getLinkID() {
        return linkID;
    }

    public void setLinkID(int linkId) {
        this.linkID = linkId;
    }


    public Integer getClientID() {
        return clientID;
    }

    public void setClientID(int clientId) {
        this.clientID = clientId;
    }


    public String getCType() {
        return cType;
    }

    public void setCType(String cType) {
        this.cType = cType;
    }


    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }


    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }


    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }


    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }


    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }


    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
