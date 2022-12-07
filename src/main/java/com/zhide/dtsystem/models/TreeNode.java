package com.zhide.dtsystem.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @ClassName: TreeNode
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年12月06日 11:55
 **/
public class TreeNode implements Serializable {
    private static final long serialVersionUID = 1L;

    private String FID;

    private String PID;

    private String Name;

    @JsonProperty("FID")
    public String getFID() {
        return FID;
    }

    public void setFID(String FID) {
        this.FID = FID;
    }

    @JsonProperty("PID")
    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    @JsonProperty("Name")
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
