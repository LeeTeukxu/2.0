package com.zhide.dtsystem.services.instance;


import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.ExceptionUtils;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.repositorys.ajRepository;
import com.zhide.dtsystem.repositorys.patentInfoPermissionRepository;
import com.zhide.dtsystem.repositorys.shenqingxxRepository;
import com.zhide.dtsystem.services.NBBHCode;
import com.zhide.dtsystem.services.define.IZlInfoUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class AJInfoUpdater implements IZlInfoUpdater {

    @Autowired
    ajRepository ajRep;
    @Autowired
    patentInfoPermissionRepository patentPerRep;
    @Autowired
    shenqingxxRepository shenqingRep;
    @Autowired
    NBBHCode NBBHCode;

    @Override
    public boolean accept(String filePath) {
        File file = new File(filePath);
        return file.getName().toLowerCase().equals("aj.txt");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(String fileContent) throws Exception {
        List<aj> datas = JSON.parseArray(fileContent, aj.class);
        String targetObject = "";
        try {
            for (int i = 0; i < datas.size(); i++) {
                aj data = datas.get(i);
                targetObject = JSON.toJSONString(data);
                String shenqingbh = data.getShenqingbh();
                if (StringUtils.isEmpty(shenqingbh) == true) continue;
                data.setCreateTime(new Date());
                ajRep.save(data);

                if (StringUtils.isEmpty(data.getNeibubh()) == false) {
                    Optional<shenqingxx> findOnes = shenqingRep.findById(shenqingbh);
                    if (findOnes.isPresent()) {
                        String shenqingh = findOnes.get().getShenqingh();
                        NBBHInfo getInfo = NBBHCode.Parse(data.getNeibubh());
                        getInfo.foreach((type, items) -> {
                            for (int n = 0; n < items.size(); n++) {
                                UInfo item = items.get(n);
                                List<patentInfoPermission> all = patentPerRep
                                        .findAllByShenqinghAndUsertypeAndUserid(shenqingh, type, item.getID());
                                if (all.size() == 0) {
                                    getLogger().info("内部编号解析成功,准备插入数据:" + JSON.toJSONString(item));
                                    patentInfoPermission px = new patentInfoPermission();
                                    px.setUsertype(type);
                                    px.setUserid(item.getID());
                                    px.setShenqingh(shenqingh);
                                    patentPerRep.save(px);
                                }
                            }
                        });
                    }
                }
            }
        } catch (Exception ax) {
            throw new Exception("何存AJ信息发生错误:" + ExceptionUtils.getStrackTrace(ax) + "\r\n" + "错误源是:" + targetObject);
        }
        return datas.size();
    }
}
