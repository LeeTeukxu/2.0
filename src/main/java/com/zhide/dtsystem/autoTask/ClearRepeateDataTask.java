package com.zhide.dtsystem.autoTask;

import com.zhide.dtsystem.common.DateTimeUtils;
import com.zhide.dtsystem.mapper.AllUserListMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.allFeePayForResult;
import com.zhide.dtsystem.models.yearFeeList;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.allFeePayForResultRepository;
import com.zhide.dtsystem.repositorys.yearFeeListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: ClearRepeateDataTask
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年12月23日 16:37
 **/
public class ClearRepeateDataTask {
    @Autowired
    AllUserListMapper userMapper;
    @Autowired
    yearFeeListRepository yearRep;
    @Autowired
    allFeePayForResultRepository  allRep;

    Logger logger= LoggerFactory.getLogger(ClearRepeateDataTask.class);
    public void Process(){
        List<String> companyIDS =new ArrayList<>();//userMapper.getCompanyList();
        for (int i = 0; i < companyIDS.size(); i++) {
            String companyId = companyIDS.get(i);
            LoginUserInfo info = new LoginUserInfo();
            info.setUserName("aa");
            info.setUserId("aa");
            info.setCompanyId(companyId);
            CompanyContext.set(info);
            Date begin= DateTimeUtils.parse("2022-01-01");
            Date end=DateTimeUtils.parse("2022-12-31");
            try {
                int Num=0;
                List<yearFeeList> yearFees = yearRep.findAllByJiaofeirBetween(begin, end);//缴费日在2022年的所有记录。
                for (yearFeeList fee : yearFees) {
                    String Shenqingh = fee.getShenqingh();
                    String feeName = fee.getFeename();
                    //根据缴费日是2022年，专利编号、费用名称进行查询 。
                    List<yearFeeList> items = yearRep.findAllByShenqinghAndFeenameAndJiaofeirBetween(Shenqingh, feeName, begin, end);
                    if (items.size() > 1) {//只有大于1条
                        List<Integer> IDS = new ArrayList<>();
                        Date maxDate = items.stream().map(f -> f.getJiaofeir()).max(Date::compareTo).get();
                        int OKNum=0;
                        for (yearFeeList item : items) {
                            Integer FeeID = item.getId();
                            List<allFeePayForResult> alls = allRep.findAllByFeeItemIdLikeAndPayStateEquals(
                                    "%,"+Integer.toString(FeeID)+",%", 2);
                            if (alls.size() == 0) {
                                IDS.add(FeeID);
                            } else {
                                item.setJiaofeir(maxDate);
                                yearRep.save(item);
                                OKNum++;
                            }
                        }
                        //如果有审核完成的业务。则其它生成的ID全部删除。
                        if(OKNum>0){
                            for (int n = 0; n < IDS.size(); n++) {
                                int ID = IDS.get(n);
                                yearRep.deleteById(ID);
                                Num++;
                            }
                        }
                        else {
                            if (IDS.size() == 1) {
                                yearRep.deleteById(IDS.get(0));
                            } else {
                                yearFeeList maxOne=
                                        items.stream().filter(f->f.getJiaofeir().compareTo(maxDate)==0).findFirst().get();
                                IDS.remove(maxOne.getId());
                                for (int n = 0; n < IDS.size(); n++) {
                                    int ID = IDS.get(n);
                                    yearRep.deleteById(ID);
                                    Num++;
                                }
                            }
                        }
                    }
                }
                logger.info(companyId+"一共删除了:"+Integer.toString(Num));
            }catch(Exception ax){
                logger.info(ax.getMessage());
            }
        }
    }
}
