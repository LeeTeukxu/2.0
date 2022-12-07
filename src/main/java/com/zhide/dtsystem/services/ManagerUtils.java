package com.zhide.dtsystem.services;

import com.zhide.dtsystem.models.managerPermission;
import com.zhide.dtsystem.models.tbLoginUser;
import com.zhide.dtsystem.services.define.IManagerPermissionService;
import com.zhide.dtsystem.services.define.ItbLoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @ClassName: ManagerUtils
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年01月24日 11:15
 **/
@Component
public class ManagerUtils {
    @Autowired
    ItbLoginUserService loginUserService;
    @Autowired
    IManagerPermissionService mangerService;
    List<Integer> ManagerIDS = Arrays.asList(7, 10, 11, 13);

    public List<Integer> getAllUsers(int ManagerID) {
        List<Integer> IDS = new ArrayList<>();
        List<tbLoginUser> users = loginUserService.getAll();
        List<managerPermission> ms = mangerService.getAll();
        tbLoginUser mInfo = users.stream().filter(f -> f.getUserId() == ManagerID).findFirst().get();
        Integer UserRoleID = Integer.parseInt(mInfo.getRoleId());
        if (ManagerIDS.contains(UserRoleID)) {
            List<Integer> DepIDS = ms.stream()
                    .filter(f -> f.getType().equals("Dep") && f.getUserId() == ManagerID)
                    .map(f -> f.getValue()).collect(toList());
            for (int i = 0; i < DepIDS.size(); i++) {
                Integer DepID = DepIDS.get(i);
                users.stream().filter(f -> f.getDepId().equals(DepID))
                        .map(f -> f.getUserId())
                        .forEach(f -> {
                            if (IDS.contains(f) == false) IDS.add(f);
                        });
            }
            ms.stream().filter(f -> f.getType().equals("Man") && f.getUserId() == ManagerID)
                    .map(f -> f.getValue()).forEach(f -> {
                if (IDS.contains(f) == false) IDS.add(f);
            });
        } else IDS.add(ManagerID);
        return IDS;
    }
}
