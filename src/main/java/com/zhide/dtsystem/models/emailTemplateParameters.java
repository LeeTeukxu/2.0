package com.zhide.dtsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "EmailTemplateParameters")
public class emailTemplateParameters implements Serializable {
    @Id
    @Column(name = "ID")
    private int id;
    @Column(name = "PID")
    private int pid;
    @Column(name = "Name")
    private String name;
    @Column(name = "Type")
    private int type;
    @Column(name = "PreviewValue")
    private String previewValue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getPreviewValue() {
        return previewValue;
    }

    public void setPreviewValue(String previewValue) {
        this.previewValue = previewValue;
    }

}
