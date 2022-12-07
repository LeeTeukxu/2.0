package com.zhide.dtsystem.services.instance.cpcPackage.models;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: A100007Xml
 * @Author: 肖新民
 * @*TODO:填充100007.xml的对象类型
 * @CreateTime: 2021年08月19日 16:25
 **/
public class A100007Xml implements Serializable {
    String agentName;
    String agentCode;
    String famingmc;
    List<String> agents;
    List<String> clients;
    List<String> images;

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


    public List<String> getAgents() {
        return agents;
    }

    public void setAgents(List<String> agents) {
        this.agents = agents;
    }

    public List<String> getClients() {
        return clients;
    }

    public void setClients(List<String> clients) {
        this.clients = clients;
    }

    public String getFamingmc() {
        return famingmc;
    }

    public void setFamingmc(String famingmc) {
        this.famingmc = famingmc;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
