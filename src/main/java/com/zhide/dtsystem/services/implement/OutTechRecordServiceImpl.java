package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.outTechRecord;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.outTechRecordRepository;
import com.zhide.dtsystem.services.define.IOutTechRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @ClassName: OutTechRecordServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年10月26日 20:43
 **/
@Service
public class OutTechRecordServiceImpl implements IOutTechRecordService {
    @Autowired
    outTechRecordRepository outTechRep;

    @Override
    public void AddOne(String SubID, String Text) {
        LoginUserInfo Info = CompanyContext.get();
        outTechRecord record = new outTechRecord();
        record.setSubId(SubID);
        record.setAction(Text);
        record.setCreateMan(Info.getUserIdValue());
        record.setCreateTime(new Date());
        outTechRep.save(record);
    }
}
