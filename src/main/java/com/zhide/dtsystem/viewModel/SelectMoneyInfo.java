package com.zhide.dtsystem.viewModel;

import java.io.Serializable;

/**
 * @ClassName: SelectMoneyInfo
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年06月04日 15:53
 **/
public class SelectMoneyInfo implements Serializable {
    String CasesID;
    Integer ArrID;
    Integer ClientID;
    String ClientName;
    Double Guan;
    Double Dai;

    public String getCasesID() {
        return CasesID;
    }

    public void setCasesID(String casesID) {
        CasesID = casesID;
    }

    public Integer getArrID() {
        return ArrID;
    }

    public void setArrID(Integer arrID) {
        ArrID = arrID;
    }

    public Integer getClientID() {
        return ClientID;
    }

    public void setClientID(Integer clientID) {
        ClientID = clientID;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public Double getGuan() {
        return Guan;
    }

    public void setGuan(Double guan) {
        Guan = guan;
    }

    public Double getDai() {
        return Dai;
    }

    public void setDai(Double dai) {
        Dai = dai;
    }
}
