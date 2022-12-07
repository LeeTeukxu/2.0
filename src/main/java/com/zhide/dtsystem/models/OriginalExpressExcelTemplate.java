package com.zhide.dtsystem.models;

import java.util.Date;

@ExcelTitle(value = "原件管理信息表", end = 18)
public class OriginalExpressExcelTemplate implements IExcelExportTemplate {
    @ExcelColumn(title = "备注信息", columnIndex = 0)
    private String Action;
    @ExcelColumn(title = "原件类型", columnIndex = 1)
    private String otypeText;
    @ExcelColumn(title = "通知书名称", columnIndex = 2)
    private String TONGZHISMC;
    @ExcelColumn(title = "原件状态", columnIndex = 3, dataSource = "{'0':'待分类','1':'待自取','2':'已自取','3':'待寄送','4':'已寄送'}")
    private int ostateText;
    @ExcelColumn(title = "专利申请人", columnIndex = 4)
    private String SHENQINGRXM;
    @ExcelColumn(columnIndex = 5, title = "专利申请号")
    private String SHENQINGH;
    @ExcelColumn(title = "专利名称", columnIndex = 6)
    private String FAMINGMC;
    @ExcelColumn(title = "专利类型", columnIndex = 7, dataSource = "{'0':'发明专利','1':'新型专利','2':'外观专利'}")
    private int SHENQINGLX;
    @ExcelColumn(title = "专利法律状态", columnIndex = 8)
    private String ANJIANYWZT;
    @ExcelColumn(title = "专利发明人", columnIndex = 9)
    private String FAMINGRXM;
    @ExcelColumn(title = "内部编号", columnIndex = 10)
    private String NEIBUBH;
    @ExcelColumn(title = "归属客户", columnIndex = 11)
    private String KH;
    @ExcelColumn(title = "销售维护人", columnIndex = 12)
    private String XS;
    @ExcelColumn(title = "代理责任人", columnIndex = 13)
    private String DL;
    @ExcelColumn(title = "流程责任人", columnIndex = 14)
    private String LC;
    @ExcelColumn(title = "登记时间", columnIndex = 15, dataType = "Date", format = "yyyy-MM-dd")
    private Date CreateTime;
    @ExcelColumn(title = "登记人", columnIndex = 16)
    private String DJR;
    @ExcelColumn(title = "二维编码", columnIndex = 17)
    private String dnum;
    @ExcelColumn(title = "自取编号", columnIndex = 18)
    private String DrawNo;

    public String getAction() {
        return Action;
    }

    public void setAction(String Action) {
        this.Action = Action;
    }

    public String getOtypeText() {
        return otypeText;
    }

    public void setOtypeText(String otypeText) {
        this.otypeText = otypeText;
    }

    public String getTONGZHISMC() {
        return TONGZHISMC;
    }

    public void setTONGZHISMC(String TONGZHISMC) {
        this.TONGZHISMC = TONGZHISMC;
    }

    public int getOstateText() {
        return ostateText;
    }

    public void setOstateText(int ostateText) {
        this.ostateText = ostateText;
    }

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

    public String getFAMINGMC() {
        return FAMINGMC;
    }

    public void setFAMINGMC(String FAMINGMC) {
        this.FAMINGMC = FAMINGMC;
    }

    public int getSHENQINGLX() {
        return SHENQINGLX;
    }

    public void setSHENQINGLX(int SHENQINGLX) {
        this.SHENQINGLX = SHENQINGLX;
    }

    public String getANJIANYWZT() {
        return ANJIANYWZT;
    }

    public void setANJIANYWZT(String ANJIANYWZT) {
        this.ANJIANYWZT = ANJIANYWZT;
    }

    public String getFAMINGRXM() {
        return FAMINGRXM;
    }

    public void setFAMINGRXM(String FAMINGRXM) {
        this.FAMINGRXM = FAMINGRXM;
    }

    public String getNEIBUBH() {
        return NEIBUBH;
    }

    public void setNEIBUBH(String NEIBUBH) {
        this.NEIBUBH = NEIBUBH;
    }

    public String getKH() {
        return KH;
    }

    public void setKH(String KH) {
        this.KH = KH;
    }

    public String getXS() {
        return XS;
    }

    public void setXS(String XS) {
        this.XS = XS;
    }

    public String getDL() {
        return DL;
    }

    public void setDL(String DL) {
        this.DL = DL;
    }

    public String getLC() {
        return LC;
    }

    public void setLC(String LC) {
        this.LC = LC;
    }

    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getDJR() {
        return DJR;
    }

    public void setDJR(String DJR) {
        this.DJR = DJR;
    }

    public String getDnum() {
        return dnum;
    }

    public void setDnum(String dnum) {
        this.dnum = dnum;
    }

    public String getDrawNo() {
        return DrawNo;
    }

    public void setDrawNo(String DrawNo) {
        this.DrawNo = DrawNo;
    }
}
