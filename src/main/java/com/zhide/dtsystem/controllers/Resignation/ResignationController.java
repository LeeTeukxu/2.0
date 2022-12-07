package com.zhide.dtsystem.controllers.Resignation;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.*;
import com.zhide.dtsystem.controllers.FinancialInitial.FinancialInitialController;
import com.zhide.dtsystem.models.tbResignationRecord;
import com.zhide.dtsystem.repositorys.tbResignationRepository;
import com.zhide.dtsystem.services.define.ItbResignationService;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("Resignation")
public class ResignationController {
    Logger logger = LoggerFactory.getLogger(ResignationController.class);
    @Autowired
    ItbResignationService itbResignationService;
    @Autowired
    tbResignationRepository resignationRepository;

    @RequestMapping("/index")
    public String Index(Map<String, Object> model) {
        return "/Resignation/index";
    }

    @RequestMapping("/getData")
    @ResponseBody
    public pageObject GetDate(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = itbResignationService.getData(request);
        } catch (Exception ax) {
            logger.info(ax.getMessage());
        }
        return result;
    }

    @RequestMapping("/add")
    public String Add(Map<String, Object> model) {
        model.put("Mode", "Add");
        model.put("LoadData", "{}");
        return "/Resignation/edit";
    }
    @RequestMapping("/edit")
    public String Add(int ResignationRecordID,Map<String, Object> model) {
        model.put("Mode", "Edit");
        Optional<tbResignationRecord> findOne = resignationRepository.findById(ResignationRecordID);
        if (findOne.isPresent()) {
            tbResignationRecord resignationRecord = findOne.get();
            model.put("LoadData", JSON.toJSONString(resignationRecord));
        } else model.put("LoadData", "{}");
        return "/Resignation/edit";
    }

    @RequestMapping("/save")
    @ResponseBody
    public successResult Save(HttpServletRequest request) {
        successResult result = new successResult();
        tbResignationRecord res = null;
        try {
            String Data = request.getParameter("Data");
            if (Strings.isEmpty(Data) == false) {
                tbResignationRecord resignationRecord = JSON.parseObject(Data,tbResignationRecord.class);
                res = itbResignationService.Save(resignationRecord);
                result.setData(res);
            }else throw new Exception("数据格式不正确");

        }catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

}

