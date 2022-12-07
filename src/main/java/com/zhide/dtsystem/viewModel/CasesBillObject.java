package com.zhide.dtsystem.viewModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: BillObjectInfo
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年07月01日 17:53
 **/
public class CasesBillObject implements Serializable {

    private String CLIENTNAME;

    private String LINKMAN;

    private String MOBILE;

    private String LINKPHONE;

    private String ADDRESS;

    private Integer CLIENTID;

    private Double TOTALDAI;

    private Double TOTALGUAN;

    private Double USEDDAI;

    private Double USEDGUAN;

    private Double FREEDAI;

    private Double FREEGUAN;

    List<CasesDetail> Rows;
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
    @JsonProperty("TOTALGUAN")
    public Double getTOTALGUAN() {
        return TOTALGUAN;
    }

    public void setTOTALGUAN(Double TOTALGUAN) {
        this.TOTALGUAN = TOTALGUAN;
    }
    @JsonProperty("USEDDAI")
    public Double getUSEDDAI() {
        return USEDDAI;
    }

    public void setUSEDDAI(Double USEDDAI) {
        this.USEDDAI = USEDDAI;
    }
    @JsonProperty("USEDGUAN")
    public Double getUSEDGUAN() {
        return USEDGUAN;
    }

    public void setUSEDGUAN(Double USEDGUAN) {
        this.USEDGUAN = USEDGUAN;
    }
    @JsonProperty("FREEDAI")
    public Double getFREEDAI() {
        return FREEDAI;
    }

    public void setFREEDAI(Double FREEDAI) {
        this.FREEDAI = FREEDAI;
    }
    @JsonProperty("FREEGUAN")
    public Double getFREEGUAN() {
        return FREEGUAN;
    }

    public void setFREEGUAN(Double FREEGUAN) {
        this.FREEGUAN = FREEGUAN;
    }
    @JsonProperty("CLIENTID")
    public Integer getCLIENTID() {
        return CLIENTID;
    }

    public void setCLIENTID(Integer CLIENTID) {
        this.CLIENTID = CLIENTID;
    }
    @JsonProperty("Rows")
    public List<CasesDetail> getRows() {
        return Rows;
    }

    public void setRows(List<CasesDetail> rows) {
        Rows = rows;
    }
}
