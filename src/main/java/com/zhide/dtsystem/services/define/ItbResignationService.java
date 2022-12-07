package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.tbResignationRecord;

import javax.servlet.http.HttpServletRequest;

public interface ItbResignationService {

    pageObject getData(HttpServletRequest request) throws Exception;

    tbResignationRecord Save(tbResignationRecord resignationRecord) throws Exception;
}
