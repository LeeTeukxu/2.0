package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.patentGovFee;

/*处理抓取官费数据处理接口*/
public interface IGovFeeProcessor {
    boolean accept(String  costName);
    void execute(patentGovFee item) throws Exception;
    void clear();
    void saveAll();
}
