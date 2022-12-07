package com.zhide.dtsystem.controllers.work;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.WebFileUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.tbClient;
import com.zhide.dtsystem.models.tbFormDesign;
import com.zhide.dtsystem.models.tradeCases;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.ICasesSubFileService;
import com.zhide.dtsystem.services.define.ITradeCasesTechBrowseService;
import com.zhide.dtsystem.services.instance.tradeCasesStateCounter;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/work/tradeCasesTech")
public class TradeCasesTechController {
    @Autowired
    tbFormDesignRepository formDesignRep;
    @Autowired
    productItemTypeRepository productRep;
    @Autowired
    ITradeCasesTechBrowseService tradeCasesTechBrowseService;
    @Autowired
    tradeCasesRepository tradeCasesRepository;
    @Autowired
    casesYwItemsRepository casesYwRep;
    @Autowired
    casesAttachmentRepository casesAttRep;
    @Autowired
    casesChangeRecordRepository casesChangeRep;
    @Autowired
    ICasesSubFileService subFileService;
    @Autowired
    tradeCasesStateCounter casesCouter;
    @Autowired
    tbClientRepository clientRep;
    @Autowired
    SysLoginUserMapper loginUserMapper;

    @RequestMapping("/index")
    public String Index(Integer State, Map<String, Object> model) {
        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("RoleName", loginInfo.getRoleName());
        model.put("UserID", loginInfo.getUserId());
        Integer UserID = Integer.parseInt(loginInfo.getUserId());
        Map<String, Integer> X = casesCouter.getResult(UserID, loginInfo.getRoleName());
        model.put("States", X);

        if (State == null) State = 1;
        model.put("State", State);
        return "/work/tradeCases/tech";
    }

    @RequestMapping("/edit")
    public String Edit(Integer ID, Map<String, Object> model) {
        Optional<tbFormDesign> tf = formDesignRep.findAll().stream().filter(f -> f.getCode().equals("TradeCase"))
                .findFirst();
        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("ViewDetail",1);
        String Mode = "Browse";
        if (tf.isPresent()) {
            model.put("config", tf.get().getAllText());
            Optional<tradeCases> findOne = tradeCasesRepository.findById(ID);
            if (findOne.isPresent()) {
                tradeCases main = findOne.get();
                Optional<tbClient> findClients = clientRep.findById(main.getClientId());
                if (findClients.isPresent()) main.setClientIdName(findClients.get().getName());
                model.put("Load", JSON.toJSONString(main));
                model.put("LoadObj", main);
                List<String> AttIDS =
                        casesAttRep.findAllByCasesId(main.getCasesid()).stream().map(f -> f.getAttId()).collect(Collectors.toList());
                model.put("AttID", JSON.toJSONString(AttIDS));
                if(Mode.equals("Browse")){
                    String RoleName=loginInfo.getRoleName();
                    if (RoleName.indexOf("流程") > -1 || main.getCreateMan() == loginInfo.getUserIdValue() || main.getAuditMan() == loginInfo.getUserIdValue()){
                        if (RoleName.indexOf("经理") > -1 || RoleName.indexOf("流程") > -1) {
                            model.put("ViewDetail", 1);
                        }else model.put("ViewDetail",0);
                    }else model.put("ViewDetail",0);

                }
            }
        } else model.put("config", "[]");
        model.put("Mode", Mode);
        String XM = loginUserMapper.getManager(loginInfo.getUserId());
        if (XM.indexOf(",") > -1) {
            XM = XM.split(",")[0];
        }
        model.put("Manager", XM);
        model.put("RoleName", loginInfo.getRoleName());
        return "/work/tradeCases/add";
    }

    @ResponseBody
    @RequestMapping("/getData")
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = tradeCasesTechBrowseService.getData(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getCasesFreeNums")
    public List<Map<String, Object>> getCasesFreeNums(String CasesID) {
        return tradeCasesTechBrowseService.getAllFreeNums(CasesID);
    }

    @ResponseBody
    @RequestMapping("/acceptTechTask")
    public successResult acceptTechTask(String CASESIDS, String TechMan) {
        successResult result = new successResult();
        try {
            tradeCasesTechBrowseService.acceptTechTask(CASESIDS,TechMan);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/sbTechFiles")
    public successResult SBTechFiles(int Result, String Memo, String SubIDS) {
        successResult result = new successResult();
        try {
            tradeCasesTechBrowseService.SBTechFiles(Result, Memo, SubIDS);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/changeTcName")
    public successResult ChangeTcName(String SubID, String NewShenqingName) {
        successResult result = new successResult();
        try {
            tradeCasesTechBrowseService.ChangeTcName(SubID, NewShenqingName);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/changeTcCategory")
    public successResult ChangeTcCategory(String SubID, String NewShenqingName) {
        successResult result = new successResult();
        try {
            tradeCasesTechBrowseService.ChangeTcCategory(SubID, NewShenqingName);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @RequestMapping("/addMemo")
    public String AddMemo(String CasesID, Map<String, Object> model) {
        model.put("CasesID", CasesID);
        return "/work/tradeCases/addMemo";
    }
    @RequestMapping("/download")
    public void Download(HttpServletResponse response, String SubIDS) {
        File targetFile = null;
        try {
            targetFile = subFileService.DownloadTradeFiles(SubIDS);
            if (targetFile != null) {
                SimpleDateFormat ss = new SimpleDateFormat("yyyyMMddHHmmss");
                WebFileUtils.download("商标版权业务协作文件下载_" + ss.format(new Date()) + ".zip", targetFile, response);
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
}
