package com.zhide.dtsystem.viewModel;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.casesSub;
import com.zhide.dtsystem.multitenancy.CompanyContext;

/**
 * @ClassName: casesSubChangedInfo
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年06月17日 11:36
 **/
public class casesSubChangedInfo extends casesSub {
    String mode;
    String companyId;
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getCompanyId() {
        return companyId;
    }
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
    public static casesSubChangedInfo parseFrom(casesSub sub,String mode){
        String X= JSON.toJSONString(sub);
        LoginUserInfo Log= CompanyContext.get();
        casesSubChangedInfo Info=JSON.parseObject(X,casesSubChangedInfo.class);
        Info.setMode(mode);
        Info.setCompanyId(Log.getCompanyId());
        return Info;
    }
}
