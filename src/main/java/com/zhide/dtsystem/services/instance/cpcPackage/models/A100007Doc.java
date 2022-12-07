package com.zhide.dtsystem.services.instance.cpcPackage.models;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: A100007Doc
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年08月20日 14:02
 **/
public class A100007Doc implements Serializable {
    String agentName;
    String agentCode;
    String famingmc;
    String imageData;
    String agent1;
    String agent2;
    List<String> clients;
    String year;
    String month;
    String day;

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

    public String getFamingmc() {
        return famingmc;
    }

    public void setFamingmc(String famingmc) {
        this.famingmc = famingmc;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public String getAgent1() {
        return agent1;
    }

    public void setAgent1(String agent1) {
        this.agent1 = agent1;
    }

    public String getAgent2() {
        return agent2;
    }

    public void setAgent2(String agent2) {
        this.agent2 = agent2;
    }

    public List<String> getClients() {
        return clients;
    }

    public void setClients(List<String> clients) {
        this.clients = clients;
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
}
