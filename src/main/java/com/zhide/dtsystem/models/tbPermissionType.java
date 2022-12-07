package com.zhide.dtsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tbPermissionType")
public class tbPermissionType implements Serializable {
    @Id
    @Column(name = "FID")
    private int fid;
    @Column(name = "PrjID")
    private int prjId;
    @Column(name = "SN")
    private int sn;
    @Column(name = "Name")
    private String name;
    @Column(name = "CanUse")
    private boolean canUse;
    @Column(name = "ConfigInRole")
    private boolean configInRole;
    @Column(name = "SourceControl")
    private String sourceControl;
    @Column(name = "DbName")
    private String dbName;
    @Column(name = "TableName")
    private String tableName;
    @Column(name = "IDField")
    private String idField;
    @Column(name = "ParentField")
    private String parentField;
    @Column(name = "TextField")
    private String textField;
    @Column(name = "Condition")
    private String condition;
    @Column(name = "OrderBy")
    private String orderBy;
    @Column(name = "AddView")
    private boolean addView;
    @Column(name = "ViewValue")
    private String viewValue;
    @Column(name = "DataPermission")
    private boolean dataPermission;

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }


    public int getPrjId() {
        return prjId;
    }

    public void setPrjId(int prjId) {
        this.prjId = prjId;
    }


    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean getCanUse() {
        return canUse;
    }

    public void setCanUse(boolean canUse) {
        this.canUse = canUse;
    }


    public boolean getConfigInRole() {
        return configInRole;
    }

    public void setConfigInRole(boolean configInRole) {
        this.configInRole = configInRole;
    }


    public String getSourceControl() {
        return sourceControl;
    }

    public void setSourceControl(String sourceControl) {
        this.sourceControl = sourceControl;
    }


    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


    public String getIdField() {
        return idField;
    }

    public void setIdField(String idField) {
        this.idField = idField;
    }


    public String getParentField() {
        return parentField;
    }

    public void setParentField(String parentField) {
        this.parentField = parentField;
    }


    public String getTextField() {
        return textField;
    }

    public void setTextField(String textField) {
        this.textField = textField;
    }


    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }


    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }


    public boolean getAddView() {
        return addView;
    }

    public void setAddView(boolean addView) {
        this.addView = addView;
    }


    public String getViewValue() {
        return viewValue;
    }

    public void setViewValue(String viewValue) {
        this.viewValue = viewValue;
    }


    public boolean getDataPermission() {
        return dataPermission;
    }

    public void setDataPermission(boolean dataPermission) {
        this.dataPermission = dataPermission;
    }

}
