package com.zhide.dtsystem.services.instance.cpcPackage.processor;

import com.zhide.dtsystem.common.ListUtils;
import com.zhide.dtsystem.common.PageSizeUtils;
import com.zhide.dtsystem.common.StringUtilTool;
import com.zhide.dtsystem.models.cpcAgent;
import com.zhide.dtsystem.models.cpcApplyMan;
import com.zhide.dtsystem.models.cpcFiles;
import com.zhide.dtsystem.models.cpcInventor;
import com.zhide.dtsystem.services.instance.cpcPackage.ICPCFileProcessor;
import com.zhide.dtsystem.services.instance.cpcPackage.models.A120101Doc;
import com.zhide.dtsystem.services.instance.cpcPackage.models.A120101Xml;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
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
public class A120101 extends ICPCFileProcessor {

    String copyDir;
    String aFilePath;

    @Override
    public String getCode() {
        String code = "120101";
        return code;
    }

    @Override
    public void execute(cpcFiles file) throws Exception {
        //baseCopy(file,targetDir);
        copyDir = targetDir + "\\" + getCode() + "\\";
        aFilePath = targetDir + "\\100001\\100001.pdf";

        String docText = createDoc();
        FileUtils.writeByteArrayToFile(new File(copyDir + getCode() + ".doc"), StringUtils.getBytesUtf8(docText));
        int X=PageSizeUtils.get(copyDir+getCode()+".doc");
        Optional<cpcFiles> findFiles= files.stream().filter(f->f.getCode().equals(getCode())).findFirst();
        if(findFiles.isPresent()){
            cpcFiles file1= findFiles.get();
            file1.setPages(X);
        }
        String xmlText = createXml();
        FileUtils.writeByteArrayToFile(new File(copyDir + getCode() + ".xml"), StringUtils.getBytesUtf8(xmlText));
        docText = createDoc();
        FileUtils.writeByteArrayToFile(new File(copyDir + getCode() + ".doc"), StringUtils.getBytesUtf8(docText));
    }

    private A120101Xml getXmlParameter() throws Exception {
        LocalDate now = LocalDate.now();
        A120101Xml xmlObj = new A120101Xml();
        xmlObj.setFamingmc(main.getFamingmc());
        xmlObj.setAgentCode(main.getAgentCode());
        xmlObj.setAgentName(main.getAgentName());
        xmlObj.setNbbh(main.getNbbh());
        xmlObj.setFirstInventor(main.getFirstInventor());
        xmlObj.setFirstInventorCountry(main.getFirstInventCountry());
        xmlObj.setFirstInventorIDCode(main.getFirstInventIdCode());
        xmlObj.setYear(Integer.toString(now.getYear()));
        xmlObj.setMonth(Integer.toString(now.getMonthValue()));
        xmlObj.setDay(Integer.toString(now.getDayOfMonth()));
        xmlObj.setItemCount(main.getItemCount());
        List<cpcInventor> inventorss = ListUtils.clone(inventros);
        cpcInventor firstInventor = inventorss.stream().filter(f -> f.getFirst()).findFirst().get();
        inventorss.remove(firstInventor);
        List<String> inv = inventorss.stream().map(f -> f.getName()).collect(Collectors.toList());
        if (inv.size() < 2) {
            int a = 2 - inv.size();
            for (int i = 1; i <= a; i++) {
                inv.add(null);
            }
        }
        xmlObj.setInventors(inv);
        List<cpcApplyMan> manss = ListUtils.clone(applyMans);

        int msize = manss.size();
        if (msize < 3) {
            int x = 3 - msize;
            for (int i = 1; i <= x; i++) manss.add(null);
        }
        xmlObj.setApplyMans(manss);

        List<cpcFiles> nFiles = files.stream().filter(f ->
                f.getCode().equals("100007") == false &&
                f.getCode().equals("10000701") == false &&
                f.getCode().equals("110401") == false
        ).collect(Collectors.toList());
        List<cpcFiles> aFiles = files.stream().filter(f ->
                f.getCode().equals("100007") == true || f.getCode().equals("110401") == true).collect(Collectors.toList());
        xmlObj.setFiles(nFiles);
        xmlObj.setAddFiles(aFiles);


        List<cpcAgent> agentss = ListUtils.clone(agents);
        int free = 2 - agentss.size();
        for (int i = 1; i <= free; i++) {
            agentss.add(null);
        }
        xmlObj.setAgents(agentss);
        return xmlObj;
    }

