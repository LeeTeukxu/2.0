package com.zhide.dtsystem.controllers.cpc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhide.dtsystem.common.*;
import com.zhide.dtsystem.mapper.CPCPackageMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.ICPCFileService;
import com.zhide.dtsystem.services.define.ICPCPackageService;
import com.zhide.dtsystem.services.instance.cpcPackage.cpcPackage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: CPCPackageController
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年08月09日 10:14
 **/
@RequestMapping("/cpc")
@Controller
public class CPCPackageController {
    @Autowired
    ICPCPackageService cpcService;
    @Autowired
    cpcPackageMainRepository mainRep;
    @Autowired
    tbAgentsRepository agentRep;
    @Autowired
    cpcApplyManRepository applyManRep;
    @Autowired
    cpcFilesRepository cpcAttRep;
    @Autowired
    CPCPackageMapper cpcMapper;
    @Autowired
    cpcPackage cpcPackage;
    @Autowired
    tbAttachmentRepository attRep;
    @Autowired
    ICPCFileService icpcFileService;
    @Autowired
    tbCompanyRepository companyRep;

    @RequestMapping("/index")
    public String Index() {
        return "/work/cpc/index";
    }

    @RequestMapping("/add")
    public String add(Integer CreateType, Map<String, Object> model) {
        model.put("Data", "{}");
        model.put("Mode", "Add");
        model.put("CreateType", CreateType);
        model.put("Company", JSON.toJSONString(companyRep.findAll().stream().findFirst().get()));
        return "/work/cpc/add";
    }

    @RequestMapping("/viewPackage")
    public String viewPackage(String MainID, Map<String, Object> model) {
        model.put("MainID", MainID);
        return "/work/cpc/viewPackageFile";
    }

    @RequestMapping("/getFileContent")
    public void getFileContent(String MainID, String FileName, HttpServletResponse response) {
        try {
            byte[] BB = icpcFileService.getFileContent(MainID, FileName);
            String ExtName = FilenameUtils.getExtension(FileName).toUpperCase();
            switch (ExtName) {
                case "XML": {
                    // response.setContentType("text/xml;charset=utf-8");
                    String X = "<html><xmp>" + new String(BB, Charset.forName("utf-8")) + "</xmp></html>";
                    BB = X.getBytes(StandardCharsets.UTF_8);
                    break;
                }
                case "PDF":
                case "DOC": {
                    response.setContentType("application/pdf;charset=utf-8");
                    break;
                }
                case "JPG":
                case "PNG":
                case "BMP": {
                    String X = Base64Utils.decode(BB);
                    String Ht = "<html> <img src='data:image/png;base64," + X + "' " + "></html>";
                    BB = Ht.getBytes(StandardCharsets.UTF_8);
                    break;
                }
            }
            response.getOutputStream().write(BB);
            response.getOutputStream().flush();
        } catch (Exception e) {
            // e.printStackTrace();
            try {
                response.getWriter().write("下载文件出错:" + e.getMessage());
            } catch (IOException ioException) {
                // ioException.printStackTrace();
            }
        }
    }

