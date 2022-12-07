package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.models.tzsPeriodConfig;
import com.zhide.dtsystem.repositorys.tzsPeriodConfigRepository;
import com.zhide.dtsystem.services.define.ITZSPeriodConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TZSPeriodConfigServiceImpl implements ITZSPeriodConfigService {
    @Autowired
    tzsPeriodConfigRepository tzsRep;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean Save(List<tzsPeriodConfig> Datas) {
        for (int i = 0; i < Datas.size(); i++) {
            tzsPeriodConfig config = Datas.get(i);
            tzsRep.save(config);
        }
        return true;
    }
}
