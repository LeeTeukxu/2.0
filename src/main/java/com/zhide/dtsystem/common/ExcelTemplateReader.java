package com.zhide.dtsystem.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhide.dtsystem.models.gridColumnInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 读取固定模版中列、标题信息。
 * 一般是第一行为字段，第二行为标题。
 **/
public class ExcelTemplateReader {
    Workbook book;
    Sheet sheet;
    int fieldRowIndex = 0;
    int titleRowIndex = 2;
    int startRow = 3;
    FileInputStream inputStream;

    public int getFieldRowIndex() {
        return fieldRowIndex;
    }

    public void setFieldRowIndex(int fieldRowIndex) {
        this.fieldRowIndex = fieldRowIndex;
    }

    public int getTitleRowIndex() {
        return titleRowIndex;
    }

    public void setTitleRowIndex(int titleRowIndex) {
        this.titleRowIndex = titleRowIndex;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public ExcelTemplateReader(String code) throws Exception {
        try {
            File fx = ResourceUtils.getFile("classpath:static/template/" + code + ".xls");
            if (fx.exists() == false) throw new Exception("指定的模版编号不存在。");
            inputStream = new FileInputStream(fx);
            book = new HSSFWorkbook(inputStream);
            sheet = book.getSheetAt(0);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (inputStream != null) inputStream.close();
        }
    }

    public ExcelTemplateReader(File file) throws Exception {
        try {
            inputStream = new FileInputStream(file);
            book = new HSSFWorkbook(inputStream);
            sheet = book.getSheetAt(0);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (inputStream != null) inputStream.close();
        }
    }

    public List<gridColumnInfo> getColumns() {
        List<gridColumnInfo> res = new ArrayList<>();
        Row fieldRow = sheet.getRow(fieldRowIndex);
        res = getFieldColumns(fieldRow, res);
        Row titleRow = sheet.getRow(titleRowIndex);
        res = getTitleColumn(titleRow, res);
        return res;
    }

    private List<gridColumnInfo> getFieldColumns(Row row, List<gridColumnInfo> cols) {
        List<gridColumnInfo> res = new ArrayList<>();
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            String field = cell.getStringCellValue();
            if (StringUtils.isEmpty(field) == true) continue;
            gridColumnInfo target = new gridColumnInfo();
            setAttribute(field, target);
            Integer X = i;
            Optional<gridColumnInfo> findCols = cols.stream().filter(f -> f.getColIndex() == X).findFirst();
            if (findCols.isPresent()) {
                target = findCols.get();
            }
            int width = sheet.getColumnWidth(i);
            target.setColIndex(i);
            target.setWidth(width / 36);
            res.add(target);
        }
        return res;
    }

    private void setAttribute(String typeText, gridColumnInfo Info) {
        Info.setType("string");
        Info.setRequired("false");
        if (typeText.indexOf("$") > -1) {
            String[] Atts = typeText.split("\\$");
            Info.setField(Atts[0]);
            for (int i = 1; i < Atts.length; i++) {
                String Att = Atts[i].trim();
                if (Att.indexOf(":") > -1) {
                    if (Att.indexOf("type") > -1) {
                        Info.setType(Att.replace("type:", ""));
                    } else if (Att.indexOf("required") > -1) {
                        Info.setRequired(Att.replace("required:", ""));
                    } else if (Att.indexOf("datasource") > -1) {
                        Info.setDataSource(Att.replace("datasource:", ""));
                    } else if (Att.indexOf("reg") > -1) {
                        Info.setRegex(Att.replace("reg:", ""));
                    } else if (Att.indexOf("error") > -1) {
                        Info.setError(Att.replace("error:", ""));
                    } else if (Att.indexOf("contains") > -1) {
                        Info.setError(Att.replace("contains:", ""));
                    }
                }
            }
        } else {
            Info.setField(typeText);
        }
    }

