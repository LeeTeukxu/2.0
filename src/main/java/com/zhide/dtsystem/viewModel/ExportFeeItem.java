package com.zhide.dtsystem.viewModel;

import java.io.Serializable;

/**
 * @ClassName: ExportFeeItem
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年07月01日 10:04
 **/
public class ExportFeeItem implements Serializable {
    Integer Index;
    String Shenqingh;
    String CreditCode;
    String FeeName;
    Double Money;
    String ClientName;

    public Integer getIndex() {
        return Index;
    }

    public void setIndex(Integer index) {
        Index = index;
    }

    public String getShenqingh() {
        return Shenqingh;
    }

    public void setShenqingh(String shenqingh) {
        Shenqingh = shenqingh;
    }

    public String getCreditCode() {
        return CreditCode;
    }

    public void setCreditCode(String creditCode) {
        CreditCode = creditCode;
    }

    public String getFeeName() {
        return FeeName;
    }

    public void setFeeName(String feeName) {
        FeeName = feeName;
    }

    public Double getMoney() {
        return Money;
    }

    public void setMoney(Double money) {
        Money = money;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }
}
