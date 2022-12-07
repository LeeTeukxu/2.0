package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.SuperUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.OriginalMapper;
import com.zhide.dtsystem.mapper.PantentInfoMemoMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.casesSubRepository;
import com.zhide.dtsystem.repositorys.tbOriginalExpressRepository;
import com.zhide.dtsystem.repositorys.tbOriginalKdRepository;
import com.zhide.dtsystem.services.NBBHCode;
import com.zhide.dtsystem.services.PantentInfoMemoCreator;
import com.zhide.dtsystem.services.define.IOriginalService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class OriginalServiceImpl implements IOriginalService {

    @Autowired
    OriginalMapper originalMapper;

    @Autowired
    tbOriginalExpressRepository originalExpressRepository;

    @Autowired
    tbOriginalKdRepository originalKdRepository;

    @Autowired
    PantentInfoMemoMapper infoMemoMapper;

    @Autowired
    NBBHCode NBBHCode;

    @Autowired
    casesSubRepository casesSubRep;

    @Override
    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> params = getParams(request);
        List<Map<String, Object>> datas = originalMapper.getData(params);
        int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            List<String> SIDS = datas.stream().map(f -> f.get("SHENQINGH").toString()).collect(toList());
            List<v_PantentInfoMemo> memos = infoMemoMapper.getAllByIds(SIDS);
            datas.stream().forEach(f -> {
                Map<String, Object> row = eachSingleRow(f, memos);
                PP.add(f);
            });
            object.setTotal(Total);
            object.setData(PP);
        }
        return object;
    }

    private Map<String, Object> getParams(HttpServletRequest request) throws Exception {

        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "AddTime";
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageIndex == 0 ? pageIndex * pageSize : pageIndex * pageSize + 1);
        params.put("End", pageSize * (pageIndex + 1));
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            params.put("DepID", Info.getDepId());
            params.put("UserID", Info.getUserId());
            params.put("RoleName", Info.getRoleName());
        } else throw new RuntimeException("登录信息失效，请重新登录！");

        String shenqingh=request.getParameter("Shenqingh");
        if(StringUtils.isEmpty(shenqingh)==false){
            params.put("Shenqingh",shenqingh);
        }
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

    private Map<String, Object> eachSingleRow(
            Map<String, Object> row,
            List<v_PantentInfoMemo> memos) {
        row.remove("_TotalNum");
        String SHENQINGH = row.get("SHENQINGH").toString();
        String NEIBUBH = SuperUtils.toString(row.get("NEIBUBH"));

        PantentInfoMemoCreator creator = new PantentInfoMemoCreator(memos);
        List<String> words = creator.Build(SHENQINGH);
        row.put("EDITMEMO", words.size() > 0 ? 1 : 0);
        if (words.size() > 0) {
            row.put("MEMO", String.join("<br/><br/>", words));
        } else {
            row.put("MEMO", "");
        }

        NBBHInfo nbInfo = NBBHCode.Parse(NEIBUBH);
        nbInfo.foreach((type, ids) -> {
            List<String> names = ids.stream().map(f -> f.getName()).collect(Collectors.toList());
            if (names.size() > 0) {
                row.put(type, Strings.join(names, ','));
                if (type == "KH") row.put("KHID", ids.get(0).getID());
                if (type == "BH") {
                    UInfo FU = ids.get(0);
                    Optional<casesSub> findSubs = casesSubRep.findFirstBySubNo(FU.getName());
                    if (findSubs.isPresent()) {
                        row.put("CasesID", findSubs.get().getCasesId());
                    }
                }
            }
        });
        return row;
    }

    @Override
    public successResult Register(HttpServletRequest request) throws Exception {
        successResult res = new successResult();
        List<Map<String, Object>> list1 = new ArrayList<>();
        LoginUserInfo loginUserInfo = CompanyContext.get();
        int otype = 0;
        try {
            String barCode = request.getParameter("barcode");
            list1 = originalMapper.Search1(barCode);
            if (list1.size() > 0) {
                res.setSuccess(false);
                res.setMessage("此证书或通知书已登记");
                return res;
            } else {
                if (barCode.equals("")) throw new Exception("编码为空！");
                if (loginUserInfo == null) throw new Exception("登录失败，请重新登录！");
                tbOriginalExpress originalExpress = new tbOriginalExpress();
                originalExpress.setCheckInTime(new Date());
                originalExpress.setRegisterPerson(Integer.parseInt(loginUserInfo.getUserId()));
                originalExpress.setCoding(barCode);
                originalExpress.setOriginalStates(0);
                originalExpressRepository.save(originalExpress);
            }

        } catch (Exception ax) {
            res.setMessage(ax.getMessage());
            res.setSuccess(false);
        }
        return res;
    }

    @Override
    public successResult RegisterShouju(HttpServletRequest request) throws Exception {
        successResult res = new successResult();
        int otype = 3;
        LoginUserInfo loginUserInfo = CompanyContext.get();
        List<Map<String, Object>> list1 = new ArrayList<>();
        try {
            String barCode = request.getParameter("barCode");
            list1 = originalMapper.Search4(barCode);
            if (list1.size() > 0) {
                if (list1.size() > 0) {
                    res.setSuccess(false);
                    res.setMessage("此票据已登记");
                    return res;
                }
            }
        } catch (Exception ax) {
            res.setMessage(ax.getMessage());
            res.setSuccess(false);
        }
        return res;
    }

    @Override
    public successResult GetToken(HttpServletRequest request) throws Exception {
        successResult res = new successResult();
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            list = originalMapper.Search6();
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    res.setData(list.get(i).get("token"));
                }
            }
        } catch (Exception ax) {
            res.setMessage(ax.getMessage());
            res.setSuccess(false);
        }
        return res;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int SaveExpress(tbOriginalKd originalKd, String Dnum) throws Exception {
        List<String> listDnum = Arrays.asList(Dnum);
        List<String> list = Arrays.asList(Dnum.split(","));
        Random random = new Random();
        String BGBH = "BGBH-" + random.nextInt(1000000) + "-" + random.nextInt(10000);
        int result = 0;
        for (int i = 0; i < list.size(); i++) {
            result = Update(list.get(i), BGBH);
        }
        save(originalKd, BGBH);
        return result;
    }

    private int Update(String Dnum, String BGBH) {
        int result = originalExpressRepository.Update(BGBH, Dnum);
        return result;
    }

    public tbOriginalKd save(tbOriginalKd originalKd, String BGBH) throws Exception {
        if (originalKd == null) throw new Exception("数据为空！");
        LoginUserInfo loginUserInfo = CompanyContext.get();
        if (loginUserInfo == null) throw new Exception("登录失败,请重新登录！");
        tbOriginalKd result = null;
        if (originalKd.getOriginalKdId() == null) {
            originalKd.setPackageStatus(1);
            originalKd.setPackageNum(BGBH);
            originalKd.setApplicationTime(new Date());
            originalKd.setMailAppicant(Integer.parseInt(loginUserInfo.getUserId()));
            result = originalKdRepository.save(originalKd);
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int UpdateYJZT(String PickUpNumber, String Dnum, String PackageNum) throws Exception {
        List<String> listDnum = Arrays.asList(Dnum.split(","));
        List<String> listPackageNum = JSON.parseArray(PackageNum, String.class);
        int result = 0;
        for (int i = 0; i < listDnum.size(); i++) {
            String Coding = listDnum.get(i);
            result = UpYJZT(PickUpNumber, "1", Coding);
        }

        //根据包裹编号判断是否存在待邮寄和未邮寄的数据
        for (int j = 0; j < listPackageNum.size(); j++) {
            String PackageNumCount = originalMapper.getPackageNumCount(listPackageNum.get(j));
            //如果不存在待邮寄和未邮寄的数据
            if (Integer.parseInt(PackageNumCount) == 0) {
                //将包裹编号清空
                originalExpressRepository.DelOfUpPackageNum(listPackageNum.get(j));
                //将包裹管理表中的数据删除
                originalExpressRepository.DelOriginalKd(listPackageNum.get(j));
            } else {
                List<Map<String, Object>> listPackageContent = originalMapper.getPackageContent(listPackageNum.get(j));
                String PackageContent = "";
                if (listPackageContent.size() > 0) {
                    for (int x = 0; x < listPackageContent.size(); x++) {
                        String Count = listPackageContent.get(x).get("Count").toString();
                        String OtypeText = listPackageContent.get(x).get("otypeText").toString();
                        PackageContent = PackageContent + (OtypeText + Count + "份,");
                        PackageContent = PackageContent.substring(0, PackageContent.length() - 1);
                        originalExpressRepository.UpdatePackageContent(PackageContent, listPackageNum.get(j));
                    }
                }
            }
        }

        for (int i = 0; i < listDnum.size(); i++) {
            String Coding = listDnum.get(i);
            result = UpYJZT(PickUpNumber, "1", Coding);
        }
        return result;
    }

    private int UpYJZT(String PickUpNumber, String PackageStatus, String Coding) {
        LoginUserInfo loginUserInfo = CompanyContext.get();
        int result = originalExpressRepository.UpdateYJZT(PickUpNumber, PackageStatus,
                Integer.parseInt(loginUserInfo.getUserId()), new Date(), Coding);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean remove(List<Integer> ids) {
        for (int i = 0; i < ids.size(); i++) {
            Integer id = ids.get(i);
            originalExpressRepository.deleteById(id);
        }
        return true;
    }
}
