package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.ListUtils;
import com.zhide.dtsystem.common.fieldObject;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.ExpenseMapper;
import com.zhide.dtsystem.mapper.FeeItemMapper;
import com.zhide.dtsystem.mapper.SuggestMapper;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.IExpenseService;
import com.zhide.dtsystem.services.define.ISuggestService;
import com.zhide.dtsystem.services.define.ISuggestUserService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @ClassName: SuggestServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2022年01月27日 10:24
 **/
@Service
public class ExpenseServiceImpl implements IExpenseService {
    @Autowired
    expenseMainRepository mainRep;
    @Autowired
    FeeItemMapper feeItemMapper;
    @Autowired
    ISuggestUserService userService;
    @Autowired
    expenseFilesRepository fileRep;
    @Autowired
    expenseChangeRecordRepository changeRep;
    @Autowired
    expenseUserRepository userRep;
    @Autowired
    ExpenseMapper expMapper;
    @Autowired
    SysLoginUserMapper loginUserMapper;
    @Autowired
    expenseSubRepository subRep;
    @Autowired
    expenseItemRepository itemRep;
    @Autowired
    expenseMoneyRepository moneyRep;
    LoginUserInfo loginUserInfo;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public expenseMain SaveAll(Map<String, Object> Data) throws Exception {
        loginUserInfo = CompanyContext.get();
        expenseMain main = SaveMain(Data);
        Integer MainID=main.getId();
        if(Data.containsKey("Sub")){
            String X=Data.get("Sub").toString();
            List<expenseSub> Subs=JSON.parseArray(X,expenseSub.class);
            SaveSub(MainID,Subs);
        }
        if (Data.containsKey("Att")) {
            String attText = Data.get("Att").toString();
            List<String> atts = JSON.parseArray(attText, String.class);
            SaveAttachment(MainID, atts);
        }
        return main;
    }

    @Override
    public void SaveItems(List<expenseItem> items) throws Exception {
        LoginUserInfo Info=CompanyContext.get();
        Integer Year=new Date().getYear()+1900;
        for(int i=0;i<items.size();i++){
            expenseItem item=items.get(i);
            if(item.getFid()==null){
                item.setCreateMan(Info.getUserIdValue());
                item.setCreateTime(new Date());
            }
            itemRep.save(item);
            List<expenseMoney> moneys= moneyRep.findAllByYearAndFid(Year,item.getFid());
            Double Money=item.getMoney();
            if(moneys.size()==0 ){
                if(Money>0.0) {
                    expenseMoney money = new expenseMoney();
                    money.setFid(item.getFid());
                    money.setMoney(item.getMoney());
                    money.setCreateTime(new Date());
                    money.setCreateMan(Info.getUserIdValue());
                    money.setYear(Year);
                    moneyRep.save(money);
                }
            } else {
                expenseMoney moneyed=moneys.get(0);
                moneyed.setMoney(Money);
                moneyRep.save(moneyed);
            }
        }
    }

