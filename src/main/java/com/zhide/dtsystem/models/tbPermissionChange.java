package com.zhide.dtsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbPermissionChange")
public class tbPermissionChange implements Serializable {
    @Id
    @Column(name = "FID")
    private int fid;
    @Column(name = "UID")
    private int uid;
    @Column(name = "PType")
    private String pType;
    @Column(name = "HasClear")
    private boolean hasClear;
    @Column(name = "Time")
    private Date time;
    @Column(name = "RoleID")
    private int roleId;
    @Column(name = "MenuID")
    private int menuId;
    @Column(name = "Type")
    private int type;

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }


    public String getPType() {
        return pType;
    }

    public void setPType(String pType) {
        this.pType = pType;
    }


    public boolean getHasClear() {
        return hasClear;
    }

    public void setHasClear(boolean hasClear) {
        this.hasClear = hasClear;
    }


    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }


    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }


    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
