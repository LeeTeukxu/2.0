package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.common.EntityHelper;
import com.zhide.dtsystem.common.MenuDataHelper;
import com.zhide.dtsystem.common.RedisUtils;
import com.zhide.dtsystem.models.ComboboxItem;
import com.zhide.dtsystem.models.tbMenuList;
import com.zhide.dtsystem.models.tbRoleFunctionSave;
import com.zhide.dtsystem.repositorys.tbMenuPermissionRepository;
import com.zhide.dtsystem.repositorys.tbRolePermissionRepository;
import com.zhide.dtsystem.services.define.ItbMenuListService;
import com.zhide.dtsystem.services.define.ItbRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class tbRolePermissionServiceImpl implements ItbRolePermissionService {

    @Autowired
    ItbMenuListService menuListService;
    @Autowired
    tbMenuPermissionRepository menuPermissionRepository;

    @Autowired
    tbRolePermissionRepository rolePermissionRepository;
    @Autowired
    RedisUtils redisUtils;

    @Override
    public List<Map<String, Object>> findAllByRoleId(int roleId) throws Exception {
        List<Map<String, Object>> OO = new ArrayList<>();
        List<tbMenuList> allMenus = menuListService.getAllByCanUse();
        List<Integer> hasMenu = getAllMenuFunctions(allMenus);
        int allMenusCount = allMenus.size();
        for (int i = 0; i < allMenusCount; i++) {
            tbMenuList singleMenu = allMenus.get(i);
            int menuId = singleMenu.getFid();
            if (hasMenu.contains(menuId) == true) {
                Map<String, Object> menuHash = EntityHelper.toMap(singleMenu);
                Optional<tbMenuList> findFirst = allMenus.stream().filter(f -> f.getPid() == menuId).findAny();
                if (findFirst.isPresent() == false) {
                    List<ComboboxItem> allFuns = getAllFunctionsByMenuId(menuId);
                    menuHash.put("Functions", allFuns);
                    List<Integer> checks = rolePermissionRepository.
                            findAllByMenuIdAndRoleId(menuId, roleId).stream()
                            .mapToInt(f -> f.getFunId()).boxed().collect(toList());
                    menuHash.put("Checkeds", checks);
                }
                OO.add(menuHash);
            }
        }
        return OO;
    }

    private List<Integer> getAllMenuFunctions(List<tbMenuList> allMenus) {
        List<Integer> allPermissionMenus = menuPermissionRepository.findAll().stream()
                .mapToInt(f -> f.getMenuId()).distinct().boxed().collect(Collectors.toList());
        List<Integer> hasMenu = MenuDataHelper.getAllParent(allPermissionMenus, allMenus);
        return hasMenu;
    }

    /**
     * @param menuId
     * @return
     */
    private List<ComboboxItem> getAllFunctionsByMenuId(int menuId) {
        List<Map<String, Object>> funs = menuPermissionRepository.findAllMenusByMenID(menuId);
        List<ComboboxItem> res = new ArrayList<>();
        for (int i = 0; i < funs.size(); i++) {
            Map<String, Object> fun = funs.get(i);
            ComboboxItem item = new ComboboxItem();
            item.setId(fun.get("funId").toString());
            item.setText(fun.get("text").toString());
            res.add(item);
        }
        Optional<ComboboxItem> finds = res.stream().filter(f -> f.getId().equals("3") == true).findFirst();
        if (finds.isPresent() == false) {
            ComboboxItem item = new ComboboxItem();
            item.setText("查看页面");
            item.setId("3");
            res.add(0, item);
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean SaveAll(Integer roleId, Map<Integer, List<Integer>> data) {
        rolePermissionRepository.removeByRoleId(roleId);
        for (int menuId : data.keySet()) {
            List<Integer> Funs = data.get(menuId);
            for (int i = 0; i < Funs.size(); i++) {
                Integer Fun = Funs.get(i);
                tbRoleFunctionSave newObj = new tbRoleFunctionSave();
                newObj.setFunId(Fun);
                newObj.setRoleId(roleId);
                newObj.setMenuId(menuId);
                rolePermissionRepository.save(newObj);
            }
        }
        redisUtils.clearAll("Role");
        return true;
    }
}
