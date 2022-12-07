package com.zhide.dtsystem.mapper;


import com.zhide.dtsystem.models.tbClient;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ClientInfoMapper {
    @Select(value = "Select ClientID as ID, upper(replace(Name,' ','')) as Name  from tbClient")
    List<Map<String, Object>> getAllByNameAndID();

    List<Map<String, Object>> getPageData(Map<String, Object> params);

    List<Map<String, Object>> getData(Map<String, Object> params);

    List<Map<String, Object>> getDataWindow(Map<String, Object> params);

    List<Map<String, Object>> getJSR(Map<String, Object> params);

    List<tbClient> getClientTree(Map<String, Object> params);

    List<Map<String, Object>> getLoginClientReords(Map<String, Object> arguments);

    List<Map<String, Object>> getDLF(Map<String, Object> arguments);

    List<Map<String, Object>> getGF(Map<String, Object> arguments);

    List<Map<String, Object>> getInvoice(Map<String, Object> arguments);

    @Select(value = "SELECT b.SHENQINGH FROM PatentInfoPermission AS a LEFT JOIN PantentInfo AS b ON a.SHENQINGH=b.SHENQINGH WHERE a.USERID=#{ClientID} AND a.USERTYPE='KH'")
    String getNEIBUBH(int ClientID);

    @Delete("DELETE FROM tbClient WHERE ClientID=#{ClientID}")
    int delClient(int ClientID);

    @Delete("DELETE FROM tbClientLinkers WHERE ClientID=#{ClientID}")
    int delClientLinkers(int ClientID);

    @Delete("DELETE FROM FollowRecord WHERE ClientID=#{ClientID}")
    int delFollowRecord(int ClientID);

    tbClient findNameByName(@Param("name") String name);

    List<Map<String, String>> getAllClient();

    Integer addImportExcelReturnClientID(tbClient client);

    List<Map<String, Object>> getAllClientInfoExistLinkMan(Map<String, Object> args);

    /**
     * create by: mmzs
     * description: TODO
     * create time:
     * 经历过三种交单。用户类型还不是合作客户的记录。全部更新为合作客户。
     * @return
     */
    @Select(value = "SELECT a.ClientID FROM View_AllCasesMain a inner join tbClient b on a.ClientID=b.ClientID Where " +
            "a.State>=4 and isnull(b.cootype,0)=2")
    List<Integer>getAllNotUpdateStatus();

    @Select(value = "Select * from tbClient Where len(isnull(OrgCode,''))>3 And len(isnull(password,''))>0 And isnull(password,'')!='123456'")
    List<tbClient> getCanLoginClient();
    @Select("Select Name FROM tbClient WHERE ClientID=#{ClientID}")
    String getNamebyId(Integer ClientID);
}
