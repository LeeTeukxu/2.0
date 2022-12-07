package com.zhide.dtsystem.models;

import java.io.Serializable;

public class EmployeeAndUser implements Serializable {
    public EmployeeAndUser() {

    }

    tbEmployee employee;
    tbLoginUser loginUser;

    public tbEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(tbEmployee employee) {
        this.employee = employee;
    }

    public tbLoginUser getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(tbLoginUser loginUser) {
        this.loginUser = loginUser;
    }
}
