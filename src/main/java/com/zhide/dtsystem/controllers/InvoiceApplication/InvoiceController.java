package com.zhide.dtsystem.controllers.InvoiceApplication;

import ch.qos.logback.core.net.server.Client;
import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.ExcelFileBuilder;
import com.zhide.dtsystem.common.WebFileUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.ItbInvoiceApplicationService;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/InvoiceApplication/invoice")
public class InvoiceController {

    Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    ItbInvoiceApplicationService tbInvoiceApplicationService;

    @Autowired
    tbInvoiceApplicationRepository invoiceApplicationRepository;

    @Autowired
    tbArrivalRegistrationRepository arrivalRegistrationRepository;

    @Autowired
    middleFileRepository middleFileRepository;

    @Autowired
    tbClientRepository clientRepository;

    @Autowired
    FinanChangeRecordRepository finanChangeRecordRepository;

    @Autowired
    contractReceiveRepository receiveRepository;

    @RequestMapping("/index")
    public String Index(Map<String, Object> model) {
        LoginUserInfo loginUserInfo = CompanyContext.get();
        model.put("RoleName", loginUserInfo.getRoleName());
        model.put("UserID", loginUserInfo.getUserId());
        model.put("RoleID", loginUserInfo.getRoleId());
        return "/InvoiceApplication/invoice/index";
    }

    @RequestMapping("/saveParameterConfigs")
    public String saveParameterConfigs(String WinType, Map<String, Object> model) {
        model.put("WinType", WinType);
        return "/InvoiceApplication/invoice/saveParameterConfigs";
    }

    @RequestMapping("/saveKuaiDiXinXi")
    public String saveKuaiDiXinXi(int InvoiceApplicationID, Map<String, Object> model) {
        model.put("InvoiceApplicationID", Integer.toString(InvoiceApplicationID));
        model.put("Mode", "saveKuaiDiXinXi");
        Optional<tbInvoiceApplication> findtb = invoiceApplicationRepository.findById(InvoiceApplicationID);
        if (findtb.isPresent()) {
            model.put("LoadData", JSON.toJSONString(findtb.get()));
        } else model.put("LoadData", "{}");
        return "/InvoiceApplication/invoice/saveKuaiDiXinXi";
    }

