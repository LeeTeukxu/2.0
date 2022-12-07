package com.zhide.dtsystem.controllers.Express;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.PickUpMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.services.define.IPickUpService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/express/pickup")
public class PickUpController {

    @Autowired
    IPickUpService pickUpService;
    @Autowired
    PickUpMapper pickUpMapper;

    @RequestMapping("/index")
    public String Index(Map<String, Object> model) {
        LoginUserInfo loginUserInfo = CompanyContext.get();
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-HH-dd HH:mm:ss");
        model.put("DrawEmp", loginUserInfo.getUserName());
        model.put("DrawTime", dt.format(new Date()));
        return "/express/pickup/index";
    }

    @ResponseBody
    @RequestMapping("getPickUpData")
    public pageObject getPickUpData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = pickUpService.getData(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/getData")
    @ResponseBody
    public pageObject GetData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = pickUpService.getDatas(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/getDetail")
    @ResponseBody
    public pageObject GetDetail(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = pickUpService.getDetailDatas(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/alreadyPickUp")
    @ResponseBody
    public successResult AlreadyPickUp(HttpServletRequest request) {
        successResult result = new successResult();
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String PickUp = request.getParameter("PickUp");
            String PickUpTime = request.getParameter("PickUpTime");
            String PickUpNumber = request.getParameter("PickUpNumber");
            int re = pickUpService.AlreadyPickUp(PickUp, dt.parse(PickUpTime), PickUpNumber);
            if (re > 0) {
                result.setSuccess(true);
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/pickupno")
    @ResponseBody
    public successResult PickUpNo(HttpServletRequest request) {
        successResult result = new successResult();
        try {
            String Dnum = request.getParameter("Dnum");
            if (Strings.isEmpty(Dnum) == false) {
                int re = pickUpService.PickUpNo(Dnum);
                if (re > 0) {
                    result.setSuccess(true);
                }
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/dzq")
    @ResponseBody
    public successResult UpdatePickUpStatusForDZQ(HttpServletRequest request) {
        successResult result = new successResult();
        try {
            String DrawNo = request.getParameter("DrawNo");
            if (Strings.isEmpty(DrawNo) == false) {
                int re = pickUpService.UpdatePickUpStatusForDZQ(DrawNo);
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
            List<Map<String, Object>> OS = pickUpMapper.getPickUpTotal(Info.getDepIdValue(), Info.getUserIdValue(),
                    Info.getRoleName());
            result.setData(OS);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
