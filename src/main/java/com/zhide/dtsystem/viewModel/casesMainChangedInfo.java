package com.zhide.dtsystem.viewModel;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.casesMain;
import com.zhide.dtsystem.multitenancy.CompanyContext;

/**
 * @ClassName: casesMainChangedInfo
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年05月29日 16:36
 **/
public class casesMainChangedInfo extends casesMain {
    String mode;
    String companyId;

    public static casesMainChangedInfo parseFrom(casesMain main, String mode) {
        String SS = JSON.toJSONString(main);
        LoginUserInfo Log = CompanyContext.get();
        casesMainChangedInfo Info = JSON.parseObject(SS, casesMainChangedInfo.class);
        Info.setMode(mode);
        Info.setCompanyId(Log.getCompanyId());
        return Info;
    }

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
}
