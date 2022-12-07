package com.zhide.dtsystem.controllers.work;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhide.dtsystem.common.ListUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.SuggestMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.suggestChangeRecordRepository;
import com.zhide.dtsystem.repositorys.suggestFilesRepository;
import com.zhide.dtsystem.repositorys.suggestMainRepository;
import com.zhide.dtsystem.repositorys.suggestTypeRepository;
import com.zhide.dtsystem.services.define.ISuggestService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

/**
 * @ClassName: SuggestController
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2022年01月26日 9:18
 **/
@Controller
@RequestMapping("/suggest")
public class SuggestController {
    @Autowired
    ISuggestService sugService;
    @Autowired
    suggestTypeRepository typeRep;
    @Autowired
    suggestMainRepository sugRep;
    @Autowired
    SuggestMapper sugMapper;
    @Autowired
    suggestChangeRecordRepository changeRep;
    @Autowired
    suggestFilesRepository fileRep;

    @RequestMapping("/index")
    public String Index(Map<String, Object> model) {
        LoginUserInfo Info = CompanyContext.get();
        model.put("UserID", Info.getUserIdValue());
        model.put("RoleID", Info.getRoleId());
        model.put("RoleName", Info.getRoleName());
        return "/work/suggest/index";
    }

    @RequestMapping("/add")
    public String Add(String mode, Map<String, Object> model) {
        model.put("Mode", mode);
        LoginUserInfo Info = CompanyContext.get();
        model.put("UserID", Info.getUserIdValue());
        model.put("Now", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        model.put("Data", "{}");
        model.put("AttIDS", "[]");
        return "/work/suggest/add";
    }

    @RequestMapping("/edit")
    public String Edit(Integer ID, String mode, Map<String, Object> model) {
        model.put("Mode", mode);
        LoginUserInfo Info = CompanyContext.get();
        Optional<suggestMain> findMains = sugRep.findById(ID);
        if (findMains.isPresent() == true) {
            suggestMain main = findMains.get();
            model.put("Data", JSON.toJSONString(main));
        } else model.put("Data", "{}");
        List<String> AttIDS = fileRep.findAllByMainId(ID).stream().map(f -> f.getAttId()).collect(Collectors.toList());
        model.put("AttIDS", JSON.toJSONString(AttIDS));
        return "/work/suggest/add";
    }

    @RequestMapping("/audit")
    public String Audit(Integer ID, String mode, Map<String, Object> model) {
        model.put("Mode", mode);
        LoginUserInfo Info = CompanyContext.get();
        Optional<suggestMain> findMains = sugRep.findById(ID);
        if (findMains.isPresent() == true) {
            suggestMain main = findMains.get();
            model.put("Data", JSON.toJSONString(main));
        } else model.put("Data", "{}");
        List<String> AttIDS = fileRep.findAllByMainId(ID).stream().map(f -> f.getAttId()).collect(Collectors.toList());
        model.put("AttIDS", JSON.toJSONString(AttIDS));
        return "/work/suggest/add";
    }

    @RequestMapping("/saveAll")
    @ResponseBody
    public successResult SaveAll(String Data) {
        successResult result = new successResult();
        try {
            Map<String, Object> data = JSON.parseObject(Data, new TypeReference<Map<String, Object>>() {
            });
            sugService.SaveAll(data);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getTypes")
    public List<suggestType> getTypes(Integer Type) {
        List<suggestType> datas = new ArrayList<>();
        try {
            datas = typeRep.findAllByType(Type);
        } catch (Exception ax) {

        }
        return datas;
    }

    @ResponseBody
    @RequestMapping("/getByType")
    public List<ComboboxItem> getByType(Integer Type) {
        List<ComboboxItem> Items = new ArrayList<>();
        if (Type != null) {
            Items = typeRep.findAllByType(Type).stream().map(f -> new ComboboxItem(Integer.toString(f.getId()),
                    f.getName())).collect(Collectors.toList());
        } else Items = typeRep.findAll().stream().map(f -> new ComboboxItem(Integer.toString(f.getId()),
                f.getName())).collect(Collectors.toList());
        return Items;
    }

    @ResponseBody
    @RequestMapping("/saveType")
    public successResult saveType(String Data) {
        successResult result = new successResult();
        try {
            List<suggestType> Datas = JSON.parseArray(Data, suggestType.class);
            typeRep.saveAll(Datas);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getData")
    public pageObject getData(HttpServletRequest request) {
        pageObject obj = new pageObject();
        try {
            obj = (sugService.getData(request));
        } catch (Exception ax) {
            obj.raiseException(ax);
        }
        return obj;
    }

    @ResponseBody
    @RequestMapping("/changeMan")
    public successResult ChangeMan(String ID, int NewMan, String Text) {
        successResult result = new successResult();
        try {
            sugService.ChangeMan(ID, NewMan, Text);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getStateNumber")
    public successResult GetStateNumber() {
        successResult result = new successResult();
        try {
            LoginUserInfo Info = CompanyContext.get();
            List<Map<String, Object>> OX = sugMapper.getStateNumber(Info.getDepIdValue(), Info.getUserIdValue(),
                    Info.getRoleName());
            result.setData(OX);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/doAudit")
    public successResult DoAudit(Integer ID, Integer AuditResult, String Text) {
        successResult result = new successResult();
        try {
            sugService.Audit(ID, AuditResult, Text);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getChangeRecord")
    public List<suggestChangeRecord> getChangeRecord(Integer ID) {
        List<suggestChangeRecord> datas = new ArrayList<>();
        if (ID != null && ID > 0) datas = changeRep.findAllByMainId(ID);
        return datas;
    }

    @ResponseBody
    @RequestMapping("/removeAll")
    public successResult RemoveAll(String IDS) {
        successResult result = new successResult();
        try {
            List<Integer> IDArray= ListUtils.parse(IDS,Integer.class);
            sugService.RemoveAll(IDArray);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
