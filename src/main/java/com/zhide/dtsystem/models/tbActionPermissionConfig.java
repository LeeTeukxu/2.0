package com.zhide.dtsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tbActionPermissionConfig")
public class tbActionPermissionConfig implements Serializable {
    @Id
    @Column(name = "Url")
    private String url;
    @Column(name = "DataType")
    private String dataType;
    @Column(name = "PermissionType")
    private String permissionType;
    @Column(name = "FilterField")
    private String filterField;
    @Column(name = "ParentField")
    private String parentField;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }


    public String getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType;
    }


    public String getFilterField() {
        return filterField;
    }

    public void setFilterField(String filterField) {
        this.filterField = filterField;
    }


    public String getParentField() {
        return parentField;
    }

    public void setParentField(String parentField) {
        this.parentField = parentField;
    }

}
