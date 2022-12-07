package com.zhide.dtsystem.controllers.cases;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.casesOutSourceMainRepository;
import com.zhide.dtsystem.repositorys.casesSubRepository;
import com.zhide.dtsystem.repositorys.casesYwItemsRepository;
import com.zhide.dtsystem.services.define.IAddSingleMemoService;
import com.zhide.dtsystem.services.define.ICasesTechBrowseNewService;
import com.zhide.dtsystem.services.define.IOutSourceTechService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * @ClassName: CasesOutSourceController
 * @Author: 肖新民
 * @*TODO:专利外协
 * @CreateTime: 2020年10月20日 20:04
 **/
@Controller
@RequestMapping("/casesOutSource")
public class CasesOutSourceController {
    @Autowired
    IOutSourceTechService outTechService;
    @Autowired
    casesOutSourceMainRepository casesOutMainRep;
    @Autowired
    casesSubRepository casesSubRep;
    @Autowired
    casesYwItemsRepository casesYwItemsRepository;
    @Autowired
    ICasesTechBrowseNewService caseTechService;
    @Autowired
    IAddSingleMemoService addMemoService;

    @RequestMapping("/index")
    public String Index(Map<String, Object> model) {
        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("RoleName", loginInfo.getRoleName());
        model.put("RoleID", loginInfo.getRoleId());
        model.put("UserID", loginInfo.getUserId());
        model.put("DepID", loginInfo.getDepId());
        return "/work/casesOutSource/index";
    }

    @ResponseBody
    @RequestMapping("/getData")
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = outTechService.getData(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/acceptTech")
    @ResponseBody
    public successResult AcceptTech(String SubIDS) {
        successResult result = new successResult();
        try {
            List<String> IDS = Arrays.stream(SubIDS.split(",")).collect(toList());
            outTechService.AcceptTech(IDS);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/rejectTech")
    @ResponseBody
    public successResult RejectTech(String SubIDS) {
        successResult result = new successResult();
        try {
            List<String> IDS = Arrays.stream(SubIDS.split(",")).collect(toList());
            outTechService.RejectTech(IDS);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/getSubFiles")
    @ResponseBody
    public successResult GetSubFiles(String SubID, String Type) {
        successResult result = new successResult();
        try {

            List<casesOutSourceFile> Fs = outTechService.GetSubFile(SubID, Type);
            result.setData(Fs.stream().map(f -> f.getAttId()).collect(toList()));
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/saveSubFiles")
    public successResult SaveSubFiles(String SubID, String OutID, String AttID, String Type) {
        successResult result = new successResult();
        try {
            outTechService.SaveSubFiles(SubID, OutID, AttID, Type);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/removeSubFiles")
    public successResult removeSubFiles(String AttID) {
        successResult result = new successResult();
        try {
            outTechService.RemoveSubFile(AttID);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/commitTech")
    public successResult CommitTech(String SubID) {
        successResult result = new successResult();
        try {
            outTechService.CommitTech(SubID);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/auditTech")
    public successResult AuditTech(String SubIDS, String Result, String Text) {
        successResult result = new successResult();
        try {
            List<String> IDS = Arrays.stream(SubIDS.split(",")).collect(toList());
            outTechService.AuditTechFiles(IDS, Text, Result);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getStateNumber")
    public successResult GetStateNumber() {
        successResult result = new successResult();
        try {
            Map<Integer, Integer> OX = outTechService.GetStateNumber();
            result.setData(OX);
        } catch (Exception ax) {

            result.raiseException(ax);
        }
        return result;
    }
    @RequestMapping("/removeMemo")
    @ResponseBody
    public successResult RemoveMemo(String SubID,String IDS) {
        successResult result = new successResult();
        try {
            if(StringUtils.isEmpty(SubID)) throw new Exception("SubID为空!");
            if(StringUtils.isEmpty(IDS)) throw new Exception("IDS为空!");

            List<String> IDD= Arrays.stream(IDS.split(",")).collect(toList());
            addMemoService.RemoveMemo(SubID,IDD);

        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/saveMemo")
    @ResponseBody
    public successResult SaveMemo(String SubID, String Data) {
        successResult result = new successResult();
        try {
            if(StringUtils.isEmpty(SubID)) throw new Exception("SubID为空!");
            if (StringUtils.isEmpty(Data) == false) {
                List<simpleMemo> postMemos = JSON.parseArray(Data, simpleMemo.class);
                addMemoService.SaveMemo(SubID,postMemos);
            } else throw new Exception("提交的数据为空!");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/changeJS")
    public successResult changeJS(String SubID, Integer OldMan, Integer NewMan) {
        successResult result = new successResult();
        try {
            List<String> SubIDS = Arrays.stream(SubID.split(",")).collect(toList());
            outTechService.ChangeJS(SubIDS, NewMan);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
