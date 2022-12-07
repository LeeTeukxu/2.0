package com.zhide.dtsystem.services.instance.govfee;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.pantentInfo;
import com.zhide.dtsystem.models.patentGovFee;
import com.zhide.dtsystem.models.tbGovFee;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.pantentInfoRepository;
import com.zhide.dtsystem.repositorys.tbGovFeeRepository;
import com.zhide.dtsystem.services.define.IGovFeeProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @ClassName: feeProcessorFactory
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年02月03日 17:13
 **/
@Component
public class feeProcessorFactory {
    @Autowired
    yearFeeProcessor year;
    @Autowired
    applyFeeProcessor apply;
    @Autowired
    otherFeeProcessor other;
    @Autowired
    pantentInfoRepository pRep;
    @Autowired
    tbGovFeeRepository govFeeRep;
    @Autowired
    StringRedisTemplate redisUtils;
    Logger logger= LoggerFactory.getLogger(feeProcessorFactory.class);
    IGovFeeProcessor getInstance(patentGovFee item){
        String costName=item.getCostName();
        if(year.accept(costName)){
            return year;
        }
        else if(apply.accept(costName)){
            return apply;
        }
        else  return other;
    }
    @Transactional(rollbackFor = Exception.class)
    public int SaveAll(List<patentGovFee> items) throws Exception {
        int Nums=0;
        if(ResultCache==null) ResultCache=new HashMap<>();else ResultCache.clear();
        List<String>AppNos=items.stream().map(f->f.getAppNo()).distinct().collect(Collectors.toList());
        int XNum=govFeeRep.deleteAllByAppNoIn(AppNos);
        if(XNum>0)logger.info("删除历史官费记录:共"+Integer.toString(XNum)+"条记录!");

        year.clear();
        apply.clear();
        other.clear();
        List<patentGovFee> Rs=new ArrayList<>();
        for(int i=0;i<items.size();i++){
            patentGovFee item=items.get(i);
            IGovFeeProcessor processor=getInstance(item);
            try {
                processor.execute(item);
                Nums++;
                Rs.add(item);
            }catch(Exception ax){
                logger.info("保存:"+JSON.toJSONString(item)+"失败,失败原因:"+ax.getMessage());
                throw ax;
            }
        }
        year.saveAll();
        apply.saveAll();
        other.saveAll();
        if(Rs.size()>0) {
            List<tbGovFee> ts = JSON.parseArray(JSON.toJSONString(Rs), tbGovFee.class);
            List<tbGovFee> Ks= govFeeRep.saveAll(ts);
            logger.info("插入新的官费记录:共"+Integer.toString(Ks.size())+"条记录!");
        }
        return Nums;
    }
    Map<String,Boolean>  ResultCache=new HashMap<>();
    public boolean isMyShenqingh(String shenqingh){
        if(ResultCache.containsKey(shenqingh)) return ResultCache.get(shenqingh);
        else{
            LoginUserInfo Info= CompanyContext.get();
            String CompanyKey = "AllPantentInfo::" + Info.getCompanyId();
            Boolean OK=redisUtils.opsForHash().hasKey(CompanyKey,shenqingh);
            ResultCache.put(shenqingh,OK);
            return OK;
        }
    }
}
