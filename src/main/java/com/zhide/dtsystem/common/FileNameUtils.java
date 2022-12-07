package com.zhide.dtsystem.common;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: FileNameUtils
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年03月16日 14:09
 **/
public class FileNameUtils {
    /**
     * create by: mmzs
     * description: TODO
     * create time:
     * 去掉文件名称里面的非法字符。
     * @return
     */
    public static  String clear(String FileName){
        List<String> EChars= Arrays.asList("/","\\",":","<",">","*","|","\"");
        for(int i=0;i<EChars.size();i++){
            String E=EChars.get(i);
            FileName=FileName.replace(E,"");
        }
        return FileName;
    }
    public static Boolean isZipFile(String FileName){
        String X=clear(FileName);
        String Y=FilenameUtils.getExtension(FileName).toLowerCase();
        List<String> exts=Arrays.asList("zip","rar","7z");
        return exts.contains(Y);
    }
    public static boolean isDirectory(String path){
        File f=new File(path);
        if(f.exists()){
            return f.isDirectory();
        } else {
            String extName = FilenameUtils.getExtension(path);
            return StringUtils.isEmpty(extName) == true;
        }
    }
}
