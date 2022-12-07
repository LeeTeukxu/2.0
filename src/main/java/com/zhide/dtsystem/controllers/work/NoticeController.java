package com.zhide.dtsystem.controllers.work;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.WebFileUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.noticeMiddleFile;
import com.zhide.dtsystem.models.pantentInfo;
import com.zhide.dtsystem.models.tzsEmailRecord;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.noticeMiddleFileRepository;
import com.zhide.dtsystem.repositorys.pantentInfoRepository;
import com.zhide.dtsystem.repositorys.tzsEmailRecordRepository;
import com.zhide.dtsystem.services.define.INoticeService;
import com.zhide.dtsystem.services.define.IPatentInfoService;
import com.zhide.dtsystem.services.define.ITZSConfigService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/work/notice")
public class NoticeController {
    @Autowired
    ITZSConfigService tzsService;
    @Autowired
    INoticeService noticeService;
    @Autowired
    tzsEmailRecordRepository tzsRecordRep;
    @Autowired
    noticeMiddleFileRepository middleRep;
    @Autowired
    pantentInfoRepository pRep;
    @Autowired
    IPatentInfoService patentInfoService;

    @RequestMapping("/index")
    public String Index(String Type, Map<String, Object> model) {
        LoginUserInfo Info=CompanyContext.get();
        if (Type.startsWith("%")) {
            try {
                Type = URLDecoder.decode(Type, "utf-8");
            } catch (Exception ax) {
                ax.printStackTrace();
            }
        }
        model.put("Type",Type);
        try {
            model.put("CodeType", URLEncoder.encode(Type, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<String> AllTypes = Arrays.asList("补正通知书", "审查意见通知书", "驳回决定");
        model.put("SpecType", AllTypes.contains(Type));
        model.put("TZSStatus", JSON.toJSONString(noticeService.getTZSStatus()));
        model.put("Users", JSON.toJSONString(patentInfoService.GetLoginUserHash()));
        model.put("RoleName",Info.getRoleName());
        return "/work/notice/index";
    }

    @ResponseBody
    @RequestMapping("/getData")
    public pageObject getData(String Type, HttpServletRequest request) {
        pageObject result = new pageObject();
        if (Type.startsWith("%")) {
            try {
                Type = URLDecoder.decode(Type, "utf-8");
            } catch (Exception ax) {
                ax.printStackTrace();
            }
        }
        try {
            Map<String, Object> arguments = noticeService.getParams(request);
            result = noticeService.getData(Type, arguments);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/getReportData")
    public List<Map<String,Object>> getData(HttpServletRequest request) {
        List<Map<String,Object>> datas=new ArrayList<>();
        try {
            datas = noticeService.getReportData(request);
        } catch (Exception ax) {
            ax.printStackTrace();
        }
        return datas;
    }

    @ResponseBody
    @RequestMapping("/setRead")
    public successResult setRead(String ID, boolean value, String Type) {
        successResult result = new successResult();
        try {
            Type = URLDecoder.decode(Type, "utf-8");
            List<String> IDS = Arrays.asList(ID.split(","));
            tzsService.SaveChange(IDS, Type, "IsRead", value);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/setJieAn")
    public successResult setJieAn(String ID, boolean value, String Type) {
        successResult result = new successResult();
        try {
            Type = URLDecoder.decode(Type, "utf-8");
            List<String> IDS = Arrays.asList(ID.split(","));
            tzsService.SaveChange(IDS, Type, "Ja", value);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/setReply")
    public successResult setReply(String ID, boolean value, String Type) {
        successResult result = new successResult();
        try {
            Type = URLDecoder.decode(Type, "utf-8");
            List<String> IDS = Arrays.asList(ID.split(","));
            tzsService.SaveChange(IDS, Type, "Reply", value);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/setAbort")
    public successResult setAbort(String ID, boolean value, String Type) {
        successResult result = new successResult();
        try {
            Type = URLDecoder.decode(Type, "utf-8");
            List<String> IDS = Arrays.asList(ID.split(","));
            tzsService.SaveChange(IDS, Type, "Abort", value);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/setCommit")
    public successResult setCommit(String ID, boolean value, String Type) {
        successResult result = new successResult();
        try {
            Type = URLDecoder.decode(Type, "utf-8");
            List<String> IDS = Arrays.asList(ID.split(","));
            tzsService.SaveChange(IDS, Type, "IsCommit", value);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/setJiaJi")
    public successResult setJiaJi(String ID, boolean value, String Type) {
        successResult result = new successResult();
        try {
            Type = URLDecoder.decode(Type, "utf-8");
            List<String> IDS = Arrays.asList(ID.split(","));
            for (int i = 0; i < IDS.size(); i++) {
                String shenqingh = IDS.get(i);
                Optional<pantentInfo> finds = pRep.findById(shenqingh);
                if (finds.isPresent()) {
                    pantentInfo find = finds.get();
                    find.setJiaji(value);
                    pRep.save(find);
                }
            }
            tzsService.SaveChange(IDS, Type, "JA", value);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/download")
    public void Download(HttpServletResponse response, String Code, String FileName) {
        String[] Codes = Code.split(",");
        File targetFile = null;
        try {
            targetFile = noticeService.download(Codes);
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

            }

        }
    }

    @RequestMapping("/downloadSource")
    public void DownloadSource(HttpServletResponse response, String Code, String FileName) {
        String[] Codes = Code.split(",");
        File targetFile = null;
        try {
            targetFile = noticeService.downloadOriginal(Codes);
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

    @RequestMapping("/downloadPdf")
    public void DownloadPdf(HttpServletResponse response, String Code, String FileName) {
        String[] Codes = Code.split(",");
        try {
            byte[] BD = noticeService.downloadPdf(Codes);
            WebFileUtils.download(FileName,BD,response);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.getWriter().write(e.getMessage());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @RequestMapping("/downloadDB")
    public void DownloadDB(HttpServletResponse response, String Code, String FileName) {
        File targetFile = null;
        try {
            targetFile = noticeService.downloadDBFile(Code);
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

    @ResponseBody
    @RequestMapping("/changeValue")
    public successResult ChangeValue(String Field, String ID, String Value, String Type) {
        successResult result = new successResult();
        try {
            String realType = URLDecoder.decode(Type, "utf-8");
            SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateValue = simple.parse(Value.toString());
            tzsService.SaveChange(Arrays.asList(ID), realType, Field, dateValue);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/addEmailRecord")
    public successResult AddEmailRecord(String Data) {
        successResult result = new successResult();
        try {
            LoginUserInfo info = CompanyContext.get();
            List<tzsEmailRecord> records = JSON.parseArray(Data, tzsEmailRecord.class);
            for (int i = 0; i < records.size(); i++) {
                tzsEmailRecord record = records.get(i);
                record.setSendTime(new Date());
                record.setSendUserName(info.getUserName());
                record.setSendUser(info.getUserIdValue());
                tzsRecordRep.save(record);
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getEmailRecord")
    public successResult getEmailRecord(String ID) {
        successResult result = new successResult();
        try {
            SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            List<tzsEmailRecord> finds = tzsRecordRep.findAllByTongzhisbhOrderBySendTimeDesc(ID);
            List<String> Ts = new ArrayList<>();
            for (int i = 0; i < finds.size(); i++) {
                tzsEmailRecord record = finds.get(i);
                String time = simple.format(record.getSendTime());
                String X = String.format("%d、在%s%s给%s(%s)发送了邮件。", i + 1, time, record.getSendUserName(),
                        record.getClient(),
                        record.getEmail());
                Ts.add(X);
            }
            result.setData(Ts);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/saveMiddleFile")
    public successResult saveMiddleFile(String Data) {
        successResult result = new successResult();
        try {
            LoginUserInfo info = CompanyContext.get();
            noticeMiddleFile middleFile = JSON.parseObject(Data, noticeMiddleFile.class);
            middleFile.setCreateTime(new Date());
            middleFile.setCreateMan(info.getUserIdValue());
            middleFile.setCreateManName(info.getUserName());

            List<String> IDS=new ArrayList<>();
            IDS.add(middleFile.getTzsbh());
            tzsService.SaveChange(IDS, middleFile.getType(), "Reply",true);
            middleRep.save(middleFile);

        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getMiddleFile")
    public successResult getMiddleFile(String TZSBH) {
        successResult result = new successResult();
        try {
            List<noticeMiddleFile> files = middleRep.findAllByTzsbh(TZSBH);
            List<String> AttIDS = files.stream().map(f -> f.getAttid()).collect(Collectors.toList());
            result.setData(AttIDS);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/removeMiddleFile")
    @Transactional
    public successResult RemoveMiddleFile(String AttID) {
        successResult result = new successResult();
        try {
            middleRep.deleteAllByAttid(AttID);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @RequestMapping("/getTZSTotal")
    @ResponseBody
    public successResult getTZSTotal(String Type){
        successResult result=new successResult();
        try
        {
            LoginUserInfo Info=CompanyContext.get();
            List<Map<String,Object>> OO= tzsService.getTZSTotal(Info.getDepIdValue(),Info.getUserIdValue(),
                    Info.getRoleName(),Type);
            result.setData(OO);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
}
