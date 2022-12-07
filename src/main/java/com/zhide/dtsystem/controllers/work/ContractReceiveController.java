package com.zhide.dtsystem.controllers.work;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.WebFileUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.contractCodeRepository;
import com.zhide.dtsystem.repositorys.contractReceiveRepository;
import com.zhide.dtsystem.services.define.IContractReceiveService;
import com.zhide.dtsystem.services.define.ITicketService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/work/contractReceive")
public class ContractReceiveController {
    @Autowired
    contractCodeRepository codeRep;
    @Autowired
    IContractReceiveService contractService;
    @Autowired
    contractReceiveRepository contractRep;
    @Autowired
    ITicketService ticketService;

    @RequestMapping("/index")
    public String Index(Map<String, Object> model) {
        LoginUserInfo Info = CompanyContext.get();
        List<contractCode> codes = codeRep.findAll();
        model.put("Codes", JSON.toJSONString(codes));
        model.put("DrawEmp", Info.getUserName());
        model.put("DrawEmpID", Info.getUserId());
        return "/work/contractReceive/index";
    }

    @RequestMapping("/query")
    public String Query(String multiselect, Integer ClientID, Map<String, Object> model) {
        if (ClientID == null) ClientID = 0;


        List<contractCode> Codes = codeRep.findAll();
        List<ComboboxItem> items = new ArrayList<>();
        for (int i = 0; i < Codes.size(); i++) {
            contractCode code = Codes.get(i);
            ComboboxItem item = new ComboboxItem();
            item.setId(Integer.toString(code.getId()));
            item.setText(code.getType());
            items.add(item);
        }
        model.put("items", JSON.toJSONString(items));
        model.put("multiselect", multiselect);
        model.put("ClientID", Integer.toString(ClientID));
        return "/work/contractReceive/query";
    }

    @RequestMapping("/save")
    @ResponseBody
    public successResult Save(String Data) {
        successResult result = new successResult();
        try {
            LoginUserInfo loginUserInfo = CompanyContext.get();
            contractReceive obj = JSON.parseObject(Data, contractReceive.class);
            obj.setDrawTime(new Date());
            obj.setNeedSubmit(0);
            contractService.Save(obj);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/saveConfig")
    @ResponseBody
    public successResult SaveConfig(String Data) {
        successResult result = new successResult();
        try {
            LoginUserInfo Info = CompanyContext.get();
            List<contractCode> codes = codeRep.findAll();
            List<contractCode> postDatas = JSON.parseArray(Data, contractCode.class);
            for (int i = 0; i < postDatas.size(); i++) {
                contractCode c1 = postDatas.get(i);
                if (c1.getId() == null) {
                    c1.setCreateMan(Info.getUserIdValue());
                    c1.setCreateTime(new Date());
                }
                codeRep.save(c1);
            }
            result.setData(codeRep.findAll());
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/remove")
    @ResponseBody
    public successResult Remove(Integer id) {
        successResult result = new successResult();
        try {
            result.setSuccess(contractService.Remove(id));
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/deleteConfig")
    @ResponseBody
    public successResult deleteConfig(Integer ID) {
        successResult result = new successResult();
        try {
            List<contractReceive> cons = contractRep.findAllByContractType(ID);
            if (cons.size() > 0) {
                throw new Exception("删除的合同类型已被引用，无法删除!");
            } else {
                codeRep.deleteById(ID);
                List<contractCode> cs = codeRep.findAll();
                result.setData(cs);
            }
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
            result = contractService.getData(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/changeDrawEmp")
    @ResponseBody
    public successResult ChangeDrawEmp(String ID, String Value) {
        successResult result = new successResult();
        try {
            contractService.ChangeDrawEmp(ID, Value);
        }catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @RequestMapping("/download")
    public void Download(HttpServletResponse response, String Code, String FileName) {
        String[] Codes = Code.split(",");
        File targetFile = null;
        try {
            List<String> Atts = new ArrayList<>();
            for (int i = 0; i < Codes.length; i++) {
                String xCode = Codes[i];
                Atts.add(xCode);
            }
            targetFile = ticketService.Download(Atts);
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
}
