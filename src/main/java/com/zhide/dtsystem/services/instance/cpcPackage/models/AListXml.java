package com.zhide.dtsystem.services.instance.cpcPackage.models;

import com.zhide.dtsystem.models.cpcFiles;

import java.util.List;

/**
 * @ClassName: AListXml
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年08月27日 17:07
 **/
public class AListXml {
    String pid;
    String nbbh;
    String famingmc;
    Integer shenqinglx;
    List<cpcFiles> files;
    List<cpcFiles> addFiles;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

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

    public Integer getShenqinglx() {
        return shenqinglx;
    }

    public void setShenqinglx(Integer shenqinglx) {
        this.shenqinglx = shenqinglx;
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
}
