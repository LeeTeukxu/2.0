package com.zhide.dtsystem.services.instance.govfee;

import com.zhide.dtsystem.models.applyFeeList;
import com.zhide.dtsystem.models.pantentInfo;
import com.zhide.dtsystem.models.patentGovFee;
import com.zhide.dtsystem.models.yearFeeList;
import com.zhide.dtsystem.repositorys.pantentInfoRepository;
import com.zhide.dtsystem.repositorys.yearFeeListRepository;
import com.zhide.dtsystem.services.define.IGovFeeProcessor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName: yearFeeProcessor
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年02月03日 10:12
 **/
@Component
public class yearFeeProcessor implements IGovFeeProcessor {
    List<yearFeeList> allDatas=new ArrayList<>();
    @Autowired
    yearFeeListRepository feeListRep;
    @Autowired
    pantentInfoRepository pantentRep;
    @Override
    public boolean accept(String costName) {
        if (StringUtils.isEmpty(costName)) return false;
        if (costName.contains("印花税")) return true;
        if (costName.contains("滞纳金")) return true;
        if (costName.contains("年费")) return true;
        return false;
    }
    @Override
    public void execute(patentGovFee item) {
        if(StringUtils.isEmpty(item.getPayState())==true)
            throw new IllegalArgumentException(item.getAppNo()+item.getCostName()+"的缴费状态不能为空!");
        String CostName=item.getCostName();
        Date Tm=item.getLimitDate();
        String AppNo=item.getAppNo();
        List<yearFeeList> findItems=feeListRep.findAllByShenqinghAndFeenameAndJiaofeir(AppNo,CostName,Tm);
        if(item.getPayState().equals("已缴费")==false){
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
        if(allDatas.size()>0)feeListRep.saveAll(allDatas);
    }

    private void AddItem(patentGovFee item){
        Optional<pantentInfo> ps=pantentRep.findByShenqingh(item.getAppNo());
        if(ps.isPresent()==false) {
            return ;
        }
        yearFeeList newItem=new yearFeeList();
        newItem.setShow(true);
        newItem.setState(1);
        newItem.setJkStatus(0);
        newItem.setNeedPayFor(true);
        newItem.setAddPayFor(false);
        newItem.setImportData(true);
        newItem.setPayState(item.getPayState().equals("已缴费")?1:0);
        if(newItem.getPayState()==1) newItem.setJkStatus(5);
        newItem.setYear(1);
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
        //feeListRep.save(newItem);
        allDatas.add(newItem);
    }
    private void UpdateItem(List<yearFeeList> sources,patentGovFee item){
        if(sources.size()>1)
            throw new IllegalArgumentException(item.getAppNo()+item.getCostName()+"至少有两个日期相同的待缴项目。请检查!");
        yearFeeList One=sources.get(0);
        One.setFeename(item.getCostName());
        One.setMoney(item.getAmount()*1.0);
        if(item.getLimitDate()!=null) {
            One.setJiaofeir(item.getLimitDate());
        } else One.setJiaofeir(item.getGovPayDate());
        One.setPayState(item.getPayState().equals("已缴费")?1:0);
        if(One.getPayState()==1) One.setJkStatus(5);
        One.setUpdateTime(new Date());
        //feeListRep.save(One);
        allDatas.add(One);
    }
}
