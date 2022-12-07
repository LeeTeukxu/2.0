package com.zhide.dtsystem.services.instance;

import com.zhide.dtsystem.common.DateTimeUtils;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 年费记录生成
 */
@Component
public class applyMoneyCreator {
    @Autowired
    tbFeeItemRepository feeItemRep;
    @Autowired
    applyFeeListRepository applyFeeRep;
    @Autowired
    pantentInfoRepository pantentRep;
    @Autowired
    applyWatchConfigRepository applyWatchRep;
    @Autowired
    TZSRepository tzsRep;
    LoginUserInfo loginUserInfo;
    TZS targetTZS;
    private String shenqingh;
    private Date fawenr;
    private String pantentType;
    /*专利数据*/
    private pantentInfo pantentInfo;
    /*监控设置数据*/
    private applyWatchConfig applyConfig;

    static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public void setShenqingh(String shenqingh) throws Exception {
        this.shenqingh = shenqingh;
        prepareData();
        loginUserInfo = CompanyContext.get();
        if (loginUserInfo == null) throw new Exception("登录信息失效。");

        Optional<TZS> finds = tzsRep.findAllByShenqinghIn(new String[]{shenqingh}).stream()
                .filter(f -> f.getTongzhismc().equals("缴纳申请费通知书") || f
                        .getTongzhismc().equals("费用减缴审批通知书")).findFirst();
        if (finds.isPresent()) {
            targetTZS = finds.get();
        } else throw new Exception(shenqingh + "申请费设置为空。");
    }

    public List<applyFeeList> getAllFeeList() {
        List<applyFeeList> res = new ArrayList<>();
        List<tbFeeItem> items = feeItemRep.findAllByShenqingh(shenqingh).stream()
                .filter(f -> f.getType().equals("Apply"))
                .filter(distinctByKey(f -> f.getFeename())).collect(Collectors.toList());
        Date lastDate = targetTZS.getLastDate();
        Integer userId = Integer.parseInt(loginUserInfo.getUserId());
        for (int i = 0; i < items.size(); i++) {
            tbFeeItem r = items.get(i);

            applyFeeList item = new applyFeeList();
            item.setCreateman(userId);
            item.setCreatetime(new Date());
            item.setFeename(r.getFeename());
            item.setJiaofeir(lastDate);
            item.setShenqingh(shenqingh);
            item.setMoney(r.getMoney());
            item.setState(1);
            item.setYear(1);
            item.setYears(DateTimeUtils.getYear(lastDate));
            item.setNeedPayFor(true);
            item.setAddPayFor(false);
            item.setShow(true);
            res.add(item);
        }
        if (pantentInfo.getShenqinglx() == 0) {
            applyFeeList last = new applyFeeList();
            last.setCreateman(userId);
            last.setCreatetime(new Date());
            last.setFeename("发明专利申请实质审查费");
            last.setJiaofeir(DateUtils.addYears(pantentInfo.getShenqingr(), 3));
            last.setShenqingh(shenqingh);
            last.setMoney(2500.0 * targetTZS.getFeePercent());
            last.setState(1);
            last.setYear(1);
            last.setNeedPayFor(true);
            last.setAddPayFor(false);
            last.setYears(DateTimeUtils.getYear(lastDate));
            last.setShow(true);
            res.add(last);
        }
        return res;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveAllApplyFeeItems() {
        List<applyFeeList> datas = getAllFeeList();
        applyFeeRep.deleteByShenqingh(shenqingh);
        for (int i = 0; i < datas.size(); i++) {
            applyFeeList data = datas.get(i);
            int year = data.getYear();
            applyFeeRep.save(data);
        }
    }

    private void prepareData() {
        pantentInfo = pantentRep.findById(shenqingh).get();
        applyConfig = applyWatchRep.findAllByShenqingh(shenqingh).get();
    }

    public void ImportDatas(String Shenqingh, String FeePercent) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        List<applyFeeList> items = createSingle(Info.getUserIdValue(), Shenqingh, FeePercent);
        applyFeeRep.deleteByShenqingh(Shenqingh);
        for (int n = 0; n < items.size(); n++) {
            applyFeeList data = items.get(n);
            applyFeeRep.save(data);
        }
        Double fPercent = 1.0;
        int feeType = 3;
        if (FeePercent.equals("85%")) {
            fPercent = 0.15;
            feeType = 1;
        } else if (FeePercent.equals("70%")) {
            fPercent = 0.3;
            feeType = 2;
        }

        Optional<pantentInfo> findOnes = pantentRep.findByShenqingh(Shenqingh);
        if (findOnes.isPresent()) {
            pantentInfo One = findOnes.get();
            applyWatchConfig config = new applyWatchConfig();
            config.setShenqingh(Shenqingh);
            config.setFeePercent(fPercent);
            config.setFeeType(feeType);
            config.setApplyDate(One.getShenqingr());
            config.setCreateTime(new Date());
            config.setCreateMan(Info.getUserIdValue());
            applyWatchRep.save(config);

        }
    }

