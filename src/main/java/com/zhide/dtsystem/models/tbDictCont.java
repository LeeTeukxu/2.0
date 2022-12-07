package com.zhide.dtsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tbDictCont")
public class tbDictCont implements Serializable {
    @Id
    @Column(name = "ID")
    private int id;
    @Column(name = "FID")
    private int fid;
    @Column(name = "PID")
    private int pid;
    @Column(name = "SN")
    private String sn;
    @Column(name = "Name")
    private String name;
    @Column(name = "PropertyNum")
    private int propertyNum;
    @Column(name = "CanUse")
    private boolean canUse;
    @Column(name = "SystemUse")
    private boolean systemUse;
    @Column(name = "Memo")
    private String memo;
    @Column(name = "Timep")
    private String timep;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }


    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }


    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getPropertyNum() {
        return propertyNum;
    }

    public void setPropertyNum(int propertyNum) {
        this.propertyNum = propertyNum;
    }


    public boolean getCanUse() {
        return canUse;
    }

    public void setCanUse(boolean canUse) {
        this.canUse = canUse;
    }


    public boolean getSystemUse() {
        return systemUse;
    }

    public void setSystemUse(boolean systemUse) {
        this.systemUse = systemUse;
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

}
