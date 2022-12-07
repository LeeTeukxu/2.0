package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.models.NBBHInfo;
import com.zhide.dtsystem.models.UInfo;
import com.zhide.dtsystem.models.pantentInfo;
import com.zhide.dtsystem.models.patentInfoPermission;
import com.zhide.dtsystem.repositorys.pantentInfoRepository;
import com.zhide.dtsystem.repositorys.patentInfoPermissionRepository;
import com.zhide.dtsystem.services.NBBHCode;
import com.zhide.dtsystem.services.define.INBBHBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @ClassName: NBBHBatchServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年03月17日 15:37
 **/
@Service
public class NBBHBatchServiceImpl implements INBBHBatchService {

    @Autowired
    pantentInfoRepository pRep;

    @Autowired
    NBBHCode nbCode;

    @Autowired
    patentInfoPermissionRepository ppRep;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> SaveAll(String OldText, String NowText) {
        Map<String,Object> result=new HashMap<>();
        List<String> Errors=new ArrayList<>();
        Integer X=0;
        Integer RNum=0;
        List<pantentInfo> ps = pRep.getAllByNeibubhLike("%"+OldText+"%");
        for (int i = 0; i < ps.size(); i++) {
            pantentInfo Info = ps.get(i);
            String NB = Info.getNeibubh();
            if (StringUtils.isEmpty(NB) == false) {
                NB = NB.replace(OldText, NowText);
                NBBHInfo RInfo = nbCode.Parse(NB);
                if (RInfo.isDecodeAll()) {
                    ppRep.deleteAllByShenqingh(Info.getShenqingh());
                    List<patentInfoPermission> news = new ArrayList<>();
                    RInfo.foreach((type, items) -> {
                        for (int n = 0; n < items.size(); n++) {
                            UInfo uInfo = items.get(n);
                            patentInfoPermission newP = new patentInfoPermission();
                            newP.setShenqingh(Info.getShenqingh());
                            newP.setUsertype(type);
                            newP.setUserid(uInfo.getID());
                            news.add(newP);
                        }
                    });
                    ppRep.saveAll(news);
                    Info.setNeibubh(NB);
                    Info.setNbFixed(true);
                    Info.setNbFixedTime(new Date());
                    pRep.save(Info);
                    X++;
                } else {
                    Errors.add(NB);
                    Info.setNeibubh(NB);
                    pRep.save(Info);
                    RNum++;
                }
            }
        }
        if(Errors.size()>0)result.put("Errors",Errors);
        result.put("OKNum",X);
        result.put("ReplaceNum",RNum);
        return result;
    }
}
