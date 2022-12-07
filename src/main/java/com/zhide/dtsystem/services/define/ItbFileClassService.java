package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.TreeListItem;
import com.zhide.dtsystem.models.tbFileClass;
import com.zhide.dtsystem.models.tbRoleClass;

import java.util.List;

/**
 * @ClassName: ItbFileClassService
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年11月15日 13:39
 **/
public interface ItbFileClassService {
    List<tbFileClass> getAll();
    List<TreeListItem> getAllCanuseItems(boolean canUse);
    boolean saveAll(List<tbFileClass> items);
    boolean removeAll(List<Integer> ids) throws Exception;
}
