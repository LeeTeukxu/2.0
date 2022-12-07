package com.zhide.dtsystem.controllers.finance;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.ExcelFileBuilder;
import com.zhide.dtsystem.common.WebFileUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.tbArrivalMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.FinanChangeRecordRepository;
import com.zhide.dtsystem.repositorys.tbArrivalRegistrationRepository;
import com.zhide.dtsystem.services.define.ItbArrivalService;
import org.apache.commons.lang.StringUtils;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/finance/arrival")
public class ArrivalRegistrationController {

    Logger logger = LoggerFactory.getLogger(ArrivalRegistrationController.class);
    @Autowired
    ItbArrivalService arrivalService;

    @Autowired
    tbArrivalRegistrationRepository arrivalRegistrationRepository;

    @Autowired
    tbArrivalMapper arrivalMapper;

    @Autowired
    FinanChangeRecordRepository finanChangeRecordRepository;

    @RequestMapping("/index")
    public String Index(Map<String, Object> model) {
        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("UserID", loginInfo.getUserId());
        model.put("RoleName", loginInfo.getRoleName());
        model.put("RoleID", loginInfo.getRoleId());
        return "/finance/arrival/index";
    }
    @RequestMapping("/report")
    public String Report(){
        return "/finance/arrival/report";
    }

