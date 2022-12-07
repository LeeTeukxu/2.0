package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "YearWatchNotExistXml")
public class yearWatchNotExistXml implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "Shenqingh")
    private String shenqingh;
    @Column(name = "TZSPath")
    private String tzsPath;
    @Column(name = "CreateTime")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getShenqingh() {
        return shenqingh;
    }

    public void setShenqingh(String shenqingh) {
        this.shenqingh = shenqingh;
    }


    public String getTzsPath() {
        return tzsPath;
    }

    public void setTzsPath(String tzsPath) {
        this.tzsPath = tzsPath;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
