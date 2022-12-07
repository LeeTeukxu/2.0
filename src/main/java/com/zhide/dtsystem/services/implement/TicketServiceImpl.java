package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.*;
import com.zhide.dtsystem.mapper.PantentInfoMemoMapper;
import com.zhide.dtsystem.mapper.TicketsMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.pantentInfoRepository;
import com.zhide.dtsystem.repositorys.tbAttachmentRepository;
import com.zhide.dtsystem.repositorys.ticketsRepository;
import com.zhide.dtsystem.repositorys.tzsEmailRecordRepository;
import com.zhide.dtsystem.services.PantentInfoMemoCreator;
import com.zhide.dtsystem.services.define.ITicketService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: TicketServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年01月12日 20:03
 **/
@Service
public class TicketServiceImpl implements ITicketService {
    @Autowired
    tbAttachmentRepository attachmentRep;
    @Autowired
    pantentInfoRepository pRep;
    @Autowired
    ticketsRepository ticketRep;
    @Autowired
    TicketsMapper tickMapper;
    @Autowired
    tzsEmailRecordRepository tzsEmailRep;
    @Autowired
    PantentInfoMemoMapper infoMemoMapper;
    File[] PdfFiles = null;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public tbAttachment AddTickFile(MultipartFile file) throws Exception {
        String uuId = UUID.randomUUID().toString();
        if(file==null) throw new Exception("文件数据无效，请检查浏览器是否工作正常!");
        String fileName = file.getOriginalFilename();
        LoginUserInfo Info = CompanyContext.get();
        File fx = SaveFile(file, uuId);
        List<String>Errors=new ArrayList<>();
        if (fx.exists()) {
            tbAttachment OO = SaveFileRecord(fx, fileName, uuId, fx.getName());
            List<List<String>> IDS = UnZipAndRead(fx);
            for (int i = 0; i < IDS.size(); i++) {
                List<String> Datas = IDS.get(i);
                boolean IsSpec=false;
                if(Datas.size()==10) IsSpec=true;
                SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
                String ticketCode = Datas.get(6);
                if(IsSpec==true) {
                    ticketCode=Datas.get(5);
                }
                Optional<tickets> findTickets = ticketRep.findFirstByTicketCode(ticketCode);
                tickets ticket = new tickets();
                if (findTickets.isPresent()) ticket = findTickets.get();
                if(IsSpec==false) {
                    ticket.setPayCompany(Datas.get(0));
                    ticket.setRequestCode(Datas.get(1));
                    ticket.setShenqingh(Datas.get(2));
                    ticket.setPayDate(simple.parse(Datas.get(3)));
                    ticket.setTicketName(Datas.get(4));
                    ticket.setTicketType(Datas.get(5));
                    ticket.setTicketCode(Datas.get(6));
                    ticket.setMoney(Double.parseDouble(Datas.get(7)));
                } else {
                    ticket.setShenqingh(Datas.get(1));
                    ticket.setPayDate(simple.parse(Datas.get(2)));
                    ticket.setTicketName(Datas.get(3));
                    ticket.setTicketType(Datas.get(4));
                    ticket.setTicketCode(Datas.get(5));
                    ticket.setMoney(Double.parseDouble(Datas.get(6)));
                }



                ticket.setCreateTime(new Date());
                ticket.setCreateMan(Info.getUserIdValue());
                ticket.setSource(OO.getGuid());
                Optional<pantentInfo> findPs = pRep.findByShenqingh(ticket.getShenqingh());
                if (findPs.isPresent() == false){
                   String X= ticket.getShenqingh();
                   if(Errors.contains(X)==false)Errors.add(X);
                }

                tbAttachment pdfObj = UploadSingleFile(ticket);
                if (pdfObj != null) ticket.setPdf(pdfObj.getGuid());
                ticketRep.save(ticket);
            }
            if(Errors.size()>0)throw new Exception("("+StringUtils.join(Errors,",")+")"+"没有对应的专利资料，请导入专利资料后再进行上传操作。");
            return OO;
        } else throw new Exception("上传文件:"+fileName+"保存失败,操作被异常中止!");
    }

