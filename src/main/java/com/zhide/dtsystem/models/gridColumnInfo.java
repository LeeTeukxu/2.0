package com.zhide.dtsystem.models;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

public class gridColumnInfo implements Serializable {
    private String field;
    private String header;
    private String type;
    private Integer width;
    private Integer colIndex;
    private String required;
    private String dataSource;
    private String regex;
    private String error;
    private String contains;

    public String getContains() {
        return contains;
    }

    public void setContains(String contains) {
        this.contains = contains;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public Integer getColIndex() {
        return colIndex;
    }

    public void setColIndex(Integer colIndex) {
        this.colIndex = colIndex;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getRequired() {
        if (StringUtils.isEmpty(required)) required = "";
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }
}
