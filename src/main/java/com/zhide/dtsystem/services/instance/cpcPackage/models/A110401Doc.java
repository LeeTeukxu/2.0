package com.zhide.dtsystem.services.instance.cpcPackage.models;

/**
 * @ClassName: A110401Doc
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年09月02日 14:14
 **/
public class A110401Doc {
    String famingmc;
    String agentName;
    String agentCode;
    String firstApplyMan;
    boolean abandonChangeRights;
    String year;
    String month;
    String day;

    public String getFamingmc() {
        return famingmc;
    }

    public void setFamingmc(String famingmc) {
        this.famingmc = famingmc;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public boolean isAbandonChangeRights() {
        return abandonChangeRights;
    }

    public void setAbandonChangeRights(boolean abandonChangeRights) {
        this.abandonChangeRights = abandonChangeRights;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getFirstApplyMan() {
        return firstApplyMan;
    }

    public void setFirstApplyMan(String firstApplyMan) {
        this.firstApplyMan = firstApplyMan;
    }
}