    private tbAttachment UploadSingleFile(tickets ticket) throws Exception {
        FileInputStream fileInputStream = null;
        try {
            LoginUserInfo loginInfo = CompanyContext.get();
            UploadUtils uploadUtils = getUtils();
            String FindFileName =
                    ticket.getShenqingh() + "(" + ticket.getTicketType() + "-" + ticket.getTicketCode() + ")";
            Optional<File> findFile = Arrays.stream(PdfFiles).filter(f -> f.getName().indexOf(FindFileName) > -1)
                    .findFirst();
            if (findFile.isPresent()) {
                File fx = findFile.get();
                fileInputStream = new FileInputStream(fx);
                String uuId = UUID.randomUUID().toString();
                String uploadFileName = uuId + ".pdf";
                uploadFileResult rr = uploadUtils.uploadAttachment(uploadFileName, fileInputStream);
                if (rr.isSuccess() == true) {
                    tbAttachment tb = new tbAttachment();
                    tb.setGuid(uuId);
                    tb.setName(fx.getName());
                    tb.setPath(rr.getFullPath());
                    tb.setType(1);
                    tb.setSize(Integer.parseInt(Long.toString(fx.length())));
                    tb.setUploadMan(Integer.parseInt(loginInfo.getUserId()));
                    tb.setUploadManName(loginInfo.getUserName());
                    tb.setUploadTime(new Date());
                    attachmentRep.save(tb);
                    return tb;
                } else throw new Exception(rr.getMessage());
            } else throw new Exception("未找到票据对应Pdf文件，请按照:专利编号(票据分类-票据号)的规则对票据文件进行命名!");
        } catch (Exception ax) {
            throw ax;
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private UploadUtils getUtils() throws Exception {
        LoginUserInfo info = CompanyContext.get();
        if (info == null) throw new Exception("登录失败，请重新登录!");
        String companyId = info.getCompanyId();
        return UploadUtils.getInstance(companyId);
    }

    private tbAttachment SaveFileRecord(File fx, String fileName, String uuId, String uploadFileName) throws Exception {
        FileInputStream fileInputStream = null;
        try {
            LoginUserInfo loginInfo = CompanyContext.get();
            fileInputStream = new FileInputStream(fx);
            UploadUtils uploadUtils = getUtils();
            uploadFileResult rr = uploadUtils.uploadAttachment(uploadFileName, fileInputStream);
            if (rr.isSuccess() == true) {
                tbAttachment tb = new tbAttachment();
                tb.setGuid(uuId);
                tb.setName(fileName);
                tb.setPath(rr.getFullPath());
                tb.setType(1);
                tb.setSize(Integer.parseInt(Long.toString(fx.length())));
                tb.setUploadMan(Integer.parseInt(loginInfo.getUserId()));
                tb.setUploadManName(loginInfo.getUserName());
                tb.setUploadTime(new Date());
                attachmentRep.save(tb);
                return tb;
            } else throw new Exception("文件上传失败!");
        } catch (Exception ax) {
            throw ax;
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private File SaveFile(MultipartFile file, String uuId) throws Exception {
        String fileName = file.getOriginalFilename();
        String[] exts = fileName.split("\\.");
        String extName = exts[exts.length - 1];
        String uploadFileName = uuId + "." + extName;
        String targetFile = CompanyPathUtils.getFullPath("Temp", uploadFileName);
        File fx = new File(targetFile);
        FileUtils.writeByteArrayToFile(fx, file.getBytes());
        return fx;
    }

    private List<List<String>> UnZipAndRead(File fx) throws Exception {
        List<List<String>> IDS = new ArrayList<>();
        String unZipDir = CompanyPathUtils.getFullPath(UUID.randomUUID().toString());
        ZipFileUtils.unZip(fx.getPath(), unZipDir);
        File UnZipFile = new File(unZipDir);
        File[] FindFiles = FileUtil.listFiles(UnZipFile, f -> f.getName().indexOf("xlsx") > -1);
        File[] ZipFiles = FileUtil.listFiles(UnZipFile, f -> f.getName().indexOf("zip") > -1);
        if (ZipFiles.length != 1) throw new Exception("上传文件格式不对，所有包含票据的Pdf文件应该放在一个压缩文件内!");
        ZipFileUtils.unZip(ZipFiles[0].getPath(), unZipDir);
        PdfFiles = FileUtil.listFiles(UnZipFile, f -> f.getName().indexOf("pdf") > -1);
        if (FindFiles.length == 1) {
            for (int i = 0; i < PdfFiles.length; i++) {
                List<String> Datas = ExcelUtils.GetSingle(FindFiles[0], 1 + i);
                IDS.add(Datas);
            }
        } else throw new Exception("没有可以解析的Excel文件!");
        return IDS;
    }

    public pageObject getData(HttpServletRequest request) throws Exception {
        pageObject object = new pageObject();
        Map<String, Object> parameters = getParameters(request);
        List<Map<String, Object>> datas = tickMapper.getData(parameters);
        int Total = 0;
        List<Map<String, Object>> PP = new ArrayList<>();
        if (datas.size() > 0) {
            Total = Integer.parseInt(datas.get(0).get("_TotalNum").toString());
            object.setTotal(Total);

            List<String> SIDS = datas.stream().map(f -> f.get("Shenqingh").toString()).collect(Collectors.toList());
            List<v_PantentInfoMemo> memos = infoMemoMapper.getAllByIds(SIDS);
            PantentInfoMemoCreator creator = new PantentInfoMemoCreator(memos);
            for (int i = 0; i < datas.size(); i++) {
                Map<String, Object> data = datas.get(i);
                data.remove("_TotalNum");
                String TicketCode = data.get("TicketCode").toString();
                List<tzsEmailRecord> es = tzsEmailRep.findAllByTongzhisbhOrderBySendTimeDesc(TicketCode);
                if (es.size() > 0) data.put("SENDMAIL", 1);
                else data.put("SENDMAIL", 0);

                String SHENQINGH=data.get("Shenqingh").toString();

                List<String> words = creator.Build(SHENQINGH);
                if (words.size() > 0) {
                    data.put("MEMO", String.join("<br/><br/>", words));
                } else {
                    data.put("MEMO", "");
                }
                PP.add(data);
            }
        }
        object.setData(PP);
        return object;
    }

    private Map<String, Object> getParameters(HttpServletRequest request) throws Exception {
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        String sortOrder = request.getParameter("sortOrder");
        if (sortOrder.isEmpty()) sortOrder = "Desc";
        String sortField = request.getParameter("sortField");
        if (sortField.isEmpty()) sortField = "QX";
        Map<String, Object> params = new HashMap<>();
        params.put("Begin", pageIndex == 0 ? pageIndex * pageSize : pageIndex * pageSize + 1);
        params.put("End", (pageIndex + 1) * pageSize);
        params.put("sortOrder", sortOrder);
        params.put("sortField", sortField);
        String queryText = request.getParameter("Query");
        if (Strings.isNotEmpty(queryText)) {
            List<sqlParameter> Vs = JSON.parseArray(queryText, sqlParameter.class);
            List<sqlParameter> OrItems = sqlParameterCreator.convert(Vs);
            params.put("orItems", OrItems);
        } else params.put("orItems", new ArrayList<>());
        String highText = request.getParameter("High");
        if (Strings.isNotEmpty(highText)) {
            List<sqlParameter> Ps = JSON.parseArray(highText, sqlParameter.class);
            List<sqlParameter> AndItems = sqlParameterCreator.convert(Ps);
            params.put("andItems", AndItems);
        } else params.put("andItems", new ArrayList<>());
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            params.put("DepID", Info.getDepId());
            params.put("RoleName", Info.getRoleName());
            params.put("UserID", Info.getUserId());
        } else throw new RuntimeException("登录信息失效，请重新登录！");
        return params;
    }

    public List<String> GetImages(String FileID) throws Exception {
        List<String> Res = new ArrayList<>();
        FTPUtil Ftp = new FTPUtil();
        if (Ftp.connect() == true) {
            tbAttachment tb = attachmentRep.findAllByGuid(FileID).get();
            String tzsPath = tb.getPath();
            String pdfFilePath = Paths.get(CompanyPathUtils.getImages(), tb.getName()).toString();
            String targetDir = Paths.get(CompanyPathUtils.getImages(), tb.getGuid()).toString();
            Ftp.download("//" + tzsPath, pdfFilePath);

            String VV = tb.getGuid() + ".jpg";
            String tPath = Paths.get(targetDir, VV).toString();
            File target = new File(tPath);
            List<String> imagePaths = ImageUtils.pdftoJpg(pdfFilePath, targetDir);
            for (int a = 0; a < imagePaths.size(); a++) {
                File fx = new File(imagePaths.get(a));
                String fxName = fx.getName();
                Res.add("/images/" + tb.getGuid() + "/" + fxName);
            }
            return Res;
        } else throw new Exception("连接FTP服务失败，操作被中止。");
    }

    public File Download(List<String> Codes) throws Exception {
        List<tbAttachment> tzss = attachmentRep.findAllByGuidIn(Codes);
        FTPUtil Ftp = new FTPUtil();
        String dirName = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString());
        File fDir = new File(dirName);
        if (fDir.exists() == false) {
            fDir.mkdirs();
        }
        Integer Total = 0;
        if (tzss.size() > 0) {
            if (Ftp.connect() == true) {
                for (int i = 0; i < tzss.size(); i++) {
                    tbAttachment tzs = tzss.get(i);
                    String tzsPath = tzs.getPath();
                    if (StringUtils.isEmpty(tzsPath) == false) {
                        tzsPath = tzsPath.replace('\\', '/');
                        String baseName = clearFileName(tzs.getName());
                        String createFile = Paths.get(dirName, baseName.replace("/", "")).toString();
                        Ftp.download("/" + tzsPath, createFile);
                    }
                }
            }
        }

        String ZipFilePath =
                Paths.get(CompanyPathUtils.getFullPath("Temp"), UUID.randomUUID().toString() + ".zip").toString();
        ZipFileUtils.zip(dirName + "\\", ZipFilePath);
        //FileUtils.deleteDirectory(fDir);
        return new File(ZipFilePath);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void Remove(List<String> IDS) throws Exception {
        for (int i = 0; i < IDS.size(); i++) {
            Integer ID = Integer.parseInt(IDS.get(i));
            Optional<tickets> findTickets = ticketRep.findById(ID);
            if (findTickets.isPresent()) {
                ticketRep.delete(findTickets.get());
            }
        }
    }

    private String clearFileName(String FileName) {
        FileName = FileName.replaceAll("/", "-");
        FileName = FileName.replaceAll("\\\\", "-");
        return FileName;
    }
}
