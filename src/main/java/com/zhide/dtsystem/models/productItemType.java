package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ProductItemType")
public class productItemType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "FID")
    private String fid;
    @Column(name = "SN")
    private Integer sn;
    @Column(name = "Name")
    private String name;
    @Column(name = "Type")
    private String type;
    @Column(name = "Cost")
    private Double cost;
    @Column(name = "Price")
    private Double price;
    @Column(name = "MaxDays")
    private Integer maxDays;
    @Column(name = "WorkDays")
    private Double workDays;
    @Column(name = "Required")
    private String required;
    @Column(name = "Memo")
    private String memo;
    @Column(name = "CreateMan")
    private Integer createMan;
    @Column(name = "CreateTime")
    private Date createTime;

    @Transient
    private String blankFile;
    @Transient
    private String blankFileName;
    @Transient
    private String standardFile;
    @Transient
    private String standardFileName;
    @Transient
    private String normalFile;
    @Transient
    private String normalFileName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }


    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    public Integer getMaxDays() {
        return maxDays;
    }

    public void setMaxDays(Integer maxDays) {
        this.maxDays = maxDays;
    }


    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }


    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public Double getWorkDays() {
        return workDays;
    }

    public void setWorkDays(Double workDays) {
        this.workDays = workDays;
    }

    public String getBlankFile() {
        return blankFile;
    }

    public void setBlankFile(String blankFile) {
        this.blankFile = blankFile;
    }

    public String getBlankFileName() {
        return blankFileName;
    }

    public void setBlankFileName(String blankFileName) {
        this.blankFileName = blankFileName;
    }

    public String getStandardFile() {
        return standardFile;
    }

    public void setStandardFile(String standardFile) {
        this.standardFile = standardFile;
    }

    public String getStandardFileName() {
        return standardFileName;
    }

    public void setStandardFileName(String standardFileName) {
        this.standardFileName = standardFileName;
    }

    public String getNormalFile() {
        return normalFile;
    }

    public void setNormalFile(String normalFile) {
        this.normalFile = normalFile;
    }

    public String getNormalFileName() {
        return normalFileName;
    }

    public void setNormalFileName(String normalFileName) {
        this.normalFileName = normalFileName;
    }
}