    @RequestMapping("/getFileTree")
    @ResponseBody
    public List<TreeNode> getFileTree(String MainID) {
        List<TreeNode> Nodes = null;
        try {
            Nodes = icpcFileService.GetFileTree(MainID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Nodes;

    }

    @RequestMapping("/getCompanyCount")
    @ResponseBody
    public int GetCompanyCount() {
        return companyRep.findAll().size();
    }

    @RequestMapping("/edit")
    public String Edit(String PID, Map<String, Object> model) throws Exception {
        Optional<cpcPackageMain> findMains = mainRep.findFirstByPid(PID);
        if (findMains.isPresent()) {
            cpcPackageMain main = findMains.get();
            String X = JSON.toJSONString(main);
            model.put("Data", X);
            Integer createType = main.getCreateType();
            if (createType == null) {
                String SubID = main.getSubId();
                if (StringUtils.isEmpty(SubID)) createType = 2;
                else createType = 1;
            }
            model.put("CreateType", createType);
        } else throw new Exception(PID + "指向的案卷包不存在!");
        model.put("Mode", "Edit");
        model.put("Company", JSON.toJSONString(companyRep.findAll().stream().findFirst().get()));
        return "/work/cpc/add";
    }

    @RequestMapping("/browse")
    public String Browse(String PID, Map<String, Object> model) throws Exception {
        Optional<cpcPackageMain> findMains = mainRep.findFirstByPid(PID);
        if (findMains.isPresent()) {
            cpcPackageMain main = findMains.get();
            String X = JSON.toJSONString(main);
            model.put("Data", X);
            Integer createType = main.getCreateType();
            if (createType == null) {
                String SubID = main.getSubId();
                if (StringUtils.isEmpty(SubID)) createType = 2;
                else createType = 1;
            }
            model.put("CreateType", createType);
        } else throw new Exception(PID + "指向的案卷包不存在!");
        model.put("Mode", "Browse");
        model.put("Company", JSON.toJSONString(companyRep.findAll().stream().findFirst().get()));
        return "/work/cpc/add";
    }

    @RequestMapping("/addCPCFile")
    public String AddCPCFile(String MainID, String FileType, Map<String, Object> model) {
        model.put("MainID", MainID);
        model.put("FileType", FileType);
        return "/work/cpc/addCPCFile";
    }

    @RequestMapping("/importOne")
    @ResponseBody
    public successResult importOne(String SubID, String FileName) {
        successResult result = new successResult(false);
        try {
            cpcPackageMain main = cpcService.createOne(SubID, FileName);
            result.setData(main);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getTargetFile")
    public successResult getTargetFile(String SubID) {
        successResult result = new successResult(false);
        try {
            List<String> names = cpcService.testAcceptFile(SubID);
            result.setData(names);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/getData")
    @ResponseBody
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = cpcService.getData(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/getCPCFiles")
    @ResponseBody
    public pageObject getCPCFiles(String MainID) {
        pageObject result = new pageObject();
        try {
            result = cpcService.getCPCFiles(MainID);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/getInventors")
    @ResponseBody
    public pageObject getInventors(String MainID) {
        pageObject result = new pageObject();
        try {
            result = cpcService.getInventors(MainID);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/getApplyMan")
    @ResponseBody
    public pageObject getApplyMan(String MainID) {
        pageObject result = new pageObject();
        try {
            result = cpcService.getApplyMan(MainID);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getSingleApplyMan")
    public successResult getSingleApplyMan(String SubID) {
        successResult result = new successResult();
        try {
            Optional<cpcApplyMan> findMans = applyManRep.findFirstBySubId(SubID);
            if (findMans.isPresent()) {
                result.setData(findMans.get());
            } else throw new Exception(SubID + "指向的业务已不存在!");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/getAgent")
    @ResponseBody
    public pageObject getAgent(String MainID) {
        pageObject result = new pageObject();
        try {
            result = cpcService.getAgent(MainID);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/saveApplyMan")
    public successResult SaveApplyMan(String Data) {
        successResult result = new successResult();
        try {
            cpcApplyMan man = JSON.parseObject(Data, cpcApplyMan.class);
            cpcService.SaveApply(man);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/removeOne")
    @ResponseBody
    public successResult RemoveOne(String PID) {
        successResult result = new successResult();
        try {
            cpcService.removeOne(PID);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/saveAll")
    public successResult SaveAll(String Data) {
        successResult result = new successResult();
        try {
            TypeReference<Map<String, Object>> OO = new TypeReference<Map<String, Object>>() {
            };
            Map<String, Object> OX = JSON.parseObject(Data, OO);
            result.setData(cpcService.SaveAll(OX));
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/saveAllData")
    public successResult SaveAllData(String Data) {
        successResult result = new successResult();
        try {
            TypeReference<Map<String, Object>> OO = new TypeReference<Map<String, Object>>() {
            };
            Map<String, Object> OX = JSON.parseObject(Data, OO);
            result.setData(cpcService.SaveAllData(OX));
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getAgentCompany")
    public List<ComboboxItem> getAgentCompany() {
        List<ComboboxItem> items = new ArrayList<>();
        List<tbCompany> allItem = companyRep.findAll();
        for (tbCompany company : allItem) {
            ComboboxItem item = new ComboboxItem();
            item.setText(company.getCompanyName());
            item.setId(company.getCompanyName());
            item.setCode(company.getOrgCode());
            items.add(item);
        }
        return items;
    }

    @ResponseBody
    @RequestMapping("/getAgents")
    public List<ComboboxItem> getAgents(String CompanyName) {
        List<ComboboxItem> items = new ArrayList<>();
        if (StringUtils.isEmpty(CompanyName) == false) {
            List<tbAgents> allItem = agentRep.findAllByCompanyName(CompanyName);
            for (tbAgents agent : allItem) {
                ComboboxItem item = new ComboboxItem();
                item.setText(agent.getName());
                item.setId(agent.getName());
                item.setName(agent.getPhone());
                item.setCode(agent.getCode());
                items.add(item);
            }
        }
        return items;
    }

    private UploadUtils getUtils() throws Exception {
        LoginUserInfo info = CompanyContext.get();
        if (info == null) throw new Exception("登录失败，请重新登录!");
        String companyId = info.getCompanyId();
        return UploadUtils.getInstance(companyId);
    }

    @RequestMapping("/uploadCPCFile")
    @ResponseBody
    public successResult UploadCPCFile(String MainID, String Type, String Code, MultipartFile file) {
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
                if (Type.equals("专利申报五书稿件") == false) {
                    inputStream = new FileInputStream(targetFile);
                    UploadUtils uploadUtils = getUtils();
                    res = uploadUtils.uploadAttachment(FileID + ExtName, inputStream);
                    if (res.isSuccess()) {
                        if (inputStream != null) {
                            inputStream.close();
                            inputStream = null;
                        }
                        tbAttachment file1 = SaveNormalFile(MainID, Type, Code, simpleName, res.getFullPath(), FileID,
                                targetFilePath);
                        result.setData(file1);
                    } else throw new Exception("上传" + simpleName + "失败，请稍候重试!");
                } else {
                    tbAttachment tbb = icpcFileService.uploadFiveFile(MainID, targetFile, simpleName);
                    result.setData(tbb);
                }
            } else throw new Exception("接收" + simpleName + "失败，请稍候重试!");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    /**
     * create by: mmzs
     * description: TODO
     * create time:
     * <p>
     * 处理上传的5书文件和图片文件。
     *
     * @return
     */
    private tbAttachment SaveNormalFile(String MainID, String Type, String Code, String simpleName, String ftpPath,
            String FileID, String targetFilePath) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        File targetFile = new File(targetFilePath);
        String ExtName = "." + FilenameUtils.getExtension(simpleName).toUpperCase();
        tbAttachment file1 = new tbAttachment();
        file1.setGuid(FileID);
        file1.setPath(ftpPath);
        file1.setSize(Math.toIntExact(FileUtils.sizeOf(targetFile)));
        file1.setType(1);
        if (Code.equals("10000701")) {
            SimpleDateFormat simple = new SimpleDateFormat("yyyyMMddHHmmss");
            file1.setName(simple.format(new Date()) + ExtName);
        } else file1.setName(Code + ExtName);

        file1.setUploadMan(Info.getUserIdValue());
        file1.setUploadManName(Info.getUserName());
        file1.setUploadTime(new Date());
        attRep.save(file1);

        cpcFiles file2 = new cpcFiles();
        file2.setMainId(MainID);
        file2.setSubId(UUID.randomUUID().toString());
        file2.setCode(Code);
        file2.setType(Type);
        file2.setAttId(FileID);
        file2.setExtName("." + FilenameUtils.getExtension(targetFilePath).toUpperCase());
        file2.setPages(PageSizeUtils.get(targetFilePath));
        cpcAttRep.save(file2);

        if (Code.equals("100001") && ExtName.equals("PDF")) {
            Optional<cpcPackageMain> findMains = mainRep.findFirstByPid(MainID);
            if (findMains.isPresent()) {
                cpcPackageMain main = findMains.get();
                main.setItemCount(PageSizeUtils.getSectionCount(targetFilePath));
                mainRep.save(main);
            }
        }
        try {
            FileUtils.forceDelete(targetFile);
        } catch (Exception ax) {
            ax.printStackTrace();
        }
        return file1;
    }

    @ResponseBody
    @RequestMapping("/deleteCPCFile")
    public successResult DeleteCPCFile(String SubID) {
        successResult result = new successResult();
        try {
            cpcService.DeleteCPCFile(SubID);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getCountry")
    public List<ComboboxItem> getCountry() {
        return cpcMapper.getCountry();
    }

    @ResponseBody
    @RequestMapping("/getXZQH")
    public List<TreeNode> getXZQH() {
        return cpcMapper.getXZQ();
    }

    @RequestMapping("/buildPackage")
    @ResponseBody
    public successResult BuildPackage(String MainID) {
        successResult result = new successResult();
        try {
            cpcPackage.createOne(MainID);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/removeInventor")
    public successResult removeInventor(Integer ID) {
        successResult result = new successResult();
        try {
            cpcService.removeInventor(ID);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/removeApplyMan")
    public successResult removeApplyMan(Integer ID) {
        successResult result = new successResult();
        try {
            cpcService.removeApplyMan(ID);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/removeAgent")
    public successResult removeAgent(Integer ID) {
        successResult result = new successResult();
        try {
            cpcService.removeAgent(ID);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/saveInventor")
    @ResponseBody
    public successResult saveInventor(String MainID, String Data) {
        successResult result = new successResult();
        try {
            List<cpcInventor> invs = JSON.parseArray(Data, cpcInventor.class);
            cpcService.SaveInventor(MainID, invs);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/saveAgent")
    @ResponseBody
    public successResult saveAgent(String MainID, String Data) {
        successResult result = new successResult();
        try {
            List<cpcAgent> agents = JSON.parseArray(Data, cpcAgent.class);
            cpcService.SaveAgents(MainID, agents);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/downloadCPC")
    public void DownloadPackage(String ID, HttpServletResponse response) {
        try {
            Optional<cpcPackageMain> findMains = mainRep.findFirstByPid(ID);
            if (findMains.isPresent()) {
                cpcPackageMain main = findMains.get();
                String FilePath = main.getPackageFilePath();
                File targetFile = new File(FilePath);
                if (targetFile.exists()) {
                    WebFileUtils.download(main.getFamingmc() + "案卷文件.zip", targetFile, response);
                } else throw new Exception(main.getFamingmc() + "的案卷包文件已丢失，请重新生成。");
            } else throw new Exception("指定的文件不存在!");
        } catch (Exception ax) {
            ax.printStackTrace();
            try {
                response.getWriter().write(ax.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("/batchDownload")
    public void BatchDownload(String IDS, HttpServletResponse response) {
        List<String> IDArray = ListUtils.parse(IDS, String.class);
        File NewDir = new File(cpcPackage.getRootPath() + UUID.randomUUID().toString());
        String ZipFilePath=cpcPackage.getRootPath()+UUID.randomUUID().toString()+".zip";
        try {
            int Num = 0;
            for (int i = 0; i < IDArray.size(); i++) {
                String ID = IDArray.get(i);
                Optional<cpcPackageMain> findMains = mainRep.findFirstByPid(ID);
                if (findMains.isPresent()) {
                    cpcPackageMain main = findMains.get();
                    String FilePath = main.getPackageFilePath();
                    File targetFile = new File(FilePath);
                    if (targetFile.exists() == false) {
                        String K = cpcPackage.createOne(ID);
                        targetFile = new File(K);
                    }
                    if (targetFile.exists() == false) continue;
                    FileUtils.copyFileToDirectory(targetFile, NewDir);
                    Num++;
                }
            }
            if(Num>0){
                ZipFileUtils.zip(NewDir.getPath(),ZipFilePath);
                FileUtils.deleteDirectory(NewDir);
                WebFileUtils.download("案卷文件打包下载.zip", new File(ZipFilePath),response);
            }
        } catch (Exception ax) {
            try {
                response.getWriter().write(ax.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @ResponseBody
    @RequestMapping("/getImages")
    public Map<String, Object> getAllImages(String AttID) {
        Map<String, Object> res = new HashMap<>();
        try {
            List<Map<String, Object>> OO = new ArrayList<>();
            List<String> Files = icpcFileService.ViewCPCFile(AttID);
            if (Files.size() > 0) {
                res.put("status", 1);
                res.put("start", 0);
                for (int n = 0; n < Files.size(); n++) {
                    String Src = Files.get(n);
                    Map<String, Object> OX = new HashMap<>();
                    OX.put("src", Src);
                    OX.put("thumb", "");
                    OX.put("alt", "第" + (n + 1) + "个文件");
                    OO.add(OX);
                }
            }
            res.put("data", OO);
            if (OO.size() == 0) throw new Exception("没有可查看的通知书附件。");
        } catch (Exception ax) {
            res.put("status", 0);
            res.put("message", ax.getMessage());
        }
        return res;
    }

    @ResponseBody
    @RequestMapping("/checkAll")
    public successResult checkAll(String MainID) {
        successResult result = new successResult();
        try {
            cpcService.CheckAll(MainID);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getCompanyInfo")
    public List<Map<String, Object>> getCompanyInfo() {
        return cpcMapper.getCompanyInfo();
    }

    @RequestMapping("/getCompany")
    @ResponseBody
    public List<ComboboxItem> getCompany() {
        List<ComboboxItem> items = new ArrayList<>();
        try {
            List<tbCompany> coms = companyRep.findAll();
            for (tbCompany com : coms) {
                ComboboxItem item = new ComboboxItem();
                item.setId(com.getCompanyName());
                item.setText(com.getCompanyName());
                items.add(item);
            }
        } catch (Exception ax) {

        }
        return items;
    }

    @ResponseBody
    @RequestMapping("/getAgentInfo")
    public List<Map<String, Object>> getAgentInfo() {
        return cpcMapper.getAgentInfo();
    }

    @ResponseBody
    @RequestMapping("/saveCompanyInfo")
    public successResult saveCompanyInfo(String Data) {
        successResult result = new successResult();
        try {
            List<tbCompany> rows = JSON.parseArray(Data, tbCompany.class);
            cpcService.saveCompanyInfo(rows);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    /**
     * create by: mmzs
     * description: TODO
     * create time:
     * <p>
     * 保存代理个人信息资料。
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveAgentInfo")
    public successResult saveAgentInfo(String Data) {
        successResult result = new successResult();
        try {
            List<tbAgents> rows = JSON.parseArray(Data, tbAgents.class);
            cpcService.saveAgentInfo(rows);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/removeAgentInfo")
    public successResult removeAgentInfo(Integer ID) {
        successResult result = new successResult();
        try {
            cpcService.removeAgentInfo(ID);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/removeCompanyInfo")
    public successResult removeCompanyInfo(Integer ID) {
        successResult result = new successResult();
        try {
            cpcService.removeCompanyInfo(ID);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/copyDocument")
    public successResult copyDocument(String MainID, String Famingmc, Integer Shenqinglx) {
        successResult result = new successResult();
        try {
            icpcFileService.CopyDocument(MainID, Famingmc, Shenqinglx);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
