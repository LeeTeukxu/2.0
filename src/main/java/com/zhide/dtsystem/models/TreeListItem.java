package com.zhide.dtsystem.models;

import java.io.Serializable;

public class TreeListItem implements Serializable {

    public TreeListItem() {
    }

    public TreeListItem(String Id, String pId, String text) {
        this.id = Id;
        this.pid = pId;
        this.text = text;
    }

    private String id;
    private String pid;
    private String text;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
