package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.CostReduction;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Map;

public interface ICostReductionService {
    pageObject getData(HttpServletRequest request) throws Exception;

    CostReduction SaveAll(Map<String, Object> Data) throws Exception;

    boolean remove(List<String> uuids);

    List<String> getAttachmentPaths(String Path, String GUID) throws Exception;

    CostReduction SaveNeiShen(CostReduction costReduction) throws Exception;

    boolean GuoZhiJu(List<Integer> CostReductionIDS);

    boolean UnGuoZhiJu(List<Integer> CostReductionIDS);

    CostReduction SaveFeiJianJieGuo(CostReduction costReduction) throws Exception;

    File DownloadFiles(String UUIDS) throws Exception;
}
