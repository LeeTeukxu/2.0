package com.zhide.dtsystem.models;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ExcelTemplateInfo {
    public static ExcelTemplateInfo parseFrom(IExcelExportTemplate row) throws Exception {
        if (row == null) return null;
        ExcelTemplateInfo result = new ExcelTemplateInfo();
        Class<? extends IExcelExportTemplate> classInfo = row.getClass();
        ExcelTitle title = classInfo.getDeclaredAnnotation(ExcelTitle.class);
        if (title != null) {
            result.setHeader(title);
        }
        Map<Integer, ExcelColumn> cs = new HashMap<>();
        Field[] fields = classInfo.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            ExcelColumn columnInfo = field.getDeclaredAnnotation(ExcelColumn.class);
            if (columnInfo != null) {
                InvocationHandler invocationHandler = Proxy.getInvocationHandler(columnInfo);
                Field value = invocationHandler.getClass().getDeclaredField("memberValues");
                value.setAccessible(true);
                Map<String, Object> memberValues = (Map<String, Object>) value.get(invocationHandler);
                memberValues.put("field", field.getName());
                int column = columnInfo.columnIndex();
                cs.put(column, columnInfo);
            }
        }
        result.setColumns(cs);
        return result;
    }

    Map<Integer, ExcelColumn> columns;
    ExcelTitle header;

    public Map<Integer, ExcelColumn> getColumns() {
        return columns;
    }

    public void setColumns(Map<Integer, ExcelColumn> columns) {
        this.columns = columns;
    }

    public ExcelTitle getHeader() {
        return header;
    }

    public void setHeader(ExcelTitle header) {
        this.header = header;
    }
}
