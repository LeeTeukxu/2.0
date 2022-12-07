package com.zhide.dtsystem.controllers.work;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.zhide.dtsystem.common.*;
import com.zhide.dtsystem.mapper.ClientInfoMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.tbClientRepository;
import com.zhide.dtsystem.repositorys.tbExcelTemplateRepository;
import com.zhide.dtsystem.services.define.IClientInfoService;
import com.zhide.dtsystem.services.emailTemplateParsor;
import com.zhide.dtsystem.services.implement.SendEmailService;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/work/clientInfo")
public class ClientInfoController {

    Logger logger = LoggerFactory.getLogger(ClientInfoController.class);
    @Autowired
    IClientInfoService clientInfoService;
    @Autowired
    tbClientRepository clientRepository;
    @Autowired
    emailTemplateParsor emailTemplateParsor;
    @Autowired
    SendEmailService sendEmailService;
    @Autowired
    ClientInfoMapper clientInfoMapper;
    @Autowired
    StringRedisTemplate redisUtil;
    @Autowired
    tbExcelTemplateRepository excelRep;
    @RequestMapping("/query")
    public String Query(boolean multiselect, Map<String, Object> model) {
        model.put("multiselect", multiselect ? "true" : "false");
        return "/work/clientInfo/query";
    }

    @RequestMapping("/queryLinkMan")
    public String QueryLinkMan(boolean multiselect, Map<String, Object> model) {
        model.put("multiselect", multiselect ? "true" : "false");
        return "/work/clientInfo/queryLinkMan";
    }

    @RequestMapping("/index")
    public String Index(String Type, Map<String, Object> model) {
        LoginUserInfo Log=CompanyContext.get();
        model.put("Mode", "Add");
        model.put("Type", Type);
        model.put("RoleName",Log.getRoleName());
        return "/work/client/index";
    }

    @RequestMapping("/edit")
    public String Edit(int ClientID, String Type, Map<String, Object> model) {
        model.put("Type", Type);
        model.put("ClientID", Integer.toString(ClientID));
        model.put("Mode", "Edit");
        model.put("AllClientInfo", "{}");
        model.put("SignMan", "");
        model.put("SignManID", "");
        model.put("SignDate", "");
        model.put("Role","Admin");
        Optional<tbClient> findtb = clientRepository.findById(ClientID);
        if (findtb.isPresent()) {
            tbClient client=findtb.get();
            model.put("LoadData", JSON.toJSONString(client));
        } else model.put("LoadData", "{}");
        return "/work/client/edit";
    }

    @RequestMapping("/add")
    public String Add(String Type, Map<String, Object> model) {
        LoginUserInfo loginUserInfo = CompanyContext.get();
        Date dt = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        model.put("Mode", "Add");
        model.put("Type", Type);
        model.put("ClientID", 0);
        model.put("LoadData", "{}");
        model.put("Role","Admin");
        model.put("SignMan", loginUserInfo.getUserName());
        model.put("SignManID", loginUserInfo.getUserId());
        model.put("SignDate", simpleDateFormat.format(dt));
//        List<Map<String, String>> ftb = clientInfoMapper.getAllClient();
        model.put("AllClientInfo", JSON.toJSONString(new ArrayList<>()));
        return "/work/client/edit";
    }

    @RequestMapping("/browse")
    public String Browse(int ClientID, String Role,String Type, Map<String, Object> model) {
        LoginUserInfo loginUserInfo = CompanyContext.get();
        Date dt = new Date();
        if(StringUtils.isEmpty(Role))Role="Admin";
        model.put("Role",Role);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        model.put("Type", Type);
        model.put("ClientID", Integer.toString(ClientID));
        model.put("Mode", "look");
        model.put("AllClientInfo", "{}");

        model.put("SignMan", loginUserInfo.getUserName());
        model.put("SignManID", loginUserInfo.getUserId());
        model.put("SignDate", simpleDateFormat.format(dt));
        Optional<tbClient> findtb = clientRepository.findById(ClientID);
        if (findtb.isPresent()) {
            tbClient client=findtb.get();
            model.put("LoadData", JSON.toJSONString(client));
        } else model.put("LoadData", "{}");
        return "/work/client/edit";
    }

