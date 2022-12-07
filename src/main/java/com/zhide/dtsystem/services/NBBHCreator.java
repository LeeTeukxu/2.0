package com.zhide.dtsystem.services;

import com.zhide.dtsystem.models.casesMain;
import com.zhide.dtsystem.models.casesOutSourceMain;
import com.zhide.dtsystem.models.casesSub;
import com.zhide.dtsystem.repositorys.casesMainRepository;
import com.zhide.dtsystem.repositorys.casesOutSourceMainRepository;
import com.zhide.dtsystem.repositorys.casesSubRepository;
import com.zhide.dtsystem.services.define.IClientInfoService;
import com.zhide.dtsystem.services.define.ILoginUserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: NBBHDecoder
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年09月06日 12:30
 **/
@Component
public class NBBHCreator {
    @Autowired
    casesMainRepository mainRep;
    @Autowired
    casesSubRepository subRep;
    @Autowired
    IClientInfoService clientInfoService;
    @Autowired
    ILoginUserService loginUserService;
    @Autowired
    casesOutSourceMainRepository casesOutMainRep;
    Logger logger= LoggerFactory.getLogger(NBBHCreator.class);

    Map<String, String> UserCache = new HashMap<>();
    Map<String, String> KHCache = new HashMap<>();

    private void init() throws Exception {
        UserCache = loginUserService.getAllByIDAndName();
        KHCache = clientInfoService.getAllByIDAndName();
    }

    /**
     * @Author:肖新民
     * @CreateTime:2020-09-06 16:51
     * @Params:[SubID] Description: 从交单信息里面生成内部编号。
     */
    public String getContent(String SubID) throws Exception {
        init();
        String AK = "";
        Optional<casesSub> subOnes = subRep.findFirstBySubId(SubID);
        if (subOnes.isPresent()) {
            casesSub subOne = subOnes.get();
            String casesId = subOne.getCasesId();
            Optional<casesMain> mainOnes = mainRep.findFirstByCasesId(casesId);
            if (mainOnes.isPresent()) {
                casesMain mainOne = mainOnes.get();
                AK = getFromModel(mainOne, subOne);
            }
        }
        return AK;
    }

    private String getFromModel(casesMain main, casesSub sub) throws Exception {
        if(UserCache==null || KHCache==null) init();
        List<String> res = new ArrayList<>();
        String  XSMan =Integer.toString( sub.getCreateMan());
        if (UserCache.containsKey(XSMan)) {
            res.add("XS:" + UserCache.get(XSMan));
        } else throw new Exception("销售人员:" + XSMan + "无法解析人员姓名!");

        String  JSMan =Integer.toString( sub.getTechMan());
        if (UserCache.containsKey(JSMan)) {
            res.add("JS:" + UserCache.get(JSMan));
        } else throw new Exception("技术人员:" + JSMan + "无法解析人员姓名!");
        String  LCMan =Integer.toString( main.getAuditMan());
        if (UserCache.containsKey(LCMan)) {
            res.add("LC:" + UserCache.get(LCMan));
        } else throw new Exception("流程人员:" + LCMan + "无法解析人员姓名!");
        if (sub.getOutTech() == true) {
            String SubID = sub.getSubId();
            Optional<casesOutSourceMain> findOuts = casesOutMainRep.findFirstBySubId(SubID);
            if (findOuts.isPresent()) {
                casesOutSourceMain outMain = findOuts.get();
                if(outMain.getTechMan()!=null) {
                    String OutTechMan = Integer.toString(outMain.getTechMan());
                    if (UserCache.containsKey(OutTechMan)) {
                        res.add("ZC:" + UserCache.get(OutTechMan));
                    } else throw new Exception("外协人员:" + OutTechMan + "无法解析人员姓名");
                }
            }
        }
        String  ClientID =Integer.toString(main.getClientId());
        if (KHCache.containsKey(ClientID)) {
            res.add("KH:" + KHCache.get(ClientID));
        } else throw new Exception("客户:" + LCMan + "无法解析人员姓名!");
        String SubNo = sub.getSubNo();
        if (StringUtils.isEmpty(SubNo)) {
            throw new Exception("SubNo为空，生成出现异常!");
        } else res.add("BH:" + SubNo);
        return StringUtils.join(res, ";");
    }

    /**
     * @Author:肖新民
     * @CreateTime:2020-09-06 16:54
     * @Params:[numberText] Description:
     */
    public Map<String, String> parse(String text) throws Exception {
        init();
        Map<String, String> result = new HashMap<>();
        String[] Vs = text.split(";");
        for (int i = 0; i < Vs.length; i++) {
            String V = Vs[i];
            String[] Ks = V.split(":");
            Optional<Integer> X = null;
            String KeyName = Ks[0];
            if (KeyName.equals("BH") || KeyName.equals("KH")) result.put(Ks[0], Ks[1]);
            else {
                X = findUserByName(Ks[1]);
                if (X.isPresent()) result.put(Ks[0], X.get().toString());
            }
        }
        return result;
    }
    /**
     * create by: mmzs
     * description: TODO
     * create time:
     *
     换一个人。重新生成新的内部编号。
     * @return
     */
    public String replace(String source,String type,int now) throws Exception{
        init();
        if(StringUtils.isEmpty(source)) source="";
        type=type.toUpperCase();
        source=source.replace("XS","YW");
        List<String> kx= Arrays.stream(source.split(";")).collect(Collectors.toList());
        if(source.indexOf(type)==-1) kx.add(type+":aaa");
        int x=kx.size();
        for(int i=0;i<x;i++){
            String single=kx.get(i);
            if(single.indexOf(type)>-1){
                String[] vv=single.split(":");
                String nowName="";
                String nowKey=Integer.toString(now);
                if(type.equals("KH")){
                    if(KHCache.containsKey(nowKey)){
                        nowName=KHCache.get(nowKey);
                    } else throw new Exception("客户ID:"+nowKey+"在系统中不存在!");
                }  else {
                    if(UserCache.containsKey(nowKey)) {
                        nowName=UserCache.get(nowKey);
                    } else throw  new Exception("用户ID:"+nowKey+"在系统中不存在!");
                }
                if(vv.length==2) {
                    vv[1] = nowName;
                    String vvk=String.join(":",vv);
                    kx.set(i,vvk);
                }
            }
        }
        return String.join(";",kx);
    }

    private Optional<Integer> findUserByName(String Name) {
        Optional<Integer> result = Optional.ofNullable(0);
        for (String  X : UserCache.keySet()) {
            String vName = UserCache.get(X);
            if (vName.equals(Name)) {
                result = Optional.of(Integer.parseInt(X));
            }
        }
        return result;
    }

    private Optional<Integer> findKHByName(String Name) {
        Optional<Integer> result = Optional.ofNullable(0);
        for (String X : KHCache.keySet()) {
            String vName = KHCache.get(X);
            if (vName.equals(Name)) {
                result = Optional.of(Integer.parseInt(X));
            }
        }
        return result;
    }
}
