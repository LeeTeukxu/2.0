package com.zhide.dtsystem.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class complexExcelBuilder {
    String code = "";
    Workbook book;
    Sheet sheet;
    Map<String, String> single = new HashMap<>();
    Map<String, Integer> rowKey = new HashMap<>();
    Integer firstRowIndex = 0;
    FileInputStream fInput = null;
    String sheetName="Sheet1";
    Boolean autoCreateNew=true;
    String numberCell="";


    public complexExcelBuilder(String code) throws Exception {
        this.code = code;
        try {
            File fx=null;
            if(code.indexOf("/")>-1){
                FTPUtil ftpUti=new FTPUtil();
                if(ftpUti.connect()){
                   String VV= CompanyPathUtils.getTempPath(UUID.randomUUID().toString()+".xls");
                   ftpUti.download(code,VV);
                   fx=new File(VV);
                }
            } else {
                fx = ResourceUtils.getFile("classpath:static/template/" + code + ".xls");
                if (fx.exists() == false) throw new Exception("指定的模版编号不存在。");
            }
            fInput = new FileInputStream(fx);
            book = new HSSFWorkbook(fInput);
            sheet = book.getSheet(sheetName);
            if(sheet==null){
               sheet= book.getSheetAt(0);
            }
            sheet.setForceFormulaRecalculation(true);
            eachAllCells();
        } catch (Exception ax) {
            ax.printStackTrace();
        } finally {
            if (fInput != null) fInput.close();
        }
    }
    public complexExcelBuilder(String code,String sheetName) throws Exception {
        this.code = code;
        this.setSheetName(sheetName);
        try {
            File fx=null;
            if(code.indexOf("/")>-1){
                FTPUtil ftpUti=new FTPUtil();
                if(ftpUti.connect()){
                    String VV= CompanyPathUtils.getTempPath(UUID.randomUUID().toString()+".xls");
                    ftpUti.download(code,VV);
                    fx=new File(VV);
                }
            } else {
                fx = ResourceUtils.getFile("classpath:static/template/" + code + ".xls");
                if (fx.exists() == false) throw new Exception("指定的模版编号不存在。");
            }
            fInput = new FileInputStream(fx);
            book = new HSSFWorkbook(fInput);
            sheet = book.getSheet(getSheetName());
            if(sheet==null){
                sheet= book.getSheetAt(0);
            }
            sheet.setForceFormulaRecalculation(true);
            eachAllCells();
        } catch (Exception ax) {
            ax.printStackTrace();
        } finally {
            if (fInput != null) fInput.close();
        }
    }
    private void eachAllCells() {
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);

            for (int n = 0; n < row.getLastCellNum(); n++) {
                Cell cell = row.getCell(n);
                String cellValue="";
                try {
                    cellValue = cell.getStringCellValue();
                }catch(Exception ax){
                    String X=ax.getMessage();
                }
                if (StringUtils.isEmpty(cellValue) == true) continue;
                if (cellValue.indexOf("{{") > -1) {
                    String rowValue = cellValue.replaceAll("\\{\\{", "").replaceAll("}}", "");
                    rowKey.put(rowValue.toUpperCase(), n);
                    if (i > firstRowIndex) firstRowIndex = i;
                    cell.setCellValue("");
                    continue;
                }
                String Pos=Integer.toString(i) + "," + Integer.toString(n);
                if (cellValue.indexOf("{") > -1) {
                    Pattern pattern=Pattern.compile("\\{\\w+\\}");
                    Matcher mx= pattern.matcher(cellValue);
                    while (mx.find()){
                        String VValue=(cellValue.substring(mx.start(),mx.end()));
                        String rowValue = VValue.replaceAll("\\{", "").replaceAll("}", "");
                        rowValue=rowValue.toUpperCase();
                        if(single.containsKey(rowValue)==false) {
                            single.put(rowValue, Pos);
                        } else {
                            List<String> ISS=new ArrayList<>();
                            String ID= single.get(rowValue);
                            String[] IDS=ID.split(";");
                            ISS.addAll(Arrays.asList(IDS));
                            ISS.add(Pos);
                            single.replace(rowValue,StringUtils.join(ISS,";"));
                        }
                    }
                    continue;
                }
            }
        }
    }

    /**
     * create by: mmzs
     * description: TODO
     * create time:
     * <p>
     * 根据横版内容来填充数据，先填单个单元的数据，再插入行，填充多行数据。
     *
     * @return
     */
    public byte[] getContent(Map<String, Object> content) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        writeSingle(content);
        if (content.containsKey("Rows")) {
            String rowText = content.get("Rows").toString();
            TypeReference<List<Map<String, Object>>> O = new TypeReference<List<Map<String, Object>>>() {};
            List<Map<String, Object>> rows = JSON.parseObject(rowText, O);
            writeRows(rows);
        }
        HSSFFormulaEvaluator.evaluateAllFormulaCells(book);
        book.write(outputStream);
        byte[] Bx = outputStream.toByteArray();
        outputStream.close();
        return Bx;
    }

    /**
     * create by: mmzs
     * description: TODO
     * create time:
     *
     * @return
     */
    private void writeRows(List<Map<String, Object>> rows) {
        int startRow = firstRowIndex;
        int count = rows.size();
        if(getAutoCreateNew()==true) {
            if (count - 1 > 0) createNewRow(startRow, count);
        }
        for (int i = 0; i < rows.size(); i++) {
            Map<String, Object> row = rows.get(i);
            for (String key : row.keySet()) {
                String Key = key.toUpperCase();
                if (rowKey.containsKey(Key)) {
                    int colIndex = rowKey.get(Key);
                    Cell writeCell = sheet.getRow(firstRowIndex + i).getCell(colIndex);
                    if (writeCell != null) {
                        Object oValue = row.get(key);
                        String value = "";
                        if (ObjectUtils.isEmpty(oValue) == false) {
                            value = oValue.toString();
                        }
                        if(getNumberCell().indexOf(Key)>-1){
                            writeCell.setCellValue(Double.parseDouble(value));
                        }
                        else writeCell.setCellValue(value);
                        writeCell.getCellStyle().setLocked(false);
                    }
                }
            }
        }
    }

    private void writeSingle(Map<String, Object> datas) {
        for (String key : single.keySet()) {
            String Key = key.toUpperCase();
            String ss = single.get(key).toString();
            String[] rowAndcols=ss.split(";");
            for(int i=0;i<rowAndcols.length;i++) {
                String rowAndcol=rowAndcols[i];
                String[] rc = rowAndcol.split(",");
                int row = Integer.parseInt(rc[0]);
                int col = Integer.parseInt(rc[1]);
                Cell cell = sheet.getRow(row).getCell(col);
                if (cell != null) {
                    String V = cell.getStringCellValue();
                    if (StringUtils.isEmpty(V)) V = "";
                    if (V.indexOf(key) > -1) {
                        Object O = datas.get(Key);
                        String Value = "";
                        if (ObjectUtils.isEmpty(O) == false) Value = O.toString();
                        V = V.replace("{" + key + "}", Value);
                        cell.setCellValue(V);
                        cell.getCellStyle().setLocked(false);
                    }
                }
            }
        }
    }

    private void createNewRow(int startRow, int count) {
        sheet.shiftRows(firstRowIndex + 1, sheet.getLastRowNum(), count - 1, true, false);
        Row sourceRow = sheet.getRow(3);
        for (int i = startRow; i < startRow + count; i++) {
            Row row = sheet.createRow(i);
            for (int n = 0; n < sourceRow.getLastCellNum(); n++) {
                Cell cell = row.createCell(n);
                CellStyle cellStyle = sourceRow.getCell(n).getCellStyle();
                cell.setCellStyle(cellStyle);
            }
        }
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public Boolean getAutoCreateNew() {
        return autoCreateNew;
    }

    public void setAutoCreateNew(Boolean autoCreateNew) {
        this.autoCreateNew = autoCreateNew;
    }

    public String getNumberCell() {
        return numberCell;
    }

    public void setNumberCell(String numberCell) {
        this.numberCell = numberCell;
    }
}
