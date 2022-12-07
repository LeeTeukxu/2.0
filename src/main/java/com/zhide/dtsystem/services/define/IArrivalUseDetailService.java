package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.arrivalUseDetail;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IArrivalUseDetailService {
    pageObject getMain(HttpServletRequest request) throws Exception;
    pageObject getSub(HttpServletRequest request) throws Exception;
    void SaveSub(List<arrivalUseDetail> details) throws Exception;
    pageObject getDetail(HttpServletRequest request) throws Exception;
    Integer  SaveDetail(List<arrivalUseDetail> Datas) throws Exception;
    void RemoveOne(int ID) throws Exception;
    void AuditOne(int Result,int ID,String ResultText)throws Exception;
    void RejectOne(int Result,int ID,String ResultText) throws Exception;
}
