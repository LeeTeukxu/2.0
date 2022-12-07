package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.ContractReceiveBrowseMapper;
import com.zhide.dtsystem.mapper.FeeItemMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.casesRepository;
import com.zhide.dtsystem.repositorys.contractCodeRepository;
import com.zhide.dtsystem.repositorys.contractReceiveRepository;
import com.zhide.dtsystem.services.define.IContractReceiveService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import io.netty.util.internal.StringUtil;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ContractReceiveServiceImpl implements IContractReceiveService {
    @Autowired
    contractReceiveRepository contractRep;
    @Autowired
    contractCodeRepository codeRep;
    @Autowired
    FeeItemMapper feeItemMapper;
    @Autowired
    ContractReceiveBrowseMapper contractBrowseMapper;
    @Autowired
    casesRepository casesRep;

    Logger logger = LoggerFactory.getLogger(ContractReceiveServiceImpl.class);

    @Override
    public boolean Save(contractReceive obj) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        if (obj.getId() == null) {
            obj.setCreateMan(Info.getUserIdValue());
            obj.setCreateTime(new Date());
            Integer Type = obj.getContractType();
            Optional<contractCode> codeFind = codeRep.findById(Type);
            if (codeFind.isPresent()) {
                String xType = codeFind.get().getCode();
                String Code = feeItemMapper.getFlowCode(xType);
                obj.setContractNo(Code);
                contractRep.save(obj);
            } else throw new Exception("未进行合同编号进行设置，无法进行保存!");
        } else {
            Optional<contractReceive> findContracts = contractRep.findById(obj.getId());
            if (findContracts.isPresent()) {
                contractReceive now = findContracts.get();
                now.setClientId(obj.getClientId());
                now.setNeadSeal(obj.getNeadSeal());
                now.setContractName(obj.getContractName());
                now.setAttId(obj.getAttId());
                now.setRemark(obj.getRemark());
                now.setDrawEmp(obj.getDrawEmp());
                contractRep.save(now);
            } else throw new Exception("数据已不存在，不能进行更新。");
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean Remove(Integer ID) throws Exception {
        if (ID == null) throw new Exception("数据主键值异常!");
        if (ID == 0) throw new Exception("数据主键值异常");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
        LoginUserInfo Info = CompanyContext.get();
        Optional<contractReceive> findOnes = contractRep.findById(ID);
        if (findOnes.isPresent()) {
            contractReceive find = findOnes.get();
            List<cases> findCases = casesRep.findAllByContractNo(find.getContractNo());
            if (findCases.size() == 0) {
                Integer CType = find.getContractType();
                Optional<contractCode> codeFind = codeRep.findById(CType);
                if (codeFind.isPresent()) {
                    String ContractNo = find.getContractNo();
                    String xType = codeFind.get().getCode();
                    Integer Num = Integer.parseInt(ContractNo.substring(ContractNo.length() - 4));
                    String YearMonth = dateFormat.format(find.getDrawTime());
                    feeItemMapper.deleteFlowCode(xType, Num, YearMonth);
                    contractRep.delete(find);
                    logger.info(Info.getUserName() + "删除了合同:" + find.getContractNo() + ":" + find.getContractName());
                }
            } else {
                throw new Exception(find.getContractNo() + find.getContractName() + "已在交单业务中被引用，无法删除!");
            }
            return true;
        } else throw new Exception("要删除的合同数据已不存在!");
    }

    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject result = new pageObject();
        Map<String, Object> params = getParameters(request);
        List<Map<String, Object>> rows = contractBrowseMapper.getData(params);
        int total = 0;
        if (rows.size() > 0) {
            total = Integer.parseInt(rows.get(0).get("_TotalNum").toString());
            rows.forEach(f -> {
                if (f.containsKey("_TotalNum")) f.remove("_TotalNum");
            });
        }
        result.setData(rows);
        result.setTotal(total);
        return result;
    }

    private Map<String, Object> getParameters(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "CreateTime";
        String key = request.getParameter("key");

        String XClient = request.getParameter("ClientID");
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageIndex * pageSize + 1);
        params.put("End", (pageIndex + 1) * pageSize);
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        if (StringUtil.isNullOrEmpty(XClient) == false) {
            Integer ClientID = Integer.parseInt(XClient);
            if (ClientID > 0) params.put("ClientID", ClientID);
        }
        String queryText = request.getParameter("Query");
        if (Strings.isNotEmpty(queryText)) {
            queryText = URLDecoder.decode(queryText, "utf-8");
            List<sqlParameter> Vs = JSON.parseArray(queryText, sqlParameter.class);
            List<sqlParameter> OrItems = sqlParameterCreator.convert(Vs);
            params.put("orItems", OrItems);
        } else params.put("orItems", new ArrayList<>());
        String highText = request.getParameter("High");
        if (Strings.isNotEmpty(highText)) {
            highText = URLDecoder.decode(highText);
            List<sqlParameter> Ps = JSON.parseArray(highText, sqlParameter.class);
            List<sqlParameter> AndItems = sqlParameterCreator.convert(Ps);
            params.put("andItems", AndItems);
        } else params.put("andItems", new ArrayList<>());
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            params.put("UserID", Info.getUserId());
            params.put("RoleName", Info.getRoleName());
            params.put("DepID", Info.getDepId());
        } else throw new RuntimeException("登录信息失效，请重新登录！");
        return params;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void ChangeDrawEmp(String ID, String Value) {
        Optional<contractReceive> findOne = contractRep.findById(Integer.parseInt(ID));
        contractReceive receive = new contractReceive();
        if (findOne.isPresent()) {
            receive = findOne.get();
            receive.setDrawEmp(Integer.parseInt(Value));
        }
        contractRep.save(receive);
    }
}
