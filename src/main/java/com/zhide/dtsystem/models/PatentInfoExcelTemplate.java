package com.zhide.dtsystem.models;

import java.util.Date;

@ExcelTitle(value = "专利基本信息表", end = 8)
public class PatentInfoExcelTemplate implements IExcelExportTemplate {
    @ExcelColumn(title = "申请人姓名", columnIndex = 0)
    private String SHENQINGRXM;
    @ExcelColumn(title = "专利编号", columnIndex = 1)
    private String SHENQINGH;
    @ExcelColumn(title = "申请类型", columnIndex = 2, dataSource = "{'0':'发明专利','1':'新型专利','2':'外观专利'}")
    private int SHENQINGLX;
    @ExcelColumn(title = "专利名称", columnIndex = 3, width = 50)
    private String FAMINGMC;
    @ExcelColumn(title = "发明人姓名", columnIndex = 4)
    private String FAMINGRXM;
    @ExcelColumn(dataType = "Date", format = "yyyy-MM-dd", columnIndex = 5, title = "申请日期")
    private Date SHENQINGR;
    @ExcelColumn(title = "专利状态", columnIndex = 6)
    private String ANJIANYWZT;
    @ExcelColumn(title = "客户名称", columnIndex = 7)
    private String KH;
    @ExcelColumn(title = "代理机构", columnIndex = 8)
    private String DAILIJGMC;

    public String getSHENQINGRXM() {
        return SHENQINGRXM;
    }

    public void setSHENQINGRXM(String SHENQINGRXM) {
        this.SHENQINGRXM = SHENQINGRXM;
    }

    public String getSHENQINGH() {
        return SHENQINGH;
    }

    public void setSHENQINGH(String SHENQINGH) {
        this.SHENQINGH = SHENQINGH;
    }

    public int getSHENQINGLX() {
        return SHENQINGLX;
    }

    public void setSHENQINGLX(int SHENQINGLX) {
        this.SHENQINGLX = SHENQINGLX;
    }

    public String getFAMINGMC() {
        return FAMINGMC;
    }

    public void setFAMINGMC(String FAMINGMC) {
        this.FAMINGMC = FAMINGMC;
    }

    public String getFAMINGRXM() {
        return FAMINGRXM;
    }

    public void setFAMINGRXM(String FAMINGRXM) {
        this.FAMINGRXM = FAMINGRXM;
    }

    public Date getSHENQINGR() {
        return SHENQINGR;
    }

    public void setSHENQINGR(Date SHENQINGR) {
        this.SHENQINGR = SHENQINGR;
    }

    public String getANJIANYWZT() {
        return ANJIANYWZT;
    }

    public void setANJIANYWZT(String ANJIANYWZT) {
        this.ANJIANYWZT = ANJIANYWZT;
    }

    public String getKH() {
        return KH;
    }

    public void setKH(String KH) {
        this.KH = KH;
    }

    public String getDAILIJGMC() {
        return DAILIJGMC;
    }

    public void setDAILIJGMC(String DAILIJGMC) {
        this.DAILIJGMC = DAILIJGMC;
    }
}
