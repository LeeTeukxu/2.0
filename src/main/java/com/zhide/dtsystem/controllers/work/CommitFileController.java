package com.zhide.dtsystem.controllers.work;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.*;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.casesCommitFile;
import com.zhide.dtsystem.models.casesYwAccept;
import com.zhide.dtsystem.models.tbAttachment;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.casesCommitFileRepository;
import com.zhide.dtsystem.repositorys.casesYwAcceptRepository;
import com.zhide.dtsystem.repositorys.tbAttachmentRepository;
import com.zhide.dtsystem.services.define.ICommitFileService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/work/commitFile")
public class CommitFileController {
    @Autowired
    ICommitFileService commitFileService;
    @Autowired
    casesCommitFileRepository commitFileRep;
    @Autowired
    casesYwAcceptRepository casesYwAccRep;

    @RequestMapping("/index")
    public String Index(String CasesID, Map<String, Object> model) {
        model.put("CasesID", CasesID);
        LoginUserInfo Info = CompanyContext.get();
        model.put("UserID", Info.getUserId());
        model.put("items", JSON.toJSONString(getItems(CasesID, Info.getUserIdValue())));
        model.put("AttIDS", StringUtils.join(getSavedItems(CasesID, Info.getUserIdValue()), ","));
        return "/work/case/addFile";
    }

