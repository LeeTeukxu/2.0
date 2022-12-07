package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.mapper.TZSEmailRecordMapper;
import com.zhide.dtsystem.services.define.ITZSEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TZSEmailRecordService implements ITZSEmailService {
    @Autowired
    TZSEmailRecordMapper tzsMapper;

    @Override
    public Map<String, Object> getAll(List<String> IDS) {
        Map<String, Object> result = new HashMap<>();
        for(int n=0;n<10;n++) {
            List<String> IS=IDS.stream().skip(n*1000).limit(1000).collect(Collectors.toList());
            if(IS.size()==0) break;
            List<Map<String, Object>> Vs = tzsMapper.getAll(IS);
            for (int i = 0; i < Vs.size(); i++) {
                Map<String, Object> S = Vs.get(i);
                String Code = S.get("Code").toString();
                String Num = S.get("Num").toString();
                if (!result.containsKey(Code)) {
                    result.put(Code, Num);
                }
            }
        }
        return result;
    }
}
