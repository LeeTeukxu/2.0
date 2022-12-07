package com.zhide.dtsystem.autoTask;

import com.zhide.dtsystem.common.CompanyPathUtils;
import com.zhide.dtsystem.common.FTPUtil;
import com.zhide.dtsystem.common.ZipFileUtils;
import com.zhide.dtsystem.mapper.AllUserListMapper;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.mapper.ViewTZSMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.PantentInfoMemo;
import com.zhide.dtsystem.models.TZS;
import com.zhide.dtsystem.models.pantentInfo;
import com.zhide.dtsystem.models.xml.QINGQIUXX;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.TZSRepository;
import com.zhide.dtsystem.repositorys.pantentInfoMemoRepository;
import com.zhide.dtsystem.repositorys.pantentInfoRepository;
import com.zhide.dtsystem.services.instance.XmlParsor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.jni.FileInfo;
import org.aspectj.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.FileFilter;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: TZSInfoFixedTask
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年01月26日 18:56
 **/
@Component
public class TZSInfoFixedTask {
    @Autowired
    AllUserListMapper userMapper;
    @Autowired
    TZSRepository tzsRep;
    @Autowired
    ViewTZSMapper tzsMapper;
    @Autowired
    pantentInfoRepository  pantentRep;
    @Autowired
    pantentInfoMemoRepository  memoRep;
    @Autowired
    SysLoginUserMapper adminMapper;
    Logger logger = LoggerFactory.getLogger(TZSInfoFixedTask.class);

    int AdminValue=0;
    @Scheduled(cron = "0 0 0/6 * * ?")
    public void Process() {
        List<String> companyIDS = userMapper.getCompanyList();
        FTPUtil util = new FTPUtil();
        if (util.connect()) {
            for (int i = 0; i < companyIDS.size(); i++) {
                String companyId = companyIDS.get(i);
                LoginUserInfo info = new LoginUserInfo();
                info.setUserName("aa");
                info.setUserId("aa");
                info.setCompanyId(companyId);
                CompanyContext.set(info);

                List<TZS> alls = tzsRep.findAllEmptyShenqingh();
                if (alls.size() > 0) {
                    logger.info("开始处理:" + companyId + "的已上传通知书但专利号为空的记录,一共有:" + Integer.toString(alls.size()));
                    for (int n = 0; n < alls.size(); n++) {
                        TZS tzs = alls.get(n);
                        try {
                            ProcessSingle(util, tzs);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                List<Map<String,Object>> EmptyDatas= tzsMapper.getAllEmptyApplyMans();
                if(EmptyDatas.size()>0){
                    if(AdminValue==0) {
                        AdminValue = adminMapper.getAdminstratorUser();
                    }
                }
                for(int n=0;n< EmptyDatas.size();n++){
                    try {
                        Map<String, Object> Empty = EmptyDatas.get(n);
                        String Shenqingh = Empty.get("SHENQINGH").toString();
                        String TZSPATH = Empty.get("TZSPATH").toString();
                        ProcessOne(util, TZSPATH, Shenqingh);
                    }
                    catch(Exception ax){
                        ax.printStackTrace();
                    }
                }
                CompanyContext.set(null);
            }
        }
    }
    private  void ProcessOne(FTPUtil ftpUtil,String tzsPath,String Shenqingh) throws Exception{
        String savePath = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString() + ".zip");
        ftpUtil.download(tzsPath, savePath);
        File Fi=new File(savePath);
        if(Fi.exists()==false){
            logger.info(tzsPath+"下载失败，不能进行下一步处理。");
            return;
        }
        if(Fi.length()==0){
            logger.info(tzsPath+"为空文件，不能进行下一步处理。");
            return ;
        }
        String saveDir = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString());
        ZipFileUtils.unZip(savePath, saveDir);
        FileUtils.deleteQuietly(new File(savePath));
        List<File> findFiles=Arrays.stream(FileUtil.listFiles(new File(saveDir), new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().toLowerCase(Locale.ROOT).endsWith("2001011.xml");
            }
        })).collect(Collectors.toList());
        Optional<pantentInfo> findPantents=pantentRep.findByShenqingh(Shenqingh);
        if(findPantents.isPresent()) {
            pantentInfo pantentInfo= findPantents.get();
            if (findFiles.size() > 0) {
                File xmlFile = findFiles.get(0);
                String agency_name = XmlParsor.getValue(xmlFile, "//patent_agency/agency_name");
                String agent_name = XmlParsor.getValue(xmlFile, "//patent_agency/agent_info[@seq=\"1\"]/agent_name");
                String applicant_name = XmlParsor.getValue(xmlFile, "//applicant_info/applicant_name[@seq=\"1\"]");
                pantentInfo.setShenqingrxm(applicant_name);
                if(StringUtils.isEmpty(pantentInfo.getDailijgmc())){
                    pantentInfo.setDailijgmc(agency_name);
                }
                if(StringUtils.isEmpty(pantentInfo.getDiyidlrxm())){
                    pantentInfo.setDiyidlrxm(agent_name);
                }
                PantentInfoMemo memo=new PantentInfoMemo();
                memo.setShenqingh(Shenqingh);
                memo.setMid(UUID.randomUUID().toString());
                memo.setMemo("定时任务填充了专利申请人、代理机构、代理人信息");
                memo.setCreateDate(new Date());
                memo.setCreateMan(AdminValue);
                memoRep.save(memo);

            } else {
                pantentInfo.setShenqingrxm("申请受理书错误,无法解析");
            }
            pantentRep.save(pantentInfo);
        }
        FileUtils.deleteDirectory(new File(saveDir));
    }
    private void ProcessSingle(FTPUtil util, TZS tzs) throws Exception {
        String tzsPath = tzs.getTzspath();

        String savePath = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString() + ".zip");
        util.download(tzsPath, savePath);
        File Fi=new File(savePath);
        if(Fi.exists()==false){
            logger.info(tzsPath+"下载失败，不能进行下一步处理。");
            return;
        }
        if(Fi.length()==0){
            logger.info(tzsPath+"为空文件，不能进行下一步处理。");
            return ;
        }
        String saveDir = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString());
        ZipFileUtils.unZip(savePath, saveDir);
        FileUtils.deleteQuietly(new File(savePath));
        File xmlFile = new File(Paths.get(saveDir, "list.xml").toString());
        if (xmlFile.exists()) {
            QINGQIUXX p = XmlParsor.getSingleByChildNode(xmlFile, "//data-bus/TONGZHISXJ/SHUXINGXX", QINGQIUXX.class);
            if (p != null) {
                if (StringUtils.isEmpty(p.getSHENQINGH()) == false) {
                    logger.info(tzs.getTongzhisbh() + "" + tzs.getTongzhismc() + "的申请号已设置为:" + p.getSHENQINGH());
                    tzs.setShenqingh(p.getSHENQINGH());
                    tzsRep.save(tzs);
                }
            }
        } else {
            logger.info(xmlFile.getPath() + " is not exist。");
        }
        FileUtils.deleteDirectory(new File(saveDir));
    }
}
