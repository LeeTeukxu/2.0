package com.zhide.dtsystem.services.instance.govfee;

import com.zhide.dtsystem.models.applyFeeList;
import com.zhide.dtsystem.models.pantentInfo;
import com.zhide.dtsystem.models.patentGovFee;
import com.zhide.dtsystem.repositorys.applyFeeListRepository;
import com.zhide.dtsystem.repositorys.pantentInfoRepository;
import com.zhide.dtsystem.services.define.IGovFeeProcessor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: applyFeeProcessor
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年02月03日 10:24
 **/
@Component
public class applyFeeProcessor  implements IGovFeeProcessor {
    List<String> allItems= Arrays.asList(
            "优先权恢复费",
            "优先权要求费",
            "公布印刷费",
            "权利要求附加费",
            "说明书附加费",
            "实质审查费"
    );
    List<applyFeeList> allDatas=new ArrayList<>();
    @Autowired
    applyFeeListRepository  applyFeeRep;
    @Autowired
    pantentInfoRepository pantentRep;
    @Override
    public boolean accept(String costName) {
        if(costName.contains("申请费")) return true;
        List<String> findItems=allItems.stream().filter(f->
        {
            return costName.contains(f);
        }).collect(Collectors.toList());
        if(findItems.size()>0) return true;
        return false;
    }

    @Override
    public void execute(patentGovFee item) {
        if(StringUtils.isEmpty(item.getPayState())==true)
            throw new IllegalArgumentException(item.getAppNo()+item.getCostName()+"的缴费状态不能为空!");
        String CostName=item.getCostName();
        Date Tm=item.getLimitDate();
        String AppNo=item.getAppNo();
        List<applyFeeList> findItems=applyFeeRep.findAllByShenqinghAndFeenameAndJiaofeir(AppNo,CostName,Tm);
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
        if(allDatas.size()>0)applyFeeRep.saveAll(allDatas);
    }


    private void AddItem(patentGovFee item){
        Optional<pantentInfo> ps=pantentRep.findByShenqingh(item.getAppNo());
        if(ps.isPresent()==false) {
            return ;
        }


        applyFeeList newItem=new applyFeeList();
        newItem.setState(1);
        newItem.setShow(true);
        newItem.setJkStatus(0);
        newItem.setNeedPayFor(true);
        newItem.setAddPayFor(false);
        newItem.setImportData(true);
        newItem.setPayState(item.getPayState().equals("已缴费")?1:0);
        newItem.setYear(1);
        if(newItem.getPayState()==1) newItem.setJkStatus(5);
        newItem.setShenqingh(item.getAppNo());
        newItem.setFeename(item.getCostName());
        Date limitDate=item.getLimitDate();
        if(limitDate!=null){
            newItem.setJiaofeir(limitDate);
        } else {
            limitDate=item.getGovPayDate();
            if(limitDate==null)
                throw new IllegalArgumentException(item.getAppNo()+item.getCostName()+"的缴费日期不能为空!");
            newItem.setJiaofeir(limitDate);
        }
        int Years=limitDate.getYear();
        newItem.setYears(Years);
        newItem.setMoney(item.getAmount()*1.0);
        newItem.setCreatetime(new Date());
        newItem.setUpdateTime(new Date());
        //applyFeeRep.save(newItem);
        allDatas.add(newItem);
    }
    private void UpdateItem(List<applyFeeList> sources,patentGovFee item){
        if(sources.size()>1)
            throw new IllegalArgumentException(item.getAppNo()+item.getCostName()+"至少有两个日期相同的待缴项目。请检查!");
        applyFeeList One=sources.get(0);
        One.setFeename(item.getCostName());
        One.setMoney(item.getAmount()*1.0);
        if(item.getLimitDate()!=null) {
            One.setJiaofeir(item.getLimitDate());
        } else One.setJiaofeir(item.getGovPayDate());
        //One.setCreatetime(new Date());
        One.setPayState(item.getPayState().equals("已缴费")?1:0);
        if(One.getPayState()==1) One.setJkStatus(5);
        One.setUpdateTime(new Date());
        //applyFeeRep.save(One);
        allDatas.add(One);
    }

}
