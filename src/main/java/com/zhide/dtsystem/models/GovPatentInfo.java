package com.zhide.dtsystem.models;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: GovPatentInfo
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年02月04日 13:54
 **/
public class GovPatentInfo implements Serializable {
    private String AppNo;
    private Date AppDate;
    private String Title;
    private String Applicant;
    private String Inventors;
    private String CpqueryStatus;
    private Date UpdateTime;
    private String Agency;
    private String Agent;
    private Integer AppType;
    private Date ProcessTime;


    public Date getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(Date updateTime) {
        UpdateTime = updateTime;
    }
    public String getAppNo() {
        return AppNo;
    }

    public void setAppNo(String appNo) {
        AppNo = appNo;
    }

    public Date getAppDate() {
        return AppDate;
    }

    public void setAppDate(Date appDate) {
        AppDate = appDate;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getApplicant() {
        return Applicant;
    }

    public void setApplicant(String applicant) {
        Applicant = applicant;
    }

    public String getInventors() {
        return Inventors;
    }

    public void setInventors(String inventors) {
        Inventors = inventors;
    }

    public String getCpqueryStatus() {
        return CpqueryStatus;
    }

    public void setCpqueryStatus(String cpqueryStatus) {
        CpqueryStatus = cpqueryStatus;
    }

    public String getAgency() {
        return Agency;
    }

    public void setAgency(String agency) {
        Agency = agency;
    }

    public String getAgent() {
        return Agent;
    }

    public void setAgent(String agent) {
        Agent = agent;
    }

    public Integer getAppType() {
        return AppType;
    }

    public void setAppType(Integer appType) {
        AppType = appType;
    }

    public Date getProcessTime() {
        return ProcessTime;
    }

    public void setProcessTime(Date processTime) {
        ProcessTime = processTime;
    }
}
