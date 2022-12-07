package com.zhide.dtsystem.models;

import java.util.Date;

@ExcelTitle(value = "客户信息表", end = 16)
public class ClientInfoExcelTemplate implements IExcelExportTemplate {
    @ExcelColumn(title = "合作类型", columnIndex = 0, dataSource = "{'1':'合作客户','2':'意向客户'}")
    private int cootype;
    @ExcelColumn(title = "客户名称", columnIndex = 1)
    private String Name;
    @ExcelColumn(title = "代理费对账", columnIndex = 2)
    private String DLF;
    @ExcelColumn(title = "官费对账", columnIndex = 3)
    private String GF;
    @ExcelColumn(title = "企业性质", columnIndex = 4, dataSource = "{'1':'工矿企业','2':'事业单位','3':'大专院校','4':'个人'}")
    private int Type;
    @ExcelColumn(title = "介绍人", columnIndex = 5)
    private String JSRName;
    @ExcelColumn(columnIndex = 6, title = "登录邮箱")
    private String OrgCode;
    @ExcelColumn(title = "上次发送密码时间", columnIndex = 7, dataType = "Date", format = "yyyy-MM-dd")
    private Date SendTime;
    @ExcelColumn(title = "登记人", columnIndex = 8)
    private String SignManName;
    @ExcelColumn(title = "登记日期", columnIndex = 9, dataType = "Date", format = "yyyy-MM-dd")
    private Date SignDate;
    @ExcelColumn(title = "最新跟进时间", columnIndex = 10, dataType = "Date", format = "yyyy-MM-dd")
    private Date FollowDate;
    @ExcelColumn(title = "备注", columnIndex = 11)
    private String Memo;

    public int getCootype() {
        return cootype;
    }

    public void setCootype(int cootype) {
        this.cootype = cootype;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDLF() {
        return DLF;
    }

    public void setDLF(String DLF) {
        this.DLF = DLF;
    }

    public String getGF() {
        return GF;
    }

    public void setGF(String GF) {
        this.GF = GF;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public String getJSRName() {
        return JSRName;
    }

    public void setJSRName(String JSRName) {
        this.JSRName = JSRName;
    }

    public String getOrgCode() {
        return OrgCode;
    }

    public void setOrgCode(String OrgCode) {
        this.OrgCode = OrgCode;
    }

    public Date getSendTime() {
        return SendTime;
    }

    public void setSendTime(Date SendTime) {
        this.SendTime = SendTime;
    }

    public String getSignManName() {
        return SignManName;
    }

    public void setSignManName(String SignManName) {
        this.SignManName = SignManName;
    }

    public Date getSignDate() {
        return SignDate;
    }

    public void setSignDate(Date SignDate) {
        this.SignDate = SignDate;
    }

    public Date getFollowDate() {
        return FollowDate;
    }

    public void setFollowDate(Date FollowDate) {
        this.FollowDate = FollowDate;
    }

    public String getMemo() {
        return Memo;
    }

    public void setMemo(String Memo) {
        this.Memo = Memo;
    }
}

