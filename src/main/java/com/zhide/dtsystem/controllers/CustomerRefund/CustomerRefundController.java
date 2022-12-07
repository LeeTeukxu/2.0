package com.zhide.dtsystem.controllers.CustomerRefund;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.ExcelFileBuilder;
import com.zhide.dtsystem.common.WebFileUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.tbArrivalMapper;
import com.zhide.dtsystem.mapper.tbCustomerRefundMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.FinanChangeRecordRepository;
import com.zhide.dtsystem.repositorys.tbCustomerRefundRepository;
import com.zhide.dtsystem.repositorys.tbLoginUserRepository;
import com.zhide.dtsystem.repositorys.v_tbEmployeeRepository;
import com.zhide.dtsystem.services.define.ItbCustomerRefundService;
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
@RequestMapping("CustomerRefund/customerRefund")
public class CustomerRefundController {

    Logger logger = LoggerFactory.getLogger(CustomerRefundController.class);

    @Autowired
    ItbCustomerRefundService customerRefundService;

    @Autowired
    tbCustomerRefundRepository customerRefundRepository;

    @Autowired
    private v_tbEmployeeRepository v_tbEmployeeRepository;

    @Autowired
    tbCustomerRefundMapper tbCustomerRefundMapper;

    @Autowired
    FinanChangeRecordRepository finanChangeRecordRepository;

    @Autowired
    tbArrivalMapper arrivalMapper;

    @Autowired
    tbLoginUserRepository loginUserRepository;

    @RequestMapping("/index")
    public String Index(Map<String, Object> model) {
        LoginUserInfo loginUserInfo = CompanyContext.get();
        model.put("RoleName", loginUserInfo.getRoleName());
        model.put("UserID", loginUserInfo.getUserId());
        model.put("RoleID", loginUserInfo.getRoleId());
        return "/CustomerRefund/customerRefund/index";
    }

