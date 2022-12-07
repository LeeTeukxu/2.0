package com.zhide.dtsystem.controllers.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhide.dtsystem.common.ExcelBuilder;
import com.zhide.dtsystem.common.WebFileUtils;
import com.zhide.dtsystem.common.complexExcelBuilder;
import com.zhide.dtsystem.models.gridColumnInfo;
import com.zhide.dtsystem.models.tbExcelTemplate;
import com.zhide.dtsystem.repositorys.tbExcelTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/excel")
public class ExcelDownloadController {
    @Autowired
    tbExcelTemplateRepository excelRep;
    @RequestMapping("/download")
    public void Download(String data, String columns, String filename, HttpServletResponse response) throws Exception {
        try {
            List<Map<String, Object>> datas = JSON.parseObject(data, new TypeReference<List<Map<String, Object>>>() {
            });
            List<gridColumnInfo> cols = JSON.parseArray(columns, gridColumnInfo.class);
            ExcelBuilder bb = new ExcelBuilder(cols, datas);
            byte[] Bx = bb.create();
            WebFileUtils.download(filename, Bx, response);
        } catch (Exception ax) {
            response.getWriter().write(ax.getMessage());
        }
    }

    @RequestMapping("/download1")
    public void Download1(String data, String filename, String code, HttpServletResponse response,
            HttpServletRequest request) throws Exception {
        try {
            Map<String, Object> O = JSON.parseObject(data, new TypeReference<Map<String, Object>>() {});
            complexExcelBuilder exB=null;
            String numberCell=request.getParameter("numberCell");
            String sheetName=request.getParameter("sheetName");
            String autoCreateNew=request.getParameter("autoCreateNew");
            Optional<tbExcelTemplate> findOnes=excelRep.findFirstByCode(code);
            if(findOnes.isPresent()) {
                tbExcelTemplate one=findOnes.get();
                String X=one.getTemplatePath();
                if(StringUtils.isEmpty(X)==true) {
                    exB = new complexExcelBuilder(code,sheetName);
                } else exB=new complexExcelBuilder(X,sheetName);
            } else {
                exB= new complexExcelBuilder(code,sheetName);
            }
            if(exB!=null){
                exB.setSheetName(sheetName);
                exB.setNumberCell(numberCell);
                exB.setAutoCreateNew(Boolean.parseBoolean(autoCreateNew));
            }
            byte[] Bs = exB.getContent(O);
            WebFileUtils.download(filename, Bs, response);
        } catch (Exception ax) {
            response.getWriter().write(ax.getMessage());
        }
    }


    public static void getFile(byte[] bfile, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists()) {//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + "\\" + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

}