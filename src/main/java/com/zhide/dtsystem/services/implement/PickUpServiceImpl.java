package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.PickUpMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.sqlParameter;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.tbOriginalExpressRepository;
import com.zhide.dtsystem.services.define.IPickUpService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class PickUpServiceImpl implements IPickUpService {

    @Autowired
    PickUpMapper pickUpMapper;

    @Autowired
    tbOriginalExpressRepository originalExpressRepository;

//    @Override
//    public pageObject getData(HttpServletRequest request) throws Exception {
//        pageObject result=new pageObject();
//        Map<String,Object>params=getParameters(request);
//        List<String> IDS=pickUpMapper.getPickUpIdPage(params);
//        int total=pickUpMapper.getAllPickUpTotal(params);
//        Map<String,Object>secParams=new HashMap<>();
//        secParams.put("sortOrder",params.get("sortOrder"));
//        secParams.put("sortField",params.get("sortField"));
//        secParams.put("sortField1",params.get("sortField1"));
//        secParams.put("ids",IDS);
//        List<Map<String,Object>> rows=pickUpMapper.getAllDataByIds(secParams);
//        List<Map<String,Object>> datas=new ArrayList<>();
//        IDS.stream().forEach(id->{
//            Map<String,Object> kRow=null;
//             Optional<Map<String,Object>> pRow=rows.stream().filter(f->f.get("FID").toString().equals(id))
//             .findFirst();
//            if(pRow.isPresent()==true){
//                kRow=pRow.get();
//                Map<String,Object> row=new HashMap<>(kRow);
//                row.put("isLeaf", false);
//                row.put("_level", 1);
//                datas.add(row);
//            }
//            List<Map<String,Object>>findRows=rows.stream()
//                    .filter(f->f.get("PID").toString().equals(id))
//                    .collect(Collectors.toList());
//
//            for(int i=0;i<findRows.size();i++){
//                Map<String,Object> row=findRows.get(i);
//                row.put("isLeaf", true);
//                row.put("_level", 2);
//                datas.add(row);
//            }
//        });
//        result.setTotal(total);
//        result.setData(datas);
//        return result;
//    }
//    private Map<String, Object> getParameters(HttpServletRequest request) throws Exception {
//        int pageSize=Integer.parseInt(request.getParameter("pageSize"));
//        int pageIndex=Integer.parseInt(request.getParameter("pageIndex"));
//        String sortOrder=request.getParameter("sortOrder");
//        String sortField1="";
//        if(sortOrder.isEmpty())sortOrder="Desc";
//        String sortField=request.getParameter("sortField");
//        if(sortField.isEmpty())sortField="DrawNo";
//        sortField1="PickUpTime";
//        Map<String,Object> params=new HashMap<>();
//        params.put("Begin",pageIndex*pageSize);
//        params.put("End",(pageIndex+1)*pageSize);
//        params.put("sortOrder",sortOrder);
//        params.put("sortField",sortField);
//        params.put("sortField1",sortField1);
//        String queryText=request.getParameter("Query");
//        if(Strings.isNotEmpty(queryText)){
//            queryText= URLDecoder.decode(queryText,"utf-8");
//            List<sqlParameter> Vs= JSON.parseArray(queryText,sqlParameter.class);
//            List<sqlParameter> OrItems= sqlParameterCreator.convert(Vs);
//            params.put("orItems",OrItems);
//        } else params.put("orItems",new ArrayList<>());
//        String highText=request.getParameter("High");
//        if(Strings.isNotEmpty(highText)){
//            highText=URLDecoder.decode(highText);
//            List<sqlParameter> Ps=JSON.parseArray(highText,sqlParameter.class);
//            List<sqlParameter> AndItems=sqlParameterCreator.convert(Ps);
//            params.put("andItems",AndItems);
//        } else params.put("andItems",new ArrayList<>());
//        LoginUserInfo Info= CompanyContext.get();
//        if(Info!=null){
//            params.put("DepID",Info.getDepId());
//            params.put("RoleName",Info.getRoleName());
//            params.put("UserID",Info.getUserId());
//        } else throw new RuntimeException("登录信息失效，请重新登录！");
//        return params;
//    }

    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = pickUpMapper.getData(params);
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
    public pageObject getDatas(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = pickUpMapper.getDatas(params);
         int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            object.setTotal(Total);
            object.setData(datas);
        }
        return object;
    }

    private Map<String, Object> getParams(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "CreateTime";
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageIndex == 0 ? pageIndex * pageSize : pageIndex * pageSize + 1);
        params.put("End", pageSize * (pageIndex + 1));
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            params.put("DepID", Info.getDepId());
            params.put("RoleName", Info.getRoleName());
            params.put("UserID", Info.getUserId());
        } else throw new RuntimeException("登录信息失效，请重新登录！");

        String queryText = request.getParameter("Query");
        if (Strings.isNotEmpty(queryText)) {
            List<sqlParameter> Vs = JSON.parseArray(queryText, sqlParameter.class);
            if (Vs.size()>0) {
                for (int i = 0; i < Vs.size(); i++) {
                    if (Vs.get(i).getField().equals("ostateText")) {
                        params.put("ostateText", Vs.get(i).getValue());
                    } else params.put("ostateText", "");
                }
            }else {
                params.put("ostateText", "");
            }
            List<sqlParameter> OrItems = sqlParameterCreator.convert(Vs);
            params.put("orItems", OrItems);
        } else {
            params.put("ostateText","");
            params.put("orItems", new ArrayList<>());
        }
        String highText = request.getParameter("High");
        if (Strings.isNotEmpty(highText)) {
            List<sqlParameter> Ps = JSON.parseArray(highText, sqlParameter.class);
            List<sqlParameter> AndItems = sqlParameterCreator.convert(Ps);
            params.put("andItems", AndItems);
        } else params.put("andItems", new ArrayList<>());
        return params;
    }

    @Override
    public pageObject getDetailDatas(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getDetailParams(request);
        List<Map<String, Object>> datas = pickUpMapper.getDetailDatas(params);
        int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            object.setTotal(Total);
            object.setData(datas);
        }
        return object;
    }

    private Map<String, Object> getDetailParams(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "CreateTime";
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageSize * pageIndex);
        params.put("End", pageSize * (pageIndex + 1));
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
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

        String idsText = request.getParameter("IDS");
//        List<Integer> ids=new ArrayList<>();
//        if(StringUtil.isNullOrEmpty(idsText)==false){
//            ids= Arrays.stream(idsText.split(",")).mapToInt(f-> Integer.parseInt(f)).boxed().collect(toList());
//        } else ids.add(0);
        params.put("ids", idsText);
        return params;
    }

    @Override
    public int AlreadyPickUp(String PickUp, Date PickUpTime, String PickUpNumber) throws Exception {
        int result = originalExpressRepository.UpdatePickUp(PickUp, PickUpTime, PickUpNumber);
        return result;
    }

    @Override
    public int PickUpNo(String Dnum) throws Exception {
        List<String> listDnum = Arrays.asList(Dnum.split(","));
        int result = 0;
        for (int i = 0; i < listDnum.size(); i++) {
            String Coding = listDnum.get(i);
            result = originalExpressRepository.PickUpNo(Coding);
        }
        return result;
    }

    @Override
    public int UpdatePickUpStatusForDZQ(String DrawNo) throws Exception {
        int result = originalExpressRepository.UpdatePickUpStatusForDZQ(DrawNo);
        return result;
    }
}
