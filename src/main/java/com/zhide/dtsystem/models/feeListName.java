package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "FeeListName")
public class feeListName implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "jfqd_id")
    private String jfqdId;
    @Column(name = "jfqd")
    private String jfqd;
    @Column(name = "jfje")
    private String jfje;
    @Column(name = "LinkMan")
    private String linkMan;
    @Column(name = "Address")
    private String address;
    @Column(name = "PostCode")
    private String postCode;
    @Column(name = "Mobile")
    private String mobile;
    @Column(name = "invoiceTitle")
    private String invoiceTitle;
    @Column(name = "auditOpinion")
    private String auditOpinion;
    @Column(name = "remark")
    private String remark;
    @Column(name = "zfsj")
    private String zfsj;
    @Column(name = "fstate")
    private boolean fstate;
    @Column(name = "CreateEmp")
    private String createEmp;
    @Column(name = "CreateTime")
    private Date createTime;
    @Column(name = "DrawEmp")
    private String drawEmp;
    @Column(name = "DrawTime")
    private Date drawTime;
    @Column(name = "DocMax")
    private int docMax;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getJfqdId() {
        return jfqdId;
    }

    public void setJfqdId(String jfqdId) {
        this.jfqdId = jfqdId;
    }


    public String getJfqd() {
        return jfqd;
    }

    public void setJfqd(String jfqd) {
        this.jfqd = jfqd;
    }


    public String getJfje() {
        return jfje;
    }

    public void setJfje(String jfje) {
        this.jfje = jfje;
    }


    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }


    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getZfsj() {
        return zfsj;
    }

    public void setZfsj(String zfsj) {
        this.zfsj = zfsj;
    }


    public boolean getFstate() {
        return fstate;
    }

    public void setFstate(boolean fstate) {
        this.fstate = fstate;
    }


    public String getCreateEmp() {
        return createEmp;
    }

    public void setCreateEmp(String createEmp) {
        this.createEmp = createEmp;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public String getDrawEmp() {
        return drawEmp;
    }

    public void setDrawEmp(String drawEmp) {
        this.drawEmp = drawEmp;
    }


    public Date getDrawTime() {
        return drawTime;
    }

    public void setDrawTime(Date drawTime) {
        this.drawTime = drawTime;
    }


    public int getDocMax() {
        return docMax;
    }

    public void setDocMax(int docMax) {
        this.docMax = docMax;
    }

}
