package com.zhide.dtsystem.viewModel;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: testAcceptFileResult
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年10月14日 16:21
 **/
public class testAcceptFileResult implements Serializable {
    String targetDir;
    List<String> wordFiles;

    public String getTargetDir() {
        return targetDir;
    }

    public void setTargetDir(String targetDir) {
        this.targetDir = targetDir;
    }

    public List<String> getWordFiles() {
        return wordFiles;
    }

    public void setWordFiles(List<String> wordFiles) {
        this.wordFiles = wordFiles;
    }
}