    private expenseMain SaveMain(Map<String,Object> Data) throws Exception{
        expenseMain main = new expenseMain();
        Integer ID = 0;
        String Action = Data.get("Action").toString();
        if (Action.equals("Save") == false && Action.equals("Commit") == false) throw new Exception("参数异常!");
        if (Data.containsKey("id")) {
            ID = Integer.parseInt(Data.get("id").toString());
        }
        Data.remove("state");
        Optional<expenseMain> finds = mainRep.findById(ID);
        if (finds.isPresent()) {
            main = finds.get();
            int state = main.getState();
            if (state > 4) throw new Exception("业务状态异常，操作被中止!");
            if (Action.equals("Save")) {
                main.setState(1);
            } else main.setState(2);
        } else {
            main.setCreateMan(loginUserInfo.getUserIdValue());
            main.setCreateTime(new Date());
            if (Action.equals("Save")) main.setState(1);
            else main.setState(2);
            main.setDocSn(feeItemMapper.getFlowCode("FYBX"));

        }
        if(Action.equals("Commit")){
            main.setSubmitTime(new Date());
        }
        for (String Key : Data.keySet()) {
            Field target = fieldObject.findByName(main, Key);
            if (target != null) {
                Object value = Data.get(Key);
                fieldObject.setValue(main, target, value);
            }
        }
        mainRep.save(main);

        /*插入商务人员*/
        userService.AddOne(main.getId(), main.getCreateMan());
        userService.AddAll(main.getId(),main.getAllocAuditMan());
        String Method=(Action=="Save"?"保存":"提交");
        AddRecord(main.getId(),Method,Method+"了业务，具体内容为:"+JSON.toJSONString(main),loginUserInfo.getUserIdValue());
        return main;
    }
    private void SaveSub(Integer MainID,List<expenseSub> Subs){
        LoginUserInfo Info=CompanyContext.get();
        for(int i=0;i<Subs.size();i++){
            expenseSub sub=Subs.get(i);
            if(sub.getItemId()==null) continue;
            if(sub.getSubId()==null) {
                sub.setCreateMan(Info.getUserIdValue());
                sub.setCreateTime(new Date());
            }
            sub.setMainId(MainID);
        }
        subRep.saveAll(Subs);
    }
    private  void SaveAttachment(Integer  MainID, List<String> IDS) {
        for (int i = 0; i < IDS.size(); i++) {
            String ID = IDS.get(i);
            Optional<expenseFiles> findAtts=fileRep.findFirstByAttId(ID);
            if(findAtts.isPresent()==false) {
                expenseFiles att = new expenseFiles();
                att.setMainId(MainID);
                att.setAttId(ID);
                att.setCreateTime(new Date());
                fileRep.save(att);
            }
        }
    }
    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object=new pageObject();
        Map<String,Object> params=getParams(request);
        List<Map<String,Object>> datas=expMapper.getData(params);
        int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            object.setTotal(Total);
            object.setData(datas);
        }
        return object;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void ChangeMan(String IDS, Integer NewMan, String Text) throws Exception  {
        List<Integer> IDArray= ListUtils.parse(IDS,Integer.class);
        LoginUserInfo Info=CompanyContext.get();
        for(int i=0;i< IDArray.size();i++){
            Integer ID= IDArray.get(i);
            Optional<expenseMain> findMains=mainRep.findById(ID);
            if(findMains.isPresent()==true){
                expenseMain main= findMains.get();
                main.setChangeMan(NewMan);
                main.setChangeText(Text);
                main.setChangeTime(new Date());
                main.setState(3);
                mainRep.save(main);
                userService.AddOne(ID,NewMan);
                String TTT=Info.getUserName()+"将当前业务交由"+loginUserMapper.getLoginUserNameById(NewMan)+"处理,具体理由:"+Text;
                AddRecord(main.getId(),"变更审核人",TTT,Info.getUserIdValue());

                userService.AddOne(main.getId(),NewMan);
            } else throw new Exception(Integer.toString(ID)+"对应的记录不存在!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void Audit(Integer ID, Integer AuditResult, String Text) throws  Exception {
        Optional<expenseMain> findMains=mainRep.findById(ID);
        LoginUserInfo Info=CompanyContext.get();
        if(findMains.isPresent()){
            expenseMain main= findMains.get();
            main.setAuditResult(AuditResult);
            main.setAuditMan(Info.getUserIdValue());
            if(AuditResult==1)main.setState(5); else main.setState(4);
            main.setAuditTime(new Date());
            main.setAuditText(Text);

            String TTT="审核费用报销为:"+(AuditResult==1?"通过":"不通过")+",具体说明:"+(Strings.isEmpty(Text)?"未做说明":
                    Text);
            AddRecord(main.getId(),"审核费用报销",TTT,Info.getUserIdValue());
            if(AuditResult==1) userService.AddOne(main.getId(),main.getAuditMan());
        } else throw new Exception("操作业务不存在!");
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void Set(Integer ID, Integer SetResult, String Text) throws  Exception {
        Optional<expenseMain> findMains=mainRep.findById(ID);
        LoginUserInfo Info=CompanyContext.get();
        if(findMains.isPresent()){
            expenseMain main= findMains.get();
            main.setSetResult(SetResult);
            main.setSetMan(Info.getUserIdValue());
            if(SetResult==1)main.setState(6); else main.setState(4);
            main.setSetTime(new Date());
            main.setSetText(Text);

            String TTT="费用报销审批为:"+(SetResult==1?"通过":"不通过")+",具体说明:"+(Strings.isEmpty(Text)?"未做说明":
                    Text);
            AddRecord(main.getId(),"审批费用报销",TTT,Info.getUserIdValue());
           if(SetResult==1) userService.AddOne(main.getId(),main.getSetMan());
        } else throw new Exception("操作业务不存在!");
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void RemoveAll(List<Integer> MainIDS) throws Exception {
        LoginUserInfo Info=CompanyContext.get();
        for(int i=0;i<MainIDS.size();i++) {
            Integer MainID=MainIDS.get(i);
            Optional<expenseMain> findMains = mainRep.findById(MainID);
            if (findMains.isPresent()) {
                expenseMain main = findMains.get();
                int State = main.getState();
                if (State != 1 && State != 4) throw new Exception("当前业务状态不允许删除!");
                int CreateMan = main.getCreateMan();
                if (CreateMan != Info.getUserIdValue()) throw new Exception("非创建操作人不能删除该笔业务!");
            }
            fileRep.deleteAllByMainId(MainID);
            userRep.deleteAllByMainId(MainID);
            changeRep.deleteAllByMainId(MainID);
            subRep.deleteAllByMainId(MainID);
            mainRep.deleteById(MainID);
        }
    }

    private Map<String, Object> getParams(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "CreateTime";
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageIndex == 0 ? pageIndex * pageSize : pageSize * pageIndex - 1);
        params.put("End", pageSize * (pageIndex + 1));
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        String StateText = request.getParameter("State");
        if (StringUtils.isEmpty(StateText) == false) {
            params.put("State", Integer.parseInt(StateText));
        }

        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            params.put("DepID", Info.getDepId());
            params.put("RoleName", Info.getRoleName());
            params.put("UserID", Info.getUserId());
        } else throw new RuntimeException("登录信息失效，请重新登录！");

        String queryText = request.getParameter("Query");
        if (Strings.isNotEmpty(queryText)) {
            List<sqlParameter> Vs = JSON.parseArray(queryText, sqlParameter.class);
            List<sqlParameter> OrItems = sqlParameterCreator.convert(Vs);
            params.put("orItems", OrItems);
        } else params.put("orItems", new ArrayList<>());
        String highText = request.getParameter("High");
        if (Strings.isNotEmpty(highText)) {
            List<sqlParameter> Ps = JSON.parseArray(highText, sqlParameter.class);
            List<sqlParameter> AndItems = sqlParameterCreator.convert(Ps);
            params.put("andItems", AndItems);
        } else params.put("andItems", new ArrayList<>());
        return params;
    }
    private void AddRecord(Integer MainID,String Mode,String Text,Integer UserID){
        expenseChangeRecord record=new expenseChangeRecord();
        record.setChangeText(Text);
        record.setMainId(MainID);
        record.setMode(Mode);
        record.setUserId(UserID);
        record.setCreateTime(new Date());
        changeRep.save(record);
    }
}
