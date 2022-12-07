package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.*;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ICPCPackageService
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年08月10日 17:26
 **/
public interface ICPCPackageService {
    pageObject getData(HttpServletRequest request) throws Exception;
    pageObject getCPCFiles(String MainID);
    pageObject getInventors(String MainID);
    pageObject getApplyMan(String MainID);
    pageObject getAgent(String MainID);
    void SaveApply(cpcApplyMan app);
    void SaveInventor(String MainID, List<cpcInventor>inventors) throws Exception;
    void SaveApplyMan(String MainID,List<cpcApplyMan> applyMans) throws Exception;
    void SaveAgents(String MainID,List<cpcAgent> agents) throws Exception;
    boolean removeOne(String MainID) throws Exception;
    void  removeInventor(Integer ID) throws Exception;
    void removeApplyMan(Integer ID) throws  Exception;
    void removeAgent(Integer ID) throws Exception;
    String SaveAll(Map<String,Object> Datas) throws  Exception;
    void DeleteCPCFile(String SubID) throws Exception;
    boolean CheckAll(String MainID) throws  Exception;
    boolean saveAgentInfo(List<tbAgents> rows) throws Exception;
    boolean saveCompanyInfo(List<tbCompany> rows) throws  Exception;
    void removeAgentInfo(Integer ID);
    void removeCompanyInfo(Integer ID);
    cpcPackageMain SaveAllData(Map<String, Object> Datas) throws Exception;
    cpcPackageMain createOne(String SubID, String TargetFile) throws Exception;
    List<String> testAcceptFile(String SubID) throws Exception;
}

