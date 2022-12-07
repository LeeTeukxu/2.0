package com.zhide.dtsystem.services.instance.cpcPackage.processor;

import com.zhide.dtsystem.common.ImageUtils;
import com.zhide.dtsystem.common.PageSizeUtils;
import com.zhide.dtsystem.common.StringUtilTool;
import com.zhide.dtsystem.models.cpcFiles;
import com.zhide.dtsystem.models.tbAttachment;
import com.zhide.dtsystem.repositorys.tbAttachmentRepository;
import com.zhide.dtsystem.services.instance.cpcPackage.ICPCFileProcessor;
import com.zhide.dtsystem.services.instance.cpcPackage.models.A100007Doc;
import com.zhide.dtsystem.services.instance.cpcPackage.models.A100007Xml;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ClassName: A100001
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年08月19日 11:48
 **/
@Component
public class A100007 extends ICPCFileProcessor {

    @Autowired
    tbAttachmentRepository attRep;
    String copyDir="";
    @Override
    public String getCode() {
        String code="100007";
        return code;
    }

    @Override
    public void execute(cpcFiles file) throws Exception {
        //baseCopy(file,targetDir);
        copyDir=targetDir+"\\"+getCode()+"\\";

        String xmlText=createXml();
        FileUtils.write(new File(copyDir+getCode()+".xml"),xmlText,"utf-8");

        String docText=createDoc();
        FileUtils.write(new File(copyDir+getCode()+".doc"),docText,"utf-8");
        int X=PageSizeUtils.get(copyDir+getCode()+".doc");
        Optional<cpcFiles> findFiles= files.stream().filter(f->f.getCode().equals(getCode())).findFirst();
        if(findFiles.isPresent()){
            cpcFiles file1= findFiles.get();
            file1.setPages(X);
        }
    }
    private String createXml() throws  Exception{
        String Text=getTemplateByCode("100007Xml");

        A100007Xml xmlObj=new A100007Xml();
        xmlObj.setFamingmc(main.getFamingmc());
        xmlObj.setAgentName(main.getAgentName());
        xmlObj.setAgentCode(main.getAgentCode());
        List<String> ags=agents.stream().map(f->f.getName()).collect(Collectors.toList());
        xmlObj.setAgents(ags);
        List<String> cs=applyMans.stream().map(f->f.getName()).collect(Collectors.toList());
        xmlObj.setClients(cs);

        cpcFiles file0701=files.stream().filter(f->f.getCode().equals("10000701")).findFirst().get();
        tbAttachment findAtt=attRep.findAllByGuid(file0701.getAttId()).get();
        List<String> images= Arrays.asList(findAtt.getName());
        xmlObj.setImages(images);
        return StringUtilTool.createByTemplate(Text,xmlObj);
    }
    private String createDoc() throws Exception{
        String Text=getTemplateByCode("100007Doc");
        A100007Doc docObj=new A100007Doc();
        LocalDate date=LocalDate.now();
        docObj.setAgentCode(main.getAgentCode());
        docObj.setAgentName(main.getAgentName());
        docObj.setFamingmc(main.getFamingmc());
        docObj.setYear(Integer.toString(date.getYear()));
        docObj.setMonth(Integer.toString(date.getMonthValue()));
        docObj.setDay(Integer.toString(date.getDayOfMonth()));

        cpcFiles file0701=files.stream().filter(f->f.getCode().equals("10000701")).findFirst().get();
        tbAttachment findAtt=attRep.findAllByGuid(file0701.getAttId()).get();
        List<String> images= Arrays.asList(findAtt.getName());

        String imagePath=copyDir+images.get(0);
        docObj.setImageData(ImageUtils.toBase64(imagePath));

        List<String> ags=agents.stream().map(f->f.getName()).collect(Collectors.toList());
        if(ags.size()==1){
            docObj.setAgent1(ags.get(0));
            docObj.setAgent2("  ");
        }
        else if(ags.size()==2){
            docObj.setAgent1(ags.get(0));
            docObj.setAgent2(ags.get(1));
        }
        List<String> cs=applyMans.stream().map(f->f.getName()).collect(Collectors.toList());
        docObj.setClients(cs);
        return StringUtilTool.createByTemplate(Text,docObj);
    }
}
