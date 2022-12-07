package com.zhide.dtsystem.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zhide.dtsystem.listeners.ClientChangeListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbClient")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
@EntityListeners({ClientChangeListener.class})
public class tbClient implements Serializable {
    @Id
    @Column(name = "ClientID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clientID;
    @Column(name = "Name")
    private String name;
    @Column(name = "FullName")
    private String fullName;
    @Column(name = "Address")
    private String address;
    @Column(name = "LinkMan")
    private String linkMan;
    @Column(name = "LinkPhone")
    private String linkPhone;
    @Column(name = "Email")
    private String email;
    @Column(name = "QQ")
    private String qq;
    @Column(name = "WX")
    private String wx;
    @Column(name = "Tele")
    private String tele;
    @Column(name = "Mobile")
    private String mobile;
    @Column(name = "PostCode")
    private String postCode;
    @Column(name = "CreateMan")
    private Integer createMan;
    @Column(name = "CreateTime")
    private Date createTime;
    @Column(name = "SignMan")
    private Integer signMan;
    @Column(name = "CanUse")
    private boolean canUse;
    @Column(name = "Memo")
    private String memo;
    @Column(name = "SignDate")
    private Date signDate;
    @Column(name = "Fax")
    private String fax;
    @Column(name = "SN")
    private String sn;
    @Column(name = "DepID")
    private Integer depId;
    @Column(name = "PreSignMan")
    private Integer preSignMan;
    @Column(name = "PreSignDate")
    private Date preSignDate;
    @Column(name = "Type")
    private Integer type;
    @Column(name = "OrgCode")
    private String orgCode;
    @Column(name = "Password")
    private String password;
    @Column(name = "Timep")
    private String timep;
    @Column(name = "SendTime")
    private Date sendTime;
    @Column(name = "CanLogin")
    private boolean canLogin;
    @Column(name = "LastLoginTime")
    private Date lastLoginTime;
    @Column(name = "LoginCount")
    private Integer loginCount;
    @Column(name = "aticode")
    private String aticode;
    @Column(name = "aticodetime")
    private Date aticodetime;
    @Column(name = "cootype")
    private Integer cootype;
    @Column(name = "JSR")
    private String  jsr;
    @Column(name="TSource")
    private String tSource;
    @Column(name="CreditCode")
    private String creditCode;


    public String gettSource() {
        return tSource;
    }

    public void settSource(String tSource) {
        this.tSource = tSource;
    }

    public Integer getClientID() {
        return clientID;
    }

    public void setClientID(int clientId) {
        this.clientID = clientId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }


    public Integer getCreateMan() {
        return createMan;
    }

    public void setCreateMan(int createMan) {
        this.createMan = createMan;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public Integer getSignMan() {
        return signMan;
    }

    public void setSignMan(int signMan) {
        this.signMan = signMan;
    }


    public boolean getCanUse() {
        return canUse;
    }

    public void setCanUse(boolean canUse) {
        this.canUse = canUse;
    }


    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }


    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }


    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }


    public Integer getDepId() {
        return depId;
    }

    public void setDepId(int depId) {
        this.depId = depId;
    }


    public Integer getPreSignMan() {
        return preSignMan;
    }

    public void setPreSignMan(int preSignMan) {
        this.preSignMan = preSignMan;
    }


    public Date getPreSignDate() {
        return preSignDate;
    }

    public void setPreSignDate(Date preSignDate) {
        this.preSignDate = preSignDate;
    }


    public Integer getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getTimep() {
        return timep;
    }

    public void setTimep(String timep) {
        this.timep = timep;
    }


    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }


    public boolean getCanLogin() {
        return canLogin;
    }

    public void setCanLogin(boolean canLogin) {
        this.canLogin = canLogin;
    }


    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }


    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }


    public String getAticode() {
        return aticode;
    }

    public void setAticode(String aticode) {
        this.aticode = aticode;
    }


    public Date getAticodetime() {
        return aticodetime;
    }

    public void setAticodetime(Date aticodetime) {
        this.aticodetime = aticodetime;
    }


    public Integer getCootype() {
        return cootype;
    }

    public void setCootype(int cootype) {
        this.cootype = cootype;
    }


    public String  getJsr() {
        return jsr;
    }

    public void setJsr(String  jsr) {
        this.jsr = jsr;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

}
