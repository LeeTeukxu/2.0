package com.zhide.dtsystem.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: SuggestMapper
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2022年01月28日 16:15
 **/
@Mapper
public interface ExpenseMapper {
    List<Map<String,Object>> getData(Map<String,Object> params);
    @Select(value = "exec [sp_getExpenseMainTotal] ${DepID},${UserID},'${RoleName}'")
    List<Map<String,Object>> getStateNumber(int DepID,int UserID,String RoleName);
}
