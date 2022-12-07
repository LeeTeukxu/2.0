package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.models.ComboboxItem;
import com.zhide.dtsystem.models.FunctionItem;
import com.zhide.dtsystem.models.tbMenuPermissionItem;
import com.zhide.dtsystem.repositorys.FunctionItemRepository;
import com.zhide.dtsystem.repositorys.tbMenuPermissionRepository;
import com.zhide.dtsystem.services.define.ItbMenuPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class tbMenuPermissionServiceImpl implements ItbMenuPermissionService {

    @Autowired
    private tbMenuPermissionRepository MenuPermissionRep;
    @Autowired
    private FunctionItemRepository functionItemRepository;

    @Override
    public List<ComboboxItem> getAllFunctionItems() {
        List<FunctionItem> Items = functionItemRepository.findAllByCanUseOrderBySn(true);
        List<ComboboxItem> res = new ArrayList<>();
        Items.stream().forEach(f -> {
            ComboboxItem item = new ComboboxItem();
            item.setId(Integer.toString(f.getfId()));
            item.setText(f.getTitle());
            if (f.getTitle().equals("查看页面") == false) {
                res.add(item);
            } else {
                res.add(0, item);
            }
        });
        return res;
    }

    @Override
    public List<Integer> getAllByMenuID(int menuId) {
        List<Integer> IDS = new ArrayList<>();
        List<Map<String, Object>> items = MenuPermissionRep.findAllMenusByMenID(menuId);
        items.forEach(f -> {
            IDS.add(Integer.parseInt(f.get("funId").toString()));
        });
        return IDS;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean SaveAll(Map<Integer, List<Integer>> Items) {
        for (int MenuID : Items.keySet()) {
            List<Integer> IDS = (List<Integer>) Items.get(MenuID);
            MenuPermissionRep.deleteByMenuId(MenuID);
            for (int i = 0; i < IDS.size(); i++) {
                int FunID = IDS.get(i);
                tbMenuPermissionItem Item = new tbMenuPermissionItem();
                Item.setMenuId(MenuID);
                Item.setFunId(FunID);
                MenuPermissionRep.save(Item);
            }
        }
        return true;
    }

    @Override
    @Transactional
    public boolean CopyConfig(int Source, int Target) {
        List<tbMenuPermissionItem> SourceMenus = MenuPermissionRep.findAllByMenuId(Source);
        for (int i = 0; i < SourceMenus.size(); i++) {
            tbMenuPermissionItem item = SourceMenus.get(i);
            tbMenuPermissionItem newItem = new tbMenuPermissionItem();
            newItem.setMenuId(Target);
            newItem.setFunId(item.getFunId());
            MenuPermissionRep.save(newItem);
        }
        return true;
    }
}
