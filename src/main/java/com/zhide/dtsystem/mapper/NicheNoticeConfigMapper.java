package com.zhide.dtsystem.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface NicheNoticeConfigMapper {
    @Select(value = "SELECT DISTINCT TONGZHISMC AS TypeName from TZS where TONGZHISMC NOT IN (SELECT TypeName FROM " +
            "TZSPeriodConfig) AND TONGZHISMC<>''")
    List<Map<String, Object>> getTZSMC();

    @Select(value = "SELECT TypeName FROM NiCheNoticeConfig")
    List<String> getNiCheNoticeConfigTypeName();
}
