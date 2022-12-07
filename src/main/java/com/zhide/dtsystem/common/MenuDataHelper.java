package com.zhide.dtsystem.common;

import com.zhide.dtsystem.models.tbMenuList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuDataHelper {
    public static List<Integer> getAllParent(List<Integer> ids, List<tbMenuList> menuList) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            Integer Id = ids.get(i);
            Optional<tbMenuList> findOne = menuList.stream().filter(f -> f.getFid().equals(Id)).findFirst();
            if (findOne.isPresent() == false) continue;
            ;
            if (res.contains(Id) == false) res.add(Id);
            Integer X = Id;
            while (true) {
                Integer findValue = findSingle(X, menuList);
                if (findValue != null) {
                    if (res.contains(findValue) == false) {
                        res.add(findValue);
                    }
                    X = findValue;
                    if (X == null) break;
                } else break;
            }
        }
        return res;
    }

    private static Integer findSingle(Integer Value, List<tbMenuList> menuLists) {
        Optional<tbMenuList> find = menuLists.stream().filter(f -> f.getFid().equals(Value)).findFirst();
        if (find.isPresent()) return find.get().getPid();
        else return null;
    }
}
