package com.zhide.dtsystem.models;

import java.io.Serializable;

public class ComboboxItem implements Serializable {
    public ComboboxItem(){

    }
    public ComboboxItem(String Id,String text){
        this.id=Id;
        this.text=text;
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String id;
    private String text;
    private String code;
    private String name;
}
