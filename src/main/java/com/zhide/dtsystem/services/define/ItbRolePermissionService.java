package com.zhide.dtsystem.services.define;

import java.util.List;
import java.util.Map;

public interface ItbRolePermissionService {
    List<Map<String, Object>> findAllByRoleId(int roleId) throws IllegalAccessException, Exception;

    boolean SaveAll(Integer roleId, Map<Integer, List<Integer>> data);
}