    @RequestMapping("/getPageData")
    @ResponseBody
    public pageObject getPageData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = clientInfoService.getPageData(request);
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
            result = clientInfoService.getData(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getAllLinksClient")
    public List<Map<String, Object>> getAllClientInfoExistLinkMan(HttpServletRequest request) {
        List<Map<String, Object>> rows = new ArrayList<>();
        try {
            rows = clientInfoService.getAllClientInfoExistLinkMan(request);
        } catch (Exception ax) {
            ax.printStackTrace();
        }
        return rows;
    }

    @RequestMapping("/getJSR")
    @ResponseBody
    public List<Map<String, Object>> getJSR(int ClientID) {
        LoginUserInfo Info=CompanyContext.get();
        return clientInfoService.getJSR(ClientID,Info.getUserIdValue(),Info.getDepIdValue(),Info.getRoleName());
    }

    @RequestMapping("/save")
    @ResponseBody
    public successResult save(HttpServletRequest request) {
        successResult result = new successResult();
        tbClient client = null;
        tbClientLinkers clientLinkers = null;
        ClientAndClientLinkers clientAndClientLinkers = null;
        try {
            String clients = request.getParameter("client");
            String clinetLinkers = request.getParameter("clientLinkers");
            String mode = request.getParameter("mode");
            if (Strings.isEmpty(clients) == false) {
                client = JSON.parseObject(clients, tbClient.class);
                clientLinkers = JSON.parseObject(clinetLinkers, tbClientLinkers.class);
                clientAndClientLinkers = clientInfoService.Save(client, clientLinkers, mode);
                result.setData(clientAndClientLinkers);

            } else throw new Exception("数据格式不正确！");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/saveFollowRecord")
    @ResponseBody
    public successResult saveFollowRecord(HttpServletRequest request) throws Exception {
        successResult result = new successResult();
        try {
            String Data = request.getParameter("Data");
            followRecord followRecord = JSON.parseObject(Data, followRecord.class);
            result.setData(clientInfoService.SaveFollowRecord(followRecord));
        } catch (Exception ax) {
            logger.info("保存跟进人记录出错:" + ax.getMessage());
            result.raiseException(ax);
            throw ax;
        }
        return result;
    }

    @RequestMapping("/getFollowRecords")
    @ResponseBody
    public pageObject getFollowRecords(Integer ClientID, int pageIndex, int pageSize) {
        pageObject result = new pageObject();
        try {
            if (ClientID == null) ClientID = 0;
            if (ClientID > 0) {
                Page<followRecord> recordPage = clientInfoService.getFollowRecords(ClientID, pageIndex, pageSize);
                result.setData(recordPage.getContent());
                String x = Long.toString(recordPage.getTotalElements());
                result.setTotal(Integer.parseInt(x));
            }
        } catch (Exception ax) {
            logger.info("获取客户" + Integer.toString(ClientID) + ",跟进记录出错:" + ax.getMessage());
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/saveClientLinkers")
    public successResult SaveLinkers(HttpServletRequest request) {
        successResult result = new successResult();
        try {
            String Data = request.getParameter("Data");
            tbClientLinkers clientLinkers = JSON.parseObject(Data, tbClientLinkers.class);
            result.setData(clientInfoService.SaveLinkers(clientLinkers));
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getClientLinkers")
    public pageObject getClientLinkers(int ClientID, int pageIndex, int pageSize) {
        pageObject result = new pageObject();
        try {
            Page<tbClientLinkers> recordPage = clientInfoService.getLinkers(ClientID, pageIndex, pageSize);
            result.setData(recordPage.getContent());
            String x = Long.toString(recordPage.getTotalElements());
            result.setTotal(Integer.parseInt(x));
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getClientLinkersUpdateRecord")
    public pageObject getClientLinkersUpdateRecord(int ClientID, int pageIndex, int pageSize) {
        pageObject result = new pageObject();
        try {
            Page<tbLinkersUpdateRecord> recordPage = clientInfoService.getLinkersUpdateRecord(ClientID, pageIndex,
                    pageSize);
            result.setData(recordPage.getContent());
            String x = Long.toString(recordPage.getTotalElements());
            result.setTotal(Integer.parseInt(x));
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/GetLoginClientReords")
    public pageObject GetLoginClientReords(int ClientID, HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = clientInfoService.GetLoginClientReords(ClientID, request);
        } catch (Exception ax) {
            logger.info(ax.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/GetDLF")
    public pageObject GetDLF(int ClientID, int SignMan, HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = clientInfoService.getDLF(ClientID, SignMan, request);
        } catch (Exception ax) {
            logger.info(ax.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/GetGF")
    public pageObject GetGF(int ClientID, int SignMan, HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = clientInfoService.getGF(ClientID, SignMan, request);
        } catch (Exception ax) {
            logger.info(ax.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/GetInvoice")
    public pageObject GetInvoice(int ClientID, HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = clientInfoService.getInvoice(ClientID, request);
        } catch (Exception ax) {
            logger.info(ax.getMessage());
        }
        return result;
    }

    @RequestMapping("/downLoadDJFQD")
    @ResponseBody
    public successResult downLoadDJFQD(String data, String code, HttpServletResponse response) throws Exception {
        successResult res = new successResult();
        try {
            String decodeName = URLDecoder.decode(data, "UTF-8");
            Map<String, Object> O = JSON.parseObject(decodeName, new TypeReference<Map<String, Object>>() {});
            complexExcelBuilder exB=null;
            if(code.equals("OneYear")) {
                Optional<tbExcelTemplate> findOnes=excelRep.findFirstByCode(code);
                if(findOnes.isPresent()) {
                    tbExcelTemplate one=findOnes.get();
                    String X=one.getTemplatePath();
                    if(StringUtils.isEmpty(X)==true) {
                        exB = new complexExcelBuilder(code);
                    } else exB=new complexExcelBuilder(X);
                }
            }
            byte[] Bs = exB.getContent(O);
            File directory = new File("");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String courseFile = CompanyPathUtils.getFullPath("Temp", "DJFQD");
            getFile(Bs, courseFile, "专利申请代缴费清单.xls");
            res.setMessage(courseFile);
            res.setSuccess(true);
        } catch (Exception ax) {
            response.getWriter().write(ax.getMessage());
            res.setSuccess(false);
        }
        return res;
    }

    @RequestMapping("/getRegisterPath")
    @ResponseBody
    public successResult getRegisterPath(HttpServletRequest request,int ClientID,String OrgCode) {
        String courseFile = "";
        successResult res = new successResult();
        try {
            LoginUserInfo Info=CompanyContext.get();
            Map<String,Object>OK=new HashMap<>();
            String RKey=UUID.randomUUID().toString();
            OK.put("ClientID",ClientID);
            OK.put("OrgCode",OrgCode);
            OK.put("CompanyID",Info.getCompanyId());
            redisUtil.opsForValue().set(RKey,JSON.toJSONString(OK), Duration.ofDays(1));
            courseFile =request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/resetPassword/index?Key="+RKey;
            res.setMessage(courseFile);
            res.setSuccess(true);
        } catch (Exception ax) {
            res.setSuccess(false);
        }
        return res;
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

    @RequestMapping("/ChangeKH")
    public String ChangeKH(Map<String, Object> model) {
        return "/work/client/ChangeKH";
    }

    @RequestMapping("/SaveChangeKH")
    @ResponseBody
    public successResult SaveChangeKH(HttpServletRequest request) {
        successResult result = new successResult();
        try {
            clientInfoService.SaveChangeKH(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/SendPassword")
    @ResponseBody
    public successResult SendPassword(HttpServletRequest request, String Code) {
        successResult result = new successResult();
        try {
            String mails = request.getParameter("mails");
            String Content = emailTemplateParsor.getSendContent(Code);
            EmailContent Con = new EmailContent();
            Con.setSubject("知朋专利监控系统试用邀请");
            mails=URLDecoder.decode(mails, "utf-8");
            List<tbClient> list = (List<tbClient>) JSONArray.parseArray(mails, tbClient.class);
            TextAndValue tv = new TextAndValue();
            for (int i = 0; i < list.size(); i++) {
                tv.setText(list.get(i).getName());
                tv.setValue(list.get(i).getOrgCode());
            }
            List<TextAndValue> listTextAndValue = new ArrayList<TextAndValue>() {
                {
                    this.add(tv);
                }
            };
            Con.setReceAddress(listTextAndValue);
            Con.setContent(Content);
            List<TextAndValue> text = new ArrayList<>();
            Con.setAttachments(text);

            sendEmailService.sendEmailByContent(Con);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public successResult remove(String IDS) {
        successResult result = new successResult();
        try {
            LoginUserInfo Info=CompanyContext.get();
            List<String> ids = JSON.parseArray(IDS, String.class);
            if(ids.size()>0) {
                String roleName=Info.getRoleName();
                if(roleName.equals("系统管理员")==false){
                    throw new Exception("只有系统管理员才能执行删除客户操作!");
                }
                clientInfoService.remove(ids);
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        try {
            String VX = request.getParameter("Data");
            List<IExcelExportTemplate> Rows = JSON.parseArray(VX, ClientInfoExcelTemplate.class)
                    .stream().map(f -> (IExcelExportTemplate) f).collect(Collectors.toList());
            if (Rows.size() > 0) {
                ExcelFileBuilder builder = new ExcelFileBuilder(Rows);
                byte[] datas = builder.export();
                String fileName = builder.getCurrentFileName();
                WebFileUtils.download(fileName, datas, response);
            } else response.getWriter().write("<script>alert('没有数据可以导出。');</script>");
        } catch (Exception ax) {
            try {
                response.getWriter().write("<script>alert('导出Excel失败:" + ax.getMessage() + "');</script>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("/findNameByName")
    @ResponseBody
    public successResult findNameByName(HttpServletRequest request) {
        successResult result = new successResult();
        tbClient tc = new tbClient();
        try {
            String Data = request.getParameter("Data");
            if (Strings.isEmpty(Data) == false) {
                tbClient client = JSON.parseObject(Data, tbClient.class);
                tc = clientInfoService.findNameByName(client.getName());
                if (tc == null) {
                    result.setData("");
                } else {
                    result.setData(tc.getName());
                }
            }
        } catch (Exception ax) {
            result.raiseException(ax);
            result.setSuccess(false);
        }
        return result;
    }

    @RequestMapping("/getGridDataExcel")
    @ResponseBody
    public successResult GetGridDataExcel(HttpServletRequest request, HttpServletResponse response) {
        successResult res = new successResult();
        try {
            String Data = request.getParameter("data");
            String Colums = request.getParameter("columns");
            String FileName = request.getParameter("filename");

            try {
                List<Map<String, Object>> datas = JSON.parseObject(Data,
                        new TypeReference<List<Map<String, Object>>>() {
                });
                List<gridColumnInfo> cols = JSON.parseArray(Colums, gridColumnInfo.class);
                ExcelBuilder bb = new ExcelBuilder(cols, datas);
                byte[] Bx = bb.create();
                String Path = uploadExcel(Bx, CompanyPathUtils.getFullPath("Temp"), FileName);
                res.setMessage(Path);
                res.setSuccess(true);
            } catch (Exception ax) {
                response.getWriter().write(ax.getMessage());
            }
        } catch (Exception ax) {
            try {
                response.getWriter().write("<script>alert('导出Excel失败:" + ax.getMessage() + "');</script>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    private String uploadExcel(byte[] bfile, String filePath, String fileName) {
        BufferedOutputStream bufferedOutputStream = null;
        FileOutputStream fileOutputStream = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {
                dir.mkdirs();
            }
            file = new File(filePath + "\\" + fileName);
            fileOutputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return filePath + "\\" + fileName;
    }
    @ResponseBody
    @RequestMapping("/saveImageFollow")
    public  successResult SaveImageFollowRecord(String Data){
        successResult result=new successResult();
        try{
            followRecord record=JSON.parseObject(Data,followRecord.class);
            clientInfoService.SaveImageFollowRecord(record);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
    @RequestMapping(value = "/editDefaultMail", method = RequestMethod.POST)
    @ResponseBody
    public successResult EditDefaultMail(String IDS, String ClientID) {
        successResult result = new successResult();
        try {
            List<String> ids = JSON.parseArray(IDS, String.class);
            clientInfoService.EditDefaultMaile(ids, ClientID);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping(value = "/cannelDefaultMail", method = RequestMethod.POST)
    @ResponseBody
    public successResult CannelDefaultMail(String IDS) {
        successResult result = new successResult();
        try {
            List<String> ids = JSON.parseArray(IDS, String.class);
            clientInfoService.CannelDefaultMail(ids);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
