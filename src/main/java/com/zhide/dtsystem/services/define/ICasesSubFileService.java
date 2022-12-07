package com.zhide.dtsystem.services.define;

import java.io.File;

public interface ICasesSubFileService {
    File DownloadFiles(String Type,String SubIDS) throws Exception;

    File DownloadTradeFiles(String SubIDS) throws Exception;
}