    @RequestMapping("/getData")
    @ResponseBody
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = customerRefundService.getData(request);
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
        model.put("arrivalData", "{}");
        model.put("LoadData", "{}");
        return "/CustomerRefund/customerRefund/edit";
    }

    @RequestMapping("/edit")
    public String Edit(String Type, int CustomerRefundRequestID, Map<String, Object> model) {
        model.put("CustomerRefundRequestID", Integer.toString(CustomerRefundRequestID));
        model.put("Mode", "Edit");
        Optional<tbCustomerRefund> findtb = customerRefundRepository.findById(CustomerRefundRequestID);
        Optional<tbArrivalRegistration> arrivalfindtb =
                arrivalMapper.getArrivalByCustomerRefundRequestID(CustomerRefundRequestID);
        if (findtb.isPresent()) {
            model.put("LoadData", JSON.toJSONString(findtb.get()));
            model.put("arrivalData", JSON.toJSONString(arrivalfindtb.get()));
        } else {
            model.put("LoadData", "{}");
            model.put("arrivalData", "{}");
        }
        return "/CustomerRefund/customerRefund/edit";
    }

    @RequestMapping("/again")
    public String Again(String Type, int CustomerRefundRequestID, String DocumentNumber, Map<String, Object> model) {
        model.put("CustomerRefundRequestID", Integer.toString(CustomerRefundRequestID));
        model.put("Mode", "Edit");
        Optional<tbCustomerRefund> findtb = customerRefundRepository.findById(CustomerRefundRequestID);
        Optional<tbArrivalRegistration> arrivalfindtb =
                arrivalMapper.getArrivalByCustomerRefundRequestID(CustomerRefundRequestID);
        if (findtb.isPresent()) {
            model.put("LoadData", JSON.toJSONString(findtb.get()));
            model.put("arrivalData", JSON.toJSONString(arrivalfindtb.get()));
        } else {
            model.put("LoadData", "{}");
            model.put("arrivalData", "{}");
        }
        return "/CustomerRefund/customerRefund/again";
    }

    @RequestMapping("/saveJinLi")
    public String saveJinLi(int CustomerRefundRequestID, Map<String, Object> model) {
        model.put("CustomerRefundRequestID", Integer.toString(CustomerRefundRequestID));
        model.put("Mode", "saveJinLi");
        Optional<tbCustomerRefund> findtb = customerRefundRepository.findById(CustomerRefundRequestID);
        LoginUserInfo loginUserInfo = CompanyContext.get();
        tbLoginUser loginUser = new tbLoginUser();
        loginUser = loginUserRepository.findAllByUserId(findtb.get().getApplicant());
        v_tbEmployee employee = v_tbEmployeeRepository.findAllByEmpID(loginUser.getEmpId());
        if (findtb.isPresent()) {
            model.put("LoadData", JSON.toJSONString(findtb.get()));
            model.put("LoginUserID", loginUserInfo.getUserId());
            model.put("ApplicantDepID", employee.getDepId());
            model.put("LoginUserDepID", loginUserInfo.getDepId());
            model.put("RoleName", loginUserInfo.getRoleName());
        } else model.put("LoadData", "{}");
        return "/CustomerRefund/customerRefund/saveJinLi";
    }

    @RequestMapping("/saveCw")
    public String saveCw(int CustomerRefundRequestID, Map<String, Object> model) {
        model.put("CustomerRefundRequestID", Integer.toString(CustomerRefundRequestID));
        model.put("Mode", "saveCw");
        Optional<tbCustomerRefund> findtb = customerRefundRepository.findById(CustomerRefundRequestID);
        LoginUserInfo loginUserInfo = CompanyContext.get();
        tbLoginUser loginUser = loginUserRepository.findAllByUserId(findtb.get().getApplicant());
        v_tbEmployee employee = v_tbEmployeeRepository.findAllByEmpID(loginUser.getEmpId());
        if (findtb.isPresent()) {
            model.put("LoadData", JSON.toJSONString(findtb.get()));
            model.put("LoginUserID", loginUserInfo.getUserId());
            model.put("ApplicantDepID", employee.getDepId());
            model.put("LoginUserDepID", loginUserInfo.getDepId());
            model.put("RoleName", loginUserInfo.getRoleName());
        } else model.put("LoadData", "{}");
        return "/CustomerRefund/customerRefund/saveCw";
    }

    @RequestMapping("/save")
    @ResponseBody
    public successResult save(HttpServletRequest request) {
        successResult result = new successResult();
        tbCustomerRefund res = null;
        try {
            String Data = request.getParameter("Data");
            String ArrivalData = request.getParameter("ArrivalData");
            String Text = request.getParameter("Text");
            String CommitType = request.getParameter("CommitType");
            if (Strings.isEmpty(Data) == false) {
                tbCustomerRefund customerRefund = JSON.parseObject(Data, tbCustomerRefund.class);
                tbArrivalRegistration arrivalRegistration = JSON.parseObject(ArrivalData, tbArrivalRegistration.class);
                res = customerRefundService.Save(customerRefund, arrivalRegistration, Text, CommitType);
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
        tbCustomerRefund res = null;
        try {
            String Data = request.getParameter("Data");
            String ArrivalData = request.getParameter("ArrivalData");
            String Text = request.getParameter("Text");
            String CommitType = request.getParameter("CommitType");
            if (Strings.isEmpty(Data) == false) {
                tbCustomerRefund customerRefund = JSON.parseObject(Data, tbCustomerRefund.class);
                tbArrivalRegistration arrivalRegistration = JSON.parseObject(ArrivalData, tbArrivalRegistration.class);
                if (customerRefund.getCustomerRefundRequestId() != null) {
                    int re = customerRefundService.update(customerRefund, arrivalRegistration, Text, CommitType);
                    if (re > 0) {
                        res = customerRefund;
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
            customerRefundService.remove(ids);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

//    @RequestMapping("/jlsp")
//    @ResponseBody
//    public successResult jlsp(HttpServletRequest request){
//        successResult result=new successResult();
//        tbCustomerRefund res=null;
//        try
//        {
//            String Data=request.getParameter("Data");
//            if(Strings.isEmpty(Data)==false) {
//                tbCustomerRefund customerRefund = JSON.parseObject(Data, tbCustomerRefund.class);
//                res= customerRefundService.jlsp(customerRefund);
//                result.setData(res);
//            }
//        }
//        catch (Exception ax)
//        {
//            result.raiseException(ax);
//        }
//        return result;
//    }
//
//    @RequestMapping("/cwsp")
//    @ResponseBody
//    public successResult cwsp(HttpServletRequest request){
//        successResult result=new successResult();
//        tbCustomerRefund res=null;
//        try
//        {
//            String Data=request.getParameter("Data");
//            if(Strings.isEmpty(Data)==false) {
//                tbCustomerRefund customerRefund = JSON.parseObject(Data, tbCustomerRefund.class);
//                res= customerRefundService.cwsp(customerRefund);
//                result.setData(res);
//            }
//        }
//        catch (Exception ax)
//        {
//            result.raiseException(ax);
//        }
//        return result;
//    }

    @RequestMapping("/jlsp")
    @ResponseBody
    public successResult saveJinLi(HttpServletRequest request) {
        successResult result = new successResult();
        tbCustomerRefund res = null;
        try {
            String Data = request.getParameter("Data");
            String Text = request.getParameter("Text");
            String CommitType = request.getParameter("CommitType");
            if (Strings.isEmpty(Data) == false) {
                tbCustomerRefund customerRefund = JSON.parseObject(Data, tbCustomerRefund.class);
                res = customerRefundService.SaveJinLi(customerRefund, Text, CommitType);
                result.setData(res);
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/cwsp")
    @ResponseBody
    public successResult saveCaiWu(HttpServletRequest request) {
        successResult result = new successResult();
        tbCustomerRefund res = null;
        try {
            String Data = request.getParameter("Data");
            String Text = request.getParameter("Text");
            String CommitType = request.getParameter("CommitType");
            if (Strings.isEmpty(Data) == false) {
                tbCustomerRefund customerRefund = JSON.parseObject(Data, tbCustomerRefund.class);
                res = customerRefundService.SaveCaiWu(customerRefund, Text, CommitType);
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
            List<IExcelExportTemplate> Rows = JSON.parseArray(VX, CustomerRefundExcelTemplate.class)
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
            List<Map<String, Object>> OS = tbCustomerRefundMapper.getCustomerRefundTotal(Info.getDepIdValue(),
                    Info.getUserIdValue(), Info.getRoleName());
            result.setData(OS);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getChangeRecord")
    public List<FinanChangeRecord> GetChangeRecord(Integer CustomerRefundRequestID) {
        return finanChangeRecordRepository.findAllByPidAndModuleNameOrderByCreateTimeDesc(CustomerRefundRequestID,
                "CustomerRefund");
    }

    @ResponseBody
    @RequestMapping("/getTotalFeeByDocumentNumber")
    public successResult GetTotalFeeByDocumentNumber(String DocumentNumber) {
        successResult result = new successResult();
        tbCustomerRefund res = null;
        try {
            res = tbCustomerRefundMapper.getTotalFeeByDocumentNumber(DocumentNumber);
            result.setData(res);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getTotalFeeByCustomerRequestID")
    public successResult GetTotalFeeByCustomerRequestID(Integer CustomerRefundRequestID) {
        successResult result = new successResult();
        tbCustomerRefund res = null;
        try {
            res = tbCustomerRefundMapper.getTotalFeeByCustomerRequestID(CustomerRefundRequestID);
            result.setData(res);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
