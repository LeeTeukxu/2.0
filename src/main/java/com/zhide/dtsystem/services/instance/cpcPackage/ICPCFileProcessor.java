package com.zhide.dtsystem.services.instance.cpcPackage;

import com.zhide.dtsystem.common.DirectoryUtils;
import com.zhide.dtsystem.common.GlobalContext;
import com.zhide.dtsystem.common.StringUtilTool;
import com.zhide.dtsystem.models.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ICPCFileProcessor {

    public List<cpcFiles> defaultFiles;
    public  List<cpcFiles> files;
    public  List<cpcAgent> agents;
    public  List<cpcApplyMan> applyMans;
    public  List<cpcInventor> inventros;
    public  cpcPackageMain main;
    public String targetDir;


    public String getTypeText(String Type) {
        Map<String, String> Keys = new HashMap<>();
        Keys.put("1", "大专院校");
        Keys.put("2", "科研单位");
        Keys.put("3", "工矿企业");
        Keys.put("4", "事业单位");
        Keys.put("5", "个人");
        if (Keys.containsKey(Type)) return Keys.get(Type);
        else return "";
    }

    public void baseCopy(cpcFiles file, String targetDir) throws Exception {
        String code = file.getCode();
        String extName = file.getExtName();
        String findFile = code + extName;
        DirectoryUtils.createNotExists(targetDir + "\\"+code);
        String sourceFile = Paths.get(targetDir, findFile).toString();
        String targetFile = Paths.get(targetDir, code, findFile).toString();
        File sFile=new File(sourceFile);
        File tFile=new File(targetFile);
        FileUtils.copyFile(sFile, tFile);
        sFile.delete();
    }

    public String getTemplateByCode(String code) throws Exception {
        String BaseDir = GlobalContext.getStaticUrl() + "\\template\\package\\";
        return StringUtilTool.readAll(BaseDir + code + ".ftl");
    }

    public boolean accept(String code) {
        return getCode().equals(code);
    }

    ///每种文件类型实现，在运行之前。将对应的文件要复制到Code名称的目录中来。
    public void execute(cpcFiles file) throws Exception {
        baseCopy(file, targetDir);
    }

    public abstract String getCode();
}
