package com.zhide.dtsystem.services.implement;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.zhide.dtsystem.common.RedisUtils;
import com.zhide.dtsystem.mapper.ViewTZSMapper;
import com.zhide.dtsystem.models.CacheableTtl;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.tzsConfig;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.TZSRepository;
import com.zhide.dtsystem.repositorys.tzsConfigRepository;
import com.zhide.dtsystem.services.define.ITZSConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.*;

@Service
public class TZSConfigService implements ITZSConfigService {
    @Autowired
    tzsConfigRepository tzsRep;
    @Autowired
    TZSRepository tzsRepository;
    @Autowired
    ViewTZSMapper tzsMapper;
    @Autowired
    RedisUtils redisUtils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void SaveChange(List<String> IDS, String Type, String Name, Object readValue) throws Exception {
        String lowName = Name.toLowerCase();
        LoginUserInfo Info= CompanyContext.get();
        for (int i = 0; i < IDS.size(); i++) {
            String ID = IDS.get(i);
            tzsConfig config = null;
            Optional<tzsConfig> configs = tzsRep.getFirstByTzsbhAndType(ID, Type);
            if (configs.isPresent() == false) {
                config = new tzsConfig();
                config.setTzsbh(ID);
            } else config = configs.get();
            Method[] Ms = config.getClass().getMethods();
            Optional<Method> methods =
                    Arrays.stream(Ms).filter(f -> f.getName().toLowerCase().equals("set" + lowName)).findFirst();
            if (methods.isPresent()) {
                Method M = methods.get();
                M.invoke(config, readValue);
            }
            Optional<Method> method1s = Arrays.asList(Ms).stream().filter(f -> f.getName().toLowerCase().equals
                    ("set" + lowName + "date"))
                    .findFirst();
            if (method1s.isPresent()) {
                Method M1 = method1s.get();
                M1.invoke(config, new Date());
            }
            Optional<Method> method2s=
                    Arrays.asList(Ms).stream().filter(f->f.getName().toLowerCase().equals("set"+lowName+"man")).findFirst();
            if(method2s.isPresent()){
                Method M2=method2s.get();
                M2.invoke(config,Info.getUserIdValue());
            }
            config.setType(Type);
            if(lowName.equals("abort")){
                Boolean B=Boolean.parseBoolean(readValue.toString());
                if(B){
                    config.setReply(false);
                    config.setReplydate(null);
                    config.setIscommit(false);
                    config.setIscommitdate(null);
                }
            }
            else if(lowName.equals("reply")){
                Boolean  C=Boolean.parseBoolean(readValue.toString());
                if(C==false) {
                    config.setReplydate(null);
                    config.setReplyman(null);
                }
            }
            else if(lowName.equals("iscommit")){
                Boolean  D=Boolean.parseBoolean(readValue.toString());
                if(D==false) {
                    config.setIscommitdate(null);
                    config.setIscommitman(null);
                }
            }
            tzsRep.save(config);
            if(lowName.equals("abort") || lowName.equals("reply") || lowName.equals("iscommit")){
                redisUtils.clearAll("getTZSTotal");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void SaveNiCheChange(List<String> IDS, List<String> TONGZHISBH, String Name, Object readValue) throws Exception {
        String lowName = Name.toLowerCase();
        LoginUserInfo Info= CompanyContext.get();
        String Type="";
        for (int i = 0; i < IDS.size(); i++) {
            String ID = IDS.get(i);
            String TZSBH = "";
            if (TONGZHISBH.size() > 0) {
                TZSBH = TONGZHISBH.get(i);
            }
            tzsConfig config = null;
            Optional<tzsConfig> configs = tzsRep.getAllByTzsbh(ID);
            String TZSMC = "";
            if (lowName.equals("ja")) {
                TZSMC = tzsRepository.getTongzhismcByTongzhisbh(TZSBH);
            } else {
                TZSMC = tzsRepository.getTongzhismcByTongzhisbh(ID);
            }
            if (configs.isPresent() == false) {
                config = new tzsConfig();
                config.setTzsbh(ID);
                config.setType(TZSMC);
            } else config = configs.get();
            Method[] Mss = config.getClass().getMethods();
            Optional<Method> methods =
                    Arrays.stream(Mss).filter(f -> f.getName().toLowerCase().equals("set" + lowName)).findFirst();
            if (methods.isPresent()) {
                Method M = methods.get();
                M.invoke(config, readValue);
            }
            Optional<Method> method1s = Arrays.asList(Mss).stream().filter(f -> f.getName().toLowerCase().equals
                    ("set" + lowName + "date"))
                    .findFirst();
            if (method1s.isPresent()) {
                Method M1 = method1s.get();
                M1.invoke(config, new Date());
            }
            Optional<Method> method2s=
                    Arrays.asList(Mss).stream().filter(f->f.getName().toLowerCase().equals("set"+lowName+"man")).findFirst();
            if(method2s.isPresent()){
                Method M2=method2s.get();
                M2.invoke(config,Info.getUserIdValue());
            }
            tzsRep.save(config);
        }
    }

    @Override
    @CacheableTtl(value = "getTZSTotal", ttl = 300)
    public List<Map<String, Object>> getTZSTotal(int DepID, int UserID, String RoleName, String Type) {
        return tzsMapper.getTZSTotal(DepID,UserID,RoleName,Type);
    }
}
