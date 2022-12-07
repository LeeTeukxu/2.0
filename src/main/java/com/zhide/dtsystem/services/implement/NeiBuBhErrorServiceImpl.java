package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.NeiBuBHErrorMapper;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.pantentInfoRepository;
import com.zhide.dtsystem.repositorys.patentInfoPermissionRepository;
import com.zhide.dtsystem.services.NBBHCode;
import com.zhide.dtsystem.services.define.INeiBuBHErrorService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @ClassName: NeiBuBhErrorServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年02月25日 15:20
 **/
@Service
public class NeiBuBhErrorServiceImpl  implements INeiBuBHErrorService {
    @Autowired
    NeiBuBHErrorMapper NBMapper;
    @Autowired
    NBBHCode nbDecode;
    @Autowired
    pantentInfoRepository pRep;
    @Autowired
    SysLoginUserMapper userMapper;
    @Autowired
    patentInfoPermissionRepository pxRep;
    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject result=new pageObject();

        Map<String,Object> args=getParameters(request);
        List<Map<String,Object>>Datas=NBMapper.getData(args);
        for(int i=0;i<Datas.size();i++){
            Map<String,Object>Data=Datas.get(i);
            int Total=Integer.parseInt(Data.get("_TotalNum").toString());
            String Shenqingh=Data.get("SHENQINGH").toString();
            String NBBH=Data.get("NEIBUBH").toString();
            String XNBBH=NBBH.replace("XS","YW");
            NBBHInfo Info= nbDecode.Parse(NBBH);
            List<String> Errors=new ArrayList<>();
            Info.foreach((type,arr)->{
                if(arr.size()==0){
                    if(type.equals("BH")==false &&  XNBBH.indexOf(type)>-1) {
                        String Pre="";
                        if(type.equals("LC"))Pre="流程";
                        else if(type.equals("JS"))Pre="技术";
                        else if(type.equals("KH"))Pre="客户";
                        else if(type.equals("YW") || type.equals("XS"))Pre="业务";
                        Errors.add(Pre+":"+GetValueByType(XNBBH,type) + "无效");
                    }
                }
            });
            if(Errors.size()>0){
                Data.replace("Error", StringUtils.join(Errors,","));
            } else {
                Boolean NBFixed=false;
                if(Data.containsKey("NBFixed")){
                    Object O=Data.get("NBFixed");
                    if(ObjectUtils.isEmpty(O)==false){
                        NBFixed=Boolean.parseBoolean(O.toString());
                    }
                }
                if(NBFixed==false)Data.replace("Error", "配置正确,待程序处理"); else Data.replace("Error","正确");
            }
            result.setTotal(Total);
        }
        result.setData(Datas);
        return result;
    }
    private String GetValueByType(String BH,String Type){
        String[] WS=BH.split(";");
        for(String Word:WS){
            if(Word.startsWith(Type)) return Word.replace(Type+":","");
        }
        return "";
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer UpdateAll(String Old, String Now) throws Exception {
        Integer XNum=userMapper.getNumberByName(Now);
        if(XNum==0)throw new Exception(Now+"在系统中不存在，不能进行替换。");
        List<pantentInfo> alls=pRep.getAllUpdateNBBHItems();
        List<pantentInfo>XResult=new ArrayList<>();
        for(int i=0;i<alls.size();i++){
            pantentInfo item=alls.get(i);
            String NBBH=item.getNeibubh();
            if(StringUtils.isEmpty(NBBH)==true) continue;
            if(NBBH.indexOf(Old)>-1){
                NBBH=NBBH.replace(Old,Now);
                item.setNeibubh(NBBH);
                XResult.add(item);
            }
        }
        if(XResult.size()>0) pRep.saveAll(XResult);
        return XResult.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void SaveAll(String shenqingh, NBBHInfo getInfo) {
        pxRep.deleteAllByShenqingh(shenqingh);
        getInfo.foreach((type, items) -> {
            for (int n = 0; n < items.size(); n++) {
                UInfo item = items.get(n);
                List<patentInfoPermission> all = pxRep.findAllByShenqinghAndUsertypeAndUserid(shenqingh, type, item.getID());
                if (all.size() == 0) {
                    //logger.info(shenqingh + "的内部编号解析成功,准备写入数据:" + JSON.toJSONString(item));
                    patentInfoPermission px = new patentInfoPermission();
                    px.setUsertype(type);
                    px.setUserid(item.getID());
                    px.setShenqingh(shenqingh);
                    pxRep.save(px);
                }
            }
        });
        if(getInfo.isDecodeAll()==true){
            Optional<pantentInfo> findOnes=pRep.findByShenqingh(shenqingh);
            if(findOnes.isPresent()){
                pantentInfo findOne=findOnes.get();
                findOne.setNbFixed(true);
                findOne.setNbFixedTime(new Date());
                pRep.save(findOne);
            }
        }
    }

    private Map<String, Object> getParameters(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "QX";
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageIndex * pageSize + 1);
        params.put("End", (pageIndex + 1) * pageSize);
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
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
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            params.put("DepID", Info.getDepId());
            params.put("RoleName", Info.getRoleName());
            params.put("UserID", Info.getUserId());
        } else throw new RuntimeException("登录信息失效，请重新登录！");
        return params;
    }
}
