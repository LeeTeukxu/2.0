package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbAttachment")
public class tbAttachment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "GUID")
    private String guid;
    @Column(name = "Name")
    private String name;
    @Column(name = "Size")
    private Integer size;
    @Column(name = "Path")
    private String path;
    @Column(name = "UploadTime")
    private Date uploadTime;
    @Column(name = "UploadMan")
    private Integer uploadMan;
    @Column(name = "UploadManName")
    private String uploadManName;
    @Column(name = "Explain")
    private String explain;
    @Column(name = "TempPath")
    private String tempPath;
    @Column(name = "Type")
    private Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }


    public Integer getUploadMan() {
        return uploadMan;
    }

    public void setUploadMan(Integer uploadMan) {
        this.uploadMan = uploadMan;
    }


    public String getUploadManName() {
        return uploadManName;
    }

    public void setUploadManName(String uploadManName) {
        this.uploadManName = uploadManName;
    }


    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }


    public String getTempPath() {
        return tempPath;
    }

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
