package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.mapper.NicheNoticeConfigMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.NiCheNoticeConfig;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.nicheNoticeConfigRepository;
import com.zhide.dtsystem.services.define.INicheNoticeConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NicheNoticeConfigServiceImpl implements INicheNoticeConfigService {

    @Autowired
    NicheNoticeConfigMapper noticeConfigMapper;
    @Autowired
    nicheNoticeConfigRepository nicheNoticeConfigRep;

    @Override
    public List<Map<String, Object>> getTZSMC() {
        LoginUserInfo loginUserInfo = CompanyContext.get();
        if (loginUserInfo == null) throw new RuntimeException("登录信息失效，请重新登录！");
        return noticeConfigMapper.getTZSMC();
    }

    @Override
    public boolean Save(List<NiCheNoticeConfig> Datas) {
        for (int i = 0; i < Datas.size(); i++) {
            NiCheNoticeConfig config = Datas.get(i);
            nicheNoticeConfigRep.save(config);
        }
        return true;
    }
}
