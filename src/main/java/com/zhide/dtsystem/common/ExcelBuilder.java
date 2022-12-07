package com.zhide.dtsystem.common;

import com.zhide.dtsystem.models.gridColumnInfo;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.ObjectUtils;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelBuilder {
    List<gridColumnInfo> columns;
    List<Map<String, Object>> datas;
    Workbook book;
    CellStyle cellStyle = null;
    CellStyle headerStyle = null;

    public ExcelBuilder(List<gridColumnInfo> cols, List<Map<String, Object>> datas) {
        this.columns = cols;
        this.datas = datas;
    }

    public byte[] create() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        book = new HSSFWorkbook();
        Sheet sheet = book.createSheet();
        Map<String, gridColumnInfo> colHash = createHeader(sheet);
        createBody(colHash, datas, sheet);
        book.write(outputStream);
        return outputStream.toByteArray();
    }

    private Map<String, gridColumnInfo> createHeader(Sheet sheet) {
        Map<String, gridColumnInfo> colHash = new HashMap<>();
        Row row = sheet.createRow(0);
        for (int i = 0; i < columns.size(); i++) {
            gridColumnInfo info = columns.get(i);
            String field = info.getField();
            String header = info.getHeader();
            Cell cell = row.createCell(i);
            cell.setCellValue(header);
            cell.setCellStyle(headerStyle);
            sheet.setColumnWidth(i, info.getWidth() * 36);
            info.setColIndex(i);
            colHash.put(field, info);
        }
        return colHash;
    }

    private void createBody(Map<String, gridColumnInfo> colHash, List<Map<String, Object>> datas, Sheet sheet) throws
            ParseException {

        for (int i = 0; i < datas.size(); i++) {
            Row row = sheet.createRow(i + 1);
            Map<String, Object> data = datas.get(i);
            for (String Key : data.keySet()) {
                HSSFDataFormat sff = (HSSFDataFormat) book.createDataFormat();
                Object OV = data.get(Key);
                gridColumnInfo Info = colHash.get(Key);
                int colIndex = Info.getColIndex();
                Cell cell = row.createCell(colIndex);
                CellStyle cellStyle = getCellStyle();
                if (ObjectUtils.isEmpty(OV) == false) {
                    String type = Info.getType();
                    if (type.equals("string")) {
                        cell.setCellValue(OV.toString());
                        cell.setCellType(CellType.STRING);
                    } else if (type.equals("date")) {
                        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                        Date tv = sf.parse(OV.toString());
                        cell.setCellValue(tv);
                        cellStyle.setDataFormat(sff.getFormat("yyyy-MM-dd"));
                    } else if (type.equals("float")) {
                        double X = Double.parseDouble(OV.toString());
                        cell.setCellType(CellType.NUMERIC);
                        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
                        cell.setCellValue(OV.toString());
                        //cell.setCellValue(X);
                    } else if (type.equals("int")) {
                        cell.setCellValue(OV.toString());
                        cell.setCellType(CellType.NUMERIC);
                    }
                }
                cell.setCellStyle(cellStyle);
            }
        }
    }

    private CellStyle getHeaderStyle() {
        if (headerStyle == null) {
            headerStyle = book.createCellStyle();
            Font font = book.createFont();
            font.setFontName("黑体");
            font.setFontHeightInPoints((short) 18.0);
            headerStyle.setFont(font);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setWrapText(true);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
        }
        return headerStyle;
    }

    private CellStyle getCellStyle() {
        if (cellStyle == null) {
            cellStyle = book.createCellStyle();
            Font font = book.createFont();
            font.setFontName("宋体");
            font.setFontHeightInPoints((short) 10.0);
            cellStyle.setFont(font);
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
        }
        return cellStyle;
    }
}
