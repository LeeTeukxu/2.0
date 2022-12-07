package com.zhide.dtsystem.viewModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: CasesDetail
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年07月02日 9:24
 **/
public class OtherDetail implements Serializable {

    private Date CREATETIME;

    private  String SUBNO;

    private  String YNAME;

    private String TCNAME;

    private String TCCATEGORY;

    private  Double GUANMONEY;

    private  Double DAIMONEY;

    private  Double TOTALMONEY;

    @JsonProperty("CREATETIME")
    public Date getCREATETIME() {
        return CREATETIME;
    }

    public void setCREATETIME(Date CREATETIME) {
        this.CREATETIME = CREATETIME;
    }
    @JsonProperty("SUBNO")
    public String getSUBNO() {
        return SUBNO;
    }

    public void setSUBNO(String SUBNO) {
        this.SUBNO = SUBNO;
    }
    @JsonProperty("YNAME")
    public String getYNAME() {
        return YNAME;
    }

    public void setYNAME(String YNAME) {
        this.YNAME = YNAME;
    }
    @JsonProperty("TCNAME")
    public String getTCNAME() {
        return TCNAME;
    }

    public void setTCNAME(String TCNAME) {
        this.TCNAME = TCNAME;
    }
    @JsonProperty("TCCATEGORY")
    public String getTCCATEGORY() {
        if(TCCATEGORY==null)TCCATEGORY="";
        return TCCATEGORY;
    }

    public void setTCCATEGORY(String TCCATEGORY) {
        this.TCCATEGORY = TCCATEGORY;
    }
    @JsonProperty("GUANMONEY")
    public Double getGUANMONEY() {
        return GUANMONEY;
    }

    public void setGUANMONEY(Double GUANMONEY) {
        this.GUANMONEY = GUANMONEY;
    }
    @JsonProperty("DAIMONEY")
    public Double getDAIMONEY() {
        return DAIMONEY;
    }

    public void setDAIMONEY(Double DAIMONEY) {
        this.DAIMONEY = DAIMONEY;
    }
    @JsonProperty("TOTALMONEY")
    public Double getTOTALMONEY() {
        return TOTALMONEY;
    }

    public void setTOTALMONEY(Double TOTALMONEY) {
        this.TOTALMONEY = TOTALMONEY;
    }
}
