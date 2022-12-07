package com.zhide.dtsystem.controllers.work;

import com.zhide.dtsystem.common.ListUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.services.define.IFeeWaitDetailService;
import com.zhide.dtsystem.services.define.IFeeWaitListService;
import com.zhide.dtsystem.viewModel.ExportFeeItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/watch/feeWaitList/")
public class FeeWaitListController {

    @Autowired
    IFeeWaitListService feeWaitListService;
    @Autowired
    IFeeWaitDetailService feeDetailService;


    @RequestMapping("/index")
    public String Index() {
        return "/work/feeWaitList/index";
    }

    @RequestMapping("/getData")
    @ResponseBody
    public pageObject GetData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = feeWaitListService.getData(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/getDetail")
    @ResponseBody
    public pageObject getDetail(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = feeDetailService.getData(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/confirm")
    @ResponseBody
    public successResult confirm(Integer ID, String SHENQINGH, String OtherOfficeID, String Text) {
        successResult result = new successResult();
        try {
            result.setSuccess(feeWaitListService.confirm(ID, SHENQINGH, OtherOfficeID, Text));
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/reject")
    @ResponseBody
    public successResult reject(Integer ID) {
        successResult result = new successResult();
        try {
            result.setSuccess(feeWaitListService.reject(ID));
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @RequestMapping("/getSelectedDatas")
    @ResponseBody
    public successResult getSelectedDatas(String IDS){
        successResult result=new successResult();
        try
        {
            List<Integer> IID= ListUtils.parse(IDS,Integer.class);
            List<ExportFeeItem>Fs=feeWaitListService.getSelectedDatas(IID);
            result.setData(Fs);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
}
