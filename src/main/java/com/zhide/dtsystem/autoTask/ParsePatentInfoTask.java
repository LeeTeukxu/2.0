package com.zhide.dtsystem.autoTask;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.mapper.AllUserListMapper;
import com.zhide.dtsystem.models.GovPatentInfo;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.pantentInfo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.pantentInfoRepository;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ClassName: ParsePatentInfoTask
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年04月16日 16:09
 **/
@Component
public class ParsePatentInfoTask {
    @Autowired
    StringRedisTemplate redisUtils;

    @Autowired
    AllUserListMapper userMapper;

    @Autowired
    pantentInfoRepository ptInfoRep;
    Logger logger = LoggerFactory.getLogger(ParsePatentInfoTask.class);

    @Scheduled(cron = "0/30 * * * * ?")
    public void Process() {
        SimpleDateFormat simple=new SimpleDateFormat("yyyyMMdd");
        List<String> companyIDS = userMapper.getCompanyList();
        for (String CompanyID : companyIDS) {
            LoginUserInfo info = new LoginUserInfo();
            info.setUserName("aa");
            info.setUserId("aa");
            info.setCompanyId(CompanyID);
            CompanyContext.set(info);

            String CompanyKey = "AcceptPatentInfo::" + CompanyID;
            List<String> PS = redisUtils.opsForList().range(CompanyKey, 0, 20);

            if (PS == null) PS = new ArrayList<>();
            if (PS.size() == 0) continue;

            List<pantentInfo> Datas = new ArrayList<>();
            Long T1=System.currentTimeMillis();
            String BackupKey="AcceptPatentInfo::Backup::"+CompanyID+"::"+simple.format(new Date());
            if(redisUtils.hasKey(BackupKey)){
                redisUtils.expire(BackupKey,1, TimeUnit.DAYS);
            }
            List<String>BackupKeys=new ArrayList<>();
            List<GovPatentInfo> Ps=new ArrayList<>();
            for(String P:PS){
                GovPatentInfo P1=JSON.parseObject(P,GovPatentInfo.class);
                Ps.add(P1);
            }
            List<String>XCode=Ps.stream().map(f->f.getAppNo()).collect(Collectors.toList());
            List<pantentInfo> FindAlls= ptInfoRep.findAllByShenqinghIn(XCode);
            for (GovPatentInfo PInfo:Ps) {
                logger.info("开始处理:"+JSON.toJSONString(PInfo));
                String AppNo = PInfo.getAppNo();
                Optional<pantentInfo> findOnes =
                        FindAlls.stream().filter(f->f.getShenqingh().equals(PInfo.getAppNo())).findFirst();
                if (findOnes.isPresent()) {
                    pantentInfo One = findOnes.get();
                    if (StringUtils.isEmpty(PInfo.getTitle()) == false) One.setFamingmc(PInfo.getTitle());
                    else {
                        logger.info(AppNo+"的专利名称为空!");
                    }
                    if (StringUtils.isEmpty(PInfo.getCpqueryStatus()) == false) One.setAnjianywzt(PInfo.getCpqueryStatus());
                    else {
                        logger.info(AppNo+"的专利法律状态为空!");
                    }
                    if (StringUtils.isEmpty(PInfo.getInventors()) == false) One.setFamingrxm(PInfo.getInventors());
                    else {
                        logger.info(AppNo+"的专利发明人为空!");
                    }
                    if (StringUtils.isEmpty(PInfo.getApplicant()) == false) One.setShenqingrxm(PInfo.getApplicant());
                    else {
                        logger.info(AppNo+"的专利申请人为空!");
                    }
                    if(PInfo.getAppType()!=null){
                        if(One.getShenqinglx()==null){
                            One.setShenqinglx(PInfo.getAppType()-1);
                        }
                    }
                    One.setLastupdatetime(new Date());
                    if (PInfo.getAppDate() != null) One.setShenqingr(PInfo.getAppDate());
                    if(PInfo.getAgency()!=null) One.setDailijgmc(PInfo.getAgency());
                    if(PInfo.getAgent()!=null)One.setDiyidlrxm(PInfo.getAgent());
                    Datas.add(One);
                    PInfo.setProcessTime(new Date());
                } else {
                    if(redisUtils.opsForHash().hasKey("AllCompanyList",CompanyID)==true){
                        String Name=redisUtils.opsForHash().get("AllCompanyList",CompanyID).toString();
                        if(StringUtils.isEmpty(PInfo.getAgency())==false){
                            if(PInfo.getAgency().equals(Name)){
                                pantentInfo pNew=new pantentInfo();
                                pNew.setShenqingh(PInfo.getAppNo());
                                if(PInfo.getAppType()!=null) {
                                    pNew.setShenqinglx(PInfo.getAppType() - 1);
                                }
                                pNew.setShenqingbh(UUID.randomUUID().toString());
                                pNew.setFamingrxm(PInfo.getInventors());
                                pNew.setFamingmc(PInfo.getTitle());
                                pNew.setAnjianywzt(PInfo.getCpqueryStatus());
                                pNew.setDailijgmc(PInfo.getAgency());
                                pNew.setDiyidlrxm(PInfo.getAgent());
                                pNew.setShenqingr(PInfo.getAppDate());
                                Datas.add(pNew);
                            }
                        }
                    } else logger.info(PInfo.getAppNo()+"在系统中不存在，放弃插入操作。");
                }
                //发送到备份数组中。用于查询。
                BackupKeys.add(JSON.toJSONString(PInfo));
            }
            if(Datas.size()>0){
                //logger.info("更新一批专利资料:"+JSON.toJSONString(Datas));
                ptInfoRep.saveAll(Datas);
                for(int i=0;i<PS.size();i++){
                    redisUtils.opsForList().leftPop(CompanyKey);
                }
                redisUtils.opsForList().leftPushAll(BackupKey,BackupKeys);
                Long T2=System.currentTimeMillis();
                logger.info("处理了"+CompanyID+"的"+Long.toString(Datas.size())+"条专利数据，用时:"+Long.toString(T2-T1)+"毫秒。");
                CompanyContext.set(null);
            }else {
                logger.info("无法处理"+CompanyID+"的"+Long.toString(Datas.size())+"条专利数据，只能全部删除!");
                for(int i=0;i<PS.size();i++){
                    redisUtils.opsForList().leftPop(CompanyKey);
                }
            }
        }
    }
}
