package com.zhide.dtsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "NotExistCPC")
public class notExistCpc implements Serializable {
    @Id
    @Column(name = "CPCID")
    private String cpcid;
    @Column(name = "CreateTime")
    private Date createTime;

    public String getCpcid() {
        return cpcid;
    }

    public void setCpcid(String cpcid) {
        this.cpcid = cpcid;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