    private List<gridColumnInfo> getTitleColumn(Row row, List<gridColumnInfo> cols) {
        List<gridColumnInfo> res = new ArrayList<>();
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            String title = cell.getStringCellValue();
            if (StringUtils.isEmpty(title) == true) continue;
            Integer X = i;
            Optional<gridColumnInfo> findCols = cols.stream().filter(f -> f.getColIndex() == X).findFirst();
            gridColumnInfo target = new gridColumnInfo();
            if (findCols.isPresent()) {
                target = findCols.get();
            }
            target.setHeader(title);
            res.add(target);
        }
        return res;
    }

    public List<Map<String, Object>> getData(List<gridColumnInfo> cols) {
        List<Map<String, Object>> res = new ArrayList<>();
        for (int i = startRow; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            Map<String, Object> O = getSingleRow(row, cols);
            if (isEmptyRow(O, cols) == true) break;
            res.add(O);
        }
        return res;
    }

    private boolean isEmptyRow(Map<String, Object> row, List<gridColumnInfo> cols) {
        int num = 0;
        for (String Key : row.keySet()) {
            Object Value = row.get(Key);
            Optional<gridColumnInfo> findInfos = cols.stream().filter(f -> f.getField().equals(Key)).findFirst();
            if (findInfos.isPresent()) {
                gridColumnInfo findInfo = findInfos.get();
                if (ObjectUtils.isEmpty(Value) == true) {
                    if (findInfo.getRequired().equals("true")) num++;
                }
            } else {

            }
        }
        return !(num == 0);
    }

    private Map<String, Object> getSingleRow(Row row, List<gridColumnInfo> cols) {
        Map<String, Object> Obj = new HashMap<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            Integer X = i;
            Optional<gridColumnInfo> finds = cols.stream().filter(f -> f.getColIndex() == X).findFirst();
            if (finds.isPresent()) {
                gridColumnInfo find = finds.get();
                Cell cell = row.getCell(i);
                Object value = null;
                if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                    if (find.getType().equals("date")) {
                        Date d = cell.getDateCellValue();
                        value = format.format(d);
                    } else value = cell.getNumericCellValue();
                } else if (cell.getCellTypeEnum() == CellType.STRING) {
                    value = cell.getStringCellValue();
                    if (find.getType().equalsIgnoreCase("combo")) {
                        String dataSource = find.getDataSource();
                        dataSource = dataSource.replace("+", " ").trim();
                        List<Map<String, Object>> OO = JSON.parseObject(dataSource, new
                                TypeReference<List<Map<String, Object>>>() {
                                });
                        Object OX = value;
                        Optional<Map<String, Object>> ffs = OO.stream()
                                .filter(f -> f.get("text").equals(OX))
                                .findFirst();
                        if (ffs.isPresent()) {
                            Map<String, Object> Of = ffs.get();
                            value = Integer.parseInt(Of.get("id").toString());
                        }
                    }
                }
                ValidateSingleRow(find, value, Obj);
                Obj.put(find.getField(), value);
            }
        }
        return Obj;
    }

    private boolean ValidateSingleRow(gridColumnInfo Info, Object Value, Map<String, Object> row) {
        String Tx = Info.getRegex();
        if (StringUtils.isEmpty(Tx) == false) {
            if (ObjectUtils.isEmpty(Value) == false) {
                String Vxx = StringUtils.trim(Value.toString());
                if (Value.getClass() == Double.class) {
                    BigDecimal DD = new BigDecimal(Double.parseDouble(Value.toString()));
                    Vxx = DD.toPlainString();
                }
                boolean OK = Pattern.matches(Tx, Vxx);
                if (OK == false) {
                    SetValue("Valid", OK, row);
                    SetValue("ImportResult", Info.getHeader() + ":" + Info.getError()+",当前值:"+Vxx, row);
                }
                return OK;
            }
        }
        if (ObjectUtils.isEmpty(Value) == true) {
            if (Info.getRequired().equals("true")) {
                SetValue("Valid", false, row);
                SetValue("ImportResult", Info.getHeader() + ":不能为空", row);
                return false;
            }
        }
        String containValue = Info.getContains();
        if (StringUtils.isEmpty(containValue) == false) {
            List<String> Values = Arrays.asList(containValue.split(","));
            if (Values.contains(Value) == false) {
                SetValue("Valid", false, row);
                SetValue("ImportResult", Value.toString() + "不存" + Info.getHeader() + "指定的值范围内!", row);
                return false;
            }
        }
        return true;
    }

    private void SetValue(String Key, Object Value, Map<String, Object> row) {
        if (row.containsKey(Key)) {

        } else row.put(Key, Value);
    }
}
