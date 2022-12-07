package com.zhide.dtsystem.models;

import java.util.Date;

@ExcelTitle(value = "通知单基本信息表")
public class NoticeInfoExcelTemplate implements IExcelExportTemplate {
    private String TZSMC;
    private String ZHUANLIMC;
    private Date SQR;
    private String SHENQINGH;
    private String SHENQINGLX;
    private String ZSTATUS;

}
