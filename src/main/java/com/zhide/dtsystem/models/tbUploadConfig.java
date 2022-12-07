package com.zhide.dtsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tbUploadConfig")
public class tbUploadConfig implements Serializable {
    @Id
    @Column(name = "ID")
    private int id;
    @Column(name = "Title")
    private String title;
    @Column(name = "Name")
    private String name;
    @Column(name = "Config")
    private String config;
    @Column(name = "FID")
    private int fid;
    @Column(name = "OnFile")
    private boolean onFile;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }


    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }


    public boolean getOnFile() {
        return onFile;
    }

    public void setOnFile(boolean onFile) {
        this.onFile = onFile;
    }

}
