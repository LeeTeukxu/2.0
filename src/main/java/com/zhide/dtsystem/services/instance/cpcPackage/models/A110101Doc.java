package com.zhide.dtsystem.services.instance.cpcPackage.models;

import com.zhide.dtsystem.models.cpcAgent;
import com.zhide.dtsystem.models.cpcApplyMan;
import com.zhide.dtsystem.models.cpcFiles;
import com.zhide.dtsystem.models.cpcInventor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: A110101Doc
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年08月24日 16:17
 **/
public class A110101Doc implements Serializable {
    String nbbh;
    String famingmc;
    String agentName;
    String agentCode;
    String firstInventCountry;
    String firstInventIDCode;
    boolean sameApply;
    boolean realMessage;
    boolean advanceApply;
    String digistImage;
    String year;
    String month;
    String day;
    Integer itemCount;

    String inventor1;
    String inventor2;
    String inventor3;

    boolean invent1NotOpen;
    boolean invent2NotOpen;
    boolean invent3NotOpen;

    cpcApplyMan  apply1;
    cpcApplyMan  apply2;
    cpcApplyMan  apply3;

    cpcAgent agent1;
    cpcAgent agent2;

    List<cpcFiles> files;
    List<cpcFiles> addFiles;

    List<cpcInventor> bigInventors;
    List<cpcApplyMan> bigApplys;

    cpcInventor inventor;
    cpcApplyMan apply;

    public String getNbbh() {
        return nbbh;
    }

    public void setNbbh(String nbbh) {
        this.nbbh = nbbh;
    }

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

    public String getFirstInventCountry() {
        return firstInventCountry;
    }

    public void setFirstInventCountry(String firstInventCountry) {
        this.firstInventCountry = firstInventCountry;
    }

    public String getFirstInventIDCode() {
        return firstInventIDCode;
    }

    public void setFirstInventIDCode(String firstInventIDCode) {
        this.firstInventIDCode = firstInventIDCode;
    }

    public boolean isSameApply() {
        return sameApply;
    }

    public void setSameApply(boolean sameApply) {
        this.sameApply = sameApply;
    }

    public boolean isRealMessage() {
        return realMessage;
    }

    public void setRealMessage(boolean realMessage) {
        this.realMessage = realMessage;
    }

    public String getDigistImage() {
        return digistImage;
    }

    public void setDigistImage(String digistImage) {
        this.digistImage = digistImage;
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

    public String getInventor1() {
        return inventor1;
    }

    public void setInventor1(String inventor1) {
        this.inventor1 = inventor1;
    }

    public String getInventor2() {
        return inventor2;
    }

    public void setInventor2(String inventor2) {
        this.inventor2 = inventor2;
    }

    public String getInventor3() {
        return inventor3;
    }

    public void setInventor3(String inventor3) {
        this.inventor3 = inventor3;
    }

    public boolean isInvent1NotOpen() {
        return invent1NotOpen;
    }

    public void setInvent1NotOpen(boolean invent1NotOpen) {
        this.invent1NotOpen = invent1NotOpen;
    }

    public boolean isInvent2NotOpen() {
        return invent2NotOpen;
    }

    public void setInvent2NotOpen(boolean invent2NotOpen) {
        this.invent2NotOpen = invent2NotOpen;
    }

    public boolean isInvent3NotOpen() {
        return invent3NotOpen;
    }

    public void setInvent3NotOpen(boolean invent3NotOpen) {
        this.invent3NotOpen = invent3NotOpen;
    }

    public cpcApplyMan getApply1() {
        return apply1;
    }

    public void setApply1(cpcApplyMan apply1) {
        this.apply1 = apply1;
    }

    public cpcApplyMan getApply2() {
        return apply2;
    }

    public void setApply2(cpcApplyMan apply2) {
        this.apply2 = apply2;
    }

    public cpcApplyMan getApply3() {
        return apply3;
    }

    public void setApply3(cpcApplyMan apply3) {
        this.apply3 = apply3;
    }

    public cpcAgent getAgent1() {
        return agent1;
    }

    public void setAgent1(cpcAgent agent1) {
        this.agent1 = agent1;
    }

    public cpcAgent getAgent2() {
        return agent2;
    }

    public void setAgent2(cpcAgent agent2) {
        this.agent2 = agent2;
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

    public List<cpcInventor> getBigInventors() {
        return bigInventors;
    }

    public void setBigInventors(List<cpcInventor> bigInventors) {
        this.bigInventors = bigInventors;
    }

    public List<cpcApplyMan> getBigApplys() {
        return bigApplys;
    }

    public void setBigApplys(List<cpcApplyMan> bigApplys) {
        this.bigApplys = bigApplys;
    }

    public cpcInventor getInventor() {
        return inventor;
    }

    public void setInventor(cpcInventor inventor) {
        this.inventor = inventor;
    }

    public cpcApplyMan getApply() {
        return apply;
    }

    public void setApply(cpcApplyMan apply) {
        this.apply = apply;
    }

    public boolean isAdvanceApply() {
        return advanceApply;
    }

    public void setAdvanceApply(boolean advanceApply) {
        this.advanceApply = advanceApply;
    }
}
