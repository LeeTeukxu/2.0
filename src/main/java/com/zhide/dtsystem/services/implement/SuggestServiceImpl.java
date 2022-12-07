package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.ListUtils;
import com.zhide.dtsystem.common.fieldObject;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.FeeItemMapper;
import com.zhide.dtsystem.mapper.SuggestMapper;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.suggestChangeRecordRepository;
import com.zhide.dtsystem.repositorys.suggestFilesRepository;
import com.zhide.dtsystem.repositorys.suggestMainRepository;
import com.zhide.dtsystem.repositorys.suggestUserRepository;
import com.zhide.dtsystem.services.define.ISuggestService;
import com.zhide.dtsystem.services.define.ISuggestUserService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

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
public class SuggestServiceImpl implements ISuggestService {
    @Autowired
    suggestMainRepository mainRep;
    @Autowired
    FeeItemMapper feeItemMapper;
    @Autowired
    ISuggestUserService userService;
    @Autowired
    suggestFilesRepository fileRep;
    @Autowired
    suggestChangeRecordRepository changeRep;
    @Autowired
    suggestUserRepository userRep;
    @Autowired
    SuggestMapper sugMapper;
    @Autowired
    SysLoginUserMapper loginUserMapper;
    LoginUserInfo loginUserInfo;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public suggestMain SaveAll(Map<String, Object> Data) throws Exception {
        loginUserInfo = CompanyContext.get();
        suggestMain main = SaveMain(Data);
        String Action = Data.get("Action").toString();
        if (Data.containsKey("Att")) {
            String attText = Data.get("Att").toString();
            List<String> atts = JSON.parseArray(attText, String.class);
            SaveAttachment(main.getId(), atts);
        }
        return main;
    }

    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object=new pageObject();
        Map<String,Object> params=getParams(request);
        List<Map<String,Object>> datas=sugMapper.getData(params);
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
            Optional<suggestMain> findMains=mainRep.findById(ID);
            if(findMains.isPresent()==true){
                suggestMain main= findMains.get();
                main.setChangeMan(NewMan);
                main.setChangeText(Text);
                main.setChangeTime(new Date());
                main.setState(3);
                mainRep.save(main);
                userService.AddOne(ID,NewMan);
                String TTT=Info.getUserName()+"将当前业务交由"+loginUserMapper.getLoginUserNameById(NewMan)+"处理,具体理由:"+Text;
                AddRecord(main.getId(),"变更处理人",TTT,Info.getUserIdValue());
                userService.AddOne(main.getId(),NewMan);
            } else throw new Exception(Integer.toString(ID)+"对应的记录不存在!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void Audit(Integer ID, Integer AuditResult, String Text) throws  Exception {
        Optional<suggestMain> findMains=mainRep.findById(ID);
        LoginUserInfo Info=CompanyContext.get();
        if(findMains.isPresent()){
            suggestMain main= findMains.get();
            main.setAuditResult(AuditResult);
            main.setActAuditMan(Info.getUserIdValue());
            if(AuditResult==1)main.setState(5); else main.setState(4);
            main.setAuditTime(new Date());
            main.setAuditText(Text);

            String TTT="受理业务为:"+(AuditResult==1?"通过":"不通过")+",具体说明:"+(Strings.isEmpty(Text)?"未做说明":
                    Text);
            AddRecord(main.getId(),"受理业务",TTT,Info.getUserIdValue());
            if(AuditResult==1) userService.AddOne(main.getId(),main.getActAuditMan());
        } else throw new Exception("操作业务不存在!");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void RemoveAll(List<Integer> MainIDS) throws Exception {
        LoginUserInfo Info=CompanyContext.get();
        for(int i=0;i<MainIDS.size();i++) {
            Integer MainID=MainIDS.get(i);
            Optional<suggestMain> findMains = mainRep.findById(MainID);
            if (findMains.isPresent()) {
                suggestMain main = findMains.get();
                int State = main.getState();
                if (State != 1 && State != 4) throw new Exception("当前业务状态不允许删除!");
                int CreateMan = main.getCreateMan();
                if (CreateMan != Info.getUserIdValue()) throw new Exception("非创建操作人不能删除该笔业务!");
            }
            fileRep.deleteAllByMainId(MainID);
            userRep.deleteAllByMainId(MainID);
            changeRep.deleteAllByMainId(MainID);
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
    private suggestMain SaveMain(Map<String,Object> Data) throws Exception{
        suggestMain main = new suggestMain();
        Integer ID = 0;
        String Action = Data.get("Action").toString();
        if (Action.equals("Save") == false && Action.equals("Commit") == false) throw new Exception("参数异常!");
        if (Data.containsKey("id")) {
            ID = Integer.parseInt(Data.get("id").toString());
        }
        Data.remove("state");
        Optional<suggestMain> finds = mainRep.findById(ID);
        if (finds.isPresent()) {
            main = finds.get();
            main.setPreFormText(main.getFormText());
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
            main.setDocSn(feeItemMapper.getFlowCode("TSJY"));

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
        main.setFormText(JSON.toJSONString(main));
        mainRep.save(main);

        /*插入商务人员*/
        userService.AddOne(main.getId(), main.getCreateMan());
        userService.AddAll(main.getId(),main.getAuditMan());
        userService.AddAll(main.getId(),main.getSuggestMan());
        return main;
    }
    private  void SaveAttachment(Integer  MainID, List<String> IDS) {
        for (int i = 0; i < IDS.size(); i++) {
            String ID = IDS.get(i);
            Optional<suggestFiles> findAtts=fileRep.findFirstByAttId(ID);
            if(findAtts.isPresent()==false) {
                suggestFiles att = new suggestFiles();
                att.setMainId(MainID);
                att.setAttId(ID);
                att.setCreateTime(new Date());
                fileRep.save(att);
            }
        }
    }
    private void AddRecord(Integer MainID,String Mode,String Text,Integer UserID){
        suggestChangeRecord record=new suggestChangeRecord();
        record.setChangeText(Text);
        record.setMainId(MainID);
        record.setMode(Mode);
        record.setUserId(UserID);
        record.setCreateTime(new Date());
        changeRep.save(record);
    }
}