    @RequestMapping("/getData")
    @ResponseBody
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = tbInvoiceApplicationService.getData(request);
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
        tbInvoiceApplication tb=new tbInvoiceApplication();
        tb.setAddTime(new Date());
        tb.setApplicant(loginUserInfo.getUserIdValue());
        model.put("LoadData", JSON.toJSONString(tb));
        model.put("ClientName", "");
        model.put("ContractName", "");
        model.put("Mode","Add");
        return "/InvoiceApplication/invoice/edit";
    }

    @RequestMapping("/edit")
    public String Edit(int InvoiceApplicationID,String Mode, Map<String, Object> model) {
        model.put("InvoiceApplicationID", Integer.toString(InvoiceApplicationID));
        Optional<tbInvoiceApplication> findtb = invoiceApplicationRepository.findById(InvoiceApplicationID);
        if (findtb.isPresent()) {
            model.put("LoadData", JSON.toJSONString(findtb.get()));
        } else model.put("LoadData", "{}");
        tbInvoiceApplication invoiceApplication = findtb.get();
        if (invoiceApplication.getClientId() != null) {
            tbClient client = clientRepository.findAllByClientID(invoiceApplication.getClientId());
            if(client!=null) {
                String ClientName = client.getName();
                if (StringUtils.isEmpty(ClientName)) ClientName = "";
                model.put("ClientName", ClientName);
            } else model.put("ClientName","");
        } else {
            model.put("ClientName", "");
        }
        if (StringUtils.isEmpty(invoiceApplication.getContractNo())==false) {
            contractReceive receive = receiveRepository.findAllByContractNo(invoiceApplication.getContractNo());
            if(receive!=null) {
                String ContactName = receive.getContractName();
                if (StringUtils.isEmpty(ContactName) == false) {
                    model.put("ContractName", ContactName);
                } else model.put("ContractName", invoiceApplication.getContractNo());
            } else model.put("ContractName","");

        }else model.put("ContractName","");
        model.put("Mode",Mode);
        return "/InvoiceApplication/invoice/edit";
    }

    @ResponseBody
    @RequestMapping("/getParameter")
    public pageObject getParameter(Integer dtId, int pageIndex, int pageSize) {
        pageObject result = new pageObject();
        try {
            Page<tbInvoiceParameter> parameterPage = tbInvoiceApplicationService.getParameter(dtId, pageIndex,
                    pageSize);
            result.setData(parameterPage.getContent());
            String x = Long.toString(parameterPage.getTotalElements());
            result.setTotal(Integer.parseInt(x));
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/saveParameter")
    @ResponseBody
    public successResult saveParameter(HttpServletRequest request, String Name) {
        successResult result = new successResult();
        tbInvoiceParameter res = null;
        try {
            String Data = request.getParameter("Entity");
            if (Strings.isEmpty(Data) == false) {
                tbInvoiceParameter invoiceParameterData = JSON.parseObject(Data, tbInvoiceParameter.class);
                res = tbInvoiceApplicationService.saveParameter(invoiceParameterData, Name);
                result.setData(res);
            } else throw new Exception("数据格式不正确！");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping(value = "/removeParameter", method = RequestMethod.POST)
    @ResponseBody
    public successResult removeParameter(@RequestBody List<Integer> ids) {
        successResult result = new successResult();
        try {
            tbInvoiceApplicationService.removeParameter(ids);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/save")
    @ResponseBody
    public successResult save(HttpServletRequest request) {
        successResult result = new successResult();
        tbInvoiceApplication res = null;
        try {
            String Data = request.getParameter("Data");
            String Text = request.getParameter("Text");
            String CommitType = request.getParameter("CommitType");
            if (Strings.isEmpty(Data) == false) {
                tbInvoiceApplication invoiceApplication = JSON.parseObject(Data, tbInvoiceApplication.class);
                res = tbInvoiceApplicationService.save(invoiceApplication, Text, CommitType);
                result.setData(res);
                if (res != null) {
                    arrivalRegistrationRepository.RenLinUpdateClientCooType(1, res.getClientId());
                }
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
        tbInvoiceApplication res = null;
        try {
            String Data = request.getParameter("Data");
            String Text = request.getParameter("Text");
            String CommitType = request.getParameter("CommitType");
            if (Strings.isEmpty(Data) == false) {
                tbInvoiceApplication invoiceApplication = JSON.parseObject(Data, tbInvoiceApplication.class);
                if (invoiceApplication.getInvoiceApplicationId() != null) {
                    invoiceApplication.setState(1);
                    int re = tbInvoiceApplicationService.update(invoiceApplication, Text, CommitType);
                    if (re > 0) {
                        res = invoiceApplication;
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
            tbInvoiceApplicationService.remove(ids);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping(value = "/updateExpressInfo", method = RequestMethod.POST)
    @ResponseBody
    public successResult updateExpressInfo(HttpServletRequest request) {
        successResult result = new successResult();
        tbInvoiceApplication res = null;
        try {
            String Data = request.getParameter("Data");
            if (Strings.isEmpty(Data) == false) {
                tbInvoiceApplication invoiceApplication = JSON.parseObject(Data, tbInvoiceApplication.class);
                if (invoiceApplication.getInvoiceApplicationId() != null) {
                    int re = tbInvoiceApplicationService.updateExpressInfo(invoiceApplication);
                    if (re > 0) {
                        res = invoiceApplication;
                        result.setData(res);
                    }
                }
            } else throw new Exception("数据格式不正确！");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        try {
            String VX = request.getParameter("Data");
            List<IExcelExportTemplate> Rows = JSON.parseArray(VX, InvoiceApplicationExcelTemplate.class)
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
    @RequestMapping("/saveMiddleFile")
    public successResult saveMiddleFile(String Data) {
        successResult result = new successResult();
        try {
            LoginUserInfo loginUserInfo = CompanyContext.get();
            middleFile mFile = JSON.parseObject(Data, middleFile.class);
            mFile.setCreateTime(new Date());
            mFile.setCreateMan(loginUserInfo.getUserIdValue());
            mFile.setCreateManName(loginUserInfo.getUserName());
            middleFileRepository.save(mFile);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getMiddleFile")
    public successResult getMiddleFile(Integer InvoiceApplicationID, String Type) {
        successResult result = new successResult();
        try {
            List<middleFile> files = middleFileRepository.findAllByIdAndType(InvoiceApplicationID, Type);
            List<String> AttIDS = files.stream().map(f -> f.getAttid()).collect(Collectors.toList());
            result.setData(AttIDS);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/removeMiddleFile")
    @Transactional
    public successResult RemoveMiddleFile(String AttID) {
        successResult result = new successResult();
        try {

            middleFileRepository.deleteAllByAttid(AttID);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getClientNameByClientID")
    public tbClient GetClientNameByClientID(Integer ClientID) {
        tbClient client = new tbClient();
        try {
            client = clientRepository.findAllByClientID(ClientID);
        } catch (Exception ax) {
            logger.info(ax.getMessage());
        }
        return client;
    }

    @ResponseBody
    @RequestMapping("/getChangeRecord")
    public List<FinanChangeRecord> GetChangeRecord(Integer InvoiceApplicationID) {
        return finanChangeRecordRepository.findAllByPidAndModuleNameOrderByCreateTimeDesc(InvoiceApplicationID, "InvoiceApplication");
    }
    @RequestMapping("/copyDocument")
    @ResponseBody
    public successResult CopyDoucment(Integer ID){
        successResult result=new successResult();
        try {
            LoginUserInfo Info=CompanyContext.get();
            Optional<tbInvoiceApplication> findOnes=invoiceApplicationRepository.findById(ID);
            if(findOnes.isPresent()){
                tbInvoiceApplication tb=findOnes.get();
                tbInvoiceApplication newOne=new tbInvoiceApplication();
                newOne.setTaxpayerIdentificationNumber(tb.getTaxpayerIdentificationNumber());
                newOne.setPurchaserAddress(tb.getPurchaserAddress());
                newOne.setPurchaserPhoneNumber(tb.getPurchaserPhoneNumber());
                newOne.setInvoiceTt(tb.getInvoiceTt());
                newOne.setAmount("0");
                newOne.setProjectName("");
                newOne.setInvoiceType(0);
                newOne.setApplicant(Info.getUserIdValue());
                newOne.setPurchaserBank(tb.getPurchaserBank());
                newOne.setPurchaserAccount(tb.getPurchaserAccount());
                newOne.setState(1);
                newOne.setAddTime(new Date());
                newOne.setPaymentToPayment("1");
                newOne.setDateOfApplication(new Date());
                invoiceApplicationRepository.save(newOne);
            } else throw new Exception("要复制的发票信息不存在，请刷新后再试!");
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
    @RequestMapping("/getInvoiceTotal")
    @ResponseBody
    public successResult getInvoiceTotal(){
        successResult result=new successResult();
        try
        {
            List<Map<String,Object>> OO= tbInvoiceApplicationService.getInvoiceTotal();
            result.setData(OO);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
    @RequestMapping("/commit")
    @ResponseBody
    public successResult Commit(HttpServletRequest request){
        successResult result = new successResult();
        tbInvoiceApplication res = null;
        try {
            String Data = request.getParameter("Data");
            String Text = request.getParameter("Text");
            String CommitType = "Update";
            if (Strings.isEmpty(Data) == false) {
                tbInvoiceApplication invoiceApplication = JSON.parseObject(Data, tbInvoiceApplication.class);
                if (invoiceApplication.getInvoiceApplicationId() != null) {
                    invoiceApplication.setState(2);
                    int re = tbInvoiceApplicationService.update(invoiceApplication, Text, CommitType);
                    if (re > 0) {
                        res = invoiceApplication;
                        result.setData(res);
                    }
                }
            } else throw new Exception("数据格式不正确！");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @RequestMapping("/notPass")
    @ResponseBody
    public successResult NotPass(Integer  ID,String auditText){
        successResult result=new successResult();
        try {
            LoginUserInfo Info=CompanyContext.get();
            Optional<tbInvoiceApplication> findOnes=invoiceApplicationRepository.findById(ID);
            if(findOnes.isPresent()){
                tbInvoiceApplication one=findOnes.get();
                one.setAuditResult(0);
                one.setAuditText(auditText);
                one.setAuditMan(Info.getUserIdValue());
                one.setAuditTime(new Date());
                one.setState(3);
                invoiceApplicationRepository.save(one);
            }
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
}
