package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.models.CostReduction;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface CostReductionMapper {
    List<Map<String, Object>> getData(Map<String, Object> arguments);

    @Delete({"Delete FROM CostReduction WHERE UUID=#{UUID}"})
    void DeleteByUUID(String UUID);

    @Select(value = "SELECT STUFF((SELECT ',' + CONVERT(VARCHAR(50), c.Name) +';'+ CONVERT(VARCHAR(200),c.Path) +';'+" +
            " CONVERT(NVARCHAR(200),a.UUID) FROM CostReduction AS a LEFT JOIN CostReductionAttachment AS b ON a" +
            ".UUID=b.UUID LEFT JOIN tbAttachment AS c ON b.AttID=c.GUID WHERE a.UUID=#{UUID} FOR XML PATH('')),1,1," +
            "'') AS Name")
    List<Map<String, Object>> getAttachmentName(String UUID);

    Optional<CostReduction> getNeiShenAndGZJFJ(Integer CostReductionID);

    @Select(value = "exec [sp_getCostReductionTotal] ${DepID},${UserID},'${RoleName}'")
    public List<Map<String, Object>> getCostReductionTotal(int DepID, int UserID, String RoleName);
}
