package com.zhide.dtsystem.services.instance.cpcPackage;

import com.zhide.dtsystem.common.DirectoryUtils;
import com.zhide.dtsystem.common.FTPUtil;
import com.zhide.dtsystem.common.ListUtils;
import com.zhide.dtsystem.common.ZipFileUtils;
import com.zhide.dtsystem.mapper.CPCPackageMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import org.apache.commons.io.FileUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: cpcPackage
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年08月27日 13:49
 **/
@Component
public class cpcPackage {
    @Autowired
    cpcInventorRepository invRep;
    @Autowired
    cpcFilesRepository cpcFileRep;
    @Autowired
    cpcAgentRepository agentRep;
    @Autowired
    cpcApplyManRepository applyRep;
    @Autowired
    cpcPackageMainRepository mainRep;
    @Autowired
    CPCPackageMapper cpcMapper;
    @Autowired
    CPCFileProcessorFactory processorFactory;
    @Autowired
    tbAttachmentRepository attRep;

    List<cpcFiles> files;
    List<cpcAgent> agents;
    List<cpcApplyMan> mans;
    List<cpcInventor> inventros;
    cpcPackageMain main;
    String kDir="D:\\CPCPackage\\";
    String bbaseDir = "";
    public String getRootPath(){
        return kDir;
    }
    private String getTypeByCode(String Code){
        Map<String,String> Types=new HashMap<>();
        Types.put("100001","权利要求书");
        Types.put("100002","说明书");
        Types.put("100003","说明书附图");
        Types.put("100004","说明书摘要");
        Types.put("100005","摘要附图");
        Types.put("100007","专利代理委托书");
        Types.put("10000701","专利代理委托书扫描件");
        Types.put("110101","发明专利请求书");
        Types.put("110401","实质审查请求书");
        Types.put("120101","实用新型专利请求书");
        Types.put("130001","外观设计图片或照片");
        Types.put("130002","外观设计简要说明");
        Types.put("130101","外观设计专利请求书");
        return Types.get(Code);
    }
    private boolean IsFiveFile(String Name){
        List<String> types=new ArrayList<>();
        types.add("权利要求书");
        types.add("说明书");
        types.add("说明书附图");
        types.add("说明书摘要");
        types.add("摘要附图");
        return types.contains(Name);
    }
    private  List<cpcFiles> getFilesByType(cpcPackageMain main, List<cpcFiles> files) {
        List<String> FileCodes = new ArrayList<>();
        if (main.getShenqinglx() == 0) {
            FileCodes= Lists.list("100001", "100002", "100003", "100004", "100007", "110401","110101", "10000701", "list");
            if(main.getAddSZSC()==false)FileCodes.remove("110401");
        } else if(main.getShenqinglx()==1) {
            FileCodes= Lists.list("100001", "100002", "100003", "100004", "100005", "100007", "120101", "10000701", "list");
        }
        if (agents.size() == 0) {
            FileCodes.remove("100007");
            FileCodes.remove("10000701");
        }
        List<cpcFiles> newFiles = new ArrayList<>();
        for (String Code : FileCodes) {
            Optional<cpcFiles> findFiles = files.stream().filter(f -> f.getCode().equals(Code)).findFirst();
            if (findFiles.isPresent()) {
                newFiles.add(findFiles.get());
            } else {
                Integer GNum=0;
                if(Code.equals("list")==false)GNum=Integer.parseInt(Code); else GNum=100009;
                cpcFiles newFile = new cpcFiles();
                newFile.setMainId(main.getPid());
                if(GNum>=100005) {
                    newFile.setExtName(".DOC");
                } else newFile.setExtName(".PDF");
                newFile.setType(getTypeByCode(Code));
                newFile.setCode(Code);
                newFile.setPages(0);
                newFiles.add(newFile);
            }
        }
        return newFiles;
    }
    public String  createOne(String MainID) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        bbaseDir=kDir+Info.getCompanyId() + "\\";
        files = cpcFileRep.findAllByMainIdOrderByCode(MainID);
        for(int i=0;i<files.size();i++){
            cpcFiles file=files.get(i);
            if(IsFiveFile(file.getType())) {
                file.setPages(0);//五书文件pages置为0
            }
        }
        agents = agentRep.findAllByMainId(MainID);
        mans = applyRep.findAllByMainId(MainID);
        inventros = invRep.findAllByMainId(MainID);
        main = mainRep.findFirstByPid(MainID).get();

        FTPUtil util = new FTPUtil();
        if (util.connect()) {
            String baseDir = bbaseDir + MainID;
            DirectoryUtils.deleteAll(baseDir);
            DirectoryUtils.createNotExists(baseDir);
            List<cpcFiles> xFiles = getFilesByType(main, files);
            xFiles.sort(new Comparator<cpcFiles>() {
                @Override
                public int compare(cpcFiles o1, cpcFiles o2) {
                    return -(o1.getCode().length() - o2.getCode().length());
                }
            });
            List<String> addCodes=Arrays.asList("100007","110401","110101","120101");
            List<cpcFiles> eachFiles = ListUtils.clone(xFiles);
            for (cpcFiles file : eachFiles) {
                String code = file.getCode();
                String attId = file.getAttId();
                Optional<tbAttachment> findFiles = attRep.findAllByGuid(attId);
                if (findFiles.isPresent()) {
                    tbAttachment findFile = findFiles.get();
                    String fileFtpPath = findFile.getPath();
                    if (StringUtils.isEmpty(fileFtpPath) == false) {
                        util.download(fileFtpPath, baseDir + "\\" + file.getCode() + file.getExtName());
                    }
                }
                List<cpcFiles> KFiles=ListUtils.clone(eachFiles);
                List<cpcFiles> delFiles= KFiles.stream().filter(f-> f.getCode().equals("list")).collect(Collectors.toList());
                KFiles.removeAll(delFiles);
                ICPCFileProcessor processor = processorFactory.getInstance(code);
                if (processor != null) {
                    processor.files = KFiles;
                    processor.agents = agents;
                    processor.applyMans = mans;
                    processor.inventros = inventros;
                    processor.main = main;
                    processor.targetDir = baseDir;
                    processor.defaultFiles=ListUtils.clone(eachFiles);
                    processor.execute(file);
                    List<cpcFiles> vFiles=processor.files;
                    if(addCodes.contains(code)){
                        for(String cc:addCodes){
                            Optional<cpcFiles> addFiles=vFiles.stream().filter(f->f.getCode().equals(cc)).findFirst();
                            if(addFiles.isPresent()){
                                cpcFiles addFile= addFiles.get();
                                int page=addFile.getPages();
                                cpcFiles xff=eachFiles.stream().filter(f->f.getCode().equals(cc)).findFirst().get();
                                xff.setPages(page);
                            }
                        }
                    }
                }
            }
            String packageFilePath = bbaseDir + "\\" + main.getFamingmc() + "案卷包.zip";
            File createFile = new File(packageFilePath);
            if (createFile.exists() == true) FileUtils.forceDelete(createFile);
            ZipFileUtils.zip(baseDir, packageFilePath, true);
            main.setPackageFilePath(packageFilePath);
            main.setPackageDirPath(baseDir);
            main.setPackageCreateTime(new Date());
            main.setPackageCreateMan(Info.getUserIdValue());
            mainRep.save(main);
            return packageFilePath;
        }
        return "";
    }
}