    private List<Map<String, Object>> getItems(String CasesID, Integer UserID) {
        List<casesYwAccept> items = casesYwAccRep.findAllByCasesIdAndTechMan(CasesID, UserID);
        List<Map<String, Object>> res = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            Map<String, Object> item = new HashMap<>();
            casesYwAccept acc = items.get(i);
            item.put("id", acc.getYid());
            item.put("text", acc.getYName());
            res.add(item);
        }
        return res;
    }

    private List<String> getSavedItems(String CasesID, Integer UserID) {
        List<casesCommitFile> items = commitFileRep.findAllByCasesIdAndCreateMan(CasesID, UserID);
        return items.stream().map(f -> f.getAttId()).collect(Collectors.toList());
    }

    @RequestMapping("/add")
    public String Add(String CasesID, Map<String, Object> model) {
        model.put("CasesID", CasesID);
        LoginUserInfo Info = CompanyContext.get();
        Integer UserID = Integer.parseInt(Info.getUserId());
        model.put("UserID", Info.getUserId());
        model.put("RoleName", Info.getRoleName());
        List<casesCommitFile> finds = commitFileRep.findAllByCasesIdAndCreateMan(CasesID, UserID);
        if (finds.size() == 0) {
            model.put("Mode", "Add");
            casesCommitFile newOne = new casesCommitFile();
            newOne.setCreateTime(new Date());
            newOne.setCreateMan(UserID);
            newOne.setCasesId(CasesID);
            model.put("LoadObj", newOne);
        } else {
            casesCommitFile find = finds.get(0);
            Integer State = find.getState();
            if (State == 1) {
                model.put("Mode", "Edit");
            } else model.put("Mode", "Browse");
            model.put("LoadObj", find);
        }
        return "/work/case/commitFile";
    }

    @RequestMapping("/audit")
    public String Audit(String CasesID, int UserID, Map<String, Object> model) {
        model.put("CasesID", CasesID);
        LoginUserInfo Info = CompanyContext.get();
        model.put("UserID", Info.getUserId());
        model.put("RoleName", Info.getRoleName());
        List<casesCommitFile> finds = commitFileRep.findAllByCasesIdAndCreateMan(CasesID, UserID);
        if (finds.size() == 1) {
            casesCommitFile find = finds.get(0);
            Integer State = find.getState();
            if (State == 2) {
                model.put("Mode", "Audit");
                find.setAuditMan(Integer.parseInt(Info.getUserId()));
                find.setAuditTime(new Date());
            } else if (State == 3) model.put("Mode", "Browse");
            model.put("LoadObj", find);
        }
        return "/work/case/commitFile";
    }

    @ResponseBody
    @RequestMapping("/saveAll")
    public successResult save(String Data, String Action) {
        successResult result = new successResult();
        try {
            casesCommitFile Datas = JSON.parseObject(Data, casesCommitFile.class);
            casesCommitFile OX = commitFileService.SaveAll(Datas, Action);
            result.setData(OX);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getData")
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = commitFileService.getData(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public successResult removeAll(String AttID) {
        successResult result = new successResult();
        try {
            boolean OX = commitFileService.RemoveAll(AttID);
            result.setSuccess(OX);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @PostMapping("/commit")
    public successResult Commit(int ID, String Result, String ResultText) {
        successResult result = new successResult();
        try {
            Result = URLDecoder.decode(Result, "utf-8");
            ResultText = URLDecoder.decode(ResultText, "utf-8");
            boolean OX = commitFileService.Commit(ID, Result, ResultText);
            result.setSuccess(OX);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/upload")
    @ResponseBody
    public successResult UploadFiles(MultipartFile file, HttpServletRequest request) {
        successResult result = new successResult();
        try {
            LoginUserInfo Info = CompanyContext.get();
            String CasesID = request.getParameter("CasesID");
            String AttType = request.getParameter("AttType");
            String Memo = request.getParameter("Memo");
            String LastDate = request.getParameter("LastDate");
            SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
            Date L = simple.parse(LastDate);
            successResult rr = upload(file);
            if (rr.isSuccess()) {
                Map<String, Object> res = (Map<String, Object>) rr.getData();
                casesCommitFile commFile = new casesCommitFile();
                commFile.setCasesId(CasesID);
                commFile.setState(1);
                commFile.setType(AttType);
                commFile.setMemo(URLDecoder.decode(Memo, "utf-8"));
                commFile.setLastDate(L);
                commFile.setAttId(res.get("AttID").toString());
                commFile.setCreateTime(new Date());
                commFile.setCreateMan(Info.getUserIdValue());
                commitFileRep.save(commFile);
            }
            result = rr;
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @Autowired
    tbAttachmentRepository attachmentRep;

    private UploadUtils getUtils() throws Exception {
        LoginUserInfo info = CompanyContext.get();
        if (info == null) throw new Exception("登录失败，请重新登录!");
        String companyId = info.getCompanyId();
        return UploadUtils.getInstance(companyId);
    }

    private successResult upload(MultipartFile file) {
        successResult result = new successResult();
        FileInputStream fileInputStream;
        try {
            LoginUserInfo loginInfo = CompanyContext.get();
            String fileName = file.getOriginalFilename();
            String[] exts = fileName.split("\\.");
            String extName = exts[exts.length - 1];
            String uuId = UUID.randomUUID().toString();
            String uploadFileName = uuId + "." + extName;
            String targetFile = CompanyPathUtils.getFullPath("Temp", uploadFileName);
            File fx = new File(targetFile);
            FileUtils.writeByteArrayToFile(fx, file.getBytes());
            if (fx.exists()) {
                try {
                    fileInputStream = new FileInputStream(fx);
                    UploadUtils uploadUtils = getUtils();
                    uploadFileResult rr = uploadUtils.uploadAttachment(uploadFileName, fileInputStream);
                    if (rr.isSuccess() == true) {
                        tbAttachment tb = new tbAttachment();
                        tb.setGuid(uuId);
                        tb.setName(fileName);
                        tb.setPath(rr.getFullPath());
                        tb.setSize(Integer.parseInt(Long.toString(fx.length())));
                        tb.setUploadMan(Integer.parseInt(loginInfo.getUserId()));
                        tb.setUploadManName(loginInfo.getUserName());
                        tb.setUploadTime(new Date());
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Map<String, Object> data = new HashMap<>();
                        data.put("AttID", uuId);
                        data.put("CreateTime", simpleDateFormat.format(new Date()));
                        data.put("CreateMan", loginInfo.getUserName());
                        data.put("Name", fileName);
                        result.setData(data);
                        attachmentRep.save(tb);
                    }
                    fileInputStream.close();
                } catch (Exception ax) {
                    result.raiseException(ax);
                } finally {
                    FileUtils.forceDeleteOnExit(fx);
                }
            } else throw new Exception("保存上传文件失败!");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/getAttachmentByIDS")
    @ResponseBody
    public successResult GetAttachmentByIDS(String IDS) {
        successResult result = new successResult();
        try {
            List<Map<String, Object>> rr = new ArrayList<>();
            if (StringUtils.isEmpty(IDS) == false) {
                String[] IDX = IDS.split(",");
                SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                List<tbAttachment> atts = attachmentRep.findAllByGuidInOrderByUploadTime(Arrays.asList(IDX));
                for (int i = 0; i < atts.size(); i++) {
                    tbAttachment att = atts.get(i);
                    Map<String, Object> r = new HashMap<>();
                    r.put("ATTID", att.getGuid());
                    r.put("FILENAME", att.getName());
                    r.put("FILESIZE", att.getSize());
                    r.put("UPLOADTIME", simple.format(att.getUploadTime()));
                    r.put("UPLOADMAN", att.getUploadManName());
                    Optional<casesCommitFile> findOnes = commitFileRep.findFirstByAttId(atts.get(i).getGuid());
                    if (findOnes.isPresent()) {
                        casesCommitFile cFile = findOnes.get();
                        r.put("ATTTYPE", cFile.getType());
                        r.put("STATE", cFile.getState());
                        r.put("MEMO", cFile.getMemo());
                        r.put("LASTDATE", simple.format(cFile.getLastDate()));
                        rr.add(r);
                    }
                }
                result.setData(rr);
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
