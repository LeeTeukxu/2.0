package com.zhide.dtsystem.common;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;

public class StringUtilTool {
    public static String getValue(Object val) {
        if (val == null) return "";
        if (StringUtils.isEmpty(val) == true) return "";
        try {
            return val.toString();
        } catch (Exception ax) {
            return "";
        }
    }
    public static String readAll(String filePath) throws Exception{
        File f=new File(filePath);
        FileInputStream inputStream=new FileInputStream(f);
        byte[] BB=new byte[inputStream.available()];
        inputStream.read(BB,0,inputStream.available());
        inputStream.close();
        return new String(BB, Charset.forName("utf-8"));
    }
    public static  String createByTemplate(String templateText,Object obj)  throws  Exception{
        Configuration configuration = new Configuration();
        StringWriter writer = new StringWriter();
        configuration.setDefaultEncoding("utf-8");
        Template tt = new Template("Template", new StringReader(templateText), configuration);
        tt.process(obj, writer);
        return writer.toString();
    }
}
