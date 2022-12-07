package com.zhide.dtsystem.controllers.watch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.PantentInfoMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.feeMemo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.services.define.IFeeMemoService;
import com.zhide.dtsystem.services.define.IFeeWatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/watch/feeWatch")
public class FeeWatchController {
    @Autowired
    IFeeWatchService feeWatchService;
    @Autowired
    IFeeMemoService feeMemoService;
    @Autowired
    PantentInfoMapper pantentInfoMapper;

    @RequestMapping("/index")
    public String Index() {
        return "/work/feeWatch/index";
    }

    @ResponseBody
    @RequestMapping("/getData")
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = feeWatchService.getData(request);
        } catch (Exception ax) {
            result.raiseException(ax);
            ax.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/importYearData")
    @ResponseBody
    public successResult importYearData(String Data, String Mode) {
        successResult result = new successResult();
        try {
            List<Map<String, Object>> Datas = JSON.parseObject(Data, new TypeReference<List<Map<String, Object>>>() {
            });
            Datas = feeWatchService.ImportYearData(Mode, Datas);
            result.setData(Datas);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/importApplyData")
    @ResponseBody
    public successResult importApplyData(String Data, String Mode) {
        successResult result = new successResult();
        try {
            List<Map<String, Object>> Datas = JSON.parseObject(Data, new TypeReference<List<Map<String, Object>>>() {
            });
            Datas = feeWatchService.ImportApplyData(Mode, Datas);
            result.setData(Datas);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/addMemo")
    public String AddMemo(String  ID, String Type, Map<String, Object> model) {
        model.put("ID", ID);
        model.put("Type", Type);
        model.put("BatchMode",ID.indexOf(",")>-1);
        return "/work/feeWatch/addMemo";
    }

    @ResponseBody
    @RequestMapping("/saveMemo")
    public successResult saveMemo(String Data) {
        successResult result = new successResult();
        try {
            List<feeMemo> feeMemos = JSON.parseArray(Data, feeMemo.class);
            feeMemoService.SaveAll(feeMemos);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getFeeMemo")
    public List<feeMemo> getFeeMemo(String  ID, String Type) {
        List<feeMemo> res=new ArrayList<>();
        if(ID.indexOf(",")>-1){

        }else {
            res= feeMemoService.GetData(Integer.parseInt(ID), Type);
        }
        return res;
    }

    @ResponseBody
    @RequestMapping("/deleteByID")
    public successResult Delete(int ID) {
        successResult result = new successResult();
        try {
            feeMemoService.DeleteByID(ID);
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
            List<Map<String, Object>> OS = pantentInfoMapper.getFeeItemTotal(Info.getDepIdValue(), Info
                            .getUserIdValue(),
                    Info.getRoleName());
            result.setData(OS);

        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/changeValue")
    public successResult changeValue(String SHENQINGHS, String Code, Integer State) {
        successResult result = new successResult();
        try {
            List<String> SH = Arrays.asList(SHENQINGHS.split(","));
            feeWatchService.ChangeValue(SH, Code, State);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/removeOne")
    public successResult RemoveOne(Integer ID){
        successResult result=new successResult();
        try
        {
            feeWatchService.RemoveOne(ID);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/saveImage")
    public successResult saveImage(String Data){
        successResult result=new successResult();
        try {
            feeMemo memo=JSON.parseObject(Data,feeMemo.class);
            feeMemoService.SaveImage(memo);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
}
