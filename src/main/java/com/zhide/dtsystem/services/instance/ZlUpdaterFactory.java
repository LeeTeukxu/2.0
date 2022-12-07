package com.zhide.dtsystem.services.instance;

import com.zhide.dtsystem.common.CompanyPathUtils;
import com.zhide.dtsystem.common.ZipFileUtils;
import com.zhide.dtsystem.services.define.IZlInfoUpdater;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;

@Component
public class ZlUpdaterFactory {
    String zipFilePath;
    List<IZlInfoUpdater> alls = null;
    @Autowired
    AJInfoUpdater ajInfoUpdater;
    @Autowired
    SQWJInfoUpdater sqwjInfoUpdater;
    @Autowired
    ShenQingXXInfoUpdater shenQingXXInfoUpdater;
    @Autowired
    TZSInfoUpdater tzsInfoUpdater;
    int Num = 0;
    Logger logger = LoggerFactory.getLogger(ZlUpdaterFactory.class);

    public ZlUpdaterFactory() {
    }

    @Transactional(rollbackFor = Exception.class)
    public void execute(String zipPath) throws Exception {
        zipFilePath = zipPath;
        Num = 0;
        alls = Arrays.asList(ajInfoUpdater, sqwjInfoUpdater, shenQingXXInfoUpdater, tzsInfoUpdater);
        File zipFile = new File(zipFilePath);
        if (zipFile.exists()) {
            String targetDir = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString());
            ZipFileUtils.unZip(zipFilePath, targetDir);
            File[] files = new File(targetDir).listFiles();
            Date begin = new Date();

            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String fullPath = file.getPath();
                Optional<IZlInfoUpdater> fupdater = alls.stream().filter(f -> f.accept(fullPath)).findFirst();
                if (fupdater.isPresent()) {
                    Date t1 = new Date();
                    IZlInfoUpdater updater = fupdater.get();
                    String text = updater.read(fullPath);
                    int X = updater.save(text);
                    Num += X;
                    Date t2 = new Date();
                    long aTime = (t2.getTime() - t1.getTime());
                    logger.info(updater.toString() + "更新" + Integer.toString(X) + "条记录耗时:" + Long.toString(aTime));
                }
            }
            try {
                FileUtils.deleteDirectory(new File(targetDir));
            } catch (Exception ax) {
                logger.info("删除" + targetDir + "目录失败，原因:" + ax.getMessage());
            }
            Date end = new Date();
            logger.info("一共更新:" + Integer.toString(Num) + "条数据,耗时:" + Long.toString((end.getTime() - begin.getTime())));
        } else throw new Exception(zipFilePath + "文件存储不正确,无法找到指定的文件。");
    }

    public Integer getExecuteNum() {
        return Num;
    }
}
