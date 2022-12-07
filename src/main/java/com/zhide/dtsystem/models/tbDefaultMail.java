package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbDefaultMail")
public class tbDefaultMail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DefaultMailID")
    private Integer defaultMailId;
    @Column(name = "ClientID")
    private Integer clientId;
    @Column(name = "LinkersID")
    private Integer linkersId;
    @Column(name = "UserID")
    private Integer userId;
    @Column(name = "AddTime")
    private Date addTime;

    public Integer getDefaultMailId() {
        return defaultMailId;
    }

    public void setDefaultMailId(Integer defaultMailId) {
        this.defaultMailId = defaultMailId;
    }


    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }


    public Integer getLinkersId() {
        return linkersId;
    }

    public void setLinkersId(Integer linkersId) {
        this.linkersId = linkersId;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

}
