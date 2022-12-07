package com.zhide.dtsystem.services.instance.cpcPackage.processor;

import com.zhide.dtsystem.common.PageSizeUtils;
import com.zhide.dtsystem.common.StringUtilTool;
import com.zhide.dtsystem.models.cpcFiles;
import com.zhide.dtsystem.services.instance.cpcPackage.ICPCFileProcessor;
import com.zhide.dtsystem.services.instance.cpcPackage.models.A110401Doc;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;
import java.util.Optional;

/**
 * @ClassName: A110401
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年09月02日 14:41
 **/
@Component
public class A110401 extends ICPCFileProcessor {
    String copyDir;
    @Override
    public void execute(cpcFiles file) throws Exception {

        copyDir=targetDir+"\\"+getCode()+"\\";
        String MainID=file.getMainId();

        String docText=createDoc();
        FileUtils.writeByteArrayToFile(new File(copyDir+getCode()+".doc"),StringUtils.getBytesUtf8(docText));
        int X= PageSizeUtils.get(copyDir+getCode()+".doc");
        Optional<cpcFiles> findFiles= files.stream().filter(f->f.getCode().equals(getCode())).findFirst();
        if(findFiles.isPresent()){
            cpcFiles file1= findFiles.get();
            file1.setPages(X);
        }
        docText=createDoc();
        FileUtils.writeByteArrayToFile(new File(copyDir+getCode()+".doc"),StringUtils.getBytesUtf8(docText));
        String xmlText=createXml();
        FileUtils.writeByteArrayToFile(new File(copyDir+getCode()+".xml"), StringUtils.getBytesUtf8(xmlText));
    }

    @Override
    public String getCode() {
        String code="110401";
        return code;
    }

    private A110401Doc getDocParameter() throws Exception{
        LocalDate now=LocalDate.now();
        A110401Doc docObj=new A110401Doc();
        docObj.setAbandonChangeRights(main.getAbandonChangeRights());
        docObj.setFamingmc(main.getFamingmc());
        docObj.setFirstApplyMan(applyMans.get(0).getName());
        docObj.setAgentCode(main.getAgentCode());
        docObj.setAgentName(main.getAgentName());
        docObj.setYear(Integer.toString(now.getYear()));
        String MonthValue=Integer.toString(now.getMonthValue());
        docObj.setMonth((MonthValue.length()==2?MonthValue:"0"+MonthValue));
        String DayValue=Integer.toString(now.getDayOfMonth());
        docObj.setDay((DayValue.length()==2?DayValue:"0"+DayValue));
        return docObj;


    }
    private String createXml() throws  Exception{
        String xmlTemplate=getTemplateByCode(getCode()+"Xml");
        A110401Doc obj=getDocParameter();
        String xmlText= StringUtilTool.createByTemplate(xmlTemplate,obj);
        return xmlText;
    }
    private String createDoc() throws Exception{
        String xmlTemplate=getTemplateByCode(getCode()+"Doc");
        A110401Doc obj=getDocParameter();
        String xmlText= StringUtilTool.createByTemplate(xmlTemplate,obj);
        return xmlText;
    }
}
