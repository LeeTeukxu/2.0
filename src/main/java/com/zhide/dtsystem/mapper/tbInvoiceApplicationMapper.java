package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.models.tbInvoiceParameter;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface tbInvoiceApplicationMapper {
    List<Map<String, Object>> getData(Map<String, Object> arguments);

    @Select("SELECT TOP 1 * FROM tbInvoiceParameter ORDER BY FID DESC")
    tbInvoiceParameter getDictDataMaxFID();

    @Select(value = "exec [sp_getInvoiceTotal] ${DepID},${UserID},'${RoleName}'")
    List<Map<String, Object>> getInvoiceTotal(int DepID, int UserID, String RoleName);
}
