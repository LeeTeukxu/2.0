package com.zhide.dtsystem.controllers.cases;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.DateTimeUtils;
import com.zhide.dtsystem.common.WebFileUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.casesSub;
import com.zhide.dtsystem.models.simpleMemo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.NBBHCreator;
import com.zhide.dtsystem.services.define.*;
import com.zhide.dtsystem.services.instance.casesStateNewCounter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.joda.LocalDateTimeParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/casesTech")
public class CasesTechNewController {
    @Autowired
    tbFormDesignRepository formDesignRep;
    @Autowired
    ICasesTechBrowseNewService browseService;

    @Autowired
    casesAttachmentRepository casesAttRep;
    @Autowired
    IAllCasesStateService allCaseService;

    @Autowired
    casesStateNewCounter casesCouter;
    @Autowired
    tbClientRepository clientRep;

    @Autowired
    SysLoginUserMapper loginUserMapper;
    @Autowired
    casesSubRepository subRep;
    @Autowired
    casesMainRepository mainRep;
    @Autowired
    ICasesUserService casesUserService;
    @Autowired
    ICasesSubUserService casesSubUserService;
    @Autowired
    casesSubFilesRepository subFileRep;
    @Autowired
    NBBHCreator nbCreator;
    @Autowired
    ICasesSubFileService subFileService;
    @Autowired
    IOutSourceTechService casesOutSourceService;
    @Autowired
    IOutSourceTechService outTechService;
    @Autowired
    casesOutSourceMainRepository casesOutMainRep;
    @Autowired
    casesOutSourceFileRepository casesOutFileRep;
    @Autowired
    IOutTechRecordService outRecordService;

    @RequestMapping("/index")
    public String Index(Integer State, Map<String, Object> model) {
        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("RoleName", loginInfo.getRoleName());
        model.put("UserID", loginInfo.getUserId());
        Integer UserID = Integer.parseInt(loginInfo.getUserId());
        Map<String, String> X = casesCouter.getResult(UserID, loginInfo.getRoleName());
        model.put("States", X);
        String XM = loginUserMapper.getManager(loginInfo.getUserId());
        if (XM.indexOf(",") > -1) {
            XM = XM.split(",")[0];
        }
        model.put("Manager", XM);
        if (State == null) State = 1;
        model.put("State", State);
        return "/work/caseNew/tech";
    }

