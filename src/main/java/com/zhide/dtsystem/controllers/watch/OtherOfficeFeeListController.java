package com.zhide.dtsystem.controllers.watch;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.OtherOfficeFeeListMapper;
import com.zhide.dtsystem.methodTestMain;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.otherOfficeFee;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.services.define.IFymxWindowService;
import com.zhide.dtsystem.services.define.IOtherOfficeFeeListService;
import com.zhide.dtsystem.services.define.IPatentInfoService;
import com.zhide.dtsystem.services.instance.otherOfficeFeeCounter;
import org.apache.commons.lang.time.DateUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/work/otherOfficeFee")
public class OtherOfficeFeeListController {
    org.slf4j.Logger logger = LoggerFactory.getLogger(methodTestMain.class);
    @Autowired
    IOtherOfficeFeeListService otherOfficeFeeListService;
    @Autowired
    IPatentInfoService patentInfoService;
    @Autowired
    IFymxWindowService fymxWindowService;
    @Autowired
    otherOfficeFeeCounter Counter;
    @Autowired
    OtherOfficeFeeListMapper otherOfficeFeeListMapper;

    @RequestMapping("/index")
    public String index(Map<String, Object> model) {
        Date dNow = new Date();
        Date yNow = DateUtils.addDays(dNow, -1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Integer> X = Counter.getResult();
        model.put("now", simpleDateFormat.format(dNow));
        model.put("next", simpleDateFormat.format(yNow));
        LoginUserInfo CInfo=CompanyContext.get();
        model.put("RoleName",CInfo.getRoleName());
        //model.put("States",X);
        return "/work/otherOfiiceFee/list";
    }

    @RequestMapping("/getData")
    @ResponseBody
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = otherOfficeFeeListService.getData(request);
        } catch (Exception ax) {
            logger.info(ax.getMessage());
        }
        return result;
    }

    @RequestMapping("/zlzhIndex")
    public String zlzhIndex(Map<String, Object> model) {
        model.put("Users", JSON.toJSONString(patentInfoService.GetLoginUserHash()));
        return "/work/otherOfiiceFee/zlzhIndex";
    }

    @RequestMapping("/fymxIndex")
    public String fymxIndex(String Type, Map<String, Object> model) {
        model.put("Type", Type);
        return "/work/otherOfiiceFee/fymxIndex";
    }

    @RequestMapping("/add")
    public String Add(String Type, Map<String, Object> model) {
        Date nowTime = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("yyyyMMddHHmmss");
        StringBuilder sdt = new StringBuilder("ZD");
        LoginUserInfo loginUserInfo = CompanyContext.get();
        model.put("Mode", "Add");
        model.put("SHENQINGH", "");
        model.put("ExpenseItem", "{}");
        model.put("Type", "{}");
        model.put("ID", "");
        model.put("LoadData", "{}");
        return "/work/otherOfiiceFee/edit";
    }

    @RequestMapping("/getFymxDataWindow")
    @ResponseBody
    public pageObject getFymxDataWindow(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = fymxWindowService.getDataWindow(request);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/save")
    @ResponseBody
    public successResult save(HttpServletRequest request) {
        successResult result = new successResult();
        try {
            String Data = request.getParameter("Data");
            if (Strings.isEmpty(Data) == false) {
                List<otherOfficeFee> list = JSON.parseArray(Data, otherOfficeFee.class);
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setOtherOfficeStates(1);
                }
                otherOfficeFeeListService.SaveAll(list);
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/edit")
    public String edit(String SHENQINGH, String ExpenseItem, String Type, String ID, Map<String, Object> model) {
        model.put("SHENQINGH", SHENQINGH);
        model.put("ExpenseItem", ExpenseItem);
        model.put("Type", Type);
        model.put("ID", ID);
        model.put("Mode", "Edit");
        model.put("LoadData", "{}");
        return "/work/otherOfiiceFee/edit";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public successResult remove(@RequestBody List<Integer> ids) {
        successResult result = new successResult();
        try {
            otherOfficeFeeListService.remove(ids);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/changePayStatus")
    public successResult changePayStatus(String IDS, String Status) {
        successResult result = new successResult();
        try {
            List<Integer> IDArray = JSON.parseArray(IDS, Integer.class);
            otherOfficeFeeListService.ChangePayForStatus(IDArray, Status);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/changeJiaoDuiMoney")
    @ResponseBody
    public successResult changeJiaoDuiMoney(String IDS, double Money) {
        successResult result = new successResult();
        try {
            List<Integer> IDArray = JSON.parseArray(IDS, Integer.class);
            otherOfficeFeeListService.ChangeXMoney(IDArray, Money);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/changeShouXiuMoney")
    @ResponseBody
    public successResult changeShouXiuMoney(String IDS, double Money) {
        successResult result = new successResult();
        try {
            List<Integer> IDArray = JSON.parseArray(IDS, Integer.class);
            otherOfficeFeeListService.ChangeSXMoney(IDArray, Money);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/removeGridLast")
    public successResult RemoveGridLast(String ID) {
        successResult result = new successResult();
        try {
            if (ID != "") {
                otherOfficeFeeListService.Remove(ID);
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
            List<Map<String, Object>> OS = otherOfficeFeeListMapper.getOtherOfficalTotal(Info.getDepIdValue(),
                    Info.getUserIdValue(), Info.getRoleName());
            result.setData(OS);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getLinkMan")
    public List<Map<String, Object>> getLinkMan(int ClientID) {
        return otherOfficeFeeListService.getLinkMan(ClientID);
    }

    @ResponseBody
    @RequestMapping("/getLinkManInfo")
    public successResult getLinkManInfo(int LinkID) {
        successResult result = new successResult();
        try {
            List<Map<String, Object>> obj = otherOfficeFeeListService.getLinkManInfo(LinkID);
            result.setData(obj);
            result.setSuccess(true);
        } catch (Exception ax) {
            result.setSuccess(false);
            result.raiseException(ax);
        }
        return result;
    }
}
