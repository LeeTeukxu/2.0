package com.zhide.dtsystem.services.instance;

import com.zhide.dtsystem.common.DateTimeUtils;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 年费记录生成
 */
@Component
public class yearMoneyCreator {

    @Autowired
    tbFeeItemRepository feeItemRep;
    @Autowired
    yearFeeListRepository yearfeeListRep;
    @Autowired
    pantentInfoRepository pantentRep;
    @Autowired
    yearFeeBaseRepository yearBaseRep;
    @Autowired
    yearWatchConfigRepository yearWatchRep;
    @Autowired
    TZSRepository tzsRep;
    private String shenqingh;
    private Date fawenr;
    private String pantentType;
    private TZS targetTZS;
    /*专利数据*/
    private pantentInfo pantentInfo;
    /*监控设置数据*/
    private yearWatchConfig yearConfig;
    /*年度年费数据*/
    List<yearFeeBase> feeBases;
    LoginUserInfo loginUserInfo;

    public void setShenqingh(String shenqingh) throws Exception {
        this.shenqingh = shenqingh;
        loginUserInfo = CompanyContext.get();
        if (loginUserInfo == null) throw new Exception("登录信息失效。");
        prepareData();

    }

    public List<yearFeeList> getAllFeeList() throws Exception {
        int maxNum = 20;
        if (pantentInfo.getShenqinglx() != 0) maxNum = 10;
        List<yearFeeList> res = new ArrayList<>();
        int beginYear = yearConfig.getBeginTimes();
        Date shenqingr = pantentInfo.getShenqingr();
        Date lastJianDate = DateUtils.addMonths(DateUtils.addYears(shenqingr, beginYear + 8), 1);
        res.addAll(createFirstYear(beginYear, targetTZS.getLastDate()));
        for (int i = 1; i <= maxNum - beginYear; i++) {
            int yearIndex = i + beginYear;
            Date maxDate = DateUtils.addMonths(DateUtils.addYears(shenqingr, yearIndex - 1), 1);
            res.add(getNexYear(yearIndex - 1, maxDate, lastJianDate));
        }
        return res;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveAllYearFeeItems() throws Exception {
        List<yearFeeList> datas = getAllFeeList();
        //yearfeeListRep.deleteByShenqingh(shenqingh);
        for (int i = 0; i < datas.size(); i++) {
            yearFeeList data = datas.get(i);
            int year = data.getYear();
            String shenqingh = data.getShenqingh();
            yearfeeListRep.save(data);

            pantentInfo one = pantentRep.findByShenqingh(shenqingh).get();
            one.setYearSource(2);
            one.setYearWatch(2);
            pantentRep.save(one);
        }
    }

    public List<yearFeeList> ImportYearFeeItems(int BeginTimes, int BeginJiaoFei, String shenqingh, String feePercent)
            throws Exception {
        List<yearFeeList> res = new ArrayList<>();
        loginUserInfo = CompanyContext.get();
        pantentInfo = pantentRep.findByShenqingh(shenqingh).get();
        feeBases = yearBaseRep.findAllByType(pantentInfo.getShenqinglx());
        yearConfig = saveImportYearWatchConfig(BeginTimes, BeginJiaoFei, feePercent, shenqingh);
        this.shenqingh = shenqingh;
        int maxNum = 20;
        if (pantentInfo.getShenqinglx() != 0) maxNum = 10;

        int beginYear = yearConfig.getBeginTimes();
        Date shenqingr = pantentInfo.getShenqingr();
        /*申请日+首次缴费年度+8+1个月*/
        Date lastJianDate = DateUtils.addMonths(DateUtils.addYears(shenqingr, beginYear + 8), 1);

        for (int i = 0; i <= maxNum - beginYear; i++) {
            int yearIndex = i + beginYear;
            /*只生成首次监控年度以后的记录*/
            if (yearIndex >= BeginJiaoFei) {
                /*首次缴费年度+N-1+1个月*/
                Date maxDate = DateUtils.addMonths(DateUtils.addYears(shenqingr, yearIndex - 1), 1);
                res.add(getNexYear(yearIndex - 1, maxDate, lastJianDate));
            }
        }
        return res;
    }

    private yearWatchConfig saveImportYearWatchConfig(int BeginTimes, int BeginJiaoFei, String feePercent, String
            shenqingh) throws Exception {
        yearWatchConfig config = new yearWatchConfig();
        config.setBeginTimes(BeginTimes);
        config.setBeginJiaoFei(BeginJiaoFei);
        config.setFaWenDate(pantentInfo.getShenqingr());
        config.setCreateMan(loginUserInfo.getUserIdValue());
        config.setFeeType(1);
        if (feePercent.equals("无费减")) {
            config.setFeePercent(1.0);
        } else if (feePercent.equals("85%") || feePercent.equals("0.85")) {
            config.setFeePercent(0.15);
        } else if (feePercent.equals("70%") || feePercent.equals("0.70")) {
            config.setFeePercent(0.3);
        } else throw new Exception(shenqingh + "的率减:" + feePercent + "不正确!");
        config.setShenQingh(shenqingh);
        config.setCreateTime(new Date());
        return yearWatchRep.save(config);

    }

    private void prepareData() throws Exception {
        pantentInfo = pantentRep.findByShenqingh(shenqingh).get();
        yearConfig = yearWatchRep.findAllByShenQingh(shenqingh).get();
        feeBases = yearBaseRep.findAllByType(pantentInfo.getShenqinglx());
        Optional<TZS> findTs = tzsRep.findAllByShenqinghIn(new String[]{shenqingh})
                .stream().filter(f -> f.getTongzhismc()
                        .equals("办理登记手续通知书")).findFirst();
        if (findTs.isPresent()) {
            targetTZS = findTs.get();
        } else throw new Exception("年费数据数据不存在。操作被中止。");
    }

    private String getFeeName(int year, int lx) {
        String feeName = "";
        if (lx == 0) feeName = "发明专利";
        else if (lx == 1) feeName = "新型专利";
        else if (lx == 2) feeName = "外观专利";
        return feeName + "第" + Integer.toString(year) + "年年费";
    }

    /**
     * create by: 肖新民
     * description: TODO
     * create time: 2019-12-28
     * 第一年度年费记录。发文日期+2个月+15天。
     *
     * @return
     */
    public List<yearFeeList> createFirstYear(int beginYear, Date lastDate) throws Exception {
        List<tbFeeItem> items = feeItemRep.findAllByShenqingh(shenqingh).stream().filter(f -> f.getType().equals(
                "Year"))
                .collect(Collectors.toList());
        if (items.size() == 0) throw new Exception("首年度年费数据为空。");
        int size = items.size();
        List<yearFeeList> res = new ArrayList<>();
        List<String> Keys = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            tbFeeItem item = items.get(i);
            String name = item.getFeename();
            if (Keys.contains(name) == false) {
                yearFeeList one = new yearFeeList();
                one.setShenqingh(shenqingh);
                one.setCreatetime(new Date());
                one.setJiaofeir(lastDate);
                one.setState(1);
                one.setShow(true);
                one.setYear(beginYear);
                one.setNeedPayFor(true);
                one.setAddPayFor(false);
                one.setYears(DateTimeUtils.getYear(lastDate));
                one.setMoney(item.getMoney());
                if (name.equals("年费")) {
                    one.setFeename(getFeeName(beginYear, pantentInfo.getShenqinglx()));
                } else one.setFeename(name);
                res.add(one);
                Keys.add(name);
            }
        }
        return res;
    }

    /**
     * create by: mmzs
     * description: TODO
     * create time:
     *
     * @return
     */
    private yearFeeList getNexYear(int year, Date realShenqinr, Date lastJianDate) {
        Date shenqingr = pantentInfo.getShenqingr();
        int lx = pantentInfo.getShenqinglx();
        double percent = yearConfig.getFeePercent();
        if (realShenqinr.after(lastJianDate)) percent = 1;
        double baseMoney = feeBases.stream()
                .filter(f -> f.getYear() == (year + 1) && f.getType() == lx)
                .findFirst()
                .get()
                .getMoney();
        yearFeeList info = new yearFeeList();
        info.setShenqingh(shenqingh);
        info.setCreatetime(new Date());
        info.setJiaofeir(realShenqinr);
        info.setYear(year);
        info.setYears(DateTimeUtils.getYear(realShenqinr));
        info.setFeename(getFeeName(year + 1, lx));
        info.setState(1);
        info.setNeedPayFor(true);
        info.setAddPayFor(false);
        info.setMoney(baseMoney * percent);
        info.setShow(true);
        return info;
    }
}
