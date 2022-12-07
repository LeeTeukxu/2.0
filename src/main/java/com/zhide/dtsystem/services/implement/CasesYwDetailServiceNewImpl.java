package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.mapper.FeeItemMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.ICasesYwDetailNewService;
import com.zhide.dtsystem.viewModel.SelectMoneyInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CasesYwDetailServiceNewImpl implements ICasesYwDetailNewService {
    @Autowired
    casesYwItemsRepository casesYwRep;
    @Autowired
    casesSubFilesRepository subFilesRep;
    @Autowired
    tbAttachmentRepository attRep;
    @Autowired
    FeeItemMapper feeItemMapper;
    @Autowired
    tradeCasesRepository mainRep;

    @Autowired
    casesSubRepository casesSubRep;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean SaveSubs(List<casesYwItems> rows) {
        for (int i = 0; i < rows.size(); i++) {
            casesYwItems row = rows.get(i);
            int ID = row.getId();
            Optional<casesYwItems> nowOnes = casesYwRep.findById(ID);
            if (nowOnes.isPresent()) {
                casesYwItems nowOne = nowOnes.get();
                nowOne.setCasesId(row.getCasesId());
                nowOne.setSignDate(row.getSignDate());
                nowOne.setYName(row.getYName());
                nowOne.setYType(row.getYType());
                nowOne.setPrice(row.getPrice());
                nowOne.setNum(row.getNum());
                nowOne.setDai(row.getDai());
                nowOne.setGuan(row.getGuan());
                nowOne.setTotal(row.getTotal());
                nowOne.setCreateTime(row.getCreateTime());
                nowOne.setCreateMan(row.getCreateMan());
                nowOne.setTechMan(row.getTechMan());
                nowOne.setTechManager(row.getTechManager());
                nowOne.setTradeName(row.getTradeName());
                nowOne.setCategory(row.getCategory());
                nowOne.setFilingTime(row.getFilingTime());
                nowOne.setShenqingh(row.getShenqingh());
                nowOne.setWhether(row.getWhether());
                nowOne.setWhetherTime(row.getWhetherTime());
                nowOne.setWhetherReject(row.getWhetherReject());
                nowOne.setRejectionDate(row.getRejectionDate());
                nowOne.setCanUse(row.getCanUse());
                nowOne.setProcessState(row.getProcessState());
                nowOne.setSubNo(row.getSubNo());
                nowOne.setSubId(row.getSubId());
                nowOne.setYid(row.getYid());
                nowOne.setTcName(row.getTcName());
                nowOne.setTcCategory(row.getTcCategory());
                nowOne.setClientRequiredDate(row.getClientRequiredDate());
                casesYwRep.save(nowOne);
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean Remove(String YWID) throws Exception {
        String[] IDS = YWID.split(",");
        for (int i = 0; i < IDS.length; i++) {
            String subId = IDS[i];
            Optional<casesYwItems> subs = casesYwRep.findFirstBySubId(subId);
            if (subs.isPresent()) {
                casesYwRep.deleteAllBySubId(subId);
            }
        }
        return true;
    }

    public List<String> getSubFiles(String SubID, String Type) {
        return subFilesRep.getAllBySubIdAndType(SubID, Type).stream().map(f -> f.getAttId()).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean SaveSubFiles(String CasesID, String SubID, String AttID, String Type) {
        LoginUserInfo logInfo = CompanyContext.get();
        casesSubFiles sub = new casesSubFiles();
        sub.setSubId(SubID);
        sub.setCasesId(CasesID);
        sub.setAttId(AttID);
        sub.setType(Type);
        sub.setCreateTime(new Date());
        sub.setCreateMan(logInfo.getUserIdValue());
        subFilesRep.save(sub);

        Optional<casesYwItems> findSubs = casesYwRep.findFirstBySubId(SubID);
        if (findSubs.isPresent()) {
            casesYwItems findSub = findSubs.get();
            List<casesSubFiles> files = subFilesRep.getAllBySubId(SubID);
            String techFiles =
                    StringUtils.join(files.stream().filter(f -> f.getType().equals("Tech")).map(f -> f.getAttId
                            ()).collect(Collectors.toList()), ",");
            findSub.setTechFiles(techFiles);
            casesYwRep.save(findSub);
        }
        return true;
    }

    @Autowired
    tbArrivalRegistrationRepository arrRep;
    @Autowired
    arrivalUseDetailRepository detailRep;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void SaveSelectMoney(SelectMoneyInfo Info) throws Exception {
        LoginUserInfo LogInfo=CompanyContext.get();
        Integer ArrID=Info.getArrID();
        Optional<tbArrivalRegistration> findArrs=arrRep.findById(ArrID);
        if(findArrs.isPresent()){
            Double  SelectTotal=Info.getDai()+Info.getGuan();
            tbArrivalRegistration main=findArrs.get();
            Double PayAmount=Double.parseDouble(main.getPaymentAmount());
            if(PayAmount==0) throw new Exception("到帐金额为0的业务不能进行认领!");

            Optional<tradeCases> findMains=mainRep.findFirstByCasesid(Info.getCasesID());
            if(findMains.isPresent()){
                List<casesYwItems> Subs=casesYwRep.findAllByCasesId(Info.getCasesID());
                if(Subs.size()==0) throw new Exception("至少要一条业务明细!");
                Double CasesMoney=Subs.stream().mapToDouble(f->f.getDai()+f.getGuan()).sum();
                if(CasesMoney==0) throw new Exception("业务交单金额不能为0");
                if(SelectTotal>CasesMoney) throw new Exception("领用金额("+Double.toString(SelectTotal)+")不能大于交单金额("+Double.toString(CasesMoney)+ ")");

            } else throw  new Exception("交单业务对象不存在!");
            List<arrivalUseDetail> savedDatas=detailRep.findAllByArrId(ArrID);
            Double savedMoney=
                    savedDatas.stream().filter(f->f.getState()!=1).mapToDouble(f->f.getDai()+f.getGuan()).sum();
            Double NowTotal=savedMoney+Info.getDai()+Info.getGuan();
            if(NowTotal>PayAmount) throw new Exception("累计领用金额已超过了到帐金额!");

            arrivalUseDetail detail=new arrivalUseDetail();
            detail.setState(0);
            detail.setMoneyType(1);
            detail.setArrId(ArrID);
            detail.setCanUse(true);
            detail.setCasesId(Info.getCasesID());
            detail.setDai(Info.getDai());
            detail.setGuan(Info.getGuan());
            detail.setTotal(SelectTotal);
            detail.setClientName(Info.getClientName());
            detail.setClientId(Info.getClientID());
            detail.setCreateMan(LogInfo.getUserIdValue());
            detail.setCreateTime(new Date());
            detail.setMemo("通过商标专利交单模块进行领用。");
            detailRep.save(detail);

            main.setClaimant(LogInfo.getUserIdValue());
            main.setClaimStatus(2);
            main.setClaimDate(new Date());
            main.setReviewerStatus(0);
            main.setKhName(Info.getClientName());
            main.setCustomerId(Integer.toString(Info.getClientID()));
            arrRep.save(main);
        } else throw new Exception("操作的回款单对象不存在!");
    }
}
