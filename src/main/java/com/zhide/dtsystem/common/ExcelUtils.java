package com.zhide.dtsystem.common;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ExcelUtils
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年01月12日 21:18
 **/
public class ExcelUtils {
    public static List<String> GetSingle(File file, int rowIndex) throws Exception {
        List<String> Res = new ArrayList<>();
        Sheet sheet = GetSheet(file);
        Row row = sheet.getRow(rowIndex);
        if (row != null) {
            for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
                Cell cell = row.getCell(i);
                String X = cell.getStringCellValue();
                if (StringUtils.isEmpty(X) == true) X = "";
                Res.add(X);
            }
        }
        return Res;
    }

    private static Sheet GetSheet(File file) throws Exception {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            Workbook book = new XSSFWorkbook(inputStream);
            Sheet sheet = book.getSheetAt(0);
            return sheet;

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (Exception bx) {
                bx.printStackTrace();
            }
        }
    }
}
