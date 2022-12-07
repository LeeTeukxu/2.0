package com.zhide.dtsystem.controllers.Express;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.OriginalKdMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.tbOriginalKd;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.services.define.IOriginalKdDetailService;
import com.zhide.dtsystem.services.define.IOriginalKdService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/express/original/")
public class OriginalKdController {

    @Autowired
    IOriginalKdService originalKdService;
    @Autowired
    IOriginalKdDetailService originalKdDetailService;
    @Autowired
    OriginalKdMapper originalKdMapper;

    @RequestMapping("/index")
    public String Index(Map<String, Object> model) {

        LoginUserInfo loginUserInfo = CompanyContext.get();
        model.put("DrawEmp", loginUserInfo.getUserName());
        return "/express/original/index";
    }

    @RequestMapping("/getData")
    @ResponseBody
    public pageObject GetData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = originalKdService.getData(request);
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
            result = originalKdDetailService.getData(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/expressAlready")
    @ResponseBody
    public successResult ExpressAlready(String ids, HttpServletRequest request) {
        successResult result = new successResult();
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String DeliveryTime = request.getParameter("DeliveryTime");
            String Render = request.getParameter("Render");
            List<String> PackageNum = JSON.parseArray(ids, String.class);
            if (Strings.isEmpty(Render) == false) {
                int re = originalKdService.ExpressAlready(Render, dt.parse(DeliveryTime), PackageNum);
                if (re > 0) {
                    result.setSuccess(true);
                }
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/ExpressNot")
    @ResponseBody
    public successResult ExpressNot(String ids, String dnum, HttpServletRequest request) {
        successResult result = new successResult();
        try {
            List<String> PackageNum = JSON.parseArray(ids, String.class);
            if (PackageNum.size() > 0) {
                int re = originalKdService.ExpressNot(PackageNum);
                if (re > 0) {
                    result.setSuccess(true);
                }
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/xgkd")
    public String XGKD(String PackageNum, Map<String, Object> model) {
        model.put("PackageNum", PackageNum);
        model.put("Mode", "Edit");
        Optional<tbOriginalKd> findtb = originalKdMapper.findByPackageNum(PackageNum);
        if (findtb.isPresent()) {
            model.put("LoadData", JSON.toJSONString(findtb.get()));
        } else model.put("LoadData", "{}");
        return "/express/original/xgkd";
    }

    @RequestMapping("saveExpress")
    @ResponseBody
    public successResult SaveExpress(HttpServletRequest request) {
        successResult result = new successResult();
        tbOriginalKd res = null;
        try {
            String Data = request.getParameter("Data");
            if (Strings.isEmpty(Data) == false) {
                tbOriginalKd originalKd = JSON.parseObject(Data, tbOriginalKd.class);
                res = originalKdService.SaveExpress(originalKd);
                result.setData(res);
            } else throw new Exception("数据格式不正确！");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/orginalkdno")
    @ResponseBody
    public successResult OriginalKdNo(String ids, String DetailsID, HttpServletRequest request) {
        successResult result = new successResult();
        try {
            String IsSelectAll = request.getParameter("IsSelectAll");
            String a = request.getParameter("ids");
            List<String> listDnum = JSON.parseArray(ids, String.class);
            List<String> listDetailsID = Arrays.asList(DetailsID.split(","));
            if (listDnum.size() > 0) {
                int re = originalKdService.OriginalKdNo(listDnum, IsSelectAll, listDetailsID);
                if (re > 0) {
                    result.setSuccess(true);
                }
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/refreshTotal")
    public successResult refreshTotal() {
        successResult result = new successResult();
        try {
            LoginUserInfo Info = CompanyContext.get();
            List<Map<String, Object>> OS = originalKdMapper.getOrignalKdTotal(Info.getDepIdValue(),
                    Info.getUserIdValue(), Info.getRoleName());
            result.setData(OS);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
