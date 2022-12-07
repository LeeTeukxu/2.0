package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.mapper.FeeItemMapper;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.*;
import com.zhide.dtsystem.services.define.ICaseHighSubService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * @ClassName: CaseHighSubServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年11月24日 20:18
 **/
@Service
public class CaseHighSubServiceImpl implements ICaseHighSubService {
    @Autowired
    FeeItemMapper feeItemMapper;
    @Autowired
    productItemTypeRepository productRep;
    @Autowired
    caseHighSubRepository caseSubRep;
    @Autowired
    caseHighMainRepository highMainRep;
    @Autowired
    caseHighSubFilesRepository highSubFileRep;
    @Autowired
    tbAttachmentRepository attRep;
    @Autowired
    SysLoginUserMapper loginUserMapper;
    @Autowired
    casesChangeRecordRepository caseChangeRep;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public caseHighMain AddAJRecord(highCreateInfo info) throws Exception {
        int num = info.getNum();
        LoginUserInfo loginInfo = CompanyContext.get();
        String CasesID = "";
        for (int i = 0; i < num; i++) {
            caseHighSub sub = new caseHighSub();
            sub.setSubId(UUID.randomUUID().toString());
            sub.setCasesId(info.getCasesId());
            sub.setYid(info.getyId());
            Optional<productItemType> findOnes = productRep.findFirstByFid(info.getyId());
            if (findOnes.isPresent()) {
                sub.setYName(findOnes.get().getName());
            }
            Integer containSJF = info.getContainSJF();
            Double sjfMoney = info.getSjfMoney();
            sub.setContainSjf(containSJF);
            sub.setDaiMoney(info.getDaiMoney());
            sub.setNum(1);
            if (containSJF == 1) sub.setSjfMoney(sjfMoney);
            else sub.setSjfMoney(0.0);
            sub.setTotalMoney(info.getDaiMoney() * 1 + (containSJF == 1 ? sjfMoney : 0));
            sub.setSubNo(feeItemMapper.getFlowCode("GQ"));
            sub.setClientRequiredDate(info.getClientRequiredDate());
            sub.setCreateMan(loginInfo.getUserIdValue());
            sub.setCreateTime(new Date());
            sub.setArea(info.getArea());
            sub.setSbYear(info.getSbYear());
            sub.setSwsName(info.getSwsName());
            sub.setSupportMan(info.getSupportMan());
            sub.setRptType(info.getRptType());
            caseSubRep.save(sub);
            CasesID = info.getCasesId();
        }
        return ReCalcTotalMoney(CasesID);
    }

