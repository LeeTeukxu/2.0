package com.zhide.dtsystem.common;

import com.zhide.dtsystem.models.tbDepList;
import com.zhide.dtsystem.services.define.ItbDepListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DepDataHelper {
    @Autowired
    private ItbDepListService itbDepListService;
    private static ItbDepListService tbDepListService;

    @PostConstruct
    public void Init() {
        tbDepListService = itbDepListService;
    }

    public static List<Integer> getAllChildrenId(Integer depId) {
        List<Integer> result = new ArrayList<>();
        result.add(depId);
        List<tbDepList> depLists = tbDepListService.getAllCanUse();
        Optional<tbDepList> findOnes = depLists.stream().filter(f -> f.getDepId() == depId).findFirst();
        if (findOnes.isPresent() == true) {
            tbDepList findOne = findOnes.get();
            String sort = findOne.getSort();
            depLists.stream().filter(f -> f.getSort().startsWith(sort) == true).forEach(f -> {
                int findId = f.getDepId();
                if (result.contains(findId) == false) {
                    result.add(findId);
                }
            });
        }
        return result;
    }

    public static List<Integer> getAllParent(Integer depId) {
        List<Integer> res = new ArrayList<>();
        res.add(depId);
        List<tbDepList> deps = tbDepListService.getAllCanUse();
        while (true) {
            Integer Y = depId;
            Optional<tbDepList> findOnes = deps.stream().filter(f -> f.getDepId() == Y).findFirst();
            if (findOnes.isPresent()) {
                Integer pId = findOnes.get().getPid();
                if (res.contains(pId) == false) res.add(pId);
                depId = pId;
            } else break;
        }
        return res;
    }

    public static List<Integer> getAllParent(List<Integer> depIds) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < depIds.size(); i++) {
            Integer depId = depIds.get(i);
            res.add(depId);
            List<tbDepList> deps = tbDepListService.getAllCanUse();
            while (true) {
                Integer Y = depId;
                Optional<tbDepList> findOnes = deps.stream().filter(f -> f.getDepId() == Y).findFirst();
                if (findOnes.isPresent()) {
                    Integer pId = findOnes.get().getPid();
                    if (res.contains(pId) == false) res.add(pId);
                    depId = pId;
                } else break;
            }
        }
        return res;
    }
}
