package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.attachmentMain;
import com.zhide.dtsystem.models.uploadProcessInfo;
import com.zhide.dtsystem.viewModel.versionUpdateResult;

import java.util.List;

/**
 * @ClassName: IWebAPIService
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年07月19日 18:05
 **/
public interface IWebAPIService {
    List<String> getNotUploadCPC();
    List<String> GetNotUploadTZS();
    attachmentMain getSoftVersion(int curVer);
    List<versionUpdateResult> getSqlVersionUpdate(String account, Integer maxVersion);
    uploadProcessInfo getUploadProcess();
}
