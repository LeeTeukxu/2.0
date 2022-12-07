package com.zhide.dtsystem.common;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.models.ExcelColumn;
import com.zhide.dtsystem.models.ExcelTemplateInfo;
import com.zhide.dtsystem.models.ExcelTitle;
import com.zhide.dtsystem.models.IExcelExportTemplate;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelFileBuilder {
    List<IExcelExportTemplate> rows;
    ExcelTemplateInfo templateInfo;
    Workbook workbook;
    String defineFileName;

    public String getCurrentFileName() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmss");
        return defineFileName + simpleDateFormat.format(new Date()) + ".xls";
    }

    public ExcelFileBuilder(List<IExcelExportTemplate> rows) throws Exception {
        this.rows = rows;
        if (rows.size() > 0) {
            templateInfo = ExcelTemplateInfo.parseFrom(this.rows.get(0));
        }
    }

    public byte[] export() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet();
            createTitle(sheet);
            createHeader(sheet);
            createBody(sheet);
            workbook.write(outputStream);
        } catch (Exception ax) {
            throw ax;
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outputStream.toByteArray();
    }

    private void createTitle(Sheet sheet) {
        ExcelTitle title = templateInfo.getHeader();
        defineFileName = title.value();
        Row row = sheet.createRow(title.rowIndex());
        row.setHeightInPoints(30);
        for (int i = 0; i < title.end(); i++) {
            Cell cell = row.createCell(i);
            if (i == title.start()) {
                cell.setCellValue(title.value());
            }
            cell.setCellStyle(getTitleStyle());
        }
        CellRangeAddress address = new CellRangeAddress(title.rowIndex(), title.rowIndex(), title.start(), title.end());
        sheet.addMergedRegion(address);
    }

    private CellStyle getTitleStyle() {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 16.0);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private CellStyle getHeaderStyle() {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short) 12.0);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private CellStyle getCellStyle() {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 10.0);
        style.setFont(font);
        style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private void createHeader(Sheet sheet) {
        ExcelTitle header = templateInfo.getHeader();
        Row row = sheet.createRow(header.rowIndex() + 1);
        Map<Integer, ExcelColumn> cols = templateInfo.getColumns();
        row.setHeightInPoints(16);
        CellStyle cellStyle = getHeaderStyle();
        for (int i = 0; i < cols.size(); i++) {
            if (cols.containsKey(i)) {
                ExcelColumn colInfo = cols.get(i);
                Cell cell = row.createCell(i);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(colInfo.title());
                sheet.setColumnWidth(i, colInfo.width() * 256);
            }
        }

    }

    private void createBody(Sheet sheet) throws IllegalAccessException {
        ExcelTitle header = templateInfo.getHeader();
        int rowIndex = header.rowIndex();
        int n = 0;
        CellStyle cellStyle = getCellStyle();
        for (int i = rowIndex + 2; i < rows.size() + 2; i++) {
            Row row = sheet.createRow(i);
            IExcelExportTemplate singleRow = rows.get(n);
            createSingle(row, singleRow, cellStyle);
            n++;
        }
    }

    private void createSingle(Row row, IExcelExportTemplate data, CellStyle style) throws IllegalAccessException {
        Map<Integer, ExcelColumn> cols = templateInfo.getColumns();
        Map<String, Object> datas = EntityHelper.toMap(data);
        for (int i = 0; i < cols.size(); i++) {
            if (cols.containsKey(i)) {
                ExcelColumn colInfo = cols.get(i);
                Cell cell = row.createCell(i);
                String field = colInfo.field();
                Object val = datas.get(field);
                String value = "";
                if (val != null) {
                    cell.setCellType(CellType.STRING);
                    String dataType = colInfo.dataType();
                    if (dataType.equals("Number")) {
                        DecimalFormat numberFormat = new DecimalFormat(colInfo.format());
                        value = numberFormat.format(val);
                        cell.setCellType(CellType.NUMERIC);
                    } else if (dataType.equals("Date")) {
                        DateFormat dateFormat = new SimpleDateFormat(colInfo.format());
                        value = dateFormat.format(val);
                    } else {
                        value = val.toString();
                        try {
                            String dataSource = colInfo.dataSource();
                            if (Strings.isEmpty(dataSource) == false) {
                                HashMap hash = JSON.parseObject(dataSource, HashMap.class);
                                if (hash.containsKey(value)) {
                                    Object OO = hash.get(value);
                                    if (OO != null) value = OO.toString();
                                }
                            }
                        } catch (Exception ax) {
                            ax.printStackTrace();
                        }
                    }
                }
                cell.setCellStyle(style);
                cell.setCellValue(value);
            }
        }
    }
}

