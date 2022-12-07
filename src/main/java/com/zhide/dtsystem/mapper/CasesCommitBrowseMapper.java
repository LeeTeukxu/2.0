package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.models.caseCounterInfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface CasesCommitBrowseMapper {
    List<Map<String, Object>> getData(Map<String, Object> params);

    @Select(value = "Select CasesID,State,CreateMan,CreateManager,LCMan as AuditMan,LCManager as AuditManager," +
            "TechMan," +
            "TechManager from View_CommitFileAudit")
    List<caseCounterInfo> getAll();
}
