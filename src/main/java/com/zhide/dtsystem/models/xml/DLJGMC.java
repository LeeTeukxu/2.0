package com.zhide.dtsystem.models.xml;

import java.util.ArrayList;
import java.util.List;

@ChineseName(value = "专利代理机构")
public class DLJGMC {
    public DLJGMC() {
        DLRXM = new ArrayList<>();
    }

    @ChineseName(value = "名称")
    private String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<String> getDLRXM() {
        return DLRXM;
    }

    public void setDLRXM(List<String> DLRXM) {
        this.DLRXM = DLRXM;
    }

    @ChineseName(value = "代理人", secName = "姓名")
    private List<String> DLRXM;
}
