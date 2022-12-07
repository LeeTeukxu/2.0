package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.mapper.FeeItemMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.IAddToPayForWaitService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class AddToPayForWaitServiceImpl implements IAddToPayForWaitService {

    @Autowired
    yearFeePayForResultRepository yearPayforRep;
    @Autowired
    yearFeeListRepository yearFeeListRep;
    @Autowired
    applyFeeListRepository applyFeeListRep;
    @Autowired
    patentInfoPermissionRepository patentPermissRep;
    @Autowired
    pantentInfoRepository pantentInfoRepository;
    @Autowired
    FeeItemMapper feeItemMapper;
    @Autowired
    otherOfficeFeeListRepository ofRepository;
    @Autowired
    tbClientRepository clientRep;

    @Override
    public allFeePayForResult initByFeeItemIDS(String Type, List<Integer> IDS) {
        allFeePayForResult result = new allFeePayForResult();
        if (Type.equals("Year")) result = createByYear(IDS);
        else result = createByApply(IDS);
        return result;
    }

    private allFeePayForResult createByYear(List<Integer> IDS) {
        LoginUserInfo loginInfo = CompanyContext.get();
        allFeePayForResult result = new allFeePayForResult();
        Long startTime = System.currentTimeMillis();
        List<yearFeeList> feeList = yearFeeListRep.findAllById(IDS);
        Optional<yearFeeList> findMin = feeList.stream().min((f, y) -> {
            Date D = f.getJiaofeir();
            Date E = y.getJiaofeir();
            long d1 = 0;
            long e1 = 0;
            if (D != null) d1 = D.getTime();
            if (E != null) e1 = E.getTime();
            return Long.compare(d1, e1);
        });
        Long endTime = System.currentTimeMillis();
        System.out.println("开始时间:" + startTime + "; 结束时间:" + endTime + "; 运行时间:" + (endTime - startTime) + "(ms)");
        double Money =
                feeList.stream().mapToDouble(f -> (f.getXmoney()==null?f.getMoney():f.getXmoney())+(f.getSxmoney()==null?0:f.getSxmoney())
).sum();
        result.setMoney(Money);
        if (findMin.isPresent()) {
            result.setJiaoFeiDate(findMin.get().getJiaofeir());
        }
        List<String> shenqingids = feeList.stream().map(f -> f.getShenqingh()).distinct().collect(toList());
        result.setFeeItemId(StringUtils.join(IDS, ","));
        result.setShenQingh(StringUtils.join(shenqingids, ","));
        result.setPayState(1);
        result.setCreateMan(Integer.parseInt(loginInfo.getUserId()));
        result.setCreateTime(new Date());
        if (shenqingids.size() >0) {
            List<patentInfoPermission> khps = patentPermissRep.findAllByShenqinghAndUsertype(shenqingids.get(0), "KH");
            if (khps.size() == 1) {
                patentInfoPermission fP = khps.get(0);
                tbClient findClient=clientRep.findAllByClientID(fP.getUserid());
                if(findClient!=null){
                    result.setCreditCode(findClient.getCreditCode());
                }
                result.setTickReceiver(fP.getUserid());
            }
        }
        return result;
    }

    private allFeePayForResult createByApply(List<Integer> IDS) {
        LoginUserInfo loginInfo = CompanyContext.get();
        allFeePayForResult result = new allFeePayForResult();
        List<applyFeeList> feeList = applyFeeListRep.findAllById(IDS);
        Optional<applyFeeList> findMin = feeList.stream().min((f, y) -> {
            Date D = f.getJiaofeir();
            Date E = y.getJiaofeir();
            long d1 = 0;
            long e1 = 0;
            if (D != null) d1 = D.getTime();
            if (E != null) e1 = E.getTime();
            return Long.compare(d1, e1);
        });
        double Money = feeList.stream().mapToDouble(f -> f.getMoney()).sum();
        double XMoney = feeList.stream().mapToDouble(f -> f.getXmoney() == null ? 0 : f.getXmoney()).sum();
        //double SXMoney=feeList.stream().mapToDouble(f->f.getSxmoney()).sum();
        result.setMoney(Money);
        result.setXMoney(XMoney);

        if (findMin.isPresent()) {
            result.setJiaoFeiDate(findMin.get().getJiaofeir());
        }
        List<String> shenqingids = feeList.stream().map(f -> f.getShenqingh()).distinct().collect(toList());
        result.setFeeItemId(StringUtils.join(IDS, ","));
        result.setShenQingh(StringUtils.join(shenqingids, ","));
        result.setPayState(1);
        result.setCreateMan(Integer.parseInt(loginInfo.getUserId()));
        result.setCreateTime(new Date());
        if (shenqingids.size()>0) {
            List<patentInfoPermission> khps = patentPermissRep.findAllByShenqinghAndUsertype(shenqingids.get(0), "KH");
            if (khps.size() == 1) {
                patentInfoPermission fP = khps.get(0);
                tbClient findClient=clientRep.findAllByClientID(fP.getUserid());
                if(findClient!=null){
                    result.setCreditCode(findClient.getCreditCode());
                }
                result.setTickReceiver(fP.getUserid());
            }
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @MethodWatch(name = "添加官费项目到待缴清单")
    public boolean Save(allFeePayForResult result, String Type) throws  Exception {
        LoginUserInfo loginInfo = CompanyContext.get();
        String FeeItemID = result.getFeeItemId();
        String[] IDS = FeeItemID.split(",");
        for (int i = 0; i < IDS.length; i++) {
            Integer FeeID = Integer.parseInt(IDS[i]);
            if (Type.equals("Year")) {
                Optional<yearFeeList> findYear = yearFeeListRep.findById(FeeID);
                if (findYear.isPresent()) {
                    yearFeeList year = findYear.get();
                    year.setAddPayFor(true);
                    year.setJkStatus(4);
                    yearFeeListRep.save(year);
                } else throw new Exception("无法更改类型为:"+Type+",ID为:"+IDS[i]+"的费用项目为待缴费状态，请将此消息反馈给系统管理员!");
            } else if (Type.equals("Apply")) {
                Optional<applyFeeList> findApply = applyFeeListRep.findById(FeeID);
                if (findApply.isPresent()) {
                    applyFeeList apply = findApply.get();
                    apply.setAddPayFor(true);
                    apply.setJkStatus(4);
                    applyFeeListRep.save(apply);
                }else throw new Exception("无法更改类型为:"+Type+",ID为:"+IDS[i]+"的费用项目为待缴费状态，请将此消息反馈给系统管理员!");
            } else if (Type.equals("OtherOfficeFee")) {
                Optional<otherOfficeFee> findOther=ofRepository.findById(FeeID);
                if(findOther.isPresent()){
                    otherOfficeFee other=findOther.get();
                    other.setNeedPayFor(true);
                    other.setOtherOfficeStates(2);
                    ofRepository.save(other);
                }else  throw new Exception("无法更改类型为:"+Type+",ID为:"+IDS[i]+"的费用项目为待缴费状态，请将此消息反馈给系统管理员!");
            }
        }
        if (result.getId() == null) {
            result.setCreateTime(new Date());
            result.setCreateMan(Integer.parseInt(loginInfo.getUserId()));
            result.setPayState(1);
            result.setType(Type);
            result.setFlowCode(feeItemMapper.getFlowCode("JFQD"));
        } else {
            Integer Id = result.getId();
            Optional<allFeePayForResult> findResult = yearPayforRep.findById(Id);
            if (findResult.isPresent()) {
                allFeePayForResult saveResult = findResult.get();
                saveResult.setFeeName(result.getFeeName());
                saveResult.setJiaoFeiDate(result.getJiaoFeiDate());
                saveResult.setMoney(result.getMoney());
                saveResult.setXMoney(result.getXMoney());
                saveResult.setTickReceiver(result.getTickReceiver());
                saveResult.setTickHeader(result.getTickHeader());
                saveResult.setAddress(result.getAddress());
                saveResult.setLinkMan(result.getLinkMan());
                saveResult.setLinkPhone(result.getLinkPhone());
                saveResult.setMemo(result.getMemo());
                result = saveResult;
            }
        }
        yearPayforRep.save(result);
        return true;
    }

    @Override
    public allFeePayForResult OtherOfficeFeeinitByFeeItemIDS(String Type, List<Integer> IDS) {
        allFeePayForResult result = new allFeePayForResult();
        result = createByOtherOfficeFee(IDS);
        return result;
    }

    private allFeePayForResult createByOtherOfficeFee(List<Integer> IDS) {
        LoginUserInfo loginInfo = CompanyContext.get();
        allFeePayForResult result = new allFeePayForResult();
        List<otherOfficeFee> otherOfficeFeeList = ofRepository.findAllById(IDS);
        Optional<otherOfficeFee> findMin = otherOfficeFeeList.stream().min((f, y) -> {
            Date D = f.getJiaofeir();
            Date E = y.getJiaofeir();
            long d1 = 0;
            long e1 = 0;
            if (D != null) d1 = D.getTime();
            if (E != null) e1 = E.getTime();
            return Long.compare(d1, e1);
        });
        double Money =
                otherOfficeFeeList.stream().mapToDouble(f -> (f.getXmoney()==null?f.getAmount():f.getXmoney())+(f.getSxmoney()==null?0:f.getSxmoney())).sum();
        double XMoney = otherOfficeFeeList.stream().mapToDouble(f -> f.getXmoney() == null ? 0 : f.getXmoney()).sum();
        //double SXMoney=feeList.stream().mapToDouble(f->f.getSxmoney()).sum();
        result.setMoney(Money);
        result.setXMoney(XMoney);

        if (findMin.isPresent()) {
            result.setJiaoFeiDate(findMin.get().getJiaofeir());
        }
        List<String> shenqingids = otherOfficeFeeList.stream().map(f -> f.getShenqingh()).distinct().collect(toList());
        result.setFeeItemId(StringUtils.join(IDS, ","));
        result.setShenQingh(StringUtils.join(shenqingids, ","));
        result.setPayState(1);
        result.setCreateMan(Integer.parseInt(loginInfo.getUserId()));
        result.setCreateTime(new Date());
        if (shenqingids.size() == 1) {
            List<patentInfoPermission> khps = patentPermissRep.findAllByShenqinghAndUsertype(shenqingids.get(0), "KH");
            if (khps.size() == 1) {
                patentInfoPermission fP = khps.get(0);
                result.setTickReceiver(fP.getUserid());
            }
        }
        return result;
    }
}