    private A120101Doc getDocParameter() throws Exception {
        LocalDate now = LocalDate.now();
        A120101Doc docObj = new A120101Doc();
        docObj.setFamingmc(main.getFamingmc());
        docObj.setAgentCode(main.getAgentCode());
        docObj.setAgentName(main.getAgentName());
        docObj.setNbbh(main.getNbbh());
        docObj.setFirstInventCountry(main.getFirstInventCountry());
        docObj.setFirstInventIDCode(main.getFirstInventIdCode());
        docObj.setYear(Integer.toString(now.getYear()));
        String MonthValue = Integer.toString(now.getMonthValue());
        docObj.setMonth((MonthValue.length() == 2 ? MonthValue : "0" + MonthValue));
        String DayValue = Integer.toString(now.getDayOfMonth());
        docObj.setDay((DayValue.length() == 2 ? DayValue : "0" + DayValue));
        docObj.setDigistImage(main.getDigistImage());
        docObj.setSameApply((main.getSameApply() == true ? 1 : 0));
        if(main.getItemCount()==null || main.getItemCount()==0){
            docObj.setItemCount(PageSizeUtils.getSectionCount(aFilePath));
        } else {
            docObj.setItemCount(main.getItemCount());
        }

        List<cpcInventor> inventorss = ListUtils.clone(inventros);
        int tSize = inventorss.size();
        int tFree = 3 - tSize;

        for (int i = 0; i < tFree; i++) {
            cpcInventor inv = new cpcInventor();
            inv.setNotOpen(false);
            inv.setName("     ");
            inventorss.add(inv);
        }
        docObj.setInventor1(inventorss.get(0).getName());
        docObj.setInvent1NotOpen(inventorss.get(0).getNotOpen() == true ? 1 : 0);
        docObj.setInventor2(inventorss.get(1).getName());
        docObj.setInvent2NotOpen(inventorss.get(1).getNotOpen() == true ? 1 : 0);
        docObj.setInventor3(inventorss.get(2).getName());
        docObj.setInvent3NotOpen(inventorss.get(2).getNotOpen() == true ? 1 : 0);

        List<cpcApplyMan> manss = ListUtils.clone(applyMans);
        int aSize = manss.size();
        int aFree = 3 - aSize;
        for (int i = 0; i < aFree; i++) {
            cpcApplyMan m = new cpcApplyMan();
            m.setName("     ");
            m.setCountry("     ");
            m.setCity("     ");
            m.setProvince("     ");
            m.setAddress("     ");
            m.setType("     ");
            m.setPostCode("     ");
            m.setPhone("     ");
            m.setIdCode("     ");
            m.setRequestFj(false);
            manss.add(m);
        }
        docObj.setApply1(manss.get(0));
        docObj.setApply2(manss.get(1));
        docObj.setApply3(manss.get(2));


        List<cpcAgent> agentss = ListUtils.clone(agents);
        int free = 2 - agentss.size();
        for (int i = 1; i <= free; i++) {
            cpcAgent cp = new cpcAgent();
            cp.setCode("     ");
            cp.setName("     ");
            cp.setPhone("     ");
            agentss.add(cp);
        }
        docObj.setAgent1(agentss.get(0));
        docObj.setAgent2(agentss.get(1));


        List<cpcFiles> nFiles = files.stream().filter(f ->
                f.getCode().equals("100007") == false &&
                f.getCode().equals("10000701") == false &&
                f.getCode().equals("110401") == false
        ).collect(Collectors.toList());
        List<cpcFiles> aFiles =
                files.stream().filter(f -> f.getCode().equals("100007") == true || f.getCode().equals("110401")).collect(Collectors.toList());
        docObj.setFiles(nFiles);
        docObj.setAddFiles(aFiles);


        List<cpcInventor> bigInventors = new ArrayList<>();
        if (inventorss.size() > 3) bigInventors =
                inventros.stream().skip(3).limit(inventros.size() - 3).collect(Collectors.toList());
        List<cpcApplyMan> bigApplys = new ArrayList<>();
        if (applyMans.size() > 3)
            bigApplys = applyMans.stream().skip(3).limit(applyMans.size() - 3).collect(Collectors.toList());
        docObj.setBigApplys(bigApplys);
        docObj.setBigInventors(bigInventors);
        return docObj;


    }

    private String createXml() throws Exception {
        String xmlTemplate = getTemplateByCode(getCode() + "Xml");
        A120101Xml obj = getXmlParameter();
        String xmlText = StringUtilTool.createByTemplate(xmlTemplate, obj);
        return xmlText;
    }

    private String createDoc() throws Exception {
        String xmlTemplate = getTemplateByCode(getCode() + "Doc");
        A120101Doc obj = getDocParameter();
        String xmlText = StringUtilTool.createByTemplate(xmlTemplate, obj);
        return xmlText;
    }
}
