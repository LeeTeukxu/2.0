package com.zhide.dtsystem.models.xml;

@ChineseName(value = "第一申请人,其他申请人")
public class SHENQINGRXM {
    @ChineseName(value = "姓名或名称")
    String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
