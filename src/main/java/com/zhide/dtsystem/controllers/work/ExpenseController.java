package com.zhide.dtsystem.controllers.work;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhide.dtsystem.common.CompanyTokenUtils;
import com.zhide.dtsystem.common.ListUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.ExpenseMapper;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.IExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: ExpenseController
 * @Author: 肖新民
 * @*TODO:费用报销
 * @CreateTime: 2022年02月13日 13:42
 **/
@RequestMapping("/expense")
@Controller
public class ExpenseController {
    @Autowired
    SysLoginUserMapper loginMapper;
    @Autowired
    IExpenseService expService;
    @Autowired
    expenseItemRepository itemRep;
    @Autowired
    expenseFilesRepository fileRep;
    @Autowired
    expenseMoneyRepository moneyRep;
    @Autowired
    expenseMainRepository mainRep;
    @Autowired
    tbProjectRepository proRep;
    @Autowired
    expenseChangeRecordRepository changeRep;
    @Autowired
    ExpenseMapper expMapper;
    @Autowired
    expenseSubRepository subRep;
    @RequestMapping("/index")
    public String Index(Map<String,Object> model){
        LoginUserInfo Info= CompanyContext.get();
        model.put("UserID",Info.getUserId());
        model.put("RoleName",Info.getRoleName());
        model.put("RoleID",Info.getRoleId());
        return "/work/expense/index";
    }
    @RequestMapping("/add")
    public String Add(Map<String,Object> model){
        LoginUserInfo Info=CompanyContext.get();
        model.put("UserID",Info.getUserId());
        model.put("RoleID",Info.getRoleId());
        model.put("DepID",Info.getDepId());
        model.put("DepName",Info.getDepName());
        model.put("Mode","Add");
        model.put("AttIDS","[]");
        expenseMain main=new expenseMain();
        main.setCreateMan(Info.getUserIdValue());
        main.setCreateTime(new Date());
        main.setDepId(Info.getDepIdValue());
        main.setAllocAuditMan(loginMapper.getManager(Info.getUserId()));
        model.put("Data", JSON.toJSONStringWithDateFormat(main,"yyyy-MM-dd"));
        return "/work/expense/add";
    }
    @RequestMapping("/edit")
    public String Edit(Integer ID,Map<String,Object> model){
        LoginUserInfo Info=CompanyContext.get();
        model.put("Mode","Edit");
        Optional<expenseMain> findMains=mainRep.findById(ID);
        if(findMains.isPresent()) {
            expenseMain main = findMains.get();
            model.put("Data", JSON.toJSONStringWithDateFormat(main, "yyyy-MM-dd"));
        }
        List<String> files=fileRep.findAllByMainId(ID).stream().map(f->f.getAttId()).collect(Collectors.toList());
        model.put("AttIDS",JSON.toJSONString(files));
        return "/work/expense/add";
    }

