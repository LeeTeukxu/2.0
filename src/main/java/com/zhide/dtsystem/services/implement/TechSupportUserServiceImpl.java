package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.common.IntegerUtils;
import com.zhide.dtsystem.common.ListUtils;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.casesUser;
import com.zhide.dtsystem.models.techSupportUser;
import com.zhide.dtsystem.repositorys.casesUserRepository;
import com.zhide.dtsystem.repositorys.techSupportUserRepository;
import com.zhide.dtsystem.services.define.ITechSupportUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: TechSupportUserServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2022年01月18日 10:13
 **/
@Service
public class TechSupportUserServiceImpl implements ITechSupportUserService {
    @Autowired
    SysLoginUserMapper sysMapper;
    @Autowired
    techSupportUserRepository userRep;
    Logger logger = LoggerFactory.getLogger(CasesUserServiceImpl.class);

    /**
     * @Author:肖新民
     * @CreateTime:2020-08-06 13:43
     * @Params:[CasesID, UserID]
     * Description:增加一个用户ID，以及经理的ID。
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void AddOne(String CasesID, int UserID) {
        List<Integer> Us = new ArrayList<>();
        if (Us.contains(UserID) == false) Us.add(UserID);
        for (int i = 0; i < Us.size(); i++) {
            Integer U = Us.get(i);
            List<techSupportUser> finds = userRep.findAllByCasesIdAndUserId(CasesID, UserID);
            if (finds.size() == 0) {
                techSupportUser newOne = new techSupportUser();
                newOne.setCasesId(CasesID);
                newOne.setUserId(U);
                newOne.setCreateTime(new Date());
                userRep.save(newOne);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void AddAll(String CasesID, String Users) {
        List<Integer> Us = ListUtils.parse(Users,Integer.class);
        for (int i = 0; i < Us.size(); i++) {
            Integer UserID = Us.get(i);
            List<techSupportUser> finds = userRep.findAllByCasesIdAndUserId(CasesID, UserID);
            if (finds.size() == 0) {
                techSupportUser newOne = new techSupportUser();
                newOne.setCasesId(CasesID);
                newOne.setUserId(UserID);
                newOne.setCreateTime(new Date());
                userRep.save(newOne);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void RemoveOne(String CasesID, int UserID) {
        List<techSupportUser> findUsers = userRep.findAllByCasesIdAndUserId(CasesID, UserID);
        userRep.deleteAll(findUsers);
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
            techSupportUser subUser=new techSupportUser();
            subUser.setUserId(UserID);
            subUser.setCasesId(CasesID);
            subUser.setCreateTime(new Date());
            userRep.save(subUser);
        }
    }
}
