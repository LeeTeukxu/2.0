package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.mapper.ClientInfoMapper;
import com.zhide.dtsystem.mapper.FeeItemMapper;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.ICasesAJDetailNewService;
import com.zhide.dtsystem.services.define.ItbLoginUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CasesAJDetailServiceNewImpl implements ICasesAJDetailNewService {
    @Autowired
    casesSubRepository casesSubRep;
    @Autowired
    casesSubFilesRepository subFilesRep;
    @Autowired
    casesMainRepository mainRep;
    @Autowired
    tbAttachmentRepository attRep;
    @Autowired
    FeeItemMapper feeItemMapper;
    @Autowired
    techSupportAttachmentRepository techAttRep;
    @Autowired
    SysLoginUserMapper loginUserMapper;
    @Autowired
    ClientInfoMapper clientInfoMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean SaveSubs(List<casesSub> rows) {
        for (int i = 0; i < rows.size(); i++) {
            casesSub row = rows.get(i);
            int ID = row.getId();
            Optional<casesSub> nowOnes = casesSubRep.findById(ID);
            if (nowOnes.isPresent()) {
                casesSub nowOne = nowOnes.get();
                nowOne.setMemo(row.getMemo());
                nowOne.setShenqingName(row.getShenqingName());
                nowOne.setGuanMoney(row.getGuanMoney());
                nowOne.setDaiMoney(row.getDaiMoney());
                nowOne.setClientRequiredDate(row.getClientRequiredDate());
                nowOne.setRelNo(row.getRelNo());
                nowOne.setRelId(row.getRelId());
                nowOne.setcLevel(row.getcLevel());
                nowOne.setClientLinkMan(row.getClientLinkMan());
                nowOne.setClientLinkMail(row.getClientLinkMail());
                nowOne.setClientLinkPhone(row.getClientLinkPhone());
                nowOne.setHasTech(row.getHasTech());
                casesSubRep.save(nowOne);
            }
        }
        return true;
    }

    public List<String> getSubFiles(String SubID, String Type) {
        List<String>KK=
                subFilesRep.getAllBySubIdAndType(SubID, Type).stream().map(f -> f.getAttId()).distinct().collect(Collectors.toList());
        List<String> DD=new ArrayList<>();
        KK.forEach(f->{
            Optional<techSupportAttachment> findAtts=techAttRep.findFirstByAttId(f);
            if(findAtts.isPresent()==false)DD.add(f);
        });
        return DD;
    }
    @Transactional(rollbackFor = Exception.class)
    public boolean SaveSubFiles(String CasesID, String SubID, String AttID, String Type) {
        LoginUserInfo logInfo = CompanyContext.get();
        boolean IsSupportFile=false;
        Optional<techSupportAttachment> findTechs=techAttRep.findFirstByAttId(AttID);
        if(findTechs.isPresent())IsSupportFile=true;
        Optional<casesSub> findSubs = casesSubRep.findFirstBySubId(SubID);
        casesSub findSub=null;
        if (findSubs.isPresent()) {
            findSub = findSubs.get();
            List<casesSubFiles> files = subFilesRep.getAllBySubId(SubID);
            List<String> TechIDS=files.stream()
                    .filter(f -> f.getType().equals("Tech"))
                    .map(f -> f.getAttId()).distinct().collect(Collectors.toList());
            List<String>RealIDS=new ArrayList<>();
            for(int i=TechIDS.size()-1;i>=0;i--){
                String ID=TechIDS.get(i);
                Optional<techSupportAttachment> findAtts=techAttRep.findFirstByAttId(ID);
                if(findAtts.isPresent()==false){
                    //不是技术挖掘文件
                    if(RealIDS.contains(ID)==false)RealIDS.add(ID);
                } else {
                    //技术挖掘只留一个文件。
                    if(IsSupportFile==true)subFilesRep.deleteAllByCasesIdAndAttId(CasesID,ID);
                }
            }
            Optional<casesSubFiles> findFiles=subFilesRep.findFirstByAttId(AttID);
            if(findFiles.isPresent()==false) {
                casesSubFiles sub = new casesSubFiles();
                sub.setSubId(SubID);
                sub.setCasesId(CasesID);
                sub.setAttId(AttID);
                sub.setType(Type);
                sub.setCreateTime(new Date());
                sub.setCreateMan(logInfo.getUserIdValue());
                subFilesRep.save(sub);
            }

            files = subFilesRep.getAllBySubId(SubID);
            RealIDS.add(AttID);
            String techFiles =StringUtils.join(RealIDS, ",");
            String zlFiles = StringUtils.join(files.stream().filter(f -> f.getType().equals("Zl")).map(f -> f.getAttId
                    ()).distinct().collect(Collectors.toList()), ",");
            String acceptFiles = StringUtils.join(files.stream().filter(f -> f.getType().equals("Accept")).map(f -> f
                    .getAttId()).collect(Collectors.toList()), ",");
            findSub.setTechFiles(techFiles);
            findSub.setZlFiles(zlFiles);
            findSub.setAcceptTechFiles(acceptFiles);
            casesSubRep.save(findSub);
        }

        if(Type.equals("Tech")){
            Optional<techSupportAttachment> findAtts=techAttRep.findFirstByRefId(SubID);
            if(findAtts.isPresent()){
                techSupportAttachment attachment= findAtts.get();
                if(attachment.getAttId().equals(AttID)==false){
                    attachment.setRefId(null);
                    attachment.setTechMan(null);
                    attachment.setTechManName(null);
                    attachment.setTechTime(null);
                    attachment.setClientId(null);
                    attachment.setClientName(null);
                    techAttRep.save(attachment);

                    Optional<techSupportAttachment> findOnes=techAttRep.findFirstByAttId(AttID);
                    if(findOnes.isPresent() && findSub!=null){
                        Optional<casesMain> findMains=mainRep.findFirstByCasesId(findSub.getCasesId());
                        techSupportAttachment one=findOnes.get();
                        casesMain findMain= findMains.get();
                        one.setRefId(SubID);
                        Integer TechMan=findSub.getTechMan();
                        if(TechMan!=null) {
                            one.setTechMan(TechMan);
                            one.setTechManName(loginUserMapper.getLoginUserNameById(TechMan));
                        }

                        one.setRefTime(new Date());
                        one.setRefMan(logInfo.getUserIdValue());
                        one.setClientId(findMain.getClientId());
                        one.setClientName(clientInfoMapper.getNamebyId(findMain.getClientId()));
                        one.setTechTime(new Date());
                        techAttRep.save(one);
                    }
                }
            }else {
                Optional<techSupportAttachment> newAtts=techAttRep.findFirstByAttId(AttID);
                if(newAtts.isPresent() && findSub!=null){
                    Optional<casesMain> findMains=mainRep.findFirstByCasesId(findSub.getCasesId());
                    techSupportAttachment newAtt= newAtts.get();
                    casesMain findMain= findMains.get();
                    newAtt.setRefId(SubID);
                    newAtt.setTechTime(new Date());
                    if(findSub.getTechMan()!=null) {
                        newAtt.setTechMan(findSub.getTechMan());
                        newAtt.setTechManName(loginUserMapper.getLoginUserNameById(findSub.getTechMan()));
                    }
                    newAtt.setRefTime(new Date());
                    newAtt.setRefMan(logInfo.getUserIdValue());
                    newAtt.setClientId(findMain.getClientId());
                    newAtt.setClientName(clientInfoMapper.getNamebyId(findMain.getClientId()));
                    techAttRep.save(newAtt);
                }
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean Remove(String AJID) throws Exception {
        String[] IDS = AJID.split(",");
        String CasesID="";
        for (int i = 0; i < IDS.length; i++) {
            String subId = IDS[i];
            Optional<casesSub> subs = casesSubRep.findFirstBySubId(subId);
            if (subs.isPresent()) {
                casesSub sub = subs.get();
                String SubNo = sub.getSubNo();
                CasesID=sub.getCasesId();
                String Type = "ZL";
                String YearMonth = SubNo.substring(2, SubNo.length() - 4);
                Integer Num = Integer.parseInt(SubNo.substring(SubNo.length() - 4));
                feeItemMapper.deleteFlowCode(Type, Num, YearMonth);
                casesSubRep.deleteAllBySubId(subId);
            }
        }
        Optional<casesMain> findMains=mainRep.findFirstByCasesId(CasesID);
        if(findMains.isPresent()) {
            casesMain main=findMains.get();
            List<casesSub> subs = casesSubRep.findAllByCasesId(CasesID);
            Double Total = subs.stream().mapToDouble(f -> f.getGuanMoney() + f.getDaiMoney()).sum();
            main.setAllMoney(Total);
            mainRep.save(main);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean RemoveSubFiles(String CasesID, String AttID) {
        Optional<casesSubFiles> subFiles=subFilesRep.findFirstByAttId(AttID);
        if(subFiles.isPresent()) {
            casesSubFiles subFile=subFiles.get();
            String subId=subFile.getSubId();
            String Type=subFile.getType();
            RebuildAttID(subId,AttID,Type);
            subFilesRep.deleteAllByCasesIdAndAttId(CasesID, AttID);
            attRep.deleteAllByGuid(AttID);
        }
        return true;
    }
    private void RebuildAttID(String SubID,String AttID,String Type){
        Optional<casesSub>findSubs=casesSubRep.findFirstBySubId(SubID);
        if(findSubs.isPresent()){
            casesSub sub= findSubs.get();
            switch (Type){
                case "Tech":{
                    String TechFiles=sub.getTechFiles();
                    if(StringUtils.isEmpty(TechFiles)==false){
                        List<String>XS= Arrays.stream(TechFiles.split(",")).collect(Collectors.toList());
                        XS.remove(AttID);
                        sub.setTechFiles(StringUtils.join(XS,","));
                    }
                    break;
                }
                case "Zl":{
                    String ZlFiles=sub.getZlFiles();
                    if(StringUtils.isEmpty(ZlFiles)==false){
                        List<String>XS= Arrays.stream(ZlFiles.split(",")).collect(Collectors.toList());
                        XS.remove(AttID);
                        sub.setZlFiles(StringUtils.join(XS,","));
                    }
                    break;
                }
                case "Accept":{
                    String AcceptFiles=sub.getAcceptTechFiles();
                    if(StringUtils.isEmpty(AcceptFiles)==false){
                        List<String>XS= Arrays.stream(AcceptFiles.split(",")).collect(Collectors.toList());
                        XS.remove(AttID);
                        sub.setAcceptTechFiles(StringUtils.join(XS,","));
                    }
                    break;
                }
                case "Exp":{
                    String ExpFiles=sub.getExpFiles();
                    if(StringUtils.isEmpty(ExpFiles)==false){
                        List<String>XS= Arrays.stream(ExpFiles.split(",")).collect(Collectors.toList());
                        XS.remove(AttID);
                        sub.setExpFiles(StringUtils.join(XS,","));
                    }
                    break;
                }
                case "Aud":{
                    String AudFiles=sub.getAuditTechFiles();
                    if(StringUtils.isEmpty(AudFiles)==false){
                        List<String>XS= Arrays.stream(AudFiles.split(",")).collect(Collectors.toList());
                        XS.remove(AttID);
                        sub.setAuditTechFiles(StringUtils.join(XS,","));
                    }
                    break;
                }
            }
            casesSubRep.save(sub);
        }
    }
}
