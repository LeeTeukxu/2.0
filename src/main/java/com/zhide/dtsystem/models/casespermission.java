package com.zhide.dtsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "CASESPERMISSION")
public class casespermission implements Serializable {
    @Id
    @Column(name = "PID")
    private String pid;
    @Column(name = "CASEID")
    private String caseid;
    @Column(name = "MANID")
    private int manid;
    @Column(name = "TYPE")
    private String type;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }


    public String getCaseid() {
        return caseid;
    }

    public void setCaseid(String caseid) {
        this.caseid = caseid;
    }


    public int getManid() {
        return manid;
    }

    public void setManid(int manid) {
        this.manid = manid;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
