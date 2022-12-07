package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.expenseItem;
import com.zhide.dtsystem.models.expenseMain;
import com.zhide.dtsystem.models.suggestMain;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ISuggestService
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2022年01月27日 10:23
 **/
public interface IExpenseService {
    expenseMain SaveAll(Map<String,Object> Data) throws Exception;
    void SaveItems(List<expenseItem> items) throws Exception;
    pageObject getData(HttpServletRequest request) throws Exception;
    void ChangeMan(String IDS,Integer NewMan,String Text) throws Exception;
    void Audit(Integer ID,Integer AuditResult,String Text) throws Exception;
    void Set(Integer ID,Integer SetResult,String Text) throws Exception;
    void RemoveAll(List<Integer> MainIDs) throws Exception;
}
