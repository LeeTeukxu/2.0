package com.zhide.dtsystem.viewModel;

import java.io.Serializable;

/**
 * @ClassName: loginMessage
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年02月17日 20:55
 **/
public class loginMessage implements Serializable {
    String Account;
    String UserName;
    String Company;
    String LoginTime;
    String DepName;
    String RoleName;
    String IPAddress;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }


    public String getDepName() {
        return DepName;
    }

    public void setDepName(String depName) {
        DepName = depName;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }

    public String getLoginTime() {
        return LoginTime;
    }

    public void setLoginTime(String loginTime) {
        LoginTime = loginTime;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }
}
