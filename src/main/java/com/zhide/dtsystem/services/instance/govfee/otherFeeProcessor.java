package com.zhide.dtsystem.services.instance.govfee;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.repositorys.f1Repository;
import com.zhide.dtsystem.repositorys.otherOfficeFeeListRepository;
import com.zhide.dtsystem.repositorys.pantentInfoRepository;
import com.zhide.dtsystem.services.define.IGovFeeProcessor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName: otherFeeProcessor
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年02月03日 10:25
 **/
@Component
public class otherFeeProcessor implements IGovFeeProcessor {
    List<otherOfficeFee> allDatas=new ArrayList<>();
    @Autowired
    otherOfficeFeeListRepository otherRep;
    @Autowired
    pantentInfoRepository pantentRep;
    @Autowired
    f1Repository  f1Rep;
    @Override
    public boolean accept(String costName) {
        return true;
    }

    Logger logger = LoggerFactory.getLogger(otherFeeProcessor.class);
    @Override
    public void execute(patentGovFee item) throws Exception {
        if(StringUtils.isEmpty(item.getPayState())==true)
            throw new IllegalArgumentException(item.getAppNo()+item.getCostName()+"的缴费状态不能为空!");
        String CostName=item.getCostName();
        Date Tm=item.getLimitDate();
        String AppNo=item.getAppNo();
        List<otherOfficeFee> findItems=otherRep.findAllByShenqinghAndExpenseItemAndJiaofeir(AppNo,CostName,Tm);
        if(item.getPayState().equals("已缴费")){
            //if(item.getGovPayDate()==null) throw new IllegalArgumentException(item.getAppNo()+item.getCostName()+"实际缴费日期不能为空!");
            //if(findItems.size()==0)AddItem(item);
        } else {
            if(item.getLimitDate()==null)
                throw new IllegalArgumentException(item.getAppNo()+item.getCostName()+"最后缴费日期不能为空!");
            if(findItems.size()==0){
                AddItem(item);
            } else UpdateItem(findItems,item);
        }
    }

    @Override
    public void clear() {
        allDatas.clear();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAll() {
        if(allDatas.size()>0)otherRep.saveAll(allDatas);
    }

    private void AddItem(patentGovFee item){
        String AppNo=item.getAppNo();
        Optional<pantentInfo> ps=pantentRep.findByShenqingh(AppNo);
        if(ps.isPresent()==false) {
            return;
            //throw new IllegalArgumentException(AppNo + "指向的专利信息在系统中不存在!");
        }
        pantentInfo pInfo=ps.get();
        Integer  Lx=pInfo.getShenqinglx();
        if(StringUtils.isEmpty(pInfo.getFamingmc())==true) return ;
        String costName=item.getCostName();
        otherOfficeFee newItem=new otherOfficeFee();
        newItem.setShow(true);
        newItem.setState(1);
        newItem.setOtherOfficeStates(1);
        newItem.setNeedPayFor(true);
        newItem.setAddPayFor(false);
        newItem.setImportData(true);
        newItem.setPayState(item.getPayState().equals("已缴费")?1:0);
        newItem.setYear(1);
        if(newItem.getPayState()==1) newItem.setOtherOfficeStates(3);
        newItem.setShenqingh(item.getAppNo());
        newItem.setFamingmc(pInfo.getFamingmc());
        newItem.setShenqinglx(Lx==1?"实用新型":(Lx==0?"发明专利":"外观设计"));
        newItem.setType(pInfo.getShenqinglx());
        newItem.setExpenseItem(item.getCostName());
        Date limitDate=item.getLimitDate();
        if(limitDate!=null){
            newItem.setJiaofeir(limitDate);
        } else {
            limitDate=item.getGovPayDate();
            if(limitDate==null)
                throw new IllegalArgumentException(item.getAppNo()+item.getCostName()+"的缴费日期不能为空!");
            newItem.setJiaofeir(limitDate);
        }
        newItem.setAmount(item.getAmount()*1L);
        Optional<f1> fx=f1Rep.findFirstByExpenseItem(costName);
        if(fx.isPresent()){
            newItem.setExpenseItemId(fx.get().getId());
        }
        newItem.setUpdateTime(new Date());
        try {
            //otherRep.save(newItem);
            allDatas.add(newItem);
        }catch(Exception ax){
            logger.info("--保存:"+ JSON.toJSONString(newItem)+"失败");
            throw ax;
        }
    }
    private void UpdateItem(List<otherOfficeFee> sources, patentGovFee item){
        if(sources.size()>1)
            throw new IllegalArgumentException(item.getAppNo()+item.getCostName()+"至少有两个日期相同的待缴项目。请检查!");
        otherOfficeFee One=sources.get(0);
        One.setExpenseItem(item.getCostName());
        One.setAmount(item.getAmount()*1L);
        if(item.getLimitDate()!=null) {
            One.setJiaofeir(item.getLimitDate());
        } else One.setJiaofeir(item.getGovPayDate());
        One.setPayState(item.getPayState().equals("已缴费")?1:0);
        if(One.getPayState()==1) One.setOtherOfficeStates(3);
        One.setUpdateTime(new Date());
        allDatas.add(One);
        //otherRep.save(One);
    }
}
