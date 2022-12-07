package com.zhide.dtsystem.services.instance;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.DateTimeUtils;
import com.zhide.dtsystem.common.EntityHelper;
import com.zhide.dtsystem.models.TZS;
import com.zhide.dtsystem.models.shenqingxx;
import com.zhide.dtsystem.models.tbFeeItem;
import com.zhide.dtsystem.repositorys.TZSRepository;
import com.zhide.dtsystem.repositorys.shenqingxxRepository;
import com.zhide.dtsystem.repositorys.tbFeeItemRepository;
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
public class TZSInfoUpdater implements IZlInfoUpdater {
    @Autowired
    TZSRepository tzsRep;
    @Autowired
    tbFeeItemRepository feeItemRep;
    @Autowired
    shenqingxxRepository sqxxRep;

    @Override
    public boolean accept(String filePath) {
        File file = new File(filePath);
        return file.getName().toLowerCase().equals("tzs.txt");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(String fileContent) throws Exception {
        List<TZS> datas = JSON.parseArray(fileContent, TZS.class);
        for (int i = 0; i < datas.size(); i++) {
            TZS result = null;
            TZS tzs = datas.get(i);
            String tzsbh = tzs.getTongzhisbh();
            Optional<TZS> find = tzsRep.findById(tzsbh);
            if (find.isPresent()) {
                result = find.get();
                EntityHelper.copyObject(tzs, result);
            } else result = tzs;
            String shenqingbh = result.getShenqingbh();
            if (shenqingbh.indexOf("{") > -1) {
                shenqingbh = shenqingbh.substring(1, shenqingbh.length() - 2);
                result.setShenqingbh(shenqingbh);
            }
            if (result.getFawenrq().after(new Date())) {
                result.setZhuangtai(3);
            }
            Date dafuDate = result.getDafurq();
            if (dafuDate != null) {
                if (DateTimeUtils.getYear(dafuDate) == 1) {
                    result.setDafurq(null);
                }
            }

            if(StringUtils.isEmpty(result.getShenqingh())){
                if(StringUtils.isEmpty(result.getShenqingbh())==false){
                   Optional<shenqingxx> findOnes= sqxxRep.findFirstByShenqingbh(result.getShenqingbh());
                   if(findOnes.isPresent()){
                       result.setShenqingh(findOnes.get().getShenqingh());
                   }
                }
            }
            tzsRep.save(result);

            if (tzs.getItems().size() > 0) {
                List<tbFeeItem> items = tzs.getItems();
                for (int a = 0; a < items.size(); a++) {
                    tbFeeItem item = items.get(a);
                    item.setCreatetime(new Date());
                }
                feeItemRep.saveAll(items);
            }
        }
        return datas.size();
    }
}
