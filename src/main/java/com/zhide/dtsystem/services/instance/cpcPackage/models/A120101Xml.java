package com.zhide.dtsystem.services.instance.cpcPackage.models;

import com.zhide.dtsystem.models.cpcAgent;
import com.zhide.dtsystem.models.cpcApplyMan;
import com.zhide.dtsystem.models.cpcFiles;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: A120101Xml
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年08月20日 16:44
 **/
public class A120101Xml implements Serializable {
    String famingmc;
    String nbbh;
    String agentName;
    String agentCode;
    String firstInventor;
    String firstInventorCountry;
    String firstInventorIDCode;
    List<String> inventors;
    List<cpcApplyMan> applyMans;
    List<cpcAgent> agents;
    List<cpcFiles> files;
    List<cpcFiles> addFiles;
    String year;
    String month;
    String day;
    Integer itemCount;

    public String getFamingmc() {
        return famingmc;
    }

    public void setFamingmc(String famingmc) {
        this.famingmc = famingmc;
    }

    public String getNbbh() {
        return nbbh;
    }

    public void setNbbh(String nbbh) {
        this.nbbh = nbbh;
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

    public String getFirstInventor() {
        return firstInventor;
    }

    public void setFirstInventor(String firstInventor) {
        this.firstInventor = firstInventor;
    }

    public String getFirstInventorCountry() {
        return firstInventorCountry;
    }

    public void setFirstInventorCountry(String firstInventorCountry) {
        this.firstInventorCountry = firstInventorCountry;
    }

    public String getFirstInventorIDCode() {
        return firstInventorIDCode;
    }

    public void setFirstInventorIDCode(String firstInventorIDCode) {
        this.firstInventorIDCode = firstInventorIDCode;
    }

    public List<String> getInventors() {
        return inventors;
    }

    public void setInventors(List<String> inventors) {
        this.inventors = inventors;
    }

    public List<cpcApplyMan> getApplyMans() {
        return applyMans;
    }

    public void setApplyMans(List<cpcApplyMan> applyMans) {
        this.applyMans = applyMans;
    }

    public List<cpcAgent> getAgents() {
        return agents;
    }

    public void setAgents(List<cpcAgent> agents) {
        this.agents = agents;
    }

    public List<cpcFiles> getFiles() {
        return files;
    }

    public void setFiles(List<cpcFiles> files) {
        this.files = files;
    }

    public List<cpcFiles> getAddFiles() {
        return addFiles;
    }

    public void setAddFiles(List<cpcFiles> addFiles) {
        this.addFiles = addFiles;
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

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }
}
