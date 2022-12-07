package com.zhide.dtsystem.models;

import java.util.List;

/**
 * @ClassName: findReportQuery
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2022年09月22日 17:23
 **/
public class FinanReportQuery {
    List<sqlParameter> cons;
    String timeField;
    String timeValue;
    String groupField;

    public List<sqlParameter> getCons() {
        return cons;
    }

    public void setCons(List<sqlParameter> cons) {
        this.cons = cons;
    }

    public String getTimeField() {
        return timeField;
    }

    public void setTimeField(String timeField) {
        this.timeField = timeField;
    }

    public String getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(String timeValue) {
        this.timeValue = timeValue;
    }

    public String getGroupField() {
        return groupField;
    }

    public void setGroupField(String groupField) {
        this.groupField = groupField;
    }
}
