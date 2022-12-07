package com.zhide.dtsystem.autoTask;

import com.zhide.dtsystem.mapper.AllUserListMapper;
import com.zhide.dtsystem.mapper.TruncateTableMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.clearNotExistRecord;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.clearNotExistRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: ClearNotExistRecordTask
 * @Author: 肖新民
 * @*TODO:半夜删一次notexistTzS和notExistCPC.重新找一下是不是有文件了。
 * @CreateTime: 2020年12月10日 16:01
 **/
@Component
public class ClearNotExistRecordTask {
    @Autowired
    TruncateTableMapper trunMapper;
    @Autowired
    AllUserListMapper userMapper;
    @Autowired
    clearNotExistRecordRepository clearRep;
    Logger logger = LoggerFactory.getLogger(ClearNotExistRecordTask.class);

    @Scheduled(cron = "0 0 0 * * ?")
    public void process() {
        LocalTime now = LocalTime.now();
        if (now.getHour() > 6) return;
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
        String Mark = simple.format(new Date());
        List<String> companyIDS = userMapper.getCompanyList();
        for (int i = 0; i < companyIDS.size(); i++) {
            String companyId = companyIDS.get(i);
            LoginUserInfo info = new LoginUserInfo();
            info.setUserName("aa");
            info.setUserId("aa");
            info.setCompanyId(companyId);
            CompanyContext.set(info);

            Date Begin = new Date();
            trunMapper.clearNotExistCPC();
            trunMapper.clearNotExistT();
            Date End = new Date();

            clearNotExistRecord newRecord = new clearNotExistRecord();
            newRecord.setBeginTime(Begin);
            newRecord.setEndTime(End);
            newRecord.setMark(Mark);
            clearRep.save(newRecord);
            logger.info("已清理" + companyId + "的不存在数据记录，用时:" + (End.getTime() - Begin.getTime()));
            CompanyContext.set(null);
        }
    }
}
