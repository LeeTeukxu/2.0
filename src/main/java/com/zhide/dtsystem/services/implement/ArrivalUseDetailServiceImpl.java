package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.PageableUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.ArrivalUseDetailMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.IArrivalUseDetailService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.DoubleStream;

import static java.util.stream.Collectors.toList;

/**
 * @ClassName: ArrivalUseDetailServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年04月21日 11:03
 **/
@Service
public class ArrivalUseDetailServiceImpl implements IArrivalUseDetailService {

    @Autowired
    ArrivalUseDetailMapper detailMapper;
    @Autowired
    tbArrivalRegistrationRepository tbArrRep;
    @Autowired
    arrivalUseDetailRepository arrUseRep;
    @Autowired
    casesMainRepository casesRep;
    @Autowired
    casesSubRepository caseSubRep;
    @Autowired
    tbClientRepository clientRep;
    @Autowired
    tbCustomerRefundRepository refundRep;

    Logger logger= LoggerFactory.getLogger(ArrivalUseDetailServiceImpl.class);

    @Override
    public pageObject getMain(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = detailMapper.getMain(params);
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
    public pageObject getSub(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = detailMapper.getSub(params);
        int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            object.setTotal(Total);
            object.setData(datas);
        }
        return object;
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void SaveSub(List<arrivalUseDetail> details) throws Exception {
        LoginUserInfo info = CompanyContext.get();
        Optional<tbArrivalRegistration> findMains = tbArrRep.findById(details.get(0).getArrId());
        if (findMains.isPresent() == false) throw new Exception("当前认领的业务已不存在!");
        Double AllMoney = Double.parseDouble(findMains.get().getPaymentAmount());
        Double X = 0.0;
        for (arrivalUseDetail detail : details) {
            if(detail.getCanUse()==false) continue;
            int moneyType = detail.getMoneyType();
            Integer state = detail.getState();
            if (state > 0) continue;
            int id = detail.getId();
            Double Total = detail.getGuan()+detail.getDai();
            X += Total;
            if (X > AllMoney) throw new Exception("领用金额:"+Double.toString(X)+"已超过了最大额度:"+Double.toString(AllMoney));
            if(Total<=0) throw new Exception("领用金额合计必须要大于0");
            if (moneyType == 1) {
                Optional<arrivalUseDetail> ddd = arrUseRep.findById(id);
                if (ddd.isPresent()) {
                    arrivalUseDetail dd = ddd.get();
                    dd.setMemo(detail.getMemo());
                    dd.setDai(detail.getDai());
                    dd.setGuan(detail.getGuan());
                    dd.setFiles(detail.getFiles());
                    dd.setTotal(Total);

                    validateSingleRow(AllMoney,findMains.get(),dd);
                    arrUseRep.save(dd);
                }
            } else {
                if (id == 0) {
                    detail.setCanUse(true);
                    detail.setState(0);
                    detail.setCreateMan(info.getUserIdValue());
                    detail.setCreateTime(new Date());
                }
                arrUseRep.save(detail);
            }
        }
    }

    private void validateSingleRow(Double TotalMoney,tbArrivalRegistration main, arrivalUseDetail detail) throws Exception {
        String CasesID = detail.getCasesId();
        Integer ID = detail.getId();
        if (ID == null) ID = 0;
        Integer ArrID = detail.getArrId();
        if (StringUtils.isEmpty(CasesID) == false) {
            Optional<casesMain> findCases = casesRep.findFirstByCasesId(CasesID);
            if (findCases.isPresent()) {
                Double CaseMoney = findCases.get().getAllMoney();
                List<casesSub> Subs = caseSubRep.findAllByCasesId(CasesID);
                if (CaseMoney == null || CaseMoney==0) {
                    CaseMoney = Subs.stream().mapToDouble(f -> f.getGuanMoney() + f.getDaiMoney()).sum();
                }
                Double TotalGuan = Subs.stream().flatMapToDouble(f -> DoubleStream.of(f.getGuanMoney())).sum();
                Double TotalDai = CaseMoney - TotalGuan;

                if (detail.getGuan() > TotalGuan) throw new Exception("领用的官费("+Double.toString(detail.getGuan())+")" +
                        "超过可领用的官费总额("+Double.toString(TotalGuan)+")!");

                if (detail.getDai() > TotalDai) throw new Exception("领用的代理费("+Double.toString(detail.getDai())+")" +
                        "超过可领用的代理费总额("+Double.toString(TotalDai)+")!");

                List<arrivalUseDetail> findChilds = arrUseRep.findAllByCasesId(CasesID);
                if (ID > 0) findChilds = arrUseRep.findAllByCasesIdAndArrIdNot(CasesID, ArrID);
                Double SavedGuan =
                        findChilds.stream().filter(f -> f.getState() != 1).flatMapToDouble(f -> DoubleStream.of(f.getGuan())).sum();
                Double SavedDai =
                        findChilds.stream().filter(f -> f.getState() != 1).flatMapToDouble(f -> DoubleStream.of(f.getDai())).sum();
                if (detail.getGuan() + SavedGuan > TotalGuan) {
                    SavedGuan=SavedGuan+detail.getGuan();
                    throw new Exception("领用的官费("+Double.toString(SavedGuan)+")" +
                            "超过可领用的官费总额("+Double.toString(TotalGuan)+")!");
                }
                if (detail.getDai() + SavedDai > TotalDai) {
                    SavedDai=SavedDai+detail.getDai();
                    throw new Exception("领用的代理费("+Double.toString(SavedDai)+")" +
                            "超过可领用的代理费总额("+Double.toString(TotalDai)+")!");
                }
            }
        }
        List<arrivalUseDetail> allSaved = arrUseRep.findAllByArrId(ArrID);
        if (ID > 0) allSaved = arrUseRep.findAllByArrIdAndIdNot(ArrID, ID);
        Double SavedNum =
                allSaved.stream().filter(f -> f.getState() !=1).flatMapToDouble(f -> DoubleStream.of(f.getTotal())).sum();
        if (SavedNum > TotalMoney) throw new RuntimeException(main.getDocumentNumber() + "领用金额已超过了到帐金额");
        Double Total = detail.getTotal();
        if (detail.getMoneyType() == 1) {
            Total = detail.getDai() + detail.getGuan();
        }
        if ((SavedNum + Total) > TotalMoney) {
            throw new RuntimeException(main.getDocumentNumber() + "累计领用金额已超过了到帐金额");
        }
    }
    @Autowired
    casesMainRepository caseMainRep;
    @Override
    public pageObject getDetail(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        String Arr = request.getParameter("ArrID");
        if (StringUtils.isEmpty(Arr)) {
            String CasesID= request.getParameter("CasesID");
            List<arrivalUseDetail> findMains=arrUseRep.findAllByCasesId(CasesID);
            if(findMains.size()>0){
                Arr=Integer.toString(findMains.get(0).getArrId());
            } else Arr = "0";
        }
        Integer ArrID = Integer.parseInt(Arr);
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "createTime";

        String showAll= request.getParameter("showAll");
        if(Strings.isEmpty(showAll)) showAll="0";


        Pageable pageable = PageableUtils.create(pageIndex, pageSize, sortField, sortOrder);
        Page XDatas = arrUseRep.findAllByArrId(ArrID, pageable);
        object.setTotal(XDatas.getTotalElements());

        List<arrivalUseDetail> Datas =new ArrayList<>(XDatas.getContent());
        if(showAll.equals("1")){
            Optional<tbArrivalRegistration> ones=tbArrRep.findById(ArrID);
            if(ones.isPresent()){
                tbArrivalRegistration one=ones.get();
                List<tbCustomerRefund> rs= refundRep.findAllByDocumentNumber(one.getDocumentNumber());
                if(rs.size()>0){
                    arrivalUseDetail dd=Datas.get(0);
                    for(int i=0;i<rs.size();i++){
                        tbCustomerRefund r=rs.get(i);
                        arrivalUseDetail v=new arrivalUseDetail();
                        v.setDai(0-Double.parseDouble(r.getAgencyFeeAmount()));
                        v.setGuan(0-Double.parseDouble(r.getOfficalFeeAmount()));
                        v.setTotal(v.getGuan()+v.getDai());
                        v.setClientName(dd.getClientName());
                        v.setClientId(dd.getClientId());
                        v.setCreateMan(r.getApplicant());
                        v.setCreateTime(r.getAddTime());
                        v.setMoneyType(4);
                        v.setAuditMan(r.getApprover());
                        v.setAuditTime(r.getApproverDate());
                        v.setAuditMemo(r.getApproverDescription());
                        Datas.add(v);
                    }
                }
            }
        }
        for (arrivalUseDetail Data : Datas) {
            Double dai = Data.getDai();
            Double guan = Data.getGuan();
            Double total = Data.getTotal();
            Integer Type = Data.getMoneyType();
            if (Type != 1) {
                if (dai == 0 && guan == 0) {
                    Data.setDai(total);
                }
            }
        }

        object.setData(Datas);
        return object;
    }

    /**
     * create by: mmzs
     * description: TODO
     * create time:
     * 领用的金额不能超过领用到款的总金额。
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public Integer SaveDetail(List<arrivalUseDetail> Datas) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        int ResID = 0;
        Optional<tbArrivalRegistration> findMains = tbArrRep.findById(Datas.get(0).getArrId());
        if (findMains.isPresent() == false) throw new Exception("当前认领的业务已不存在!");
        else {
            tbArrivalRegistration main = findMains.get();
            Double TotalMoney = Double.parseDouble(main.getPaymentAmount());
            for (arrivalUseDetail detail : Datas) {
                Integer ID = detail.getId();
                if (ID == null) ID = 0;
                validateSingleRow(TotalMoney,main,detail);
                Double Total = detail.getTotal();
                if (detail.getMoneyType() == 1) {
                    Total = detail.getDai() + detail.getGuan();
                }
                if(Total==0) throw new Exception("领用金额的合计不能为0!");
                if (ID == 0) {
                    detail.setCreateMan(Info.getUserIdValue());
                    detail.setCreateTime(new Date());
                }
                detail.setTotal(Total);
                detail.setCanUse(true);
                detail.setState(0);
                arrivalUseDetail dd = arrUseRep.save(detail);
                ResID = dd.getId();
            }
        }
        return ResID;
    }

    @Override
    @MethodWatch(name="删除回款领用明细")
    @Transactional
    public void RemoveOne(int ID) throws Exception {
        Optional<arrivalUseDetail> findOnes = arrUseRep.findById(ID);
        if (findOnes.isPresent()) {
            arrivalUseDetail detail = findOnes.get();
            Integer State = detail.getState();
            if (State == null) State = 0;
            if (State == 2) throw new Exception("此业务已审核成功，无法删除!");
            arrUseRep.delete(detail);


            ///如果领用的记录全部删完了。将主单设置为未领用。
            int ArrId=detail.getArrId();
            List<arrivalUseDetail> us=arrUseRep.findAllByArrId(ArrId);
            if(us.size()==0){
                Optional<tbArrivalRegistration> findAtts=tbArrRep.findById(ArrId);
                if(findAtts.isPresent()){
                    tbArrivalRegistration one= findAtts.get();
                    one.setClaimStatus(0);
                    one.setReviewerStatus(0);
                    tbArrRep.save(one);
                }
            }
        } else throw new Exception("删除的对象已不存在!");
    }

    @Override
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.SERIALIZABLE)
    @MethodWatch(name="回款领用审核通过",des="ID为{ID},审核结果为:{ResultText}")
    public void AuditOne(int Result, int ID, String ResultText) throws Exception {
        LoginUserInfo info = CompanyContext.get();
        Optional<arrivalUseDetail> findOnes = arrUseRep.findById(ID);
        if (findOnes.isPresent()) {
            arrivalUseDetail One = findOnes.get();
            Integer AuditMan = One.getAuditMan();

            if (StringUtils.isEmpty(One.getClientName()) == true) {
                Optional<tbClient> findClients = clientRep.findById(One.getClientId());
                if (findClients.isPresent()) {
                    One.setClientName(findClients.get().getName());
                }
            }
            One.setState(Result);
            One.setAuditMemo(ResultText);
            One.setCanUse(true);
            One.setAuditTime(new Date());
            One.setAuditMan(info.getUserIdValue());
            arrUseRep.save(One);
            logger.info("ArrID:{}审核为:{},Memo:{}",One.getArrId(),Result,ResultText);
            int ArrID = One.getArrId();
            List<arrivalUseDetail> arrs = arrUseRep.findAllByArrId(ArrID);
            Double UsedMoney = arrs.stream()
                    .filter(f -> f.getState() == 2)
                    .flatMapToDouble(f -> DoubleStream.of(f.getTotal())).sum();
            Optional<tbArrivalRegistration> kOnes = tbArrRep.findById(ArrID);
            if (kOnes.isPresent()) {
                tbArrivalRegistration KOne = kOnes.get();
                Double AllMoney = Double.parseDouble(KOne.getPaymentAmount());
                if (UsedMoney > AllMoney) throw new Exception("累计认领金额超过了允许认领的上限!");
                //先存钱。所以已用金额包含了本笔金额。
                logger.info("审核ArrID:{}时，AllMoney:{},UsedMoney:{}",ArrID,AllMoney,UsedMoney);
                if (AllMoney.equals(UsedMoney)) {
                    KOne.setReviewerStatus(2);
                    KOne.setClaimStatus(2);
                    logger.info("AllMoney==UsedMoney,最终状态为:2");
                } else {
                    //领用金额少于回款金额。还要继续领用。
                    int NotAudit = arrs.stream().filter(f -> f.getAuditMan() == 0 && f.getId()!=ID).collect(toList()).size();
                    //没有审核的记录。设置为可以继续领用。
                    int NNN=0;
                    if(NotAudit==0) {
                        KOne.setClaimStatus(4);
                        NNN=4;
                    } else {
                        //否则让财务继续审核。
                        KOne.setClaimStatus(2);
                        NNN=2;
                    }
                    logger.info("NotAudit:{},ClaimStatus:{}",NotAudit,NNN);
                    KOne.setReviewerStatus(0);
                }
                List<String> Cs =
                        arrs.stream().filter(f -> f.getState() != 2).map(f -> f.getClientName()).distinct().collect(toList());
                KOne.setKhName(String.join(",", Cs));
                KOne.setCustomerId(Integer.toString(arrs.get(0).getClientId()));
                KOne.setReviewer(info.getUserIdValue());
                KOne.setReviewerDate(new Date());
                tbArrRep.save(KOne);
            } else throw new Exception("审核的到帐业务已不存在!");

        } else throw new Exception("审核的到帐业务已不存在!");
    }
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.SERIALIZABLE)
    @MethodWatch(name="回款领用审核驳回",des="ID为{ID},审核结果为:{ResultText}")
    public void RejectOne(int Result,int ID,String ResultText) throws Exception{
        LoginUserInfo info = CompanyContext.get();
        Optional<arrivalUseDetail> findOnes = arrUseRep.findById(ID);
        if (findOnes.isPresent()) {
            arrivalUseDetail One = findOnes.get();
            if (StringUtils.isEmpty(One.getClientName()) == true) {
                Optional<tbClient> findClients = clientRep.findById(One.getClientId());
                if (findClients.isPresent()) {
                    One.setClientName(findClients.get().getName());
                }
            }
            One.setState(Result);
            One.setAuditMemo(ResultText);
            One.setCanUse(false);
            One.setAuditMan(info.getUserIdValue());
            One.setAuditTime(new Date());
            arrUseRep.save(One);
            int ArrID = One.getArrId();
            List<arrivalUseDetail> arrs = arrUseRep.findAllByArrId(ArrID);
            Optional<tbArrivalRegistration> kOnes = tbArrRep.findById(ArrID);
            if (kOnes.isPresent()) {
                tbArrivalRegistration KOne = kOnes.get();
                //代表未审核的记录
                int NotAudit =
                        arrs.stream().filter(f -> f.getAuditMan() == 0 && f.getId()!=ID).collect(toList()).size();
                if (NotAudit == 0) {
                    //审核完了。就这一条记录。打为审核不通过。
                    KOne.setClaimStatus(3);
                    KOne.setReviewerStatus(0);
                    List<String> Cs =
                            arrs.stream().filter(f -> f.getState() != 1).map(f -> f.getClientName()).distinct().collect(toList());
                    KOne.setCustomerId(Integer.toString(arrs.get(0).getClientId()));
                    KOne.setKhName(String.join(",", Cs));
                    KOne.setReviewer(info.getUserIdValue());
                    KOne.setReviewerDate(new Date());
                }
                else if(NotAudit>0){
                    //还有未审核的记录。就要让财务能继续审核。
                    KOne.setClaimStatus(2);
                    KOne.setReviewerStatus(0);

                }
                tbArrRep.save(KOne);
            } else throw new Exception("审核的到帐业务已不存在!");

        } else throw new Exception("审核的到帐业务已不存在!");
    }

    private Map<String, Object> getParams(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "SignTime";
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageSize * pageIndex + 1);
        params.put("End", pageSize * (pageIndex + 1));
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        LoginUserInfo Info = CompanyContext.get();
        String CasesID = request.getParameter("CasesID");
        params.put("CasesID", CasesID);
        String ArrIDX = request.getParameter("ArrID");
        if (StringUtils.isEmpty(ArrIDX) == false) {
            params.put("ArrID", ArrIDX);
        }
        String TotalX = request.getParameter("Total");
        if (StringUtils.isEmpty(TotalX) == false) {
            params.put("Total", TotalX);
        }
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
