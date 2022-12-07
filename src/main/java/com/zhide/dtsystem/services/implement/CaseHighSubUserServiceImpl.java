package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.common.IntegerUtils;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.caseHighMain;
import com.zhide.dtsystem.models.caseHighSubUser;
import com.zhide.dtsystem.repositorys.caseHighMainRepository;
import com.zhide.dtsystem.repositorys.caseHighSubUserRepository;
import com.zhide.dtsystem.repositorys.caseHighUserRepository;
import com.zhide.dtsystem.services.define.ICaseHighSubUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: CasesSubUserServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年08月22日 9:46
 **/
@Service
public class CaseHighSubUserServiceImpl implements ICaseHighSubUserService {

    @Autowired
    caseHighMainRepository mainRep;
    @Autowired
    caseHighUserRepository caseHighUserRep;
    @Autowired
    caseHighSubUserRepository caseHighSubRep;
    @Autowired
    SysLoginUserMapper sysMapper;

    /**
     * @Author:肖新民
     * @CreateTime:2020-08-22 9:46
     * @Params:[CasesID] Description: 交单审核的时候把当前的人员全部插到CasesSubUser中。
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importOne(String CasesID, String SubID) {
        Optional<caseHighMain> findMains = mainRep.findFirstByCasesId(CasesID);
        if (findMains.isPresent()) {
            caseHighMain main = findMains.get();
            List<String> nowUsers = new ArrayList<>();
            nowUsers.add(main.getCreateMan().toString());
            nowUsers.addAll(Arrays.asList(main.getCreateManager().split(",")));
            nowUsers.add(main.getAuditMan().toString());
            nowUsers.addAll(Arrays.asList(main.getAuditManager().split(",")));
            for (int i = 0; i < nowUsers.size(); i++) {
                int UserID = Integer.parseInt(nowUsers.get(i));
                Optional<caseHighSubUser> finds = caseHighSubRep.findFirstBySubIdAndUserId(SubID, UserID);
                if (finds.isPresent() == false) {
                    caseHighSubUser newOne = new caseHighSubUser();
                    newOne.setCasesId(CasesID);
                    newOne.setSubId(SubID);
                    newOne.setUserId(UserID);
                    newOne.setCreateTime(new Date());
                    caseHighSubRep.save(newOne);
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeOne(String CasesID, String SubID, Integer UserID) {
        String Uss = sysMapper.getManager(Integer.toString(UserID));
        List<Integer> Us = Arrays.asList(Uss.split(","))
                .stream().map(f -> Integer.parseInt(f)).distinct()
                .collect(Collectors.toList());
        if (Us.contains(UserID) == false) Us.add(UserID);
        for (int i = 0; i < Us.size(); i++) {
            Integer U = Us.get(i);
            Optional<caseHighSubUser> finds = caseHighSubRep.findFirstBySubIdAndUserId(SubID, U);
            if(finds.isPresent()){
                caseHighSubRep.delete(finds.get());
            }
        }
    }

    /**
     * @Author:肖新民
     * @CreateTime:2020-08-22 9:47
     * @Params:[CasesID, SubID, UserID]
     * Description:
     */
    @Override
    @Transactional
    public void addOne(String CasesID, String SubID, Integer UserID) {
        String Uss = sysMapper.getManager(Integer.toString(UserID));
        List<Integer> Us = Arrays.asList(Uss.split(","))
                .stream().map(f -> Integer.parseInt(f)).distinct()
                .collect(Collectors.toList());
        if (Us.contains(UserID) == false) Us.add(UserID);

        for (int i = 0; i < Us.size(); i++) {
            Integer U = Us.get(i);
            Optional<caseHighSubUser> finds = caseHighSubRep.findFirstBySubIdAndUserId(SubID, U);
            if (finds.isPresent() == false) {
                caseHighSubUser newOne = new caseHighSubUser();
                newOne.setCasesId(CasesID);
                newOne.setSubId(SubID);
                newOne.setUserId(U);
                newOne.setCreateTime(new Date());
                caseHighSubRep.save(newOne);
            }
        }

    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void replaceOne(String CasesID,String SubID, Integer OldMan, Integer NewMan,String OldManager, String NewManager) {
        List<Integer> RIDS=new ArrayList<>();
        RIDS.add(OldMan);
        List<Integer> OldManagerIDS= IntegerUtils.parseIntArray(OldManager);
        RIDS.addAll(OldManagerIDS);

        caseHighSubRep.deleteAllBySubIdAndUserIdIn(SubID,RIDS);

        List<Integer>AIDS=new ArrayList<>();
        AIDS.add(NewMan);
        List<Integer> NewManagerIDS=IntegerUtils.parseIntArray(NewManager);
        AIDS.addAll(NewManagerIDS);
        for(Integer UserID:AIDS){
            caseHighSubUser subUser=new caseHighSubUser();
            subUser.setUserId(UserID);
            subUser.setSubId(SubID);
            subUser.setCasesId(CasesID);
            subUser.setCreateTime(new Date());
            caseHighSubRep.save(subUser);
        }
    }
}
