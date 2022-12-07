package com.zhide.dtsystem.models;

import java.io.Serializable;
import java.util.List;

public class LoginUserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String companyId;
    private String depId;
    private String userId;
    private String userName;
    private String companyName;
    private String account;
    private String depName;
    private String password;
    private String roleName;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    private String roleId;
    private boolean canLogin;

    public List<Integer> getMyManager() {
        return myManager;
    }

    public Integer getUserIdValue() {
        return Integer.parseInt(userId);
    }

    public Integer getDepIdValue() {
        return Integer.parseInt(depId);
    }

    public void setMyManager(List<Integer> myManager) {
        this.myManager = myManager;
    }

    private List<Integer> myManager;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isCanLogin() {
        return canLogin;
    }

    public void setCanLogin(boolean canLogin) {
        this.canLogin = canLogin;
    }
}
