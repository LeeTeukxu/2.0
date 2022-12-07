package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.models.tbCustomerRefund;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface tbCustomerRefundMapper {
    List<Map<String, Object>> getData(Map<String, Object> arguments);

    @Select(value = "exec [sp_getCustomerRefundTotal] ${DepID},${UserID},'${RoleName}'")
    public List<Map<String, Object>> getCustomerRefundTotal(int DepID, int UserID, String RoleName);

    @Select(value = "SELECT SUM(CAST(AgencyFeeAmount AS INT)) AS AgencyFeeAmount,SUM(CAST(OfficalFeeAmount AS INT)) " +
            "AS OfficalFeeAmount FROM View_CustomerRefund_Window WHERE DocumentNumber=#{DocumentNumber}")
    public tbCustomerRefund getTotalFeeByDocumentNumber(String DocumentNumber);

    @Select(value = "SELECT SUM(CAST(AgencyFeeAmount AS INT)) AS AgencyFeeAmount,SUM(CAST(OfficalFeeAmount AS INT)) " +
            "AS OfficalFeeAmount FROM View_CustomerRefund_Window WHERE " +
            "CustomerRefundRequestID=#{CustomerRefundRequestID}")
    public tbCustomerRefund getTotalFeeByCustomerRequestID(Integer CustomerRefundRequestID);
}
