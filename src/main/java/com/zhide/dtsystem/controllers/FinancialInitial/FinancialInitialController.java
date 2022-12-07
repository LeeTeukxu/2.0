package com.zhide.dtsystem.controllers.FinancialInitial;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.FinancialInitalMapper;
import com.zhide.dtsystem.models.FinanChangeRecord;
import com.zhide.dtsystem.models.FinancialInitial;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.tbClient;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.FinanChangeRecordRepository;
import com.zhide.dtsystem.repositorys.FinancialInitialRepository;
import com.zhide.dtsystem.repositorys.tbClientRepository;
import com.zhide.dtsystem.repositorys.tbFormDesignRepository;
import com.zhide.dtsystem.services.define.IFinancialInitalService;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/FinancialInitial")
public class FinancialInitialController {

    Logger logger = LoggerFactory.getLogger(FinancialInitialController.class);

    @Autowired
    IFinancialInitalService iFinancialInitalService;
    @Autowired
    FinancialInitialRepository financialInitialRepository;
    @Autowired
    FinancialInitalMapper financialInitalMapper;
    @Autowired
    tbFormDesignRepository tbFormDesignRepository;
    @Autowired
    FinanChangeRecordRepository finanChangeRecordRepository;
    @Autowired
    tbClientRepository clientRep;

    @RequestMapping("/index")
    public String Index(Map<String, Object> model) {
        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("UserID", loginInfo.getUserId());
        model.put("RoleName", loginInfo.getRoleName());
        model.put("RoleID", loginInfo.getRoleId());
        return "/FinancialInitial/index";
    }

    @RequestMapping("/getData")
    @ResponseBody
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = iFinancialInitalService.getData(request);
        } catch (Exception ax) {
            logger.info(ax.getMessage());
        }
        return result;
    }

    @RequestMapping("/add")
    public String Add(int ClientID,String Type, Map<String, Object> model) {
        model.put("Mode", "Add");
        model.put("Type", "");
        model.put("LoadData", "{}");
        Optional<tbClient> findOnes=clientRep.findById(ClientID);
        if(findOnes.isPresent()){
            model.put("ClientID",ClientID);
            model.put("ClientName",findOnes.get().getName());
        }
        return "/FinancialInitial/edit";
    }

    @RequestMapping("/edit")
    public String Edit(Integer FinancialInitialID, String Type, Map<String, Object> model) {
        model.put("FinancialInitialID", Integer.toString(FinancialInitialID));
        model.put("Mode", "Edit");
        model.put("Type", Type);
        Optional<FinancialInitial> findtb = financialInitalMapper.getAllByFinancialIntitalID(FinancialInitialID);
        if (findtb.isPresent()) {
            model.put("LoadData", JSON.toJSONString(findtb.get()));
            int ClientID=findtb.get().getCustomerId();
            Optional<tbClient> findOnes=clientRep.findById(ClientID);
            if(findOnes.isPresent()){
                model.put("ClientID",ClientID);
                model.put("ClientName",findOnes.get().getName());
            }
        } else model.put("LoadData", "{}");
        return "/FinancialInitial/edit";
    }

    @RequestMapping("/save")
    @ResponseBody
    public successResult save(HttpServletRequest request) {
        successResult result = new successResult();
        FinancialInitial res = null;
        try {
            String Data = request.getParameter("Data");
            String Text = request.getParameter("Text");
            String CommitType = request.getParameter("CommitType");
            if (Strings.isEmpty(Data) == false) {
                FinancialInitial financialInitial = JSON.parseObject(Data, FinancialInitial.class);
                res = iFinancialInitalService.Save(financialInitial, Text, CommitType);
                result.setData(res);
            } else throw new Exception("数据格式不正确！");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public successResult Remove(@RequestBody List<Integer> ids) {
        successResult result = new successResult();
        try {
            iFinancialInitalService.remove(ids);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getChangeRecord")
    public List<FinanChangeRecord> GetChangeRecord(Integer FinancialInitialID) {
        return finanChangeRecordRepository.findAllByPidAndModuleNameOrderByCreateTimeDesc(FinancialInitialID,
                "FinancialInitial");
    }
}
