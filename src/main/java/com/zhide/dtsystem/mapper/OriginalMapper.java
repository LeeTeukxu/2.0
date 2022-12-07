package com.zhide.dtsystem.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
public interface OriginalMapper {

    List<Map<String, Object>> getData(Map<String, Object> arguments);

    @Select(value = "SELECT TOP 1 * FROM tbOriginalExpress WHERE Coding =#{barcode}")
    List<Map<String, Object>> Search1(String barcode);

    @Select(value = "select top 1 shenqingh AS SHENQINGH from t_1 where shenqingh=#{barcode}")
    List<Map<String, Object>> Search2(String barcode);

    @Select(value = "SELECT TOP 1 shenqingh FROM Original WHERE dnum=#{dnum} AND otype=#{otype}")
    List<Map<String, Object>> Search3(String dnum, int otype);

    @Select(value = "SELECT TOP 1 shenqingh FROM t_4 WHERE shoujuh=#{barcode}")
    List<Map<String, Object>> Search4(String barcode);

    @Select(value = "SELECT TOP 1 shenqingh FROM Original WHERE dnum=#{barcode} AND otype=3")
    List<Map<String, Object>> Search5(String barcode);

    @Select(value = "SELECT token FROM cpquery_sipo_power WHERE id=1")
    List<Map<String, Object>> Search6();

    @Select(value = "SELECT Coding FROM tbOriginalExpress")
    List<String> getOriginalExpressCoding();

    @Select(value = "SELECT COUNT(OriginalStates) FROM tbOriginalExpress WHERE PackageNum=#{PackageNum} AND " +
            "OriginalStates in (3,4)")
    String getPackageNumCount(String PackageNum);

    @Select(value = "SELECT COUNT(*) AS Count,otypeText FROM View_Original WHERE PackageNum=#{PackageNum} AND " +
            "ostateText in (3,4) GROUP BY otypeText")
    List<Map<String, Object>> getPackageContent(String PackageNum);

    @Select(value = "exec [sp_getOrigTotal] ${DepID},${UserID},'${RoleName}'")
    public List<Map<String, Object>> getOrigTotal(int DepID, int UserID, String RoleName);
}
