package com.zhide.dtsystem.models;

/**
 * @ClassName casesSubAndMain
 * @Author xiao
 * @CreateTime 2020-07-23 15:03
 **/
public class casesSubAndMain {
    private String createMan;
    private String createManager;
    private String auditMan;
    private String auditManager;
    private String techMan;
    private String techManager;
    private Integer state;
    private String casesId;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCasesId() {
        return casesId;
    }

    public void setCasesId(String casesId) {
        this.casesId = casesId;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public String getCreateManager() {
        return createManager;
    }

    public void setCreateManager(String createManager) {
        this.createManager = createManager;
    }

    public String getAuditMan() {
        return auditMan;
    }

    public void setAuditMan(String auditMan) {
        this.auditMan = auditMan;
    }

    public String getAuditManager() {
        return auditManager;
    }

    public void setAuditManager(String auditManager) {
        this.auditManager = auditManager;
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
}
