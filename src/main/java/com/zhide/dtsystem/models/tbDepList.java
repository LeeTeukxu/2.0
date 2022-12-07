package com.zhide.dtsystem.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbDepList")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class tbDepList implements Serializable {
    @Id
    @Column(name = "DepID")
    private Integer depId;
    @Column(name = "SN")
    private Integer sn;
    @Column(name = "Code")
    private String code;
    @Column(name = "PID")
    private Integer pid;
    @Column(name = "Name")
    private String name;
    @Column(name = "CanUse")
    private boolean canUse;
    @Column(name = "Memo")
    private String memo;
    @Column(name = "Timep")
    private String timep;
    @Column(name = "ChargeMan")
    private String chargeMan;
    @Column(name = "Sort")
    private String sort;
    @Column(name = "Type")
    private String type;
    @Transient
    private Integer num;

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }


    public Integer getDepId() {
        return depId;
    }

    public void setDepId(Integer depId) {
        this.depId = depId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    public String getTimep() {
        return timep;
    }

    public void setTimep(String timep) {
        this.timep = timep;
    }


    public String getChargeMan() {
        return chargeMan;
    }

    public void setChargeMan(String chargeMan) {
        this.chargeMan = chargeMan;
    }


    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNum() {
        if(num==null)num=0;
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
