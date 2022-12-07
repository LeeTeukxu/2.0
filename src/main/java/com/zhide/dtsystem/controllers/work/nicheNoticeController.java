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
import com.zhide.dtsystem.services.define.INiCheNoticeService;
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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/work/nicheNotice")
public class nicheNoticeController {
    @Autowired
    ITZSConfigService tzsService;
    @Autowired
    INiCheNoticeService niCheNoticeService;
    @Autowired
    tzsEmailRecordRepository tzsRecordRep;
    @Autowired
    noticeMiddleFileRepository middleRep;
    @Autowired
    pantentInfoRepository pRep;

    @RequestMapping("/index")
    public String Index(Map<String, Object> model) {
        model.put("TZSStatus", JSON.toJSONString(niCheNoticeService.getTZSStatus()));
        return "/work/nicheNotice/index";
    }

    @ResponseBody
    @RequestMapping("/getData")
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            Map<String, Object> arguments = niCheNoticeService.getParams(request);
            result = niCheNoticeService.getData(arguments);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }


    @ResponseBody
    @RequestMapping("/setRead")
    public successResult setRead(String ID, boolean value) {
        successResult result = new successResult();
        try {
            List<String> IDS = Arrays.asList(ID.split(","));
            List<String> list = new ArrayList<String>();
            tzsService.SaveNiCheChange(IDS, list, "IsRead", value);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/setJieAn")
    public successResult setJieAn(String ID, boolean value) {
        successResult result = new successResult();
        try {
            List<String> IDS = Arrays.asList(ID.split(","));
            List<String> list = new ArrayList<String>();
            tzsService.SaveNiCheChange(IDS, list, "Ja", value);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/setReply")
    public successResult setReply(String ID, boolean value) {
        successResult result = new successResult();
        try {
            List<String> IDS = Arrays.asList(ID.split(","));
            List<String> list = new ArrayList<String>();
            tzsService.SaveNiCheChange(IDS, list, "Reply", value);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/setCommit")
    public successResult setCommit(String ID, boolean value) {
        successResult result = new successResult();
        try {
            List<String> IDS = Arrays.asList(ID.split(","));
            List<String> list = new ArrayList<String>();
            tzsService.SaveNiCheChange(IDS, list, "IsCommit", value);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/setJiaJi")
    public successResult setJiaJi(String ID, String TZSBH, boolean value) {
        successResult result = new successResult();
        try {
            List<String> IDS = Arrays.asList(ID.split(","));
            List<String> TONGZHISBH = Arrays.asList(TZSBH.split(","));
            for (int i = 0; i < IDS.size(); i++) {
                String shenqingh = IDS.get(i);
                Optional<pantentInfo> finds = pRep.findById(shenqingh);
                if (finds.isPresent()) {
                    pantentInfo find = finds.get();
                    find.setJiaji(value);
                    pRep.save(find);
                }
            }
            tzsService.SaveNiCheChange(IDS, TONGZHISBH, "JA", value);
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
            targetFile = niCheNoticeService.download(Codes);
            WebFileUtils.download(FileName, targetFile, response);
        } catch (Exception e) {
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
            targetFile = niCheNoticeService.downloadOriginal(Codes);
            WebFileUtils.download(FileName, targetFile, response);
        } catch (Exception e) {
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
    public successResult ChangeValue(String Field, String ID, String Value) {
        successResult result = new successResult();
        try {
            SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateValue = simple.parse(Value.toString());

            tzsService.SaveNiCheChange(Arrays.asList(ID), Arrays.asList(ID), Field, dateValue);
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
}
