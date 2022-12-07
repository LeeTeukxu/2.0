package com.zhide.dtsystem.services.implement;

import com.aspose.words.*;
import com.google.common.collect.Lists;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.zhide.dtsystem.common.*;
import com.zhide.dtsystem.mapper.FeeItemMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.ICPCFileService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: CPCFileServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年09月03日 17:08
 **/
@Service
public class CPCFileServiceImpl implements ICPCFileService {
    Logger logger = LoggerFactory.getLogger(CPCFileServiceImpl.class);
    String bDir = "D:\\CPCPackage\\";
    String baseDir = "";
    List<String> FiveName = Lists.newArrayList("100004", "100005", "100001", "100002", "100003");

    Map<String, String> FiveNames = new HashMap<String, String>() {
        {
            put("100004", "说明书摘要");
            put("100005", "摘要附图");
            put("100001", "权利要求书");
            put("100002", "说明书");
            put("100003", "说明书附图");
        }
    };
    @Autowired
    tbAttachmentRepository attRep;
    @Autowired
    cpcFilesRepository cpcFileRep;
    @Autowired
    cpcPackageMainRepository mainRep;
    @Autowired
    cpcInventorRepository cpcInvRep;
    @Autowired
    cpcApplyManRepository cpcApplyRep;
    @Autowired
    cpcAgentRepository cpcAgentRep;
    @Autowired
    FeeItemMapper feeItemMapper;
    private UploadUtils getUtils() throws Exception {
        LoginUserInfo info = CompanyContext.get();
        if (info == null) throw new Exception("登录失败，请重新登录!");
        String companyId = info.getCompanyId();
        return UploadUtils.getInstance(companyId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public tbAttachment uploadFiveFile(String MainID, File docFile,String fileName) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        String uID = UUID.randomUUID().toString();
        baseDir = bDir + Info.getCompanyId() + "\\" + uID;
        splitFiveFiles(MainID, docFile.getPath(), baseDir);
        File baseDirFile = new File(baseDir);
        List<File> pdfFiles =
                FileUtils.listFiles(baseDirFile, new String[]{"PDF"}, true).stream().collect(Collectors.toList());
        UploadUtils uploadUtils = getUtils();
        List<cpcFiles> cpcFiles =
                cpcFileRep.findAllByMainIdOrderByCode(MainID).stream().filter(f -> FiveName.contains(f.getCode())).collect(Collectors.toList());
        List<String> attIds = cpcFiles.stream().map(f -> f.getAttId()).collect(Collectors.toList());
        attRep.deleteAllByGuidIn(attIds);
        cpcFileRep.deleteAll(cpcFiles);
        for (File pdfFile : pdfFiles) {
            uploadFileResult result = uploadSingleFile(uploadUtils, pdfFile);
            String attId = result.getAttId();
            String filePath = result.getFullPath();
            String extName = "." + FilenameUtils.getExtension(filePath).toUpperCase();
            String xCode = FilenameUtils.getBaseName(pdfFile.getPath());
            tbAttachment tb = new tbAttachment();
            tb.setName(pdfFile.getName());
            tb.setGuid(attId);
            tb.setType(1);
            tb.setPath(filePath);
            tb.setSize(Math.toIntExact(pdfFile.length()));
            tb.setUploadMan(Info.getUserIdValue());
            tb.setUploadManName(Info.getUserName());
            tb.setUploadTime(new Date());
            attRep.save(tb);

            cpcFiles cpcFile = new cpcFiles();
            cpcFile.setPages(PageSizeUtils.get(pdfFile.getPath()));
            cpcFile.setExtName(extName);
            cpcFile.setType(FiveNames.get(xCode));
            cpcFile.setCode(xCode);
            cpcFile.setMainId(MainID);
            cpcFile.setAttId(attId);
            cpcFile.setSubId(UUID.randomUUID().toString());
            cpcFileRep.save(cpcFile);
        }
        try {
            DirectoryUtils.deleteAll(baseDir);
        }catch(Exception ax){

        }
        tbAttachment res=new tbAttachment();
        res.setGuid(MainID);
        res.setName(fileName);
        res.setPath(docFile.getPath());
        res.setUploadTime(new Date());
        res.setUploadMan(Info.getUserIdValue());
        res.setUploadManName(Info.getUserName());
        return  res;
    }

    @Override
    public List<String> ViewCPCFile(String AttID) throws Exception {
        List<String> ids = new ArrayList<>();
        Optional<cpcFiles> findFiles = cpcFileRep.findFirstByAttId(AttID);
        if (findFiles.isPresent()) {
            cpcFiles file = findFiles.get();
            String fileName = file.getCode() + file.getExtName();
            String uId= UUID.randomUUID().toString();
            String tempDir=Paths.get(CompanyPathUtils.getImages(),uId).toString();
            String tempFileName = Paths.get(tempDir, fileName).toString();
            Optional<tbAttachment> findAtts = attRep.findAllByGuid(AttID);
            if (findAtts.isPresent()) {
                DirectoryUtils.createNotExists(tempDir);
                tbAttachment att = findAtts.get();
                String filePath = att.getPath();
                FTPUtil Ftp = new FTPUtil();
                if (Ftp.connect() == true) {
                    Ftp.download(filePath, tempFileName);
                    File tempFile = new File(tempFileName);
                    if (tempFile.exists()) {
                        List<String>imagePaths=null;
                        if(file.getExtName().equals(".PDF")){
                            imagePaths=ImageUtils.pdftoJpg(tempFileName,tempDir);
                        }
                        else if(file.getExtName().equals(".JPG") || file.getExtName().equals(".PNG")){
                            imagePaths=Arrays.asList(tempFileName);
                        }

                        for (int i = 0; i < imagePaths.size(); i++) {
                            File fx = new File(imagePaths.get(i));
                            String fxName = fx.getName();
                            ids.add("/images/" + uId + "/" + fxName);
                        }
                    } else  throw new Exception("指定的文件在服务器上不存在,下载失败!");
                } else throw new Exception("连接Ftp服务器出错!");
            } else throw new Exception("指定的文件ID:"+AttID+"在服务器上不存在!");
        } else  throw new Exception("指定的文件ID:"+AttID+"在服务器上不存在!");
        return ids;
    }

    private uploadFileResult uploadSingleFile(UploadUtils up, File file) throws Exception {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            String uId = UUID.randomUUID().toString();
            String extName = "." + FilenameUtils.getExtension(file.getPath()).toUpperCase();
            String saveName = uId + extName;
            uploadFileResult result = up.uploadAttachment(saveName, inputStream);
            if (result.isSuccess()) {
                result.setAttId(uId);
                return result;
            } else throw new Exception(result.getMessage());
        } catch (Exception ax) {
            logger.info(ax.getMessage());
            throw ax;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    private void splitFiveFiles(String MainID, String FilePath, String SaveDir) throws Exception {
        Document document = new Document(FilePath);
        String docName = "";
        int lx=0;
        List<String> Names=Lists.newArrayList("说明书摘要","摘要附图","权利要求书","说明书","说明书附图");
        Optional<cpcPackageMain> mains = mainRep.findFirstByPid(MainID);
        if (mains.isPresent()) {
            lx = mains.get().getShenqinglx();
        }
        SectionCollection sections=document.getSections();
        for(int i=0;i<sections.getCount();i++){
            Section section=sections.get(i);
            String Title=section.getHeadersFooters()
                    .getByHeaderFooterType(1)
                    .getFirstParagraph()
                    .getChildNodes(21, false)
                    .get(0)
                    .getText();
          if(Names.contains(Title)) Names.remove(Title);
        }

        if(Names.size()>0){
            throw new Exception("当前的五书文件【"+ StringUtils.join(Names,",")+"】缺失，请确认文件格式正确无误后再进行上传!");
        }
        List<Map<String,String>> files=new ArrayList<>();
        for (int i = 0; i < sections.getCount(); i++) {
            if(lx==0){
                String code=FiveName.get(i);
                if(code.equals("100005")) continue;
            }
            Section section =(Section) sections.get(i).deepClone(true);
            section.ensureMinimum();
            Document newDoc = new Document();
            newDoc.getSections().clear();
            Section newSection = (Section) newDoc.importNode(section, true);
            newSection.getBody().getFirstParagraph().getParagraphFormat().getStyle().getFont().setName("宋体");
            newSection.getPageSetup().setLeftMargin(section.getPageSetup().getLeftMargin());
            newSection.getPageSetup().setRightMargin(section.getPageSetup().getRightMargin());
            newSection.getPageSetup().setTopMargin(section.getPageSetup().getTopMargin());
            newSection.getPageSetup().setBottomMargin(section.getPageSetup().getBottomMargin());
            newDoc.getSections().add(newSection);
            removeBlank(newDoc);
            docName = FiveName.get(i);
            String savePath = SaveDir + "/" + docName + ".DOC";
            newDoc.save(savePath);
            String pdfPath = SaveDir + "/" + docName + ".PDF";

            Map<String,String> fs=new HashMap<>();
            fs.put("doc",savePath);
            fs.put("pdf",pdfPath);
            files.add(fs);

            //wordToPDF(savePath, pdfPath);
            //FileUtils.deleteQuietly(new File(savePath));
        }
        if(files.size()>0){
            wordToPDF1(files);
        }
    }
    public List<File>getFiveFiles(int lx,String filePath,String saveDir) throws Exception{
        Document document = new Document(filePath);
        String docName = "";
        SectionCollection sections=document.getSections();
        List<String> Names=Lists.newArrayList("说明书摘要","摘要附图","权利要求书","说明书","说明书附图");
        for(int i=0;i<sections.getCount();i++){
            Section section=sections.get(i);
            String Title=section.getHeadersFooters()
                    .getByHeaderFooterType(1)
                    .getFirstParagraph()
                    .getChildNodes(21, false)
                    .get(0)
                    .getText();
            if(Names.contains(Title)) Names.remove(Title);
        }

        if(Names.size()>0){
            throw new Exception("当前的五书文件【"+ StringUtils.join(Names,",")+"】缺失，请确认文件格式正确无误后再进行上传!");
        }
        List<Map<String,String>> files=new ArrayList<>();
        for (int i = 0; i < document.getSections().getCount(); i++) {
            if(lx==0){
                String code=FiveName.get(i);
                if(code.equals("100005")) continue;
            }

            Section section = document.getSections().get(i).deepClone();
            Document newDoc = new Document();
            newDoc.getSections().clear();
            Section newSection = (Section) newDoc.importNode(section, true);
            newSection.getBody().getFirstParagraph().getParagraphFormat().getStyle().getFont().setName("宋体");
            newSection.getPageSetup().setLeftMargin(section.getPageSetup().getLeftMargin());
            newSection.getPageSetup().setRightMargin(section.getPageSetup().getRightMargin());
            newDoc.getSections().add(newSection);
            docName = FiveName.get(i);
            docName=FiveNames.get(docName);
            String savePath = saveDir + "/" + docName + ".DOC";
            newDoc.save(savePath);
            String pdfPath = saveDir + "/" + docName + ".PDF";
            //wordToPDF(savePath, pdfPath);
            //FileUtils.deleteQuietly(new File(savePath));
            Map<String,String> fs=new HashMap<>();
            fs.put("doc",savePath);
            fs.put("pdf",pdfPath);
            files.add(fs);
        }
        if(files.size()>0){
            wordToPDF1(files);
        }
        return FileUtils.listFiles(new File(saveDir),new String[]{"PDF"},true).stream().collect(Collectors.toList());
    }
    public void wordToPDF(String inputFile, String pdfFile) throws Exception {
        ActiveXComponent app = null;
        System.out.println("开始转换:"+inputFile);
        long start = System.currentTimeMillis();
        ComThread.InitSTA();
        try {
            app = new ActiveXComponent("Word.Application");
            Dispatch documents = app.getProperty("Documents").toDispatch();
            Dispatch document =
                    Dispatch.call(documents, "Open", inputFile,
                    false, //是否转换
                    true,
                    false,
                    "",
                    "",
                    true,
                    "",
                    "",
                    0,
                    65001,
                    false,
                    true).toDispatch();

            File target = new File(pdfFile);
            if (target.exists()) {
                target.delete();
            }
            Dispatch.call(document, "SaveAs", pdfFile, 17);
            Dispatch.call(document, "Close", false);
            System.out.println("转换为: " + pdfFile);
            long end = System.currentTimeMillis();
            System.out.println("转换成功，用时：" + (end - start) + "ms");
        } catch (Exception e) {
            System.out.println("转换word文件:"+inputFile+"失败:" + e.getMessage());
        } finally {
            try {
                app.invoke("Quit", 0);
            }catch(Exception dx){
                logger.info("执行word的quit命令失败，错误:"+dx.getMessage());
            }
            try {
                ComThread.Release();
            }
            catch(Exception mx){
                logger.info("扫行 ComThread.Release()失败:"+mx.getMessage());
            }
        }
    }
    public void wordToPDF(List<Map<String,String>> files) throws Exception{
        ActiveXComponent app = null;
        ComThread.InitMTA();
        String inputFile="";
        try {
            app = new ActiveXComponent("Word.Application");
            for(Map<String,String> file:files){
                Dispatch documents = app.getProperty("Documents").toDispatch();
                inputFile=file.get("doc").toString();
                String pdfFile=file.get("pdf").toString();
                System.out.println("开始转换:"+inputFile);
                long start = System.currentTimeMillis();
                Dispatch document = Dispatch.call(documents, "Open", inputFile,
                                false, //是否转换
                                true,
                                false,
                                "",
                                "",
                                true,
                                "",
                                "",
                                0,
                                65001,
                                false,
                                true).toDispatch();
                File target = new File(pdfFile);
                if (target.exists()) {
                    target.delete();
                }
                Dispatch.call(document, "SaveAs", pdfFile, 17);
                Dispatch.call(document, "Close", false);
                long end = System.currentTimeMillis();
                System.out.println("已成功转换为:" + pdfFile);
                System.out.println("累计用时：" + (end - start) + "ms");
                FileUtils.deleteQuietly(new File(inputFile));
                document.safeRelease();
                documents.safeRelease();
                Thread.sleep(100);
            }
        } catch (Exception e) {
            logger.info("转换word文件:"+inputFile+"失败:" + e.getMessage());
        } finally {
            try {
                app.invoke("Quit", 0);
            }catch(Exception dx){
                logger.info("执行word的quit命令失败，错误:"+dx.getMessage());
            }
            try {
                ComThread.Release();
            }
            catch(Exception mx){
                logger.info("扫行 ComThread.Release()失败:"+mx.getMessage());
            }
        }
    }
    public void wordToPDF1(List<Map<String,String>> files) throws Exception{
        String inputFile="";
        for(Map<String,String> file:files) {
            try {
                inputFile = file.get("doc").toString().replace("\\","//");
                String pdfFile = file.get("pdf").toString().replace("\\","//");
                System.out.println("开始转换:" + inputFile);
                long start = System.currentTimeMillis();
                Process P = Runtime.getRuntime().exec("wscript D://DocToPdf.vbs" + " " + inputFile + " " + pdfFile);
                P.waitFor();
                long end = System.currentTimeMillis();
                System.out.println("已成功转换为:" + pdfFile);
                System.out.println("累计用时：" + (end - start) + "ms");
                P.destroy();
            }catch (Exception ax){
                logger.info("转换word文件:"+inputFile+"失败:" + ax.getMessage());
            }
        }
    }
    public byte[] getFileContent(String MainID,String FileName) throws Exception{
        LoginUserInfo Info=CompanyContext.get();
        Optional<cpcPackageMain> findMains=mainRep.findFirstByPid(MainID);
        if(findMains.isPresent()){
            String TargetDir=bDir+Info.getCompanyId()+"\\"+MainID+"\\";
            String extName=FilenameUtils.getExtension(FileName);
            List<File> findFils=
                    FileUtils.listFiles(new File(TargetDir),new String[]{extName,extName.toUpperCase()},
                            true).stream().collect(Collectors.toList());

            Optional<File> targetFiles=
                    findFils.stream().filter(f->f.getName().toUpperCase().equals(FileName.toUpperCase())).findFirst();
            if(targetFiles.isPresent()==false) throw new Exception(FileName+"在服务器上不存在!");
            else {
                File targetFile=targetFiles.get();
                String inputFile=targetFile.getPath();
                String BExtName=extName.toUpperCase();
                if(BExtName.equals("DOC") || BExtName.equals("DOCX")){
                    String VV=inputFile.replace(extName,"XPDF");
                    File fileVV=new File(VV);
                    if(fileVV.exists()==false) {
                        wordToPDF(inputFile, VV);
                    }
                    return FileUtils.readFileToByteArray(fileVV);
                }
                else if(BExtName.equals("XML")){
                   String X= FileUtils.readFileToString(targetFile,"utf-8");
                   return X.getBytes(StandardCharsets.UTF_8);
                }
               else return com.mchange.io.FileUtils.getBytes(targetFile);
            }
        } else throw new Exception("无法访问文件:"+FileName);
    }
    public List<TreeNode> GetFileTree(String MainID) throws Exception{
        List<TreeNode> Nodes=new ArrayList<>();
        LoginUserInfo Info=CompanyContext.get();
        Optional<cpcPackageMain> findMains=mainRep.findFirstByPid(MainID);
        if(findMains.isPresent()) {
            File TargetDir = new File(bDir + Info.getCompanyId() + "\\" + MainID + "\\");
            AddFile(TargetDir,"",Nodes);
        }
        return Nodes;
    }
    private  void AddFile(File sDir,String parent,List<TreeNode> Nodes){
       File[] files= sDir.listFiles();
       if(files!=null) {
           for (int i = 0; i < files.length; i++) {
               File file = files[i];
               String Name = FilenameUtils.getName(file.getPath());
               if(Name.endsWith("XPDF")==true)continue;
               TreeNode node = new TreeNode();
               node.setName(Name);
               node.setPID(parent);
               node.setFID(Name);
               Nodes.add(node);
               AddFile(file, node.getFID(), Nodes);
           }
       }
    }
    @Transactional(rollbackFor = Exception.class)
    public void CopyDocument(String MainID,String Famingmc,Integer Shenqinglx) throws Exception{
        LoginUserInfo Info=CompanyContext.get();
        Optional<cpcPackageMain> findMains=mainRep.findFirstByPid(MainID);
        if(findMains.isPresent()){
            cpcPackageMain newMain=new cpcPackageMain();
            EntityHelper.copyObject(findMains.get(),newMain);
            newMain.setId(null);
            newMain.setCreateMan(Info.getUserIdValue());
            newMain.setCreateTime(new Date());
            newMain.setFamingmc(Famingmc);
            newMain.setShenqinglx(Shenqinglx);
            newMain.setPackageCreateMan(null);
            newMain.setPackageCreateTime(null);
            newMain.setPackageDirPath(null);
            newMain.setPackageFilePath(null);
            newMain.setDocSn(feeItemMapper.getFlowCode("AJB"));
            newMain.setPid(UUID.randomUUID().toString());

            mainRep.save(newMain);
            List<cpcInventor> invs=cpcInvRep.findAllByMainId(MainID);
            for(cpcInventor inn:invs){
                cpcInventor inv=EntityHelper.copyObject(inn,cpcInventor.class);
                inv.setId(null);
                inv.setMainId(newMain.getPid());
                inv.setCreateMan(Info.getUserIdValue());
                inv.setCreateTime(new Date());
                inv.setSubId(UUID.randomUUID().toString());
                cpcInvRep.save(inv);
            }

            List<cpcAgent> agents=cpcAgentRep.findAllByMainId(MainID);
            for(cpcAgent inn:agents){
                cpcAgent inv=EntityHelper.copyObject(inn,cpcAgent.class);
                inv.setId(null);
                inv.setMainId(newMain.getPid());
                inv.setCreateMan(Info.getUserIdValue());
                inv.setCreateTime(new Date());
                inv.setSubId(UUID.randomUUID().toString());
                cpcAgentRep.save(inv);
            }
            List<cpcApplyMan> applys=cpcApplyRep.findAllByMainId(MainID);
            for(cpcApplyMan inn:applys){
                cpcApplyMan inv=EntityHelper.copyObject(inn,cpcApplyMan.class);
                inv.setId(null);
                inv.setMainId(newMain.getPid());
                inv.setCreateMan(Info.getUserIdValue());
                inv.setCreateTime(new Date());
                inv.setSubId(UUID.randomUUID().toString());
                cpcApplyRep.save(inv);
            }
        }
    }
    public void removeBlank(Document document) {
        for (Section section : document.getSections()) {
            for (Paragraph paragraph : section.getBody().getParagraphs()) {
                boolean flag = false;
                if (paragraph.getChildNodes(NodeType.SHAPE, true).getCount() == 0) {
                    flag = true;
                }
                RunCollection runs = paragraph.getRuns();
                if (flag) {
                    //首先去除各个部分的转义字符，如果删除之后run为空则去除
                    for (Run run : runs) {
                        try {
                            run.setText(run.getText().replaceAll("[\f|\r|\n]", ""));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    //删除空白的paragraph，如果有图片则不删除，
                    String content = StringUtils.clean(paragraph.getText());
                    if (StringUtils.isBlank(content)) {
                        section.getBody().getParagraphs().remove(paragraph);
                    }
                }
            }
        }
    }
}
