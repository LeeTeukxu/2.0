package com.zhide.dtsystem.services.instance;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.DateTimeUtils;
import com.zhide.dtsystem.common.EntityHelper;
import com.zhide.dtsystem.common.ExceptionUtils;
import com.zhide.dtsystem.models.pantentInfo;
import com.zhide.dtsystem.models.shenqingxx;
import com.zhide.dtsystem.repositorys.ajRepository;
import com.zhide.dtsystem.repositorys.pantentInfoRepository;
import com.zhide.dtsystem.repositorys.shenqingxxRepository;
import com.zhide.dtsystem.services.define.IZlInfoUpdater;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class ShenQingXXInfoUpdater implements IZlInfoUpdater {
    @Autowired
    shenqingxxRepository shenqingRep;
    @Autowired
    pantentInfoRepository patentRep;
    @Autowired
    ajRepository ajRep;

    @Override
    public boolean accept(String filePath) {
        File file = new File(filePath);
        return file.getName().toLowerCase().equals("shenqing.txt");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(String fileContent) throws Exception {
        String TargetObj = "";
        List<shenqingxx> datas = JSON.parseArray(fileContent, shenqingxx.class);
        try {
            for (int i = 0; i < datas.size(); i++) {
                shenqingxx data = datas.get(i);
                String shenqingbh = data.getShenqingbh();
                if (StringUtils.isEmpty(shenqingbh)) continue;
                TargetObj = JSON.toJSONString(data);
                int sqYear = DateTimeUtils.getYear(data.getShenqingr());

                Date shenqingr = data.getShenqingr();
                if (shenqingr != null) {
                    if (sqYear < 1971) {
                        data.setShenqingr(null);
                    }
                }
                Date chuangjianr = data.getChuangjianr();
                if (chuangjianr != null) {
                    int cjYear = DateTimeUtils.getYear(chuangjianr);
                    if (cjYear < 1971) {
                        data.setChuangjianr(null);
                    }
                }
                shenqingxx result = data;
                Optional<shenqingxx> find = shenqingRep.findById(shenqingbh);
                if (find.isPresent()) {
                    result = find.get();
                    EntityHelper.copyObject(data, result);
                    result.setUploadtime(new Date());
                } else result.setUploadtime(new Date());
                shenqingRep.save(result);

                String shenqingh = result.getShenqingh();
                if (StringUtils.isEmpty(shenqingh)) continue;
                pantentInfo newOne = new pantentInfo();
                Optional<pantentInfo> fs = patentRep.findById(shenqingh);
                if (fs.isPresent()) {
                    newOne = fs.get();
                    //getLogger().info("更新了:"+shenqingh+"数据。");
                }
                newOne.setShenqingh(shenqingh);
                newOne.setShenqingbh(shenqingbh);
                newOne.setShenqinglx(result.getShenqinglx());
                newOne.setFamingmc(result.getZhuanlimc());
                Date sqr = result.getShenqingr();
                if (sqr != null) {
                    int sqrYear = DateTimeUtils.getYear(sqr);
                    if (sqrYear > 1970) newOne.setShenqingr(sqr);
                }
                Date cjr = result.getChuangjianr();
                if (cjr != null) {
                    int cjrYear = DateTimeUtils.getYear(cjr);
                    if (cjrYear > 1970) newOne.setChuangjianr(cjr);
                }
                newOne.setLastupdatetime(new Date());
                patentRep.save(newOne);
            }
        } catch (Exception ax) {
            String trace = ExceptionUtils.getStrackTrace(ax);
            throw new Exception("处理SHENQINGXX时发生错误:" + trace + ",数据为:" + TargetObj);

        }
        return datas.size();
    }
}