    @RequestMapping("/audit")
    public String Audit(Integer ID,Map<String,Object> model){
        LoginUserInfo Info=CompanyContext.get();
        model.put("Mode","Audit");
        Optional<expenseMain> findMains=mainRep.findById(ID);
        if(findMains.isPresent()) {
            expenseMain main = findMains.get();
            main.setAuditMan(Info.getUserIdValue());
            main.setAuditTime(new Date());
            model.put("Data", JSON.toJSONStringWithDateFormat(main, "yyyy-MM-dd"));
        }
        List<String> files=fileRep.findAllByMainId(ID).stream().map(f->f.getAttId()).collect(Collectors.toList());
        model.put("AttIDS",JSON.toJSONString(files));
        return "/work/expense/add";
    }
    @RequestMapping("/browse")
    public String Browse(Integer ID,Map<String,Object> model){
        LoginUserInfo Info=CompanyContext.get();
        model.put("Mode","Browse");
        Optional<expenseMain> findMains=mainRep.findById(ID);
        if(findMains.isPresent()) {
            expenseMain main = findMains.get();
            model.put("Data", JSON.toJSONStringWithDateFormat(main, "yyyy-MM-dd"));
        }
        List<String> files=fileRep.findAllByMainId(ID).stream().map(f->f.getAttId()).collect(Collectors.toList());
        model.put("AttIDS",JSON.toJSONString(files));
        return "/work/expense/add";
    }
    @RequestMapping("/set")
    public String Set(Integer ID,Map<String,Object> model){
        LoginUserInfo Info=CompanyContext.get();
        model.put("Mode","Set");
        Optional<expenseMain> findMains=mainRep.findById(ID);
        if(findMains.isPresent()) {
            expenseMain main = findMains.get();
            main.setSetMan(Info.getUserIdValue());
            main.setSetTime(new Date());
            model.put("Data", JSON.toJSONStringWithDateFormat(main, "yyyy-MM-dd"));
        }
        List<String> files=fileRep.findAllByMainId(ID).stream().map(f->f.getAttId()).collect(Collectors.toList());
        model.put("AttIDS",JSON.toJSONString(files));
        return "/work/expense/add";
    }
    @RequestMapping("/saveAll")
    @ResponseBody
    public successResult SaveAll(String Data) {
        successResult result = new successResult();
        try {
            Map<String, Object> data = JSON.parseObject(Data, new TypeReference<Map<String, Object>>() {
            });
            expService.SaveAll(data);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/getSubs")
    public List<expenseSub> getSubs(Integer MainID){
        List<expenseSub> subs= subRep.findAllByMainId(MainID);
        for(int i=0;i<subs.size();i++){
            expenseSub sub=subs.get(i);
            sub.setMoney(sub.getPrice()*sub.getNum());
        }
        return subs;
    }

    @ResponseBody
    @RequestMapping("/getData")
    public pageObject getData(HttpServletRequest request) {
        pageObject obj = new pageObject();
        try {
            obj = expService.getData(request);
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
            expService.ChangeMan(ID, NewMan, Text);
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
            List<Map<String, Object>> OX = expMapper.getStateNumber(Info.getDepIdValue(), Info.getUserIdValue(),
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
            expService.Audit(ID, AuditResult, Text);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/doSet")
    public successResult DoSet(Integer ID, Integer SetResult, String Text) {
        successResult result = new successResult();
        try {
            expService.Set(ID, SetResult, Text);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/getChangeRecord")
    public List<expenseChangeRecord> getChangeRecord(Integer ID) {
        List<expenseChangeRecord> datas = new ArrayList<>();
        if (ID != null && ID > 0) datas = changeRep.findAllByMainId(ID);
        return datas;
    }

    @ResponseBody
    @RequestMapping("/removeAll")
    public successResult RemoveAll(String IDS) {
        successResult result = new successResult();
        try {
            List<Integer> IDArray= ListUtils.parse(IDS,Integer.class);
            expService.RemoveAll(IDArray);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/saveItems")
    public successResult SaveItems(String Data){
        successResult result=new successResult();
        try {
            List<expenseItem> items=JSON.parseArray(Data,expenseItem.class);
            expService.SaveItems(items);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/getItems")
    public List<expenseItem> getItems(){
        Integer Year=new Date().getYear()+1900;
        List<expenseMoney> ms= moneyRep.findAllByYear(Year);
        List<expenseItem> items= itemRep.findAll();
        for(int i=0;i<items.size();i++){
            expenseItem item=items.get(i);
            Integer FID=item.getFid();
            Optional<expenseMoney> findMones=ms.stream().filter(f->f.getFid()==FID).findFirst();
            if(findMones.isPresent()){
                expenseMoney money= findMones.get();
                item.setMoney(money.getMoney());
            }
        }
        return items;
    }
    @ResponseBody
    @RequestMapping("/getProject")
    public List<tbProject> getProject(){
        return proRep.findAll();
    }
}
