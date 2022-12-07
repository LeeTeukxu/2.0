package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.NiCheNoticeConfig;

import java.util.List;
import java.util.Map;

public interface INicheNoticeConfigService {
    List<Map<String, Object>> getTZSMC();

    boolean Save(List<NiCheNoticeConfig> Datas);
}
