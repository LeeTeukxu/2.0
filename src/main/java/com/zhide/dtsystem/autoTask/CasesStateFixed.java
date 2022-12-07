package com.zhide.dtsystem.autoTask;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.mapper.AllUserListMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.casesMain;
import com.zhide.dtsystem.models.casesSub;
import com.zhide.dtsystem.models.tbClient;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.casesMainRepository;
import com.zhide.dtsystem.repositorys.casesSubRepository;
import com.zhide.dtsystem.repositorys.tbClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * @ClassName: CasesStateFixed
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年12月05日 16:02
 **/
@Component
public class CasesStateFixed {
    @Autowired
    casesMainRepository caseMainRep;
    @Autowired
    casesSubRepository casesSubRep;
    @Autowired
    AllUserListMapper userMapper;
    @Autowired
    tbClientRepository clientRep;
    Logger logger = LoggerFactory.getLogger(CasesStateFixed.class);

    @Scheduled(cron = "0 0/30 * * * ?")
    public void process() {
        LocalTime now = LocalTime.now();
        List<String> companyIDS = userMapper.getCompanyList();
        for (int i = 0; i < companyIDS.size(); i++) {
            String companyId = companyIDS.get(i);
            LoginUserInfo info = new LoginUserInfo();
            info.setUserName("aa");
            info.setUserId("aa");
            info.setCompanyId(companyId);
            CompanyContext.set(info);
            casesMain target = null;
            try {

                List<casesMain> mains = caseMainRep.findAllByStateEquals(4);
                for (int n = 0; n < mains.size(); n++) {
                    casesMain main = mains.get(n);
                    String CasesID = main.getCasesId();

                    List<casesSub> subs = casesSubRep.findAllByCasesId(CasesID);
                    int CompleteNum = subs.stream().filter(f -> f.getProcessState() == 62).collect(toList()).size();
                    if (CompleteNum == subs.size()) {
                        casesSub first = subs.get(0);
                        main.setCompleteMan(first.getSettleMan());
                        main.setCompleteTime(first.getSettleTime());
                        main.setState(8);
                        target = main;
                        caseMainRep.save(main);
                        logger.info("已成功修复:" + main.getDocSn() + "的状态!");
                    }
                }

                List<casesMain> CompleteOnes=caseMainRep.findAllByStateEquals(8);
                for(int n=0;n<CompleteOnes.size();n++){
                    casesMain main=CompleteOnes.get(n);
                    int ClientID=main.getClientId();
                    Optional<tbClient> findOnes=clientRep.findById(ClientID);
                    if(findOnes.isPresent()){
                        tbClient One=findOnes.get();
                        One.setCootype(1);
                        clientRep.save(One);
                    } else {
                        logger.info(ClientID+"没有找到客户记录!");
                    }
                }
            } catch (Exception ax) {
                if (target != null) logger.info("修复出错:" + JSON.toJSONString(target));
                ax.printStackTrace();
            } finally {
                CompanyContext.set(null);
            }
        }
    }
}
