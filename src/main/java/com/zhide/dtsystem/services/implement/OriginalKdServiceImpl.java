package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.EntityHelper;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.OriginalKdMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.sqlParameter;
import com.zhide.dtsystem.models.tbOriginalKd;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.tbOriginalExpressRepository;
import com.zhide.dtsystem.repositorys.tbOriginalKdRepository;
import com.zhide.dtsystem.services.define.IOriginalKdService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class OriginalKdServiceImpl implements IOriginalKdService {
    @Autowired
    OriginalKdMapper originalKdMapper;

    @Autowired
    tbOriginalKdRepository originalKdRepository;
    @Autowired
    tbOriginalExpressRepository tbOriginalExpressRepository;

    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = originalKdMapper.getData(params);
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
        if (sortField.isEmpty()) sortField = "ApplicationTime";
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
                    if (Vs.get(i).getField().equals("PackageStatus")) {
                        params.put("PackageStatus", Vs.get(i).getValue());
                    } else params.put("PackageStatus", "");
                }
            }else {
                params.put("PackageStatus", "");
            }
            List<sqlParameter> OrItems = sqlParameterCreator.convert(Vs);
            params.put("orItems", OrItems);
        } else {
            params.put("PackageStatus", "");
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
    public int ExpressAlready(String Render, Date DeliveryTime, List<String> PackageNum) throws Exception {
        int result = 0;
        for (int i = 0; i < PackageNum.size(); i++) {
            String PackageNumber = PackageNum.get(i);
            result = UpdatePackageInfo(DeliveryTime, Render, PackageNumber);
        }
        return result;
    }

    private int UpdatePackageInfo(Date DeliveryTime, String Render, String PackageNum) {
        int re = originalKdRepository.UpdatePackageInfo(DeliveryTime, Render, PackageNum);
        return re;
    }

    @Override
    public int ExpressNot(List<String> PackageNum) throws Exception {
        int result = 0;
        for (int i = 0; i < PackageNum.size(); i++) {
            String PackageNumber = PackageNum.get(i);
            result = UpdatePackageInfoNot(PackageNumber);
            tbOriginalExpressRepository.UpdateOriginalStates(3, PackageNumber);
        }
        return result;
    }

    private int UpdatePackageInfoNot(String PackageNum) {
        int re = originalKdRepository.ExpressNot(PackageNum);
        return re;
    }

    @Override
    public tbOriginalKd SaveExpress(tbOriginalKd originalKd) throws Exception {
        if (originalKd.getOriginalKdId() != null) {
            Optional<tbOriginalKd> fOriginalKd = originalKdRepository.findById(originalKd.getOriginalKdId());
            originalKd.setPackageStatus(2);
            if (fOriginalKd.isPresent()) {
                EntityHelper.copyObject(originalKd, fOriginalKd.get());
            }
        }
        originalKdRepository.save(originalKd);
        tbOriginalExpressRepository.UpdateOriginalStates(4, originalKd.getPackageNum());
        return originalKd;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int OriginalKdNo(List<String> Dnum, String IsSelectAll, List<String> PackageNum) throws Exception {
        int result = 0;
        for (int i = 0; i < Dnum.size(); i++) {
            String Coding = Dnum.get(i);
            originalKdRepository.OriginalKdNo(Coding);
        }
        if (IsSelectAll != "") {
            for (int j = 0; j < PackageNum.size(); j++) {
                String BGBH = PackageNum.get(j);
                result = originalKdRepository.delOriginalKd(BGBH);
            }
        }
        return result;
    }
}
