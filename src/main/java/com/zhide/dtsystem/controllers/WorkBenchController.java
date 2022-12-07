package com.zhide.dtsystem.controllers;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.DateTimeUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.WorkBenchMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.companyConfig;
import com.zhide.dtsystem.models.tbMenuList;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.companyConfigRepository;
import com.zhide.dtsystem.services.define.IWorkBenchService;
import com.zhide.dtsystem.services.define.ItbMenuListService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/workBench")
public class WorkBenchController {
    @Autowired
    ItbMenuListService itbMenuListService;
    @Autowired
    IWorkBenchService workBenchService;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    companyConfigRepository companyRep;
    @Autowired
    WorkBenchMapper benchMapper;

    @RequestMapping("/index")
    public String Index(Map<String, Object> model) {
        LoginUserInfo Info = CompanyContext.get();
        List<tbMenuList> shorts = itbMenuListService.getVisibleMenus(Integer.parseInt(Info.getRoleId()))
                .stream().filter(f -> f.getShortCut() != null)
                .filter(f -> f.getShortCut() == true).collect(Collectors.toList());
        int size = shorts.size();
        for (int i = 0; i < size; i++) {
            tbMenuList item = shorts.get(i);
            if (StringUtils.isEmpty(item.getUrl())) continue;
            if (StringUtils.isEmpty(item.getTitle())) continue;
            String pageName = item.getPageName();
            String url = item.getUrl();
            if (url.startsWith("/work/notice")) {
                item.setTitle(item.getTitle().replace("期限", "")
                        .replace("监控", "").replace("办理", ""));
            }
            if (url.indexOf("pageName") == -1) {
                if (url.indexOf("?") == -1) {
                    url += "?pageName=" + pageName;
                } else {
                    url += "&pageName=" + pageName;
                }
            }
            url += "&MenuID=" + Integer.toString(item.getFid());
            item.setUrl(url);
        }
        model.put("menus", shorts);
        List<Map<String, Object>> types = workBenchService.getPantentTotalByType();
        List<Map<String, Object>> buzhengs = workBenchService.getBuZhengNoticeTotal();
        List<Map<String, Object>> shenchas = workBenchService.getShenChaNoticeTotal();
        model.put("types", JSON.toJSONString(types));
        model.put("typeData", types);

        model.put("buzhengs", JSON.toJSONString(buzhengs));
        model.put("buzhengData", buzhengs);

        model.put("shenchas", JSON.toJSONString(shenchas));
        model.put("shenchaData", shenchas);

        model.put("UserInfo", CompanyContext.get());
        model.put("Year",Integer.toString(LocalDateTime.now().getYear()));
        Optional<companyConfig> finds=companyRep.findAll().stream().findFirst();
        if(finds.isPresent()){
            companyConfig find=finds.get();
            int Days= DateTimeUtils.getDays(new Date(),find.getEndTime());
            String PP="<span style='color:"+(Days<=30?"red":"blue")+"'>剩余"+Integer.toString(Days)+"天"+"</span>";
            model.put("Days",PP);
        } else model.put("Days","无限制");
        model.put("CaseNum",benchMapper.getCaseMainNum());
        return "/workBench";

    }
    @RequestMapping("/getLastMonthTZS")
    @ResponseBody
    public List<Map<String,Object>> getLastMonthTZS(String Type,HttpServletRequest request){
        List<Map<String,Object>> Datas=new ArrayList<>();
        try {
            String Key  = request.getParameter("Key");
            Datas=workBenchService.getLastMonthTZS(Key,Type,request);
        }
        catch(Exception ax){
            Datas=new ArrayList<>();
        }
        return Datas;
    }
    @RequestMapping("/getLastMonthGovFee")
    @ResponseBody
    public pageObject getLastMonthGovFee(HttpServletRequest request){
        pageObject result=new pageObject();
        try {
            List<Map<String,Object>> datas=workBenchService.getLastMonthGovFee(request);
            int total = 0;
            if (datas.size() > 0) {
                total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            }
            result.setData(datas);
            result.setTotal(total);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/getNumbers")
    public successResult getNumbers(){
        successResult result=new successResult(false);
        Map<String,Integer> N=new HashMap<>();
        N.put("P",0);
        N.put("G",0);
        try {
            LoginUserInfo Info=CompanyContext.get();
            String PKey="AcceptGovFee::"+Info.getCompanyId();
            String GKey="AcceptPatentInfo::"+Info.getCompanyId();
            if(redisTemplate.hasKey(PKey)){
               Integer PP=Math.toIntExact(redisTemplate.opsForList().size(PKey));
               N.replace("P",PP);
            }
            if(redisTemplate.hasKey(GKey)){
                Integer GG=Math.toIntExact(redisTemplate.opsForList().size(GKey));
                N.replace("G",GG);
            }
            result.setData(N);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
    @RequestMapping("/getWorkColumn")
    @ResponseBody
    public successResult getWorkColumns(int Year){
        successResult result=new successResult();
        try {
           result.setData(workBenchService.getWorkColumns(Year));
        }catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
    @RequestMapping("/getData")
    @ResponseBody
    public pageObject getData(int Year){
        pageObject obj=new pageObject();
        List<Map<String,Object>> rows=workBenchService.getWorkMonths(Year);
        obj.setData(rows);
        obj.setTotal(rows.size());
        return obj;
    }
}
