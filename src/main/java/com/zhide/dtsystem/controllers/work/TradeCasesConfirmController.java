package com.zhide.dtsystem.controllers.work;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.casesCommitFile;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.IAllCasesStateService;
import com.zhide.dtsystem.services.define.ICaseService;
import com.zhide.dtsystem.services.define.ITradeCasesConfirmBrowseService;
import com.zhide.dtsystem.services.instance.tradeCasesFilesStateCounter;
import com.zhide.dtsystem.services.instance.tradeCasesStateCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/work/tradeCasesConfirm")
public class TradeCasesConfirmController {
    @Autowired
    tbFormDesignRepository formDesignRep;
    @Autowired
    productItemTypeRepository productRep;
    @Autowired
    ICaseService caseService;
    @Autowired
    ITradeCasesConfirmBrowseService tradeCasesConfirmBrowseService;
    @Autowired
    casesRepository casesRep;
    @Autowired
    casesYwItemsRepository casesYwRep;
    @Autowired
    casesAttachmentRepository casesAttRep;
    @Autowired
    casesChangeRecordRepository casesChangeRep;
    @Autowired
    IAllCasesStateService allCaseService;

    @Autowired
    tradeCasesStateCounter tradeCasesStateCounter;
    @Autowired
    tradeCasesFilesStateCounter tradeCasesFilesStateCounter;
    @Autowired
    casesCommitFileRepository commitFileRep;

    @RequestMapping("/index")
    public String Index(Integer State, Map<String, Object> model) {
        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("RoleName", loginInfo.getRoleName());
        model.put("UserID", loginInfo.getUserId());
        model.put("DepID", loginInfo.getDepId());
        model.put("State", State);
        Integer UserID = Integer.parseInt(loginInfo.getUserId());
        Map<String, Integer> X = tradeCasesStateCounter.getResult(UserID, loginInfo.getRoleName());
        Map<String, Integer> Y = tradeCasesFilesStateCounter.getResult(UserID, loginInfo.getRoleName());
        for (String Key : Y.keySet()) {
            X.put(Key, Y.get(Key));
        }
        model.put("States", X);
        if (State == null) State = 1;
        model.put("State", State);
        return "/work/tradeCases/confirm";
    }

    @ResponseBody
    @RequestMapping("/getData")
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = tradeCasesConfirmBrowseService.getData(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/commit")
    public successResult commit(Integer ID, String Result, String ResultText) {
        successResult result = new successResult();
        try {
            LoginUserInfo LoginInfo = CompanyContext.get();
            Result = URLDecoder.decode(Result, "utf-8");
            ResultText = URLDecoder.decode(ResultText, "utf-8");
            Optional<casesCommitFile> findFiles = commitFileRep.findById(ID);
            if (findFiles.isPresent()) {
                casesCommitFile findOne = findFiles.get();
                findOne.setShenBaoMan(LoginInfo.getUserIdValue());
                findOne.setShenBaoTime(new Date());
                findOne.setShenBaoResult(Result);
                findOne.setShenBaoText(ResultText);
                if (Result.equals("同意申报")) findOne.setState(4);
                else findOne.setState(3);
                commitFileRep.save(findOne);
            }
            result.setSuccess(true);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
