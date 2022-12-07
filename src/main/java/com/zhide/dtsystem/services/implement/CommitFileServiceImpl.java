package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.CasesCommitBrowseMapper;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.casesCommitFileRepository;
import com.zhide.dtsystem.repositorys.casesRepository;
import com.zhide.dtsystem.repositorys.casesYwAcceptRepository;
import com.zhide.dtsystem.repositorys.tbAttachmentRepository;
import com.zhide.dtsystem.services.define.ICommitFileService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
public class CommitFileServiceImpl implements ICommitFileService {
    @Autowired
    casesCommitFileRepository commitFileRep;
    @Autowired
    casesRepository casesRep;
    @Autowired
    casesYwAcceptRepository ywAcceptRep;
    @Autowired
    tbAttachmentRepository attRep;
    @Autowired
    CasesCommitBrowseMapper commitBrowseMapper;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public casesCommitFile SaveAll(casesCommitFile item, String Action) throws Exception {
        casesCommitFile target = null;
        LoginUserInfo LoginInfo = CompanyContext.get();
        Integer Id = item.getId();
        if (Id == null) Id = 0;
        if (Id == 0) {
            item.setCreateTime(new Date());
            if (Action.equals("Save")) item.setState(1);
            else item.setState(2);
            commitFileRep.save(item);
            target = item;
        } else {
            Optional<casesCommitFile> findOnes = commitFileRep.findById(Id);
            if (findOnes.isPresent()) {
                if (StringUtils.isEmpty(item.getAttId()) == true) {
                    throw new Exception("必须要上传技术文件。");
                }
                casesCommitFile accept = findOnes.get();
                //accept.setMemo(item.getMemo());
                //accept.setAtts(item.getAtts());
                if (Action.equals("Save")) accept.setState(1);
                else if (Action.equals("Commit")) {
                    accept.setState(2);
                }
                commitFileRep.save(accept);
                target = accept;
            }
        }
        changeStates(target.getCasesId(), true);
        return target;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean RemoveAll(String AttID) throws Exception {
        Optional<casesCommitFile> findOnes = commitFileRep.findFirstByAttId(AttID);
        if (findOnes.isPresent()) {
            casesCommitFile one = findOnes.get();
            commitFileRep.delete(one);
        }
        Optional<tbAttachment> findAtts = attRep.findFirstByGuid(AttID);
        if (findAtts.isPresent()) {
            attRep.delete(findAtts.get());
        }
        return true;
    }

    @Autowired
    SysLoginUserMapper loginUserMapper;

    private void changeStates(String casesId, boolean commit) {
        Optional<cases> findCases = casesRep.findFirstByCasesid(casesId);
        if (findCases.isPresent()) {
            cases find = findCases.get();
            if (HasCommitFileFinish(casesId)) {
                find.setState(7);
            } else find.setState(8);
            List<casesCommitFile> all = commitFileRep.findAllByCasesId(casesId);
            if (all.size() == 0) find.setState(5);
            List<String> commitFileText = new ArrayList<>();
            for (int i = 0; i < all.size(); i++) {
                casesCommitFile item = all.get(i);
                Integer userID = item.getCreateMan();
                String userName = loginUserMapper.getLoginUserNameById(userID);
                if (item.getState() == 2) {
                    commitFileText.add(String.format("%s提交了技术文件，但未经技术经理审核", userName));
                } else if (item.getState() == 3) {
                    commitFileText.add(String.format("%s提交的技术文件已审核", userName));
                } else if (item.getState() == 1) {
                    if (commit == false) {
                        commitFileText.add(String.format("%s提交的技术已被退回", userName));
                    }
                }
            }
            if (commitFileText.size() > 0) {
                find.setCommitFileStatus(StringUtils.join(commitFileText, ';'));
            } else find.setCommitFileStatus("");
            casesRep.save(find);
        }
    }

    /**
     * 这一笔交单是不是都认领完成了。
     *
     * @param CasesID
     * @return
     */
    private boolean HasCommitFileFinish(String CasesID) {
        List<casesYwAccept> accepts = ywAcceptRep.findAllByCasesId(CasesID);
        List<String> names = accepts.stream().map(f -> f.getYName()).distinct().collect(toList());
        List<casesCommitFile> findAll = commitFileRep.findAllByCasesId(CasesID)
                .stream().filter(f -> f.getState().equals(3))
                .collect(toList());
        return findAll.size() >= names.size();
    }

    @Transactional(rollbackOn = Exception.class)
    public boolean Commit(Integer ID, String Result, String ResultText) throws Exception {
        Optional<casesCommitFile> findOne = commitFileRep.findById(ID);
        LoginUserInfo Login = CompanyContext.get();
        if (findOne.isPresent()) {
            casesCommitFile find = findOne.get();
            find.setAuditMan(Login.getUserIdValue());
            find.setAuditTime(new Date());
            find.setAuditText(ResultText);
            find.setAuditResult(Result);
            if (Result.equals("满足要求")) find.setState(3);
            else find.setState(2);
            commitFileRep.save(find);
            return true;
        } else throw new Exception("审核的记录不存在。");
    }

    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = commitBrowseMapper.getData(params);
        int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            object.setTotal(Total);
            object.setData(PP);
        }
        return object;
    }

    private Map<String, Object> getParams(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");

        if (sortOrder.isEmpty()) sortOrder = "Asc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "State";
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageSize * pageIndex);
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
}