    @RequestMapping("/getData")
    @ResponseBody
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = arrivalService.getData(request);
        } catch (Exception ax) {
            logger.info(ax.getMessage());
        }
        return result;
    }
    @RequestMapping("/getWorkData")
    @ResponseBody
    public pageObject getWorkData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = arrivalService.getWorkData(request);
        } catch (Exception ax) {
            logger.info(ax.getMessage());
        }
        return result;
    }

    @RequestMapping("/add")
    public String Add(String Type, Map<String, Object> model) {
        Date nowTime = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuilder sdt = new StringBuilder("ZD");
        LoginUserInfo loginUserInfo = CompanyContext.get();
        model.put("Mode", "Add");
        model.put("DocumentNumber", sdt.append(dt.format(nowTime)));
        model.put("SignMan", Integer.parseInt(loginUserInfo.getUserId()));
        model.put("LoadData", "{}");
        return "/finance/arrival/edit";
    }

    @RequestMapping("/edit")
    public String Edit(int ArrivalRegistrationID, Map<String, Object> model) {
        model.put("ArrivalRegistrationID", Integer.toString(ArrivalRegistrationID));
        model.put("Mode", "Edit");
        Optional<tbArrivalRegistration> findtb = arrivalRegistrationRepository.findById(ArrivalRegistrationID);
        if (findtb.isPresent()) {
            model.put("LoadData", JSON.toJSONString(findtb.get()));
        } else model.put("LoadData", "{}");
        return "/finance/arrival/edit";
    }

    @RequestMapping("/renlin")
    public String saveRenLin(int ArrivalRegistrationID, Map<String, Object> model) {
        model.put("ArrivalRegistrationID", Integer.toString(ArrivalRegistrationID));
        model.put("Mode", "saveRenLin");
        Optional<tbArrivalRegistration> findtb = arrivalMapper.getRenLinAndFuHe(ArrivalRegistrationID);
        String AttIDS="";
        if (findtb.isPresent()) {
            tbArrivalRegistration tb=findtb.get();
            model.put("LoadData", JSON.toJSONString(tb));
            AttIDS=tb.getAttIDS();
        } else model.put("LoadData", "{}");
        if(StringUtils.isEmpty(AttIDS))AttIDS="";
        model.put("AttIDS",AttIDS);
        return "/finance/arrival/saveRenLin";
    }

    @RequestMapping("/browse")
    public String browse(int ArrivalRegistrationID, Map<String, Object> model) {
        model.put("ArrivalRegistrationID", Integer.toString(ArrivalRegistrationID));
        model.put("Mode", "saveRenLin");
        Optional<tbArrivalRegistration> findtb = arrivalMapper.getRenLinAndFuHe(ArrivalRegistrationID);
        String AttIDS="";
        if (findtb.isPresent()) {
            tbArrivalRegistration tb=findtb.get();
            model.put("LoadData", JSON.toJSONString(tb));
            AttIDS=tb.getAttIDS();
        } else model.put("LoadData", "{}");

        if(StringUtils.isEmpty(AttIDS))AttIDS="";
        model.put("AttIDS",AttIDS);
        return "/finance/arrival/browse";
    }
    @RequestMapping("/fuhe")
    public String saveFuHe(int ArrivalRegistrationID, Map<String, Object> model) {
        LoginUserInfo loginInfo = CompanyContext.get();
        model.put("ArrivalRegistrationID", Integer.toString(ArrivalRegistrationID));
        model.put("RoleName", loginInfo.getRoleName());
        model.put("Mode", "saveFuHe");
        String AttIDS="";
        Optional<tbArrivalRegistration> findtb = arrivalMapper.getRenLinAndFuHe(ArrivalRegistrationID);
        if (findtb.isPresent()) {
            tbArrivalRegistration tb=findtb.get();
            model.put("LoadData", JSON.toJSONString(tb));
            AttIDS=tb.getAttIDS();
        } else model.put("LoadData", "{}");
        if(StringUtils.isEmpty(AttIDS))AttIDS="";
        model.put("AttIDS",AttIDS);
        return "/finance/arrival/saveFuHe";
    }

    @RequestMapping("/save")
    @ResponseBody
    public successResult save(HttpServletRequest request) {
        successResult result = new successResult();
        tbArrivalRegistration res = null;
        try {
            String Data = request.getParameter("Data");
            String Text = request.getParameter("Text");
            String Sub=request.getParameter("Sub");
            String CommitType = request.getParameter("CommitType");
            if (Strings.isEmpty(Data) == false) {
                tbArrivalRegistration tbArrivalRegistration = JSON.parseObject(Data, tbArrivalRegistration.class);
                res = arrivalService.Save(tbArrivalRegistration, Text, CommitType,Sub);
                result.setData(res);
            } else throw new Exception("数据格式不正确！");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/update")
    @ResponseBody
    public successResult update(HttpServletRequest request) {
        successResult result = new successResult();
        tbArrivalRegistration res = null;
        try {
            String Data = request.getParameter("Data");
            String Text = request.getParameter("Text");
            String CommitType = request.getParameter("CommitType");
            if (Strings.isEmpty(Data) == false) {
                tbArrivalRegistration tbArrivalRegistration = JSON.parseObject(Data, tbArrivalRegistration.class);
                if (tbArrivalRegistration.getArrivalRegistrationId() != null) {
                    //arrivalRegistrationRepository.UpdateCWTX(tbArrivalRegistration.getDocumentNumber(),
                    // tbArrivalRegistration.getDateOfPayment(),tbArrivalRegistration.getPaymentMethod(),
                    // tbArrivalRegistration.getPaymentAccount(),tbArrivalRegistration.getPaymentAmount(),
                    // tbArrivalRegistration.getPayer(),tbArrivalRegistration.getReturnBank(),tbArrivalRegistration
                    // .getDescription(),tbArrivalRegistration.getArrivalRegistrationId());
                    int re = arrivalService.update(tbArrivalRegistration, Text, CommitType);
                    if (re > 0) {
                        res = tbArrivalRegistration;
                        result.setData(res);
                    }
                }
            } else throw new Exception("数据格式不正确！");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public successResult remove(@RequestBody List<Integer> ids) {
        successResult result = new successResult();
        try {
            arrivalService.remove(ids);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/saveRenLin")
    @ResponseBody
    public successResult saveRenLin(HttpServletRequest request) {
        successResult result = new successResult();
        tbArrivalRegistration res = null;
        try {
            String Data = request.getParameter("Data");
            String Text = request.getParameter("Text");
            String CommitType = request.getParameter("CommitType");
            String Sub=request.getParameter("Sub");
            if (Strings.isEmpty(Data) == false) {
                tbArrivalRegistration tbArrivalRegistration = JSON.parseObject(Data, tbArrivalRegistration.class);
                res = arrivalService.SaveRenLin(tbArrivalRegistration, Text, CommitType,Sub);
                result.setData(res);
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @RequestMapping("/commitRenLin")
    @ResponseBody
    public successResult CommitRenLin(HttpServletRequest request) {
        successResult result = new successResult();
        tbArrivalRegistration res = null;
        try {
            String Data = request.getParameter("Data");
            String Text = request.getParameter("Text");
            String CommitType = request.getParameter("CommitType");
            String Sub=request.getParameter("Sub");
            if (Strings.isEmpty(Data) == false) {
                tbArrivalRegistration tbArrivalRegistration = JSON.parseObject(Data, tbArrivalRegistration.class);
                res = arrivalService.CommitRenLin(tbArrivalRegistration, Text, CommitType,Sub);
                result.setData(res);
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @RequestMapping("/saveFuHe")
    @ResponseBody
    public successResult saveFuHe(HttpServletRequest request) {
        successResult result = new successResult();
        tbArrivalRegistration res = null;
        try {
            String Data = request.getParameter("Data");
            String Text = request.getParameter("Text");
            String CommitType = request.getParameter("CommitType");
            if (Strings.isEmpty(Data) == false) {
                tbArrivalRegistration tbArrivalRegistration = JSON.parseObject(Data, tbArrivalRegistration.class);
                res = arrivalService.SaveFuHe(tbArrivalRegistration, Text, CommitType);
                if (tbArrivalRegistration.getReviewerStatus() != null && tbArrivalRegistration.getReviewerStatus() == 1) {
                    arrivalRegistrationRepository.UnUpdateReviewerStatus(3,
                            tbArrivalRegistration.getArrivalRegistrationId());
                } else if (tbArrivalRegistration.getReviewerStatus() != null && tbArrivalRegistration.getReviewerStatus() == 2) {
                    arrivalRegistrationRepository.UpdateReviewerStatus(2,
                            tbArrivalRegistration.getArrivalRegistrationId());
                }

                result.setData(res);
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        try {
            String VX = request.getParameter("Data");
            List<IExcelExportTemplate> Rows = JSON.parseArray(VX, ArrivalRegistrationExcelTemplate.class)
                    .stream().map(f -> (IExcelExportTemplate) f).collect(Collectors.toList());
            if (Rows.size() > 0) {
                ExcelFileBuilder builder = new ExcelFileBuilder(Rows);
                byte[] datas = builder.export();
                String fileName = builder.getCurrentFileName();
                WebFileUtils.download(fileName, datas, response);
            } else response.getWriter().write("<script>alert('没有数据可以导出。');</script>");
        } catch (Exception ax) {
            try {
                response.getWriter().write("<script>alert('导出Excel失败:" + ax.getMessage() + "');</script>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @ResponseBody
    @RequestMapping("/refreshTotal")
    public successResult refreshTotal() {
        successResult result = new successResult();
        try {
            LoginUserInfo Info = CompanyContext.get();
            List<Map<String, Object>> OS = arrivalMapper.getArrivalTotal(Info.getDepIdValue(), Info.getUserIdValue(),
                    Info.getRoleName());
            result.setData(OS);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getChangeRecord")
    public List<FinanChangeRecord> GetChangeRecord(Integer ArrivalRegistrationID) {
        return finanChangeRecordRepository.findAllByPidAndModuleNameOrderByCreateTimeDesc(ArrivalRegistrationID,
                "Arrival");
    }

    @ResponseBody
    @RequestMapping("/getClientNameByDocumentNumber")
    public successResult GetClientNameByDocumentNumber(String DocumentNumber) {
        successResult result = new successResult();
        try {
            String ClientName = arrivalMapper.getClientNameByDocumentNumber(DocumentNumber);
            result.setData(ClientName);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/cancelAudit")
    public successResult cancelAudit(Integer ID){
        successResult result = new successResult();
        try {
            arrivalService.cancelAudit(ID);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
