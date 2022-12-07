package com.zhide.dtsystem.services.define;

import java.util.List;
import java.util.Map;

public interface ITZSConfigService {
    void SaveChange(List<String> IDS, String Type, String Name, Object readValue) throws Exception;

    void SaveNiCheChange(List<String> IDS, List<String> TONGZHISBH, String Name, Object readValue) throws Exception;
    List<Map<String, Object>> getTZSTotal(int DepID, int UserID, String RoleName,String Type);

}
