package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.ComboboxItem;

import java.util.List;
import java.util.Map;

public interface ItbMenuPermissionService {
    List<ComboboxItem> getAllFunctionItems();

    List<Integer> getAllByMenuID(int menuId);

    boolean SaveAll(Map<Integer, List<Integer>> Items);

    boolean CopyConfig(int Source, int Target);
}
