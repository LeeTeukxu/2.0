package com.zhide.dtsystem.services.Resignation;

import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.followRecord;
import com.zhide.dtsystem.models.patentInfoPermission;
import com.zhide.dtsystem.models.tbClient;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.followRecordRepository;
import com.zhide.dtsystem.repositorys.patentInfoPermissionRepository;
import com.zhide.dtsystem.repositorys.tbClientRepository;
import com.zhide.dtsystem.services.define.IAllUserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatentInfoResignation {
    @Autowired
    patentInfoPermissionRepository pInfo;

    public void PatentInfoPermissionChangeUserID(Integer Transfer, Integer Resgination) throws Exception {
        List<patentInfoPermission> listPatentInfoPermission = new ArrayList<>();
        pInfo.findAll().stream().forEach(f -> {
            if (!f.getUsertype().equals("KH") && f.getUserid() == Resgination) {
                patentInfoPermission patentInfoPermission = new patentInfoPermission();
                patentInfoPermission = f;
                patentInfoPermission.setUserid(Transfer);
                listPatentInfoPermission.add(patentInfoPermission);
            }
        });
        pInfo.saveAll(listPatentInfoPermission);
    }
}
