package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.models.tbOriginalKd;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OriginalKdMapper {
    List<Map<String, Object>> getData(Map<String, Object> arguments);

    @Select(value = "select DISTINCT OriginalKdID,PackageNum,PackageStatus,PackageContent,Receiver,Address,Phone," +
            "Postcode,CourierCompany,PostalCode,DeliveryTime,Render,ExpressNotes,KHName,ContactPerson," +
            "ApplicationTime,MailAppicant from View_OriginalKd WHERE PackageNum IN (#{PackageNum})")
    Optional<tbOriginalKd> findByPackageNum(String PackageNum);

    @Select(value = "exec [sp_getOrignalKdTotal] ${DepID},${UserID},'${RoleName}'")
    public List<Map<String, Object>> getOrignalKdTotal(int DepID, int UserID, String RoleName);
}