    @ResponseBody
    @RequestMapping("/getData")
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = browseService.getData(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/changeJGDate")
    @ResponseBody
    public successResult ChangeJGDate(String Field, String ID, String Value) {
        successResult result = new successResult();
        try {
            Date dateValue = null;
            if (Strings.isEmpty(Value) == false) {
                SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dateValue = simple.parse(Value.toString());
            }
            browseService.ChangeJGDate(ID, Field, dateValue);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/changeRequiredDate")
    @ResponseBody
    public successResult ChangeRequiredDate(String ID, String Value) {
        successResult result = new successResult();
        try {
            SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateValue = simple.parse(Value.toString());
            browseService.ChangeRequiredDate(ID, dateValue);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getWaitReport")
    public List<Map<String, Object>> getWaitReport(Integer State, String Type, HttpServletRequest request) {
        List<Map<String, Object>> res = new ArrayList<>();
        try {
            res = browseService.getWaitReport(State, Type, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @ResponseBody
    @RequestMapping("/getClientReport")
    public List<Map<String, Object>> getClientReport(String Type, HttpServletRequest request) {
        List<Map<String, Object>> res = new ArrayList<>();
        try {
            res = browseService.getClientReport(Type, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @ResponseBody
    @RequestMapping("/acceptTechTask")
    public successResult acceptTechTask(String SubIDS, String AuditMan, String TechMan) {
        successResult result = new successResult();
        try {
            browseService.acceptTechTask(SubIDS, AuditMan, TechMan);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/rejectTechTask")
    public successResult rejectTechTask(String SubIDS) {
        successResult result = new successResult();
        try {
            browseService.rejectTechTask(SubIDS);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    /**
     * @Author:肖新民
     * @CreateTime:2020-07-27 6:38
     * @Params:[SubIDS, Memo]
     * Description: 接单人员上传技术文件提交核稿
     */
    @ResponseBody
    @RequestMapping("/commitTechFiles")
    public successResult CommitTechFiles(String SubIDS, String Memo) {
        successResult result = new successResult();
        try {
            browseService.CommitTechFiles(SubIDS, Memo);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    /**
     * @Author:肖新民
     * @CreateTime:2020-07-27 9:53
     * @Params:[Result, SubIDS, Memo]
     * Description:技术核稿
     */
    @ResponseBody
    @RequestMapping("/auditTechFiles")
    public successResult AuditTechFiles(int Result, String SubIDS, String Memo) {
        successResult result = new successResult();
        try {
            browseService.AuditTechFiles(Result, SubIDS, Memo);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }


    /**
     * @Author:肖新民
     * @CreateTime:2020-07-27 14:14
     * @Params:[Result, SubIDS]
     * Description:客户定稿是否通过
     */
    @ResponseBody
    @RequestMapping("/setTechFiles")
    public successResult SetTechFiles(int Result, String Memo, String SubIDS) {
        successResult result = new successResult();
        try {
            browseService.SetTechFiles(Result, Memo, SubIDS);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }


    /**
     * @Author:肖新民
     * @CreateTime:2020-07-30 23:21
     * @Params:[Result, Memo, SubIDS]
     * Description: 案件是否申报审核
     */
    @ResponseBody
    @RequestMapping("/sbTechFiles")
    public successResult SBTechFiles(int Result, String Memo, String SubIDS) {
        successResult result = new successResult();
        try {
            browseService.SBTechFiles(Result, Memo, SubIDS);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    /**
     * @Author:肖新民
     * @CreateTime:2020-07-30 23:23
     * @Params:[Result, Memo, SubIDS]
     * Description:提成是否结算审核
     */
    @ResponseBody
    @RequestMapping("/auditSettle")
    public successResult AuditSettle(int Result, String Memo, String SubIDS) {
        successResult result = new successResult();
        try {
            browseService.AuditSettle(Result, Memo, SubIDS);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }


    /**
     * @Author:肖新民
     * @CreateTime:2020-09-18 11:38
     * @Params:[Data] Description: 客户定稿以后修改技术联系人信息。
     */
    @ResponseBody
    @RequestMapping("/saveClientInfo")
    public successResult SaveClientInfo(String Data) {
        successResult result = new successResult();
        try {
            casesSub postOne = JSON.parseObject(Data, casesSub.class);
            Optional<casesSub> findOnes = subRep.findFirstBySubId(postOne.getSubId());
            if (findOnes.isPresent()) {
                casesSub One = findOnes.get();
                One.setClientLinkMan(postOne.getClientLinkMan());
                One.setClientLinkPhone(postOne.getClientLinkPhone());
                One.setClientLinkMail(postOne.getClientLinkMail());
                subRep.save(One);
            } else throw new Exception("操作的交单业务对象已不存在，请刷新后再试!");

        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/download")
    public void Download(HttpServletResponse response, String Type, String SubIDS) {
        File targetFile = null;
        try {
            targetFile = subFileService.DownloadFiles(Type, SubIDS);
            if (targetFile != null) {
                SimpleDateFormat ss = new SimpleDateFormat("yyyyMMddHHmmss");
                WebFileUtils.download("项目交单立案文件下载_" + ss.format(new Date()) + ".zip", targetFile, response);
            } else throw new Exception("下载文件失败，请稍候重试!");
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

    @RequestMapping("/addOutSourceTech")
    @ResponseBody
    public successResult AddOutSourceTech(String IDS, Integer OutTechMan) {
        successResult result = new successResult();
        try {
            List<String> SubIDS = Arrays.stream(IDS.split(",")).collect(toList());
            casesOutSourceService.AddOutSourceTask(SubIDS, OutTechMan);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/cancelOutTech")
    public successResult CancelOutTech(String IDS) {
        successResult result = new successResult();
        try {
            List<String> PPS = Arrays.stream(IDS.split(",")).collect(toList());
            casesOutSourceService.CancelOutTech(PPS);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/changeShenqingName")
    public successResult ChangeShenqingName(String SubID, String NewShenqingName) {
        successResult result = new successResult();
        try {
            browseService.ChangeShenqingName(SubID, NewShenqingName);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/changeLXText")
    public successResult ChangeLXText(String SubID, String NewLXText) {
        successResult result = new successResult();
        try {
            browseService.ChangeLXText(SubID, NewLXText);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/changeSupportMan")
    public successResult ChangeSupportMan(String SubID, String SupportMan) {
        successResult result = new successResult();
        try {
            browseService.ChangeSupportMan(SubID, SupportMan);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/changeTechMan")
    public successResult ChangeTechMan(int OldMan, int NewMan, String SubID) {
        successResult result = new successResult();
        try {
            browseService.ChangeTechMan(OldMan, NewMan, SubID);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/getDynamicColumn")
    @ResponseBody
    public successResult getDynamicColumn(String Type, int State) {
        successResult result = new successResult();
        try {
            List<Map<String, Object>> cols = browseService.getDynamicColumns(Type, State);
            result.setData(cols);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/updateWorkStatus")
    @ResponseBody
    public successResult updateWorkStatus(String SubID, int WorkStatus) {
        successResult result = new successResult();
        try {

            Map<Integer, String> Status = new HashMap<>();
            Status.put(1, "正在处理");
            Status.put(2, "完成初稿");
            Status.put(3, "已定稿");

            LoginUserInfo Info = CompanyContext.get();
            Optional<casesSub> findSubs = subRep.findFirstBySubId(SubID);
            if (findSubs.isPresent()) {
                casesSub sub = findSubs.get();
                Integer Pre = sub.getWorkStatus();

                sub.setWorkStatus(WorkStatus);
                sub.setStatusChangeMan(Info.getUserIdValue());
                sub.setStatusChangeTime(new Date());

                String PreText = Status.get(Pre);
                String NowText = Status.get(WorkStatus);

                String ChangeText = Info.getUserName() + "将业务处理状态由:" + PreText + "变更为:" + NowText;

                String proText = sub.getProcessText();
                if (StringUtils.isEmpty(proText)) proText = "[]";
                List<simpleMemo> sms = JSON.parseArray(proText, simpleMemo.class);
                simpleMemo vv = new simpleMemo();
                vv.setCreateTime(new Date());
                vv.setMemo(ChangeText);
                vv.setCreateManName(Info.getUserName());
                sms.add(vv);
                sub.setProcessText(JSON.toJSONString(sms));
                subRep.save(sub);

            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @RequestMapping("/getWorkDays")
    @ResponseBody
    public pageObject getWorkDays(String Begin, String End) throws Exception {
        pageObject obj=new pageObject();
        try {
            SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
            if (StringUtils.isEmpty(Begin)) return obj;
            Date beginTime = simple.parse(Begin);
            Date endTime = new Date();
            if (StringUtils.isEmpty(End) == false) {
                endTime = simple.parse(End);
            }
            if (beginTime.after(endTime)) throw new Exception("开始时间必须小于结束时间!");
            Map<String, Object> params = new HashMap<>();
            params.put("Begin", Begin);
            params.put("End", End);
            List<String> Items = new ArrayList<>();
            Date t = beginTime;
            while (t.before(endTime)) {
                String kk = getYearMonth(t);
                if (Items.contains(kk) == false) {
                    Items.add(kk);
                }
                t = addMonth(t);
            }
            if(equalsYearMonth(t,endTime)){
                String kk=getYearMonth(t);
                Items.add(kk);
            }
            params.put("Items", Items);
            List<Map<String, Object>> rows = browseService.getWorkDays(params);

            obj.setTotal(rows.size());
            obj.setData(rows);
        }
        catch(Exception ax){
            obj.raiseException(ax);
        }
        return obj;
    }
    @RequestMapping("/getWorkColumns")
    @ResponseBody
    public successResult getWorkColumns(String Begin, String End){
        successResult obj=new successResult();
        try {
            SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
            if (StringUtils.isEmpty(Begin)) return obj;
            Date beginTime = simple.parse(Begin);
            Date endTime = new Date();
            if (StringUtils.isEmpty(End) == false) {
                endTime = simple.parse(End);
            }
            if (beginTime.after(endTime)) throw new Exception("开始时间必须小于结束时间!");
            Map<String, Object> params = new HashMap<>();
            params.put("Begin", Begin);
            params.put("End", End);
            List<String> Items = new ArrayList<>();
            Date t = beginTime;
            while (t.before(endTime)) {
                String kk = getYearMonth(t);
                if (Items.contains(kk) == false) {
                    Items.add(kk);
                }
                t = addMonth(t);
            }
            if(equalsYearMonth(t,endTime)){
                String kk=getYearMonth(t);
                Items.add(kk);
            }
            obj.setData(Items);
        }
        catch(Exception ax){
            obj.raiseException(ax);
        }
        return obj;
    }
    private Date addMonth(Date t){
        Calendar C=Calendar.getInstance();
        C.setTime(t);
        C.add(GregorianCalendar.MONTH,1);
        return C.getTime();
    }
    private boolean equalsYearMonth(Date begin, Date end) {
        int yearBegin = begin.getYear();
        int monthBegin = begin.getMonth();

        int yearEnd = end.getYear();
        int monthEnd = end.getMonth();
        return yearBegin == yearEnd && monthBegin == monthEnd;
    }
    private String getYearMonth(Date t){
        SimpleDateFormat simple=new SimpleDateFormat("yyyy年M月");
        return simple.format(t);
    }
}
