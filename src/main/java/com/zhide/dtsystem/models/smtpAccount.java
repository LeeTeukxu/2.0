package com.zhide.dtsystem.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SmtpAccount")
public class smtpAccount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "Server")
    private String server;
    @Column(name = "UserName")
    private String userName;
    @Column(name = "Password")
    private String password;
    @Column(name = "CanUse")
    private boolean canUse;
    @Column(name = "Ssl")
    private boolean ssl;
    @Column(name = "Port")
    private Integer port;
    @Column(name = "NickName")
    private String nickName;
    @Column(name = "UserId")
    private Integer userId;
    @Column(name="CompanyDefault")
    private boolean companyDefault;

    public boolean getCompanyDefault() {
        return companyDefault;
    }

    public void setCompanyDefault(boolean companyDefault) {
        this.companyDefault = companyDefault;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public boolean getCanUse() {
        return canUse;
    }

    public void setCanUse(boolean canUse) {
        this.canUse = canUse;
    }


    public boolean getSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }


    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
