package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.TreeNode;
import com.zhide.dtsystem.models.tbDepList;

import java.util.List;
import java.util.Map;

public interface ItbDepListService {
    List<tbDepList> getAll();

    List<tbDepList> getAllCanUse();

    boolean saveAll(List<Map<String, Object>> datas);

    boolean removeAll(List<Integer> ids);

    List<TreeNode> getAllUsersByDep();

    List<TreeNode> getAllLoginUserInDep();

    List<TreeNode> getAllLoginUserInDepNotSelf(Integer UserID);

    List<Map<String, Object>> getAllByCanUseAndDepNum();
    Map<Integer, Integer> GetEmployeeNumbers() throws Exception;
    List<Map<String,Object>> getAllLoginUserByFun(String FunName);
}
