package com.zhide.dtsystem.services.instance;

import com.zhide.dtsystem.models.pantentInfo;
import com.zhide.dtsystem.models.xml.*;
import io.netty.util.internal.StringUtil;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

import static java.util.stream.Collectors.toList;

/*
 * 从申请文件中获取一部分信息更新到专利信息中。
 * */
public class PatentInfoGetter {
    CPCPageIndexObject indexObject;
    String cpcPath;
    int cpcType = 0;
    String type;
    String manName;
    Hashtable<Integer, String> types = new Hashtable<>();

    Logger logger = LoggerFactory.getLogger(PatentInfoGetter.class);
    Map<Integer, String> typeMans = new HashMap<Integer, String>();

    public PatentInfoGetter(String cpcPath, CPCPageIndexObject indexObject) {
        types.put(0, "发明专利请求书");
        types.put(1, "实用新型专利请求书");
        types.put(2, "外观设计专利请求书");


        typeMans.put(0, "发明人");
        typeMans.put(1, "发明人");
        typeMans.put(2, "设计人");

        this.cpcPath = cpcPath;
        this.indexObject = indexObject;
        cpcType = indexObject.getQingqiuxx().getYEWULX();
        type = types.get(cpcType);
        manName = typeMans.get(cpcType);
    }

    public pantentInfo getInstance() throws Exception {
        pantentInfo result = new pantentInfo();
        result.setShenqingh(indexObject.getQingqiuxx().getSHENQINGH());
        File cpcDir = new File(cpcPath);
        result.setShenqingbh(cpcDir.getName());
        File targetFile = findCPCFile();
        List<String> fs = getFamingrxm(targetFile)
                .stream()
                .filter(f -> StringUtil.isNullOrEmpty(f.getName()) == false)
                .distinct()
                .map(f -> f.getName())
                .collect(toList());
        if (fs.size() > 0) {
            result.setFamingrxm(Strings.join(fs, ','));
            logger.info("从" + result.getShenqingbh() + "文件中解析出发明人:" + result.getFamingrxm());
        }
        List<String> ss = getShenqingrxm(targetFile)
                .stream()
                .filter(f -> StringUtil.isNullOrEmpty(f.getName()) == false)
                .map(f -> f.getName().trim())
                .distinct()
                .collect(toList());
        if (ss.size() > 0) {
            result.setShenqingrxm(Strings.join(ss, ','));
            logger.info("从" + result.getShenqingbh() + "文件中解析出申请人:" + result.getShenqingrxm());
        }
        DLJGMC dljgmc = getDLMC(targetFile);
        String ds = dljgmc.getName();
        if (Strings.isEmpty(ds) == false) {
            result.setDailijgmc(ds);
            logger.info("从" + result.getShenqingbh() + "文件中解析出代理机构名称:" + result.getDailijgmc());
        }
        List<String> dls = dljgmc.getDLRXM()
                .stream()
                .filter(f -> StringUtil.isNullOrEmpty(f) == false)
                .distinct()
                .collect(toList());
        if (dls.size() > 0) {
            result.setDiyidlrxm(Strings.join(dls, ','));
            logger.info("从" + result.getShenqingbh() + "文件中解析出代理人员名称:" + result.getDiyidlrxm());
        }
        LXRXM lxrxm = getLXM(targetFile);
        if (!Strings.isNotEmpty(lxrxm.getName())) {
            result.setLianxirxm(lxrxm.getName());
        }
        if (!Strings.isNotEmpty(lxrxm.getPostCode())) {
            result.setLianxiryb(lxrxm.getPostCode());
        }
        if (!Strings.isNotEmpty(lxrxm.getAddress())) {
            result.setLianxirdz(lxrxm.getAddress());
        }
        result.setShenqinglx(cpcType);
        return result;
    }

    private File findCPCFile() {
        Optional<BAONEIWJXX> finds = indexObject.getBaoneiwjxxList()
                .stream().filter(f -> f.getWENJIANMC().endsWith("专利请求书"))
                .findFirst();
        if (finds.isPresent()) {
            BAONEIWJXX find = finds.get();
            String newPath = cpcPath + "\\new\\" + find.getXIANGDUILJ();
            return new File(newPath);
        } else return null;
    }

    private List<FAMINGRXM> getFamingrxm(File file) throws Exception {
        List<FAMINGRXM> Fs = XmlParsor.getByChildNodes(file, "//" + type + "/" + manName + "/*", FAMINGRXM.class);
        return Fs;
    }

    private List<SHENQINGRXM> getShenqingrxm(File file) throws Exception {
        List<SHENQINGRXM> Ft = XmlParsor.getByChildNodes(file, "//" + type + "/申请人/*", SHENQINGRXM.class);
        return Ft;
    }

    private DLJGMC getDLMC(File file) throws Exception {
        DLJGMC dljgmc = XmlParsor.getSingleByChildNode(file, "//" + type + "/专利代理机构", DLJGMC.class);
        return dljgmc;
    }

    private LXRXM getLXM(File file) throws Exception {
        LXRXM lxrxm = XmlParsor.getSingleByChildNode(file, "//" + type + "/联系人", LXRXM.class);
        return lxrxm;
    }
}
