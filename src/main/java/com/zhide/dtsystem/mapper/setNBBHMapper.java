package com.zhide.dtsystem.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface setNBBHMapper {
    @Select(value = "SELECT vt.CreateMan as XS ,vt.TechMan as JS,(SELECT Name FROM tbClient WHERE ClientID=vt" +
            ".ClientID) as KH FROM View_CommitFileAudit AS vt where ID=#{ID}")
    List<Map<String, Object>> getZlxz(int ID);
}
