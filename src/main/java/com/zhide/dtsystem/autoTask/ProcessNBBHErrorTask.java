package com.zhide.dtsystem.autoTask;


import com.zhide.dtsystem.common.PageableUtils;
import com.zhide.dtsystem.mapper.AllUserListMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.NBBHInfo;
import com.zhide.dtsystem.models.decodeTaskError;
import com.zhide.dtsystem.models.pantentInfo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.decodeTaskErrorRepository;
import com.zhide.dtsystem.repositorys.pantentInfoRepository;
import com.zhide.dtsystem.repositorys.patentInfoPermissionRepository;
import com.zhide.dtsystem.services.NBBHCode;
import com.zhide.dtsystem.services.define.INeiBuBHErrorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName: ProcessNBBHErrorTask
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年06月29日 11:36
 **/
@Component
public class ProcessNBBHErrorTask {
    @Autowired
    decodeTaskErrorRepository decodeRep;
    @Autowired
    pantentInfoRepository shenRep;
    @Autowired
    patentInfoPermissionRepository pInfo;
    @Autowired
    AllUserListMapper userMapper;
    @Autowired
    NBBHCode NBBHCode;
    @Autowired
    INeiBuBHErrorService nbErrService;
    Logger logger = LoggerFactory.getLogger(ProcessNBBHErrorTask.class);
    Integer PageSize=20;
    @Scheduled(cron = "0 0 0/6 * * ?")
    public void process() {
        List<String> companyIDS = userMapper.getCompanyList();
        for (int i = 0; i < companyIDS.size(); i++) {
            String companyId = companyIDS.get(i);
            changeContext(companyId);
            ProcessSingleCompany();
            CompanyContext.set(null);
        }
    }
    private void ProcessSingleCompany(){
        Integer Count=decodeRep.countAllByCanUseIsFalse();
        int MaxPage=Math.floorDiv(Count,20)+1;
        for(int n=0;n<MaxPage;n++){
            Pageable pageable= PageableUtils.create(n,PageSize,"ErrorTime","Desc");
            Page<decodeTaskError> errorPage=decodeRep.findAllByCanUseIsFalse(pageable);
            List<decodeTaskError> errors=errorPage.getContent();
            for(decodeTaskError error:errors){
                String Shenqingh=error.getShenqingh();
                Optional<pantentInfo> findPs=shenRep.findByShenqingh(Shenqingh);
                if(findPs.isPresent()){
                    pantentInfo pInfo=findPs.get();
                    String NBBH=pInfo.getNeibubh();
                    if(StringUtils.isEmpty(NBBH)==true){
                        //内部编号为空就不存在错误了。不在那个列表里面显示了。
                        error.setError("");
                        error.setCanUse(true);
                        error.setProcessTime(new Date());
                    } else{
                        String  XNBBH=NBBH.replace("XS","YW");
                        NBBHInfo NBInfo= NBBHCode.Parse(NBBH);

                        nbErrService.SaveAll(Shenqingh,NBInfo);
                        List<String>errorTexts=new ArrayList<>();
                        if(NBInfo.isDecodeAll()==true){
                            error.setCanUse(true);
                            error.setError("");
                            error.setProcessTime(new Date());

                            pInfo.setNbFixed(true);
                            pInfo.setNbFixedTime(new Date());
                            shenRep.save(pInfo);
                            logger.info(Shenqingh+"的内部编号:"+NBBH+"已处理完成，将成预警列表中消失。");
                        } else {
                            NBInfo.foreach((type,arr)->{
                                if(arr.size()==0){
                                    if(type.equals("BH")==false &&  XNBBH.indexOf(type)>-1) {
                                        String Pre="";
                                        if(type.equals("LC"))Pre="流程";
                                        else if(type.equals("JS"))Pre="技术";
                                        else if(type.equals("KH"))Pre="客户";
                                        else if(type.equals("YW") || type.equals("XS"))Pre="业务";
                                        errorTexts.add(Pre+":"+GetValueByType(XNBBH,type) + "无效");
                                    }
                                }
                            });
                            if(errorTexts.size()>0){
                                error.setError(String.join(",",errorTexts));
                                error.setProcessTime(new Date());
                                error.setCanUse(false);
                            } else {
                                logger.info(Shenqingh+"的内部编号:"+NBBH+"解析出现了问题。");
                            }
                        }
                    }
                } else {
                    error.setError(Shenqingh+"指向的专利不存在!");
                    error.setCanUse(false);
                    error.setProcessTime(new Date());
                }
                decodeRep.save(error);
            }
        }
    }
    private String GetValueByType(String BH,String Type){
        String[] WS=BH.split(";");
        for(String Word:WS){
            if(Word.startsWith(Type)) return Word.replace(Type+":","");
        }
        return "";
    }
    private void changeContext(String companyId) {
        LoginUserInfo info = new LoginUserInfo();
        info.setUserName("aa");
        info.setUserId("aa");
        info.setCompanyId(companyId);
        CompanyContext.set(info);
    }
}
