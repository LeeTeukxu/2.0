package com.zhide.dtsystem.models;

import java.util.Date;

@ExcelTitle(value = "专利费用减缓办理表", end = 11)
public class CostReductionExcelTemplate implements IExcelExportTemplate {
    @ExcelColumn(title = "填写人", columnIndex = 0)
    private String UserIDName;
    @ExcelColumn(dataType = "Date", format = "yyyy-MM-dd", title = "填写时间", columnIndex = 1)
    private Date AddTime;
    @ExcelColumn(title = "客户名称", columnIndex = 3)
    private String KHName;
    @ExcelColumn(title = "费减请求人名称", columnIndex = 4)
    private String ReductionRequest;
    @ExcelColumn(title = "费减请求人证件号", columnIndex = 5)
    private String ReductionRequestNumber;
    @ExcelColumn(columnIndex = 6, title = "费减请求人附件")
    private String FileName;

    public String getUserIDName() {
        return UserIDName;
    }

    public void setUserIDName(String UserIDName) {
        this.UserIDName = UserIDName;
    }

    public Date getAddTime() {
        return AddTime;
    }

    public void setAddTime(Date AddTime) {
        this.AddTime = AddTime;
    }

    public String getKHName() {
        return KHName;
    }

    public void setKHName(String KHName) {
        this.KHName = KHName;
    }

    public String getReductionRequest() {
        return ReductionRequest;
    }

    public void setReductionRequest(String ReductionRequest) {
        this.ReductionRequest = ReductionRequest;
    }

    public String getReductionRequestNumber() {
        return ReductionRequestNumber;
    }

    public void setReductionRequestNumber(String ReductionRequestNumber) {
        this.ReductionRequestNumber = ReductionRequestNumber;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String FileName) {
        this.FileName = FileName;
    }
}
