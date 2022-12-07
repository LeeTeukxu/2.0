package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.casesCommitFile;

import javax.servlet.http.HttpServletRequest;

public interface ICommitFileService {
    casesCommitFile SaveAll(casesCommitFile datas, String Action) throws Exception;

    boolean RemoveAll(String AttID) throws Exception;

    boolean Commit(Integer ID, String Result, String ResultText) throws Exception;

    pageObject getData(HttpServletRequest request) throws Exception;
}
