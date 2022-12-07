package com.zhide.dtsystem.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface OtherOfficeFeeListMapper {
    List<Map<String, Object>> getData(Map<String, Object> arguments);

    @Select(value = "SELECT COUNT(*) FROM View_OtherOfficeFee WHERE SHENQINGH=#{SHENQINGH} AND id=#{id}")
    public int getSQHAndID(String SHENQINGH, Integer id);

    @Select(value = "exec [sp_getOtherOfficeFeeTotal] ${DepID},${UserID},'${RoleName}'")
    public List<Map<String, Object>> getOtherOfficalTotal(int DepID, int UserID, String RoleName);

    List<Map<String, Object>> getLinkMan(Map<String, Object> params);

    List<Map<String, Object>> getLinkManInfo(Map<String, Object> params);
}