    public List<applyFeeList> createSingle(int userId, String shenqingh, String feePercent) throws Exception {
        List<applyFeeList> Res = new ArrayList<>();
        pantentInfo One = null;
        Optional<pantentInfo> findOnes = pantentRep.findByShenqingh(shenqingh);
        if (findOnes.isPresent()) {
            One = findOnes.get();
        } else throw new Exception(shenqingh + "的专利资料不存在!");
        Date lastDate = DateUtils.addMonths(One.getShenqingr(), 2);
        int shenqinglx = One.getShenqinglx();
        Double F = 1.0;
        if (feePercent.equals("85%")) F = 0.15;
        else if (feePercent.equals("70%")) F = 0.3;

        if (shenqinglx == 0) {
            applyFeeList item = new applyFeeList();
            item.setCreateman(userId);
            item.setCreatetime(new Date());
            item.setFeename("公布印刷费");
            item.setJiaofeir(lastDate);
            item.setShenqingh(shenqingh);
            item.setMoney(50.0);
            item.setState(1);
            item.setYear(1);
            item.setYears(DateTimeUtils.getYear(lastDate));
            item.setNeedPayFor(true);
            item.setAddPayFor(false);
            item.setShow(true);

            applyFeeList item1 = new applyFeeList();
            item1.setCreateman(userId);
            item1.setCreatetime(new Date());
            item1.setFeename("发明专利申请费");
            item1.setJiaofeir(lastDate);
            item1.setShenqingh(shenqingh);
            item1.setMoney(900.0 * F);
            item1.setState(1);
            item1.setYear(1);
            item1.setYears(DateTimeUtils.getYear(lastDate));
            item1.setNeedPayFor(true);
            item1.setAddPayFor(false);
            item1.setShow(true);

            applyFeeList item2 = new applyFeeList();
            item2.setCreateman(userId);
            item2.setCreatetime(new Date());
            item2.setFeename("发明专利申请实质审查费");
            item2.setJiaofeir(DateUtils.addYears(One.getShenqingr(), 3));
            item2.setShenqingh(shenqingh);
            item2.setMoney(2500.0 * F);
            item2.setState(1);
            item2.setYear(1);
            item2.setYears(DateTimeUtils.getYear(item.getJiaofeir()));
            item2.setNeedPayFor(true);
            item2.setAddPayFor(false);
            item2.setShow(true);
            Res.addAll(Arrays.asList(item, item1, item2));
        } else if (shenqinglx == 1) {
            applyFeeList item = new applyFeeList();
            item.setCreateman(userId);
            item.setCreatetime(new Date());
            item.setFeename("实用新型专利申请费");
            item.setJiaofeir(lastDate);
            item.setShenqingh(shenqingh);
            item.setMoney(500.0 * F);
            item.setState(1);
            item.setYear(1);
            item.setYears(DateTimeUtils.getYear(lastDate));
            item.setNeedPayFor(true);
            item.setAddPayFor(false);
            item.setShow(true);
            Res.add(item);
        } else if (shenqinglx == 2) {
            applyFeeList item = new applyFeeList();
            item.setCreateman(userId);
            item.setCreatetime(new Date());
            item.setFeename("外观设计专利申请费");
            item.setJiaofeir(lastDate);
            item.setShenqingh(shenqingh);
            item.setMoney(500.0 * F);
            item.setState(1);
            item.setYear(1);
            item.setYears(DateTimeUtils.getYear(lastDate));
            item.setNeedPayFor(true);
            item.setAddPayFor(false);
            item.setShow(true);
            Res.add(item);
        }
        return Res;
    }
}
