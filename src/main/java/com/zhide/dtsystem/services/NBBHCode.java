package com.zhide.dtsystem.services;

import com.google.common.base.Strings;
import com.zhide.dtsystem.models.NBBHInfo;
import com.zhide.dtsystem.models.UInfo;
import com.zhide.dtsystem.services.define.IClientInfoService;
import com.zhide.dtsystem.services.define.ILoginUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope(value = "prototype")
public class NBBHCode {

    @Autowired
    private IClientInfoService clientInfoService;
    @Autowired
    private ILoginUserService loginUserService;
    @Autowired
    private CompanyTimeOutCache loginUserCache;
    @Autowired
    private CompanyTimeOutCache khCache;

    String hKey = "Client";
    String lKey = "LoginUser";
    Map<String, String> userNameCache = new HashMap<>();
    Map<String, String> clientNameCache = new HashMap<>();
    Logger logger = LoggerFactory.getLogger(NBBHCode.class);

    private void Prepare(String Code) {
        GetNames();
        GetKHNames();
    }

    private void GetNames() {
        userNameCache = loginUserService.getAllByNameAndID();
    }


    private void GetKHNames() {
        try {
            clientNameCache = clientInfoService.getAllByNameAndID();
        } catch (Exception e) {
            logger.info("获取客户缓存时发生了错误:"+e.getMessage());
        }
    }

    public NBBHInfo Parse(String Code) {
        NBBHInfo Info = new NBBHInfo();
        if (Strings.isNullOrEmpty(Code) == false) {
            Code = Code.toUpperCase();
            Code = Code.replace("：", ":");
            Code = Code.replace("；", ";");
            Code=Code.replace("XS","YW");
            Prepare(Code);
            String[] Codes = Code.split(";");
            for (int i = 0; i < Codes.length; i++) {
                String TCode = Codes[i];
                ParseSingle(TCode, Info);
            }
        }
        return Info;
    }
    private void ParseSingle(String tCode, NBBHInfo Info) {
        if (tCode.indexOf(":") > -1) {
            String[] Codes = tCode.split(":");
            if (Codes.length == 2) {
                String Name = Codes[0].trim();
                Name=Name.toUpperCase(Locale.ROOT);
                String[] Values = Codes[1].trim().split(",");
                List<UInfo> Us = new ArrayList<UInfo>();
                for (int i = 0; i < Values.length; i++) {
                    String Value = Values[i];
                    String  ID = "0";
                    if (Name.equals("KH") == true) {
                        if (clientNameCache.containsKey(Value) == false) GetKHNames();
                        if (clientNameCache.containsKey(Value)) {
                            ID =String.valueOf(clientNameCache.get(Value));
                        } else {
                            Info.setDecodeAll(false);
                        }
                    } else if (Name.equals("BH") == true) {
                        ID = "1";
                    } else {
                        if (userNameCache.containsKey(Value) == false) GetNames();
                        if (userNameCache.containsKey(Value)) {
                            ID =String.valueOf(userNameCache.get(Value));
                        } else {
                            Info.setDecodeAll(false);
                        }
                    }
                    int XID=Integer.parseInt(ID);
                    if (XID > 0) {
                        UInfo UI = new UInfo(Value, XID);
                        Us.add(UI);
                    }
                }
                if (Us.size() > 0) Info.SetValue(Name, Us);
                else Info.setDecodeAll(false);
            }
        }
    }
}
