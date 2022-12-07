package com.zhide.dtsystem.services.implement;

import com.google.common.base.Strings;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.pantentInfoMemoRepository;
import com.zhide.dtsystem.repositorys.pantentInfoRepository;
import com.zhide.dtsystem.repositorys.patentInfoPermissionRepository;
import com.zhide.dtsystem.services.define.IChangeTechManService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChangeTechManServiceImpl implements IChangeTechManService {

    @Autowired
    pantentInfoRepository pantentRep;
    @Autowired
    patentInfoPermissionRepository patentPermissionRep;
    @Autowired
    pantentInfoMemoRepository memoRep;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean SaveAll(List<ChangeTechManResult> Datas, Map<Integer, String> Users) throws Exception {
        Map<String, String> VV = ChangeToMap(Datas);
        for (int i = 0; i < Datas.size(); i++) {
            ChangeTechManResult result = Datas.get(i);
            String Shenqingh = result.getSHENQINGH();
            String OldXs = result.getOLDXS();
            String NowXs = result.getNOWXS();
            String OldName = "";
            if (Users.containsKey(OldXs)) {
                OldName = Users.get(OldXs);
            }
            String NowName = Users.get(NowXs);
            if (OldXs != null) {
                if (Integer.parseInt(OldXs) > 0) {
                    List<patentInfoPermission> Finds = patentPermissionRep.findAllByShenqinghAndUsertypeAndUserid
                            (Shenqingh, "JS", Integer.parseInt(OldXs));
                    patentPermissionRep.deleteAll(Finds);
                    AddChangeMemo(Shenqingh, OldName, NowName);
                    AddJSPermission(Shenqingh, Integer.parseInt(NowXs));
                    if (VV.containsKey(Shenqingh)) {
                        String NeiBuBh = VV.get(Shenqingh);
                        UpdateNeiBuBH(Shenqingh, NeiBuBh, NowName, OldName);
                    } else throw new Exception(Shenqingh + "没有找到相关记录。");
                }
            } else if (OldXs == null) {
                AddChangeMemo(Shenqingh, OldName, NowName);
                AddJSPermission(Shenqingh, Integer.parseInt(NowXs));
                if (VV.containsKey(Shenqingh)) {
                    String NeiBuBH = VV.get(Shenqingh);
                    UpdateNeiBuBHToJS(Shenqingh, NeiBuBH, NowName, OldName);
                } else throw new Exception(Shenqingh + "没有找到相关记录。");
            }
        }
        return true;
    }

    private Map<String, String> ChangeToMap(List<ChangeTechManResult> Datas) {
        Map<String, String> result = new HashMap<>();
        List<String> PIDS = Datas.stream().map(f -> f.getSHENQINGH()).distinct().collect(Collectors.toList());
        List<pantentInfo> pantentInfos = pantentRep.findAllByShenqinghIn(PIDS);
        for (int i = 0; i < pantentInfos.size(); i++) {
            pantentInfo Info = pantentInfos.get(i);
            String Shenqingh = Info.getShenqingh();
            String Neibubh = Info.getNeibubh();
            result.put(Shenqingh, Neibubh);
        }
        return result;
    }

    private void AddChangeMemo(String shenqingh, String OldName, String NowName) {
        LoginUserInfo LogInfo = CompanyContext.get();
        PantentInfoMemo Info = new PantentInfoMemo();
        Info.setMid(UUID.randomUUID().toString());
        Info.setMemo(LogInfo.getUserName()+"使用转移技术人员功能，将专利:"+shenqingh+"的技术人员由:" + OldName + "被更换为:" + NowName);
        Info.setCreateMan(Integer.parseInt(LogInfo.getUserId()));
        Info.setCreateDate(new Date());
        Info.setShenqingh(shenqingh);
        memoRep.save(Info);
    }

    private void AddJSPermission(String Shenqingh, Integer NowJs) {
        patentInfoPermission Info = new patentInfoPermission();
        //Info.setPpid(UUID.randomUUID().toString());
        Info.setShenqingh(Shenqingh);
        Info.setUserid(NowJs);
        Info.setUsertype("JS");
        patentPermissionRep.save(Info);
    }

    private void UpdateNeiBuBH(String Shenqingh, String NeiBuBh, String NowName, String OldName) throws Exception {
        String RR = "";
        if (Strings.isNullOrEmpty(NeiBuBh)) {
            RR = "JS:" + NowName;
        } else {
            String[] SS=NeiBuBh.split(";");
            List<String> Codes=new ArrayList<>();
            Codes.add("JS:"+NowName);
            for(String X:SS){
                if(X.startsWith("JS")) continue;
                Codes.add(X);
            }

            RR=String.join(";",Codes);
        }
        Optional<pantentInfo> Find = pantentRep.findById(Shenqingh);
        if (Find.isPresent()) {
            pantentInfo Info = Find.get();
            Info.setNeibubh(RR);
            pantentRep.save(Info);
        } else throw new Exception(Shenqingh + "无法找到匹配的记录。");
    }

    private void UpdateNeiBuBHToJS(String Shenqingh, String NeiBuBH, String NowName, String OldName) throws Exception {
        String[] listStr = null;
        if (!Strings.isNullOrEmpty(NeiBuBH)) {
            listStr = NeiBuBH.split(";");
        }
        String RR = "";
        String strNeiBuBH = "";
        if (Strings.isNullOrEmpty(NeiBuBH)) {
            RR = "JS:" + NowName;
        } else {
            List<String> Codes=new ArrayList<>();
            Codes.add("JS:"+NowName);
            for(String X:listStr){
                if(X.startsWith("JS")) continue;
                Codes.add(X);
            }

            RR=String.join(";",Codes);
        }
        Optional<pantentInfo> Find = pantentRep.findById(Shenqingh);
        if (Find.isPresent()) {
            pantentInfo info = Find.get();
            info.setNeibubh(RR);
            pantentRep.save(info);
        } else throw new Exception(Shenqingh + "无法找到匹配的记录。");
    }
}
