package com.zhide.dtsystem.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbLoginUser")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class tbLoginUser implements Serializable {
    @Id
    @Column(name = "UserID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @Column(name = "DepID")
    private Integer depId;
    @Column(name = "EmpID")
    private Integer empId;
    @Column(name = "RoleID")
    private String roleId;
    @Column(name = "LoginCode")
    private String loginCode;
    @Column(name = "Password")
    private String password;
    @Column(name = "CanLogin")
    private boolean canLogin;
    @Column(name = "CreateTime")
    private Date createTime;
    @Column(name = "LastLoginTime")
    private Date lastLoginTime;
    @Column(name = "LoginCount")
    private Integer loginCount;
    @Column(name = "Timep")
    private String timep;
    @Column(name="TSource")
    private String tSource;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public Integer getDepId() {
        return depId;
    }

    public void setDepId(Integer depId) {
        this.depId = depId;
    }


    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }


    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }


    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public boolean getCanLogin() {
        return canLogin;
    }

    public void setCanLogin(boolean canLogin) {
        this.canLogin = canLogin;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLogIntegerime) {
        this.lastLoginTime = lastLogIntegerime;
    }


    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }


    public String getTimep() {
        return timep;
    }

    public void setTimep(String timep) {
        this.timep = timep;
    }

    public String gettSource() {
        return tSource;
    }

    public void settSource(String tSource) {
        this.tSource = tSource;
    }
}
