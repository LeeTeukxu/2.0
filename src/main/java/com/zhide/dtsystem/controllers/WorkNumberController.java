package com.zhide.dtsystem.controllers;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.services.define.IWorkNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workNumber")
public class WorkNumberController {
    @Autowired
    IWorkNumberService workService;

    @RequestMapping("/index")
    public String Index() {
        return "workNumber";
    }

    @ResponseBody
    @RequestMapping("getTZS")
    public pageObject GetTZS(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            List<Map<String,Object>> datas = workService.getTZS(request);
            int total = 0;
            if (datas.size() > 0) {
                total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            }
            result.setData(datas);
            result.setTotal(total);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("getCPC")
    public pageObject GetCPC(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            List<Map<String,Object>> datas = workService.getCPC(request);
            int total = 0;
            if (datas.size() > 0) {
                total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            }
            result.setData(datas);
            result.setTotal(total);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("getAddFee")
    public pageObject GetAddFee(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            List<Map<String,Object>> datas  = workService.getAddFee(request);
            int total = 0;
            if (datas.size() > 0) {
                total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            }
            result.setData(datas);
            result.setTotal(total);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("getPantent")
    public pageObject GetPantent(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            List<Map<String,Object>> datas  = workService.getPantent(request);
            int total = 0;
            if (datas.size() > 0) {
                total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            }
            result.setData(datas);
            result.setTotal(total);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
