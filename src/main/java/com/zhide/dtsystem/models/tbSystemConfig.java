package com.zhide.dtsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tbSystemConfig")
public class tbSystemConfig implements Serializable {
    @Id
    @Column(name = "FID")
    private int fid;
    @Column(name = "Title")
    private String title;
    @Column(name = "Config")
    private String config;

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

}
