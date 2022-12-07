package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.mapper.CasesYwFreeMapper;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.cases;
import com.zhide.dtsystem.models.casesYwAccept;
import com.zhide.dtsystem.models.casesYwItems;
import com.zhide.dtsystem.repositorys.casesRepository;
import com.zhide.dtsystem.repositorys.casesYwAcceptRepository;
import com.zhide.dtsystem.repositorys.casesYwItemsRepository;
import com.zhide.dtsystem.services.define.IJieDanDetailService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
public class JieDanDetailServiceImpl implements IJieDanDetailService {

    @Autowired
    casesYwAcceptRepository casesYwRep;
    @Autowired
    casesYwItemsRepository casesYwItemRep;
    @Autowired
    casesRepository casesRep;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean SaveAll(List<casesYwAccept> datas) throws Exception {
        String CasesID = "";
        List<casesYwAccept> items = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            casesYwAccept item = datas.get(i);
            Integer Id = item.getId();
            if (Id == null) Id = 0;
            if (Id == 0) {
                CasesID = item.getCasesId();
                String YID = item.getYid();
                Optional<casesYwItems> findOne = casesYwItemRep.findFirstByCasesIdAndYName(CasesID, YID);
                if (findOne.isPresent()) {
                    Integer totalNum = findOne.get().getNum();
                    List<casesYwAccept> finds = casesYwRep.findAllByCasesIdAndYid(CasesID, YID);
                    Integer hasNum = finds.stream().mapToInt(f -> f.getNum()).sum();
                    Integer freeNum = totalNum - hasNum;
                    if (freeNum < 0) {
                        throw new Exception("认领业务数量已超过了业务交单数量,请刷新后重新认领!");
                    }
                }
                item.setCreateTime(new Date());
                casesYwRep.save(item);
            } else {
                Optional<casesYwAccept> findOnes = casesYwRep.findById(Id);
                if (findOnes.isPresent()) {
                    casesYwAccept accept = findOnes.get();
                    accept.setProgress(item.getProgress());
                    accept.setFinishTime(item.getFinishTime());
                    casesYwRep.save(accept);
                }
            }
        }
        changeStates(CasesID);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean RemoveAll(Integer ID) throws Exception {
        Optional<casesYwAccept> findOnes = casesYwRep.findById(ID);
        if (findOnes.isPresent()) {
            casesYwAccept one = findOnes.get();
            casesYwRep.delete(one);
            String casesId = one.getCasesId();
            changeStates(casesId);
        }

        return true;
    }

    @Autowired
    SysLoginUserMapper loginUserMapper;

    private void changeStates(String casesId) {
        Optional<cases> findCases = casesRep.findFirstByCasesid(casesId);
        if (findCases.isPresent()) {
            cases find = findCases.get();
            if (HasAcceptFinish(casesId)) {
                find.setState(6);
            } else find.setState(4);
            List<casesYwAccept> items = casesYwRep.findAllByCasesId(casesId);
            if (items.size() == 0) find.setState(4);
            List<Integer> IDS = items.stream().mapToInt(f -> f.getTechMan()).boxed().distinct().collect(toList());
            find.setTechMan(StringUtils.join(IDS, ','));

            List<String> techManTexts = new ArrayList<>();
            List<String> techManTimes = new ArrayList<>();
            Map<String, String> names = new HashMap<>();
            List<String> TechManagers = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                casesYwAccept item = items.get(i);
                Integer num = item.getNum();
                String ywName = item.getYName();
                Integer techMan = item.getTechMan();
                String userName = loginUserMapper.getLoginUserNameById(techMan);
                Integer DepID = loginUserMapper.getLoginUserDepIDbyId(techMan);
                techManTexts.add(String.format("%s领取%s%d件", userName, ywName, num));
                techManTimes.add(String.format("%s领取的%s完成时间:%tF", userName, ywName, item.getFinishTime()));
                if (names.containsKey(techMan) == false) {
                    names.put(Integer.toString(DepID) + ":" + Integer.toString(techMan), userName);
                }
                String TechManager = loginUserMapper.getManager(Integer.toString(techMan));
                String[] IDSS = TechManager.split(",");
                for (int X = 0; X < IDSS.length; X++) {
                    String ID = IDSS[X];
                    if (TechManagers.contains(ID) == false) TechManagers.add(ID);
                }
            }
            find.setTechNums(StringUtils.join(techManTexts, ';'));
            find.setTechTime(StringUtils.join(techManTimes, ';'));
            find.setTechManager(StringUtils.join(TechManagers, ','));
            find.setTechManNames(JSON.toJSONString(names));
            casesRep.save(find);
        }
    }

    @Autowired
    CasesYwFreeMapper freeMapper;

    /**
     * 这一笔交单是不是都认领完成了。
     *
     * @param CasesID
     * @return
     */
    private boolean HasAcceptFinish(String CasesID) {
        List<Map<String, Object>> findAll = freeMapper.getAllFreeNums(CasesID);
        return findAll.size() == 0;
    }
}
