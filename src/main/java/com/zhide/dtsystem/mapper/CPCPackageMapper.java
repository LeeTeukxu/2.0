package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.models.ComboboxItem;
import com.zhide.dtsystem.models.TreeNode;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: CPCPackageMapper
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年08月12日 10:51
 **/
public interface CPCPackageMapper {
    List<Map<String,Object>> getData(Map<String,Object> arg);
    List<Map<String,Object>> getCPCFiles(String MainID);
    List<Map<String,Object>> getInventors(String MainID);
    List<Map<String,Object>> getApplyMan(String MainID);
    List<Map<String,Object>> getAgents(String MainID);
    @Select(value = "Select Name from tbCountry WHere Code=#{Code}")
    String getCountryByCode(String Code);
    @Select(value="Select Name from tbXZQH Where FID=#{FID}")
    String getXZQHNameByID(String FID);
    @Select(value="Select Code as ID,Name as Text from tbCountry Order by ID")
    List<ComboboxItem> getCountry();
    @Select(value="Select FID,PID,Name from tbXZQH  Order by PID")
    List<TreeNode> getXZQ();
    List<Map<String,Object>> getCompanyInfo();
    List<Map<String,Object>> getAgentInfo();
}
