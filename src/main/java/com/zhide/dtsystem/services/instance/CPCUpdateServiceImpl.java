package com.zhide.dtsystem.services.instance;

import com.zhide.dtsystem.common.uploadFileResult;
import com.zhide.dtsystem.models.clientUpdateRecord;
import com.zhide.dtsystem.repositorys.clientUpdateRecordRepository;
import com.zhide.dtsystem.services.define.ICPCAttachmentService;
import com.zhide.dtsystem.services.define.ICPCUpdateService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @ClassName: CPCUpdateServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年02月26日 15:47
 **/
@Service
public class CPCUpdateServiceImpl  implements ICPCUpdateService {

    @Autowired
    ICPCAttachmentService cpcAttachmentService;
    @Autowired
    clientUpdateRecordRepository clientUpdateRecordRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void SaveAll(String SHENQINGBH, String fullName, String unZipDir, uploadFileResult res) {
        cpcAttachmentService.UpdateByPackage(fullName, res.getFullPath(), SHENQINGBH, unZipDir);
        try {
            FileUtils.forceDeleteOnExit(new File(fullName));
        } catch (IOException e) {
            e.printStackTrace();
        }


        clientUpdateRecord record = new clientUpdateRecord();
        record.setCreateTime(new Date());
        record.setDealTime(new Date());
        record.setDeal(true);
        record.setTimes(1);
        record.setPath(res.getFullPath());
        clientUpdateRecordRepository.save(record);
    }
}
