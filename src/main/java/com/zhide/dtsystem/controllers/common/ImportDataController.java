package com.zhide.dtsystem.controllers.common;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.CompanyPathUtils;
import com.zhide.dtsystem.common.ExcelTemplateReader;
import com.zhide.dtsystem.common.WebFileUtils;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.gridColumnInfo;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/common/importData")
public class ImportDataController {
    @RequestMapping("/index")
    public String Index(String code, String fileName, Map<String, Object> model) {
        try {
            ExcelTemplateReader reader = new ExcelTemplateReader(code);
            List<gridColumnInfo> cols = reader.getColumns();
            model.put("ColsText", URLEncoder.encode(JSON.toJSONString(cols), "utf-8"));
            model.put("Cols", cols);
            model.put("code", code);
            model.put("fileName", fileName);
        } catch (Exception ax) {
            ax.printStackTrace();
        }
        return "/common/ImportData";
    }

    @RequestMapping("/download")
    public void Download(String fileName, String code, HttpServletResponse response) {
        try {
            File fx = ResourceUtils.getFile("classpath:static/template/" + code + ".xls");
            WebFileUtils.download(fileName, fx, response);
        } catch (Exception ax) {
            try {
                response.getWriter().write("<script type='text/javascript'>alert('下载文件失败：" + ax.getMessage() + "');" +
                        "</script>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("/upload")
    @ResponseBody
    public successResult upload(String Cols, MultipartFile file) {
        successResult result = new successResult();
        File fullFile = null;
        try {
            List<gridColumnInfo> cols = JSON.parseArray(Cols, gridColumnInfo.class);
            String simpleName = file.getOriginalFilename();
            String fullName = CompanyPathUtils.getFullPath("Temp", simpleName);
            fullFile = new File(fullName);
            org.apache.commons.io.FileUtils.writeByteArrayToFile(fullFile, file.getBytes());
            ExcelTemplateReader reader = new ExcelTemplateReader(fullFile);
            List<Map<String, Object>> Datas = reader.getData(cols);
            result.setData(Datas);
        } catch (Exception ax) {
            ax.printStackTrace();
            result.raiseException(ax);
        } finally {
            if (fullFile != null) FileUtils.deleteQuietly(fullFile);
        }
        return result;
    }
}
