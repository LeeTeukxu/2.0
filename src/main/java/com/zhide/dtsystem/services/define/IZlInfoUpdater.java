package com.zhide.dtsystem.services.define;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public interface IZlInfoUpdater {
    boolean accept(String filePath);

    Integer totalNumber = 0;

    default String read(String filePath) throws IOException {
        File file = new File(filePath);
        return FileUtils.readFileToString(file, "utf-8");
    }

    default Logger getLogger() {
        return LoggerFactory.getLogger(IZlInfoUpdater.class);
    }

    int save(String fileContent) throws Exception;
}
