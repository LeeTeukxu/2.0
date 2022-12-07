package com.zhide.dtsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "NotExistTZS")
public class notExistTzs implements Serializable {
    @Id
    @Column(name = "TZSID")
    private String tzsid;
    @Column(name = "CreateTime")
    private Date createTime;

    public String getTzsid() {
        return tzsid;
    }

    public void setTzsid(String tzsid) {
        this.tzsid = tzsid;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
