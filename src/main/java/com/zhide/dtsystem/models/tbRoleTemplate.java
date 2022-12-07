package com.zhide.dtsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tbRoleTemplate")
public class tbRoleTemplate implements Serializable {
    @Id
    @Column(name = "ID")
    private int id;
    @Column(name = "ProjectType")
    private String projectType;
    @Column(name = "RoleID")
    private int roleId;
    @Column(name = "TypeName")
    private String typeName;
    @Column(name = "Option")
    private String option;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }


    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

}
