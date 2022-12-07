package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.TreeListItem;
import com.zhide.dtsystem.models.tbRoleClass;

import java.util.List;
import java.util.Map;

public interface ItbRoleClassService {
    List<tbRoleClass> getAll();
    List<TreeListItem> getAllCanuseItems(boolean canUse);
    boolean saveAll(List<tbRoleClass> items);
    boolean removeAll(List<Integer> ids);
    List<Map<String,Object>>getAllUserByRole(Integer RoleID);
}
