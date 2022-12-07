package com.zhide.dtsystem.controllers.watch;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.controllers.work.FeeItemController;
import com.zhide.dtsystem.mapper.FeeItemMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.yearFeeListRepository;
import com.zhide.dtsystem.services.define.IFeeListNewService;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/watch/yearList")
public class YearWatchListController {
    org.slf4j.Logger logger = LoggerFactory.getLogger(FeeItemController.class);
    @Autowired
    IFeeListNewService newService;
    @Autowired
    yearFeeListRepository yearFeeListRep;
    @Autowired
    FeeItemMapper feeItemMapper;

    @RequestMapping("/index")
    public String index(Map<String, Object> model) {
        Date dNow = new Date();
        Date yNow = DateUtils.addDays(dNow, -1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        model.put("now", simpleDateFormat.format(dNow));
        model.put("next", simpleDateFormat.format(yNow));
        LoginUserInfo CInfo = CompanyContext.get();
        model.put("CompanyID", CInfo.getCompanyId());
        model.put("RoleName",CInfo.getRoleName());
        return "/work/yearWatch/list";
    }

    @RequestMapping("/getData")
    @ResponseBody
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = newService.getData(request);
        } catch (Exception ax) {
            logger.info(ax.getMessage());
        }
        return result;
    }

    @RequestMapping("/addPayForResult")
    @ResponseBody
    public successResult AddPayForResult(String IDS) {
        successResult result = new successResult();
        try {
            List<Integer> ids = JSON.parseArray(IDS, Integer.class);
            result.setSuccess(newService.addPayForList(ids));
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
            newService.ChangeXMoney(IDArray, Money);
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
            newService.ChangePayForStatus(IDArray, Status);
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
            newService.ChangeSXMoney(IDArray, Money);
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
            List<Map<String, Object>> OS = feeItemMapper.getYearTotal(Info.getDepIdValue(), Info
                            .getUserIdValue(),
                    Info.getRoleName());
            result.setData(OS);

        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
