package com.zhide.dtsystem.services.define;

import com.alibaba.fastjson.JSONArray;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.*;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IClientInfoService {
    Map<String, String> getAllByNameAndID() throws Exception;

    Map<String, String> getAllByIDAndName() throws Exception;

    pageObject getPageData(HttpServletRequest request) throws Exception;

    pageObject getData(HttpServletRequest request) throws Exception;

    List<Map<String, Object>> getJSR(int ClientID, int UserID, int DepID, String RoleName);

    ClientAndClientLinkers Save(tbClient client, tbClientLinkers clientLinkers, String mode) throws Exception;

    followRecord SaveFollowRecord(followRecord followRecord) throws Exception;

    Page<followRecord> getFollowRecords(int ClientID, int pageIndex, int pageSize);

    tbClientLinkers SaveLinkers(tbClientLinkers linkers) throws Exception;

    Page<tbClientLinkers> getLinkers(int ClientID, int pageIndex, int pageSize);

    Page<tbLinkersUpdateRecord> getLinkersUpdateRecord(int ClientID, int pageIndex, int pageSize);

    pageObject GetLoginClientReords(int ClientID, HttpServletRequest request) throws Exception;

    pageObject getDLF(int ClientID, int SignMan, HttpServletRequest request) throws Exception;

    pageObject getGF(int ClientID, int SignMan, HttpServletRequest request) throws Exception;

    pageObject getInvoice(int ClientID, HttpServletRequest request) throws Exception;

    void SaveChangeKH(HttpServletRequest request) throws Exception;

    boolean remove(List<String> ids) throws  Exception;

    tbClient findNameByName(String Name);

    List<Map<String, Object>> getAllClientInfoExistLinkMan(HttpServletRequest request) throws Exception;

    void SaveImageFollowRecord(followRecord record) throws Exception;
    List<TreeListItem> getClientTree(Integer UserID,Integer DepID,String RoleName);
    List<TreeListItem> getAllClientTree(Integer UserID,Integer DepID,String RoleName);
    Map<String, Object> SaveAll(String Type, JSONArray jsonArray) throws Exception;

    void EditDefaultMaile(List<String> ids, String ClientID);
    void CannelDefaultMail(List<String> ids);

    void ChangeSignMan(Integer Transfer, Integer Resgination) throws Exception;
    void FollowRecordUserIDChange(Integer Transfer, Integer Resgination) throws Exception;
    void FollowRecordAddInChange(Integer Transfer, Integer Resgination) throws Exception;
}
