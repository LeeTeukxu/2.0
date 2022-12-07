package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.models.tbArrivalRegistration;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface tbArrivalMapper {
    List<Map<String, Object>> getData(Map<String, Object> arguments);
    List<Map<String, Object>> getWorkData(Map<String, Object> arguments);
    Optional<tbArrivalRegistration> getRenLinAndFuHe(Integer ArrivalRegistrationID);

    @Select(value = "exec [sp_getArrivalTotal] ${DepID},${UserID},'${RoleName}'")
    List<Map<String, Object>> getArrivalTotal(int DepID, int UserID, String RoleName);

    @Select(value = "SELECT b.Name FROM tbArrivalRegistration AS a LEFT JOIN tbClient AS b ON a.CustomerID=b.ClientID" +
            " WHERE a.DocumentNumber=#{DocumentNumber}")
    String getClientNameByDocumentNumber(String DocumentNumber);

    @Select(value = "SELECT b.DocumentNumber,b.CustomerName AS KHName,b.AgencyFee,b.OfficalFee FROM tbCustomerRefund " +
            "AS a LEFT JOIN View_ArrivalRegistration_Window AS b ON a.DocumentNumber=b.DocumentNumber WHERE a" +
            ".CustomerRefundRequestID=#{CustomerRefundRequestID}")
    Optional<tbArrivalRegistration> getArrivalByCustomerRefundRequestID(Integer CustomerRefundRequestID);
}
