package com.zhide.dtsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "FunctionPermissionList")
public class functionPermissionList implements Serializable {
    @Id
    @Column(name = "FID")
    private int fid;
    @Column(name = "SN")
    private String sn;
    @Column(name = "Name")
    private String name;
    @Column(name = "Title")
    private String title;
    @Column(name = "CanUse")
    private boolean canUse;
    @Column(name = "Timep")
    private String timep;

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
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


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public boolean getCanUse() {
        return canUse;
    }

    public void setCanUse(boolean canUse) {
        this.canUse = canUse;
    }


    public String getTimep() {
        return timep;
    }

    public void setTimep(String timep) {
        this.timep = timep;
    }

}
