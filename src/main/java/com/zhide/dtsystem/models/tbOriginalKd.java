package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbOriginalKd")
public class tbOriginalKd implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OriginalKdID")
    private Integer originalKdId;
    @Column(name = "PackageNum")
    private String packageNum;
    @Column(name = "PackageContent")
    private String packageContent;
    @Column(name = "PackageStatus")
    private Integer packageStatus;
    @Column(name = "DeliveryTime")
    private Date deliveryTime;
    @Column(name = "Render")
    private String render;
    @Column(name = "Receiver")
    private Integer receiver;
    @Column(name = "ContactPerson")
    private String contactPerson;
    @Column(name = "Postcode")
    private String postcode;
    @Column(name = "Address")
    private String address;
    @Column(name = "CourierCompany")
    private String courierCompany;
    @Column(name = "PostalCode")
    private String postalCode;
    @Column(name = "Phone")
    private String phone;
    @Column(name = "ExpressNotes")
    private String expressNotes;
    @Column(name = "ApplicationTime")
    private Date applicationTime;
    @Column(name = "MailAppicant")
    private Integer mailAppicant;
    @Column(name = "KHName")
    private String kHName;

    public Integer getOriginalKdId() {
        return originalKdId;
    }

    public void setOriginalKdId(Integer originalKdId) {
        this.originalKdId = originalKdId;
    }

    public String getPackageNum() {
        return packageNum;
    }

    public void setPackageNum(String packageNum) {
        this.packageNum = packageNum;
    }

    public String getPackageContent() {
        return packageContent;
    }

    public void setPackageContent(String packageContent) {
        this.packageContent = packageContent;
    }

    public Integer getPackageStatus() {
        return packageStatus;
    }

    public void setPackageStatus(Integer packageStatus) {
        this.packageStatus = packageStatus;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getRender() {
        return render;
    }

    public void setRender(String render) {
        this.render = render;
    }

    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCourierCompany() {
        return courierCompany;
    }

    public void setCourierCompany(String courierCompany) {
        this.courierCompany = courierCompany;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExpressNotes() {
        return expressNotes;
    }

    public void setExpressNotes(String expressNotes) {
        this.expressNotes = expressNotes;
    }

    public Date getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(Date applicationTime) {
        this.applicationTime = applicationTime;
    }

    public Integer getMailAppicant() {
        return mailAppicant;
    }

    public void setMailAppicant(Integer mailAppicant) {
        this.mailAppicant = mailAppicant;
    }

    public String getkHName() {
        return kHName;
    }

    public void setKHName(String kHName) {
        this.kHName = kHName;
    }
}
