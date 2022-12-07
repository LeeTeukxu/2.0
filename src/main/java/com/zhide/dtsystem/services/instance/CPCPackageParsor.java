package com.zhide.dtsystem.services.instance;

import com.zhide.dtsystem.models.pantentInfo;
import com.zhide.dtsystem.models.xml.BAONEIWJXX;
import com.zhide.dtsystem.models.xml.CPCPageIndexObject;
import com.zhide.dtsystem.models.xml.QINGQIUXX;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Optional;

/*
 * CPC案卷包解析
 */
public class CPCPackageParsor {
    String cpcDirName = "";
    CPCPageIndexObject indexObject;
    static final Logger logger = LoggerFactory.getLogger(CPCPackageParsor.class);

    public CPCPackageParsor(String cpcDirName) {
        this.cpcDirName = cpcDirName;
        Init();
    }

    File listFile = null;

    private void Init() {
        try {
            listFile = new File(cpcDirName + "\\new\\list.xml");
            if (listFile.exists()) {
                QINGQIUXX obj = XmlParsor.getSingleByChildNode(listFile, "//data-bus/QINGQIUXX", QINGQIUXX.class);

                logger.info("当前上传的CPC包专利名称为:" + obj.getFAMINGMC());
                List<BAONEIWJXX> fields = XmlParsor.getByChildNodes(listFile, "//data-bus/WENJIANBYSXX/BAONEIWJXX",
                        BAONEIWJXX.class);
                logger.info("CPC包由" + Integer.toString(fields.size()) + "个文件组成。");
                indexObject = new CPCPageIndexObject();
                indexObject.setQingqiuxx(obj);
                indexObject.setBaoneiwjxxList(fields);
            } else logger.info(listFile.getPath() + "不存在，跳过解析过程。");
        } catch (Exception ax) {
            ax.printStackTrace();
        }
    }

    public pantentInfo getPatentInfo() {
        try {
            QINGQIUXX qingqiuxx = indexObject.getQingqiuxx();
            if (qingqiuxx == null) return null;
            String shenqingh = qingqiuxx.getSHENQINGH();
            if (Strings.isEmpty(shenqingh)) return null;
            PatentInfoGetter patentInfoGetter = new PatentInfoGetter(cpcDirName, indexObject);
            return patentInfoGetter.getInstance();
        } catch (Exception ax) {
            ax.printStackTrace();
            return null;
        }

    }

    public boolean canWork() {
        if (listFile.exists() == true) {
            if (indexObject.getBaoneiwjxxList() != null) {
                Optional<BAONEIWJXX> findOne = indexObject.getBaoneiwjxxList()
                        .stream().filter(f -> StringUtils.isEmpty(f.getWENJIANMC()) == false)
                        .filter(f -> f.getWENJIANMC().indexOf("专利请求书") > -1)
                        .findFirst();
                if (findOne.isPresent()) return true;
                else return false;
            } else return false;
        } else return false;
    }
}
