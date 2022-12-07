package com.zhide.dtsystem.models;

import java.io.Serializable;

public class sqlParameter implements Serializable {
    String field;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    String oper;
    String value;
}
