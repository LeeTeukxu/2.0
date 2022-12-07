package com.zhide.dtsystem.viewModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: HighBillObject
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年07月02日 15:38
 **/
public class HighBillObject implements Serializable {
    private String CLIENTNAME;

    private String LINKMAN;

    private String MOBILE;

    private String LINKPHONE;

    private String ADDRESS;

    private Integer CLIENTID;

    private Double TOTALDAI;

    private Double USEDDAI;

    private Double FREEDAI;
    private String CONTRACTNO;
    private String NUMS;
    private Date   SIGNTIME;

    @JsonProperty("CLIENTNAME")
    public String getCLIENTNAME() {
        return CLIENTNAME;
    }

    public void setCLIENTNAME(String CLIENTNAME) {
        this.CLIENTNAME = CLIENTNAME;
    }

    @JsonProperty("LINKMAN")
    public String getLINKMAN() {
        return LINKMAN;
    }

    public void setLINKMAN(String LINKMAN) {
        this.LINKMAN = LINKMAN;
    }

    @JsonProperty("MOBILE")
    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    @JsonProperty("LINKPHONE")
    public String getLINKPHONE() {
        return LINKPHONE;
    }

    public void setLINKPHONE(String LINKPHONE) {
        this.LINKPHONE = LINKPHONE;
    }

    @JsonProperty("ADDRESS")
    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    @JsonProperty("TOTALDAI")
    public Double getTOTALDAI() {
        return TOTALDAI;
    }

    public void setTOTALDAI(Double TOTALDAI) {
        this.TOTALDAI = TOTALDAI;
    }

    @JsonProperty("USEDDAI")
    public Double getUSEDDAI() {
        return USEDDAI;
    }

    public void setUSEDDAI(Double USEDDAI) {
        this.USEDDAI = USEDDAI;
    }

    @JsonProperty("FREEDAI")
    public Double getFREEDAI() {
        return FREEDAI;
    }

    public void setFREEDAI(Double FREEDAI) {
        this.FREEDAI = FREEDAI;
    }

    @JsonProperty("CLIENTID")
    public Integer getCLIENTID() {
        return CLIENTID;
    }

    public void setCLIENTID(Integer CLIENTID) {
        this.CLIENTID = CLIENTID;
    }
    @JsonProperty("CONTRACTNO")
    public String getCONTRACTNO() {
        return CONTRACTNO;
    }

    public void setCONTRACTNO(String CONTRACTNO) {
        this.CONTRACTNO = CONTRACTNO;
    }
    @JsonProperty("NUMS")
    public String getNUMS() {
        return NUMS;
    }

    public void setNUMS(String NUMS) {
        this.NUMS = NUMS;
    }
    @JsonProperty("SIGNTIME")
    public Date getSIGNTIME() {
        return SIGNTIME;
    }

    public void setSIGNTIME(Date SIGNTIME) {
        this.SIGNTIME = SIGNTIME;
    }
}
