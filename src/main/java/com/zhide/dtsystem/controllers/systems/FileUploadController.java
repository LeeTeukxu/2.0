package com.zhide.dtsystem.controllers.systems;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.*;
import com.zhide.dtsystem.mapper.FileUploadMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.sqlParameter;
import com.zhide.dtsystem.models.tbAttachment;
import com.zhide.dtsystem.models.tbFileUpload;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.tbAttachmentRepository;
import com.zhide.dtsystem.repositorys.tbFileUploadRepository;
import com.zhide.dtsystem.services.define.IFileUploadService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;

/**
 * @ClassName: FileUploadController
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年11月15日 10:50
 **/
@Controller
@RequestMapping("/systems/fileUpload")
public class FileUploadController {
    @Autowired
    tbAttachmentRepository attRep;
    @Autowired
    tbFileUploadRepository uploadRep;
    @Autowired
    FileUploadMapper fileMapper;
    @Autowired
    IFileUploadService fileService;

    @RequestMapping("/index")
    public String Index() {
        return "/systems/fileUpload/index";
    }

    @RequestMapping("/addFile")
    public String uploadPage(Integer Type, Map<String, Object> model) {
        model.put("Type", Type);
        return "/systems/fileUpload/addTypeFile";
    }

    private UploadUtils getUtils() throws Exception {
        LoginUserInfo info = CompanyContext.get();
        if (info == null) throw new Exception("登录失败，请重新登录!");
        String companyId = info.getCompanyId();
        return UploadUtils.getInstance(companyId);
    }

    @RequestMapping("/doUpload")
    @ResponseBody
    public successResult UploadFile(Integer Type, MultipartFile file) {
        successResult result = new successResult();
        FileInputStream inputStream = null;
        String simpleName = "";
        uploadFileResult res = null;
        try {
            LoginUserInfo Info = CompanyContext.get();
            simpleName = file.getOriginalFilename();
            String ExtName = "." + FilenameUtils.getExtension(simpleName).toUpperCase();
            String FileID = UUID.randomUUID().toString();
            String targetFilePath = CompanyPathUtils.getFullPath("Temp", FileID + ExtName);
            File targetFile = new File(targetFilePath);
            FileUtils.writeByteArrayToFile(targetFile, file.getBytes());
            if (targetFile.exists()) {
                inputStream = new FileInputStream(targetFile);
                UploadUtils uploadUtils = getUtils();
                res = uploadUtils.uploadAttachment(FileID + ExtName, inputStream);
                if (res.isSuccess()) {
                    if (inputStream != null) {
                        inputStream.close();
                        inputStream = null;
                    }
                    tbAttachment file1 = SaveNormalFile(Type, simpleName, res.getFullPath(), FileID, targetFilePath);
                    result.setData(file1);
                } else throw new Exception("上传" + simpleName + "失败，请稍候重试!");
            } else throw new Exception("接收" + simpleName + "失败，请稍候重试!");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    private tbAttachment SaveNormalFile(Integer Type, String simpleName, String ftpPath, String FileID,
            String targetFilePath) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        File targetFile = new File(targetFilePath);
        String ExtName = "." + FilenameUtils.getExtension(simpleName).toUpperCase();
        tbAttachment file1 = new tbAttachment();
        file1.setGuid(FileID);
        file1.setPath(ftpPath);
        file1.setSize(Math.toIntExact(FileUtils.sizeOf(targetFile)));
        file1.setType(1);
        file1.setName(simpleName);
        file1.setUploadMan(Info.getUserIdValue());
        file1.setUploadManName(Info.getUserName());
        file1.setUploadTime(new Date());
        attRep.save(file1);

        tbFileUpload file2 = new tbFileUpload();
        file2.setTypeId(Type);
        file2.setAttId(FileID);
        file2.setUploadMan(Info.getUserIdValue());
        file2.setUploadTime(new Date());
        uploadRep.save(file2);
        try {
            FileUtils.forceDelete(targetFile);
        } catch (Exception ax) {
            ax.printStackTrace();
        }
        return file1;
    }

    @RequestMapping("/getData")
    @ResponseBody
    public pageObject getData(String Type, HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            Map<String,Object> params=getParameters(request);
            List<Map<String,Object>>Datas= fileMapper.getData(params);
            Integer Total=0;
            if(Datas.size()>0){
                Total=Integer.parseInt(Datas.get(0).get("_TotalNum").toString());
            }
            result.setTotal(Total);
            result.setData(Datas);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    private Map<String, Object> getParameters(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "SN";
        String key = request.getParameter("key");
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageIndex * pageSize + 1);
        params.put("End", (pageIndex + 1) * pageSize);
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        String Type = request.getParameter("Type");
        if (Strings.isEmpty(Type) == false) params.put("Type", Type);

        String queryText = request.getParameter("Query");
        if (Strings.isNotEmpty(queryText)) {
            queryText = URLDecoder.decode(queryText, "utf-8");
            List<sqlParameter> Vs = JSON.parseArray(queryText, sqlParameter.class);
            List<sqlParameter> OrItems = sqlParameterCreator.convert(Vs);
            params.put("orItems", OrItems);
        } else params.put("orItems", new ArrayList<>());
        String highText = request.getParameter("High");
        if (Strings.isNotEmpty(highText)) {
            highText = URLDecoder.decode(highText);
            List<sqlParameter> Ps = JSON.parseArray(highText, sqlParameter.class);
            List<sqlParameter> AndItems = sqlParameterCreator.convert(Ps);
            params.put("andItems", AndItems);
        } else params.put("andItems", new ArrayList<>());

        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            params.put("DepID", Info.getDepId());
            params.put("RoleName", Info.getRoleName());
            params.put("UserID", Info.getUserId());
            params.put("UserName", Info.getUserName());
            params.put("RoleID", Info.getRoleId());
        } else throw new RuntimeException("登录信息失效，请重新登录！");
        return params;
    }
    @RequestMapping("/save")
    @ResponseBody
    public successResult Save(String Data){
        successResult result=new successResult();
        try {
            tbFileUpload file=JSON.parseObject(Data,tbFileUpload.class);
            fileService.SaveAll(Arrays.asList(file));
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
    @RequestMapping("/remove")
    @ResponseBody
    public successResult Remove(String IDS){
        successResult result=new successResult();
        try {
            List<Integer> IDD=IntegerUtils.parseIntArray(IDS);
            fileService.DeleteAll(IDD);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
    @RequestMapping("/download")
    public void Download(HttpServletResponse response, String Code, String FileName) {
        String[] Codes = Code.split(",");
        File targetFile = null;
        try {
            if(Codes.length>1){
                targetFile = fileService.download(Codes,true);
            } else targetFile = fileService.download(Codes,false);
            WebFileUtils.download(FileName, targetFile, response);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.getWriter().write(e.getMessage());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (targetFile != null) {
                    FileUtils.forceDelete(targetFile);
                }
            } catch (Exception ax) {
                ax.printStackTrace();
            }
        }
    }
}

