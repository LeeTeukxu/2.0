package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PatentInfoPermission")
public class patentInfoPermission implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PPID")
    private Integer ppid;
    @Column(name = "SHENQINGH")
    private String shenqingh;
    @Column(name = "USERID")
    private Integer userid;
    @Column(name = "USERTYPE")
    private String usertype;

    public Integer getPpid() {
        return ppid;
    }

    public void setPpid(Integer ppid) {
        this.ppid = ppid;
    }


    public String getShenqingh() {
        return shenqingh;
    }

    public void setShenqingh(String shenqingh) {
        this.shenqingh = shenqingh;
    }


    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }


    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

}
