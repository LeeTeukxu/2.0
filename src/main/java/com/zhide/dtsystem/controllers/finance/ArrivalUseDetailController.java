package com.zhide.dtsystem.controllers.finance;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.arrivalUseDetail;
import com.zhide.dtsystem.services.define.IArrivalUseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName: ArrivalUseDetailController
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年04月21日 10:35
 **/
@Controller
@RequestMapping("/arrivalUse")
public class ArrivalUseDetailController {

    @Autowired
    IArrivalUseDetailService detailService;

    @RequestMapping("/getMain")
    @ResponseBody
    public pageObject getMain(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = detailService.getMain(request);
        } catch (Exception e) {
            result.raiseException(e);
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/getSub")
    @ResponseBody
    public pageObject getSub(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = detailService.getSub(request);
        } catch (Exception e) {
            result.raiseException(e);
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/getDetail")
    @ResponseBody
    public pageObject getDetail(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = detailService.getDetail(request);
        } catch (Exception e) {
            result.raiseException(e);
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/saveDetail")
    @ResponseBody
    public successResult SaveDetail(String Data) {
        successResult Result = new successResult();
        try {
            List<arrivalUseDetail> Datas = JSON.parseArray(Data, arrivalUseDetail.class);
            Integer X = detailService.SaveDetail(Datas);
            Result.setData(X);
        } catch (Exception ax) {
            Result.raiseException(ax);
        }
        return Result;
    }

    @RequestMapping("/saveSub")
    @ResponseBody
    public successResult SaveSub(String Data) {
        successResult Result = new successResult();
        try {
            List<arrivalUseDetail> Datas = JSON.parseArray(Data, arrivalUseDetail.class);
            detailService.SaveSub(Datas);
        } catch (Exception ax) {
            Result.raiseException(ax);
        }
        return Result;
    }

    @RequestMapping("/removeOne")
    @ResponseBody
    public successResult RemoveOne(int ID) {
        successResult Result = new successResult();
        try {
            detailService.RemoveOne(ID);
        } catch (Exception ax) {
            Result.raiseException(ax);
        }
        return Result;
    }
    @RequestMapping("/auditOne")
    @ResponseBody
    public successResult AuditOne(int ID,int Result,String ResultText){
        successResult res = new successResult();
        try {
            if(Result==2) {
                detailService.AuditOne(Result, ID, ResultText);
            } else detailService.RejectOne(Result,ID,ResultText);
        } catch (Exception ax) {
            res.raiseException(ax);
        }
        return res;
    }
}
