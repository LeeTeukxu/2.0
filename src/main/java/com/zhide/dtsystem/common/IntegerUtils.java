package com.zhide.dtsystem.common;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class IntegerUtils {
    public static Integer parseInt(Long obj) {
        String X = Long.toString(obj);
        return Integer.parseInt(X);
    }

    public static Integer parseInt(Double obj) {
        String X = Double.toString(obj);
        String[] Vs = X.split("\\.");
        return Integer.parseInt(Vs[0]);
    }

    public static Integer parseInt(Object Obj) {
        if (ObjectUtils.isEmpty(Obj)) return null;
        String Dx = Obj.toString();
        return Integer.parseInt(Dx);
    }

    public static List<Integer> parseIntArray(String ids) {
        String dot = ",";
        List<Integer> res = new ArrayList<>();
        if (StringUtils.isEmpty(ids)) return res;
        if (ids.indexOf(dot) >= -1) {
            String[] ts = ids.split(dot);
            for (int i = 0; i < ts.length; i++) {
                String t = ts[i];
                if (StringUtils.isEmpty(t)) continue;
                res.add(Integer.parseInt(t));
            }
            return res;
        } else return res;
    }
}
