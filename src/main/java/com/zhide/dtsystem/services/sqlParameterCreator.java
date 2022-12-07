package com.zhide.dtsystem.services;

import com.zhide.dtsystem.models.sqlParameter;
import org.apache.commons.lang.StringUtils;

import java.net.URLDecoder;
import java.util.*;

public class sqlParameterCreator {
    public static List<sqlParameter> convert(List<sqlParameter> parameters) throws Exception {
        List<sqlParameter> result = new ArrayList<>();
        Map<String, String> opHash = new HashMap<>();
        opHash.put("GE", ">=");
        opHash.put("LE", "<=");
        opHash.put("EQ", "=");
        opHash.put("GT", ">");
        opHash.put("LT", "<");
        for (int i = 0; i < parameters.size(); i++) {
            sqlParameter parameter = parameters.get(i);
            attachCheck(parameter.getField());
            attachCheck(parameter.getOper());
            attachCheck(parameter.getValue());
            String xValue=parameter.getValue();
            if(xValue.indexOf("%")>-1) {
                xValue = URLDecoder.decode(xValue, "utf-8");
                parameter.setValue(xValue);
            }
            String oper = parameter.getOper();
            if (opHash.containsKey(oper)) {
                parameter.setOper(opHash.get(oper));
            }
            if (oper.equals("LIKE")) {
                String value = parameter.getValue();
                parameter.setValue("%" + StringUtils.trim(value) + "%");
            }
            result.add(parameter);
        }
        return result;
    }

    private static void attachCheck(String value) throws Exception {
        List<String> targets = Arrays.asList("select", "update", "delete", "truncate", "drop");
        for (int i = 0; i < targets.size(); i++) {
            String tf = targets.get(i);
            if (value.indexOf(tf) > -1) {
                throw new Exception("存在在安全风险，不进行查询 。");
            }
        }
    }
}
