package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.tbFileUpload;

import java.io.File;
import java.util.List;

/**
 * @ClassName: IFileUploadService
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年11月17日 10:52
 **/
public interface IFileUploadService {
    void DeleteAll(List<Integer> IDS) throws  Exception;
    void SaveAll(List<tbFileUpload> files) throws Exception;
    File download(String[] IDS,Boolean zipFile) throws Exception;
}
