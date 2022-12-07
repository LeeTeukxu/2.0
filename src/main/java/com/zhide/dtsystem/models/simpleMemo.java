package com.zhide.dtsystem.models;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class simpleMemo implements Serializable {
    private Integer xid;
    private String id;
    private String pid;
    private String memo;
    private Date createTime;
    private  String imageData;

    public String getId() {
        if(StringUtils.isEmpty(id))id= UUID.randomUUID().toString();
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateMan() {
        return createMan;
    }

    public void setCreateMan(Integer createMan) {
        this.createMan = createMan;
    }

    public String getCreateManName() {
        return createManName;
    }

    public void setCreateManName(String createManName) {
        this.createManName = createManName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateMan() {
        return updateMan;
    }

    public void setUpdateMan(Integer updateMan) {
        this.updateMan = updateMan;
    }

    public String getUpdateManName() {
        return updateManName;
    }

    public void setUpdateManName(String updateManName) {
        this.updateManName = updateManName;
    }

    public Integer getEdit() {
        return Edit;
    }

    public void setEdit(Integer edit) {
        Edit = edit;
    }

    private Integer createMan;
    private String createManName;
    private Date updateTime;
    private Integer updateMan;
    private String updateManName;
    private Integer Edit;
    private String source;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    private String change;

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public Integer getXid() {
        return xid;
    }

    public void setXid(Integer xid) {
        this.xid = xid;
    }
}
