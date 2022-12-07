package com.zhide.dtsystem.autoTask;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.mapper.AllUserListMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.patentGovFee;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.services.instance.govfee.feeProcessorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ClassName: ParseGoFeeTask
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年04月07日 14:52
 **/
@Component
public class ParseGoFeeTask {
    @Autowired
    StringRedisTemplate redisUtils;

    @Autowired
    AllUserListMapper userMapper;
    @Autowired
    feeProcessorFactory feeFactory;
    Logger logger = LoggerFactory.getLogger(ParseGoFeeTask.class);

    @Scheduled(cron = "0 0/1 * * * ?")
    public void Process() {
        List<String> companyIDS = userMapper.getCompanyList();
        SimpleDateFormat simple=new SimpleDateFormat("yyyyMMdd");
        for (String CompanyID : companyIDS) {
            LoginUserInfo info = new LoginUserInfo();
            info.setUserName("aa");
            info.setUserId("aa");
            info.setCompanyId(CompanyID);
            CompanyContext.set(info);

            String CompanyKey = "AcceptGovFee::" + CompanyID;
            List<String> PS = redisUtils.opsForList().range(CompanyKey, 0, 30);
            if(PS==null)PS=new ArrayList<>();
            if(PS.size()==0) continue;

            String BackupKey="AcceptGovFee::Backup::"+CompanyID+"::"+simple.format(new Date());
            if(redisUtils.hasKey(BackupKey)){
                redisUtils.expire(BackupKey,1, TimeUnit.DAYS);
            }
            List<patentGovFee> Items =new ArrayList<>();
            for(String P:PS){
                List<patentGovFee> fees=JSON.parseArray(P,patentGovFee.class);
                Items.addAll(fees);
            }
            try {
                Long T1= System.currentTimeMillis();
                logger.info("开始处理"+CompanyID+"的官费数据,原始数据有:"+Integer.toString(Items.size())+"条");
                int Nums= feeFactory.SaveAll(Items);
                Long T2=System.currentTimeMillis();
                logger.info("一共处理了:"+Integer.toString(Nums)+"条" + CompanyID + "的官费数据,一共:"+Long.toString(T2-T1)+"毫秒。");
                List<String> Values=new ArrayList<>();
                for(int i=0;i<PS.size();i++){
                    redisUtils.opsForList().leftPop(CompanyKey);
                    patentGovFee G=Items.get(i);
                    G.setProcessTime(new Date());
                    Values.add(JSON.toJSONString(G));
                }
                redisUtils.opsForList().leftPushAll(BackupKey,Values);
            } catch (Exception ax) {
                logger.info("处理" + CompanyID + "的官费数据时发生了:" +ax.getMessage()+ "的错误!");
                ax.printStackTrace();
            }
            finally {
                CompanyContext.set(null);
                PS=null;
            }
        }
    }
}
