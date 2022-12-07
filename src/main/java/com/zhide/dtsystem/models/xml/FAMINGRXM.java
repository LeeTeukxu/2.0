package com.zhide.dtsystem.models.xml;

@ChineseName(value = "第一发明人,其他发明人,第一设计人,其他设计人")
public class FAMINGRXM {
    @ChineseName(value = "姓名")
    String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