    @Override
    public List<caseHighSub> getSubRecords(String CasesID) {
        List<caseHighSub> subs = caseSubRep.findAllByCasesId(CasesID);
        for (int i = 0; i < subs.size(); i++) {
            caseHighSub sub = subs.get(i);
            Integer containSJF = sub.getContainSjf();
            Double Total = 0.0;
            if (containSJF == 1) {
                Total = sub.getDaiMoney() * sub.getNum() + sub.getSjfMoney();
            } else Total = sub.getDaiMoney() * sub.getNum();
            sub.setTotalMoney(Total);
        }
        return subs;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public caseHighMain RemoveSubs(List<String> IDS) throws Exception {
        String CasesID = "";
        for (int i = 0; i < IDS.size(); i++) {
            String SubID = IDS.get(i);
            Optional<caseHighSub> findSubs = caseSubRep.findBySubId(SubID);
            if (findSubs.isPresent()) {
                caseHighSub sub = findSubs.get();
                CasesID = sub.getCasesId();
                caseSubRep.delete(sub);
            }
        }
        return ReCalcTotalMoney(CasesID);
    }

    @Override
    public caseHighMain SaveSubs(List<caseHighSub> Subs) throws Exception {
        String CasesID = "";
        for (int i = 0; i < Subs.size(); i++) {
            caseHighSub info = Subs.get(i);
            String SubID = info.getSubId();
            Optional<caseHighSub> findSubs = caseSubRep.findBySubId(SubID);
            if (findSubs.isPresent()) {
                caseHighSub sub = findSubs.get();

                sub.setCasesId(info.getCasesId());
                sub.setYid(info.getYid());
                Optional<productItemType> findOnes = productRep.findFirstByFid(info.getYid());
                if (findOnes.isPresent()) {
                    sub.setYName(findOnes.get().getName());
                }
                Integer containSJF = info.getContainSjf();
                Double sjfMoney = info.getSjfMoney();
                sub.setContainSjf(containSJF);
                sub.setDaiMoney(info.getDaiMoney());
                sub.setNum(1);
                if (containSJF == 1) sub.setSjfMoney(sjfMoney);
                else sub.setSjfMoney(0.0);
                sub.setTotalMoney(info.getDaiMoney() * 1 + (containSJF == 1 ? sjfMoney : 0));
                sub.setSubNo(feeItemMapper.getFlowCode("GQ"));
                sub.setClientRequiredDate(info.getClientRequiredDate());
                sub.setArea(info.getArea());
                sub.setSbYear(info.getSbYear());
                sub.setSwsName(info.getSwsName());
                caseSubRep.save(sub);
                CasesID = sub.getCasesId();
            }
        }
        return ReCalcTotalMoney(CasesID);
    }

    private caseHighMain ReCalcTotalMoney(String CasesID) throws Exception {
        if (StringUtils.isEmpty(CasesID) == false) {
            Optional<caseHighMain> findMains = highMainRep.findFirstByCasesId(CasesID);
            if (findMains.isPresent()) {
                List<caseHighSub> Subs = caseSubRep.findAllByCasesId(CasesID);
                Double Total = Subs.stream().flatMapToDouble(f -> DoubleStream.of(f.getContainSjf() == 1 ?
                        f.getNum() * f.getDaiMoney
                        () + f.getSjfMoney() : f.getNum() * f.getDaiMoney())).sum();
                caseHighMain main = findMains.get();
                main.setAllMoney(Total);
                highMainRep.save(main);
                return main;
            } else throw new Exception("CasesID:" + CasesID + "指向记录不存在!");
        } else return null;
    }

    public List<String> getSubFiles(String SubID, String Type) {
        return highSubFileRep.getAllBySubIdAndType(SubID, Type).stream().map(f -> f.getAttId()).collect(Collectors.toList());
    }

    public boolean SaveSubFiles(String CasesID, String SubID, String AttID, String Type) {
        LoginUserInfo logInfo = CompanyContext.get();
        caseHighSubFiles sub = new caseHighSubFiles();
        sub.setSubId(SubID);
        sub.setCasesId(CasesID);
        sub.setAttId(AttID);
        sub.setType(Type);
        sub.setCreateTime(new Date());
        sub.setCreateMan(logInfo.getUserIdValue());
        highSubFileRep.save(sub);

        Optional<caseHighSub> findSubs = caseSubRep.findFirstBySubId(SubID);
        if (findSubs.isPresent()) {
            caseHighSub findSub = findSubs.get();
            List<caseHighSubFiles> files = highSubFileRep.getAllBySubId(SubID);
            String techFiles =
                    StringUtils.join(files.stream().filter(f -> f.getType().equals("Tech")).map(f -> f.getAttId
                    ()).collect(Collectors.toList()), ",");
            String acceptFiles = StringUtils.join(files.stream().filter(f -> f.getType().equals("Accept")).map(f -> f
                    .getAttId()).collect(Collectors.toList()), ",");
            findSub.setTechFiles(techFiles);
            findSub.setAcceptTechFiles(acceptFiles);
            caseSubRep.save(findSub);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean RemoveSubFiles(String CasesID, String AttID) {
        highSubFileRep.deleteAllByCasesIdAndAttId(CasesID, AttID);
        attRep.deleteAllByGuid(AttID);
        return true;
    }

    @Override
    public void CaseHighSubIDChange(Integer Transfer, Integer Resignation) throws Exception {
        LoginUserInfo loginUserInfo = CompanyContext.get();
        List<caseHighSub> listCaseHighSub = new ArrayList<>();
        List<casesChangeRecord> listCasesChangeRecord = new ArrayList<>();

        String loginResignation = loginUserMapper.getLoginUserNameById(Resignation);
        String loginTransfer = loginUserMapper.getLoginUserNameById(Transfer);

        caseSubRep.findAll().stream().forEach(f -> {
            casesChangeRecord crecord = new casesChangeRecord();
            caseHighSub caseHighSub = new caseHighSub();
            caseHighSub = f;

            String Record = "";
            String AssignTechManRecord = "";
            String TechManRecord = "";
            String TechManagerRecord = "";
            String TechAuditManRecord = "";
            String TechAuditManagerRecord = "";
            String CommitTechManRecord = "";
            String AuditTechManRecord = "";
            String AuditManagerRecord = "";
            String ClientSetManRecord = "";
            String TechSBManRecord = "";
            String SettleManRecord = "";
            String SettleManagerRecord = "";
            String CreateManRecord = "";
            String SupportManRecord = "";
            String CancelManRecord = "";

            //AssignTechMan
            if (f.getAssignTechMan() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetAssignTechMan(f, Transfer, Resignation, loginResignation, loginTransfer);
                AssignTechManRecord = iar.getRecord();
                caseHighSub.setAssignTechMan(iar.getId());
            }
            //代理师
            if (f.getTechMan() != null) {
                if (f.getTechMan() == Resignation) {
                    caseHighSub.setTechMan(Transfer);
                    TechManRecord = "代理师由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }
            //代理师经理
            if (f.getTechManager() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetTechManager(f, Transfer, Resignation, loginResignation, loginTransfer);
                TechManagerRecord = iar.getRecord();
                caseHighSub.setTechManager(iar.getId());
            }
            //指定核稿人
            if (f.getTechAuditMan() != null) {
                if (f.getTechAuditMan() == Resignation) {
                    caseHighSub.setTechAuditMan(Transfer);
                    TechAuditManRecord = "指定核稿人由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }
            //指定核稿人经理
            if (f.getTechAuditManager() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetTechAuditManager(f, Transfer, Resignation, loginResignation, loginTransfer);
                TechAuditManagerRecord = iar.getRecord();
                caseHighSub.setTechAuditManager(iar.getId());
            }
            //技审提交人
            if (f.getCommitTechMan() != null) {
                if (f.getCommitTechMan() == Resignation) {
                    caseHighSub.setCommitTechMan(Transfer);
                    CommitTechManRecord = "技审提交人由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }
            //实际技审人
            if (f.getAuditTechMan() != null) {
                if (f.getAuditTechMan() == Resignation) {
                    caseHighSub.setAuditTechMan(Transfer);
                    AuditTechManRecord = "实际技审人由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }
            //实际技审经理
            if (f.getAuditManager() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetAuditManager(f, Transfer, Resignation, loginResignation, loginTransfer);
                AuditManagerRecord = iar.getRecord();
                caseHighSub.setAuditManager(iar.getId());
            }
            //商务人员
            if (f.getClientSetMan() != null) {
                if (f.getClientSetMan() == Resignation) {
                    caseHighSub.setClientSetMan(Transfer);
                    ClientSetManRecord = "商务人员由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }
            //技术申报人员
            if (f.getTechSbMan() != null) {
                if (f.getTechSbMan() == Resignation) {
                    caseHighSub.setTechSbMan(Transfer);
                    TechSBManRecord = "技术申报人员由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }
            //提成报销人
            if (f.getSettleMan() != null) {
                if (f.getSettleMan() == Resignation) {
                    caseHighSub.setSettleMan(Transfer);
                    SettleManRecord = "提成报销人由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }
            //提成报销人经理
            if (f.getSetttleManager() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetSettleManager(f, Transfer, Resignation, loginResignation, loginTransfer);
                SettleManagerRecord = iar.getRecord();
                caseHighSub.setSettleMan(Integer.parseInt(iar.getId()));
            }
            //创建人
            if (f.getCreateMan() != null) {
                if (f.getCreateMan() == Resignation) {
                    caseHighSub.setCreateMan(Transfer);
                    CreateManRecord = "创建人由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }
            //SupportMan
            if (f.getSupportMan() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetSupportMan(f, Transfer, Resignation, loginResignation, loginTransfer);
                SupportManRecord = iar.getRecord();
                caseHighSub.setSupportMan(iar.getId());
            }
            //CannelMan
            if (f.getCancelMan() != null) {
                if (f.getCancelMan() == Resignation) {
                    caseHighSub.setCancelMan(Transfer);
                    CancelManRecord = "CancelMan由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }

            listCaseHighSub.add(caseHighSub);
            Record = AssignTechManRecord + TechManRecord + TechManagerRecord + TechAuditManRecord + TechAuditManagerRecord + CommitTechManRecord + AuditTechManRecord + AuditManagerRecord +
                    ClientSetManRecord + TechSBManRecord + SettleManRecord + SettleManagerRecord + CreateManRecord + SupportManRecord + CancelManRecord;
            if (!Record.equals("")) {
                crecord.setCasesId(f.getCasesId());
                crecord.setMode("AddCaseSubManChange");
                crecord.setChangeText(Record);
                crecord.setUserId(loginUserInfo.getUserIdValue());
                crecord.setCreateTime(new Date());
                listCasesChangeRecord.add(crecord);
            }
        });
        if (listCasesChangeRecord.size() > 0) {
            caseChangeRep.saveAll(listCasesChangeRecord);
        }
        if (listCaseHighSub.size() > 0) {
            caseSubRep.saveAll(listCaseHighSub);
        }
    }

    private IdAndRecord GetAssignTechMan(caseHighSub f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String assignTechMan = f.getAssignTechMan();
        String result = "";
        String[] assignTechMans = assignTechMan.split(",");
        for (String strAssignTechMan : assignTechMans) {
            if (strAssignTechMan.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("AssignTechMan由：" + loginResignation + "变更为：" + loginTransfer + "；");
                }
            }else result += strAssignTechMan + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetTechManager(caseHighSub f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String techManager = f.getTechManager();
        String result = "";
        String[] techManagers = techManager.split(",");
        for (String strTechManager : techManagers) {
            if (strTechManager.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("代理师经理由：" + loginResignation + "变更为：" + loginTransfer + "；");
                }
            }else result += strTechManager + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetTechAuditManager(caseHighSub f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String techAuditManager = f.getTechAuditManager();
        String result = "";
        String[] techAuditManagers = techAuditManager.split(",");
        for (String strTechAuditManager : techAuditManagers) {
            if (strTechAuditManager.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("指定核稿人经理由：" + loginResignation + "变更为：" + loginTransfer + "；");
                }
            }else result += strTechAuditManager + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetSettleManager(caseHighSub f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String settleManager = f.getSetttleManager();
        String result = "";
        String[] settleManagers = settleManager.split(",");
        for (String strSettleManager : settleManagers) {
            if (strSettleManager.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("提成报销人经理由：" + loginResignation + "变更为：" + loginTransfer + "；");
                }
            }else result += strSettleManager + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetAuditManager(caseHighSub f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String auditManager = f.getAuditManager();
        String result = "";
        String[] auditManagers = auditManager.split(",");
        for (String strAuditManager : auditManagers) {
            if (strAuditManager.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("实际技审经理由：" + loginResignation + "变更为：" + loginTransfer + "；");
                }
            }else result += strAuditManager + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetSupportMan(caseHighSub f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String supportMan = f.getSupportMan();
        String result = "";
        String[] supportMans = supportMan.split(",");
        for (String strSupportMan : supportMans) {
            if (strSupportMan.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("SupportMan由：" + loginResignation + "变更为：" + loginTransfer + "；");
                }
            }else result += strSupportMan + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    public class IdAndRecord {
        private String Id;
        private String Record;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getRecord() {
            return Record;
        }

        public void setRecord(String record) {
            Record = record;
        }
    }
}
