package com.zhide.dtsystem.models;

import java.io.Serializable;
import java.util.Date;

public class allUsersList implements Serializable {
    private Integer id;
    private String account;
    private String companyID;
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCompanyId() {
        return companyID;
    }

    public void setCompanyId(String companyId) {
        this.companyID = companyId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
