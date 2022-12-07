package com.zhide.dtsystem.services;

import com.zhide.dtsystem.models.NBBHInfo;
import com.zhide.dtsystem.models.UInfo;
import com.zhide.dtsystem.models.patentInfoPermission;
import com.zhide.dtsystem.repositorys.patentInfoPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class NBBHSaver {
    @Autowired
    patentInfoPermissionRepository permissionRep;
    @Autowired
    NBBHCode NBBHCode;

    @Transactional(rollbackFor = Exception.class)
    public boolean SaveAll(String shenqingh, String nbbh) {
        NBBHInfo Info = NBBHCode.Parse(nbbh);
        permissionRep.deleteAllByShenqingh(shenqingh);
        Info.foreach((type, items) -> {
            List<patentInfoPermission> news = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                UInfo uInfo = items.get(i);
                patentInfoPermission newP = new patentInfoPermission();
                newP.setShenqingh(shenqingh);
                newP.setUsertype(type);
                newP.setUserid(uInfo.getID());
                news.add(newP);
            }
            permissionRep.saveAll(news);
        });
        return true;
    }
}
