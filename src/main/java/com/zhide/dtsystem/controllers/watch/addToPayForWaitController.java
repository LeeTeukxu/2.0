package com.zhide.dtsystem.controllers.watch;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.allFeePayForResult;
import com.zhide.dtsystem.repositorys.yearFeePayForResultRepository;
import com.zhide.dtsystem.services.define.IAddToPayForWaitService;
import freemarker.template.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/watch/addToPayForWait")
public class addToPayForWaitController {

    @Autowired
    IAddToPayForWaitService payforService;
    @Autowired
    yearFeePayForResultRepository yearFeeRep;

    @RequestMapping("/index")
    public String Index(String Type, String IDS, String Mode, Map<String, Object> model) {
        List<Integer> allIDS =
                Arrays.stream(StringUtil.split(IDS, ',')).map(f -> Integer.parseInt(f)).collect(Collectors.toList());

        allFeePayForResult result = null;
        if (Mode.equals("Add")) {
            result = payforService.initByFeeItemIDS(Type, allIDS);
        } else {
            Optional<allFeePayForResult> Fs = yearFeeRep.findById(Integer.parseInt(IDS));
            if (Fs.isPresent()) result = Fs.get();
        }
        model.put("obj", JSON.toJSONString(result));
        model.put("mode", Mode);
        model.put("type", Type);
        return "/work/yearWatch/addToPayForWait";
    }

    @RequestMapping("/otherOfficeIndex")
    public String OtherOfficeIndex(String Type, String IDS, String Mode, Map<String, Object> model) {
        List<Integer> allID =
                Arrays.stream(StringUtil.split(IDS, ',')).map(f -> Integer.parseInt(f)).collect(Collectors.toList());

        allFeePayForResult result = null;
        if (Mode.equals("Add")) {
            result = payforService.OtherOfficeFeeinitByFeeItemIDS(Type, allID);
        }
//        else {
//            Optional<allFeePayForResult> Fs=yearFeeRep.findById(Integer.parseInt(IDS));
//            if(Fs.isPresent()) result=Fs.get();
//        }
        model.put("obj", JSON.toJSONString(result));
        model.put("mode", Mode);
        model.put("type", Type);
        return "/work/yearWatch/addToPayForWait";
    }

    @ResponseBody
    @RequestMapping("/save")
    public successResult Save(String Data, String Type) {
        successResult result = new successResult();
        try {
            allFeePayForResult rr = JSON.parseObject(Data, allFeePayForResult.class);
            result.setSuccess(payforService.Save(rr, Type));
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
