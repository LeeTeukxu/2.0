package com.zhide.dtsystem.services.instance;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.casesMemo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.casesMemoRepository;
import com.zhide.dtsystem.repositorys.tbImageMemoRepository;
import com.zhide.dtsystem.services.define.ICasesMemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CasesMemoServiceImpl implements ICasesMemoService {
    @Autowired
    casesMemoRepository casesMemoRep;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean SaveAll(List<casesMemo> items) {
        LoginUserInfo loginInfo = CompanyContext.get();
        String CID=items.get(0).getCasesid();
        List<String> CSID= Arrays.stream(CID.split(",")).collect(Collectors.toList());
        List<casesMemo> allItem=new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            casesMemo vv = items.get(i);
            String K= JSON.toJSONString(vv);
            for(String CasesID:CSID) {
                casesMemo item=JSON.parseObject(K,casesMemo.class);
                item.setCasesid(CasesID);
                if (item.getCreateMan() == null) {
                    item.setSubId(UUID.randomUUID().toString());
                    item.setCreateMan(loginInfo.getUserIdValue());
                    item.setCreateTime(new Date());
                } else {
                    item.setUpdateMan(loginInfo.getUserIdValue());
                    item.setUpdateTime(new Date());
                }
                allItem.add(item);
            }
        }
        casesMemoRep.saveAll(allItem);
        return true;
    }
    @Autowired
    tbImageMemoRepository imageRep;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean Remove(String CasesID, Integer ID) {
        Optional<casesMemo> findOnes = casesMemoRep.findFirstByCasesidAndId(CasesID, ID);
        if (findOnes.isPresent()) {
            casesMemo one=findOnes.get();
            String subID=one.getSubId();
            imageRep.deleteAllByPid(subID);
            casesMemoRep.delete(findOnes.get());
            return true;
        } else return false;
    }

}
