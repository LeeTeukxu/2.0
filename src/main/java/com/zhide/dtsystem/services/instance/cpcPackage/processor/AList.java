package com.zhide.dtsystem.services.instance.cpcPackage.processor;

import com.zhide.dtsystem.common.StringUtilTool;
import com.zhide.dtsystem.models.cpcFiles;
import com.zhide.dtsystem.services.instance.cpcPackage.ICPCFileProcessor;
import com.zhide.dtsystem.services.instance.cpcPackage.models.AListXml;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: AList
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年08月27日 16:48
 **/
@Component
public class AList extends ICPCFileProcessor {
    @Override
    public String getCode() {
        return "list";
    }
    @Override
    public void execute(cpcFiles file) throws Exception {
       String  copyDir=targetDir+"\\";

        String xmlText=createXml();
        FileUtils.write(new File(copyDir+getCode()+".xml"),xmlText,"utf-8");
    }
    public AListXml getXmlParameter() throws Exception{
        AListXml obj=new AListXml();
        obj.setPid(main.getPid());
        obj.setShenqinglx(main.getShenqinglx());
        obj.setFamingmc(main.getFamingmc());
        obj.setNbbh(main.getNbbh());
        List<cpcFiles> delFiles= defaultFiles.stream().filter(f->f.getCode().equals("10000701") ||
                f.getCode().equals("list")).collect(Collectors.toList());
        defaultFiles.removeAll(delFiles);
        List<cpcFiles> addFiles=defaultFiles.stream().filter(f->f.getCode().equals("100007") ||
                f.getCode().equals("110401")).collect(Collectors.toList());
        obj.setAddFiles(addFiles);
        defaultFiles.removeAll(addFiles);
        List<cpcFiles>  filess= defaultFiles;
        obj.setFiles(filess);
        return obj;

    }
    private String createXml() throws  Exception{
        String xmlTemplate=getTemplateByCode(getCode()+"Xml");
        AListXml obj=getXmlParameter();
        String xmlText= StringUtilTool.createByTemplate(xmlTemplate,obj);
        return xmlText;
    }
}
