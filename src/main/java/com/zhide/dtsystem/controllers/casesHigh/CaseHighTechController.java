package com.zhide.dtsystem.controllers.casesHigh;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.WebFileUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.caseHighSub;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.NBBHCreator;
import com.zhide.dtsystem.services.define.*;
import com.zhide.dtsystem.services.instance.casesStateNewCounter;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/caseHighTech")
public class CaseHighTechController {
    @Autowired
    tbFormDesignRepository formDesignRep;
    @Autowired
    ICaseHighTechBrowseService browseService;

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
    caseHighSubRepository subRep;
    @Autowired
    caseHighMainRepository mainRep;
    @Autowired
    ICaseHighUserService highUserService;
    @Autowired
    ICaseHighSubUserService caseHighSubUserService;
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
        return "/work/caseHigh/tech";
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


    @ResponseBody
    @RequestMapping("/acceptTechTask")
    public successResult acceptTechTask(String SubIDS, String AuditMan,String TechMan) {
        successResult result = new successResult();
        try {
            List<String> IDS = Arrays.asList(SubIDS.split(","));
            browseService .acceptTechTask(IDS, AuditMan,TechMan);
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
            List<String> IDS = Arrays.asList(SubIDS.split(","));
            browseService.rejectTechTask(IDS);
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
            List<String> IDS = Arrays.asList(SubIDS.split(","));
            browseService.CommitTechFiles(IDS, Memo);
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
            List<String> IDS = Arrays.asList(SubIDS.split(","));
            browseService.AuditTechFiles(IDS, Result, Memo);
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
            List<String> IDS = Arrays.asList(SubIDS.split(","));
            browseService.SetTechFiles(IDS, Result, Memo);
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
            List<String> IDS = Arrays.asList(SubIDS.split(","));
            browseService.SBTechFiles(IDS, Result, Memo);
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
            List<String> IDS = Arrays.asList(SubIDS.split(","));
            browseService.AuditSettle(IDS, Result, Memo);
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
            caseHighSub postOne = JSON.parseObject(Data, caseHighSub.class);
            Optional<caseHighSub> findOnes = subRep.findFirstBySubId(postOne.getSubId());
            if (findOnes.isPresent()) {
                caseHighSub One = findOnes.get();
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
    public void Download(HttpServletResponse response, String SubIDS) {
        File targetFile = null;
        try {
            targetFile = subFileService.DownloadFiles("All",SubIDS);
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
    public successResult ChangeSupportMan(String SubID,String SupportMan){
        successResult result=new successResult();
        try
        {
            browseService.ChangeSupportMan(SubID,SupportMan);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/changeTechMan")
    public successResult ChangeTechMan(int OldMan,int NewMan,String SubID){
        successResult result=new successResult();
        try
        {
            browseService.ChangeTechMan(OldMan,NewMan,SubID);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/getWaitReport")
    public List<Map<String,Object>> getWaitReport(String Type,HttpServletRequest request){
        List<Map<String,Object>> res=new ArrayList<>();
        try {
            res= browseService.getWaitReport(Type,request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
