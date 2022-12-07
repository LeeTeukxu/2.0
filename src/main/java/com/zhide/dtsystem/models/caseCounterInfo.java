package com.zhide.dtsystem.models;


import java.io.Serializable;

public class caseCounterInfo implements Serializable {
    private String casesid;
    private String subid;
    private Integer state;
    private Integer createMan;
    private Integer auditMan;
    private String techMan;
    private String techManager;
    private String auditManager;
    private String createManager;
    private String mode;

    public String getCreateManager() {
        return createManager;
    }

    public void setCreateManager(String createManager) {
        this.createManager = createManager;
    }

    public String getCasesid() {
        return casesid;
    }

    public void setCasesid(String casesid) {
        this.casesid = casesid;
    }

    public Integer getState() {
        if (state == null) state = 0;
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getCreateMan() {
        return createMan;
    }

    public void setCreateMan(Integer createMan) {
        this.createMan = createMan;
    }

    public Integer getAuditMan() {
        return auditMan;
    }

    public void setAuditMan(Integer auditMan) {
        this.auditMan = auditMan;
    }

    public String getTechMan() {
        return techMan;
    }

    public void setTechMan(String techMan) {
        this.techMan = techMan;
    }

    public String getTechManager() {
        return techManager;
    }

    public void setTechManager(String techManager) {
        this.techManager = techManager;
    }

    public String getAuditManager() {
        return auditManager;
    }

    public void setAuditManager(String auditManager) {
        this.auditManager = auditManager;
    }

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
