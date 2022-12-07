package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.common.IntegerUtils;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.caseHighUser;
import com.zhide.dtsystem.repositorys.caseHighUserRepository;
import com.zhide.dtsystem.services.define.ICaseHighUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: CaseHighUserServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年11月23日 10:34
 **/

@Service
public class CaseHighUserServiceImpl implements ICaseHighUserService {

    @Autowired
    SysLoginUserMapper sysMapper;
    @Autowired
    caseHighUserRepository userRep;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void AddOne(String CasesID, int UserID) {
        String Uss = sysMapper.getManager(Integer.toString(UserID));
        List<Integer> Us = Arrays.asList(Uss.split(","))
                .stream().map(f -> Integer.parseInt(f)).distinct()
                .collect(Collectors.toList());
        if (Us.contains(UserID) == false) Us.add(UserID);
        for (int i = 0; i < Us.size(); i++) {
            Integer U = Us.get(i);
            List<caseHighUser> finds = userRep.findAllByCasesIdAndUserId(CasesID, UserID);
            if (finds.size() == 0) {
                caseHighUser newOne = new caseHighUser();
                newOne.setCasesId(CasesID);
                newOne.setUserId(U);
                newOne.setCreateTime(new Date());
                userRep.save(newOne);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void RemoveOne(String CasesID, int UserID) {
        String Uss = sysMapper.getManager(Integer.toString(UserID));
        List<Integer> Us = Arrays.asList(Uss.split(","))
                .stream().map(f -> Integer.parseInt(f)).distinct()
                .collect(Collectors.toList());
        if (Us.contains(UserID) == false) Us.add(UserID);
        for(Integer UID:Us){
            List<caseHighUser> finds = userRep.findAllByCasesIdAndUserId(CasesID, UID);
            if (finds.size()>0) {
                userRep.deleteAll(finds);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void replaceOne(String CasesID, Integer OldMan, Integer NewMan, String OldManager,
            String NewManager) {
        List<Integer> RIDS=new ArrayList<>();
        RIDS.add(OldMan);
        List<Integer> OldManagerIDS= IntegerUtils.parseIntArray(OldManager);
        RIDS.addAll(OldManagerIDS);

        userRep.deleteAllByCasesIdAndUserIdIn(CasesID,RIDS);

        List<Integer>AIDS=new ArrayList<>();
        AIDS.add(NewMan);
        List<Integer> NewManagerIDS=IntegerUtils.parseIntArray(NewManager);
        AIDS.addAll(NewManagerIDS);
        for(Integer UserID:AIDS){
            caseHighUser subUser=new caseHighUser();
            subUser.setUserId(UserID);
            subUser.setCasesId(CasesID);
            subUser.setCreateTime(new Date());
            userRep.save(subUser);
        }
    }
}
