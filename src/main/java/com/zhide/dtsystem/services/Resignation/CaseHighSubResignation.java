package com.zhide.dtsystem.services.Resignation;

import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.caseHighMain;
import com.zhide.dtsystem.models.caseHighSub;
import com.zhide.dtsystem.models.casesChangeRecord;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.caseHighMainRepository;
import com.zhide.dtsystem.repositorys.caseHighSubRepository;
import com.zhide.dtsystem.repositorys.casesChangeRecordRepository;
import com.zhide.dtsystem.services.implement.CaseHighSubServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CaseHighSubResignation {
    @Autowired
    SysLoginUserMapper loginUserMapper;
    @Autowired
    caseHighSubRepository caseSubRep;
    @Autowired
    casesChangeRecordRepository caseChangeRep;

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

            //指定技术人员
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
            //技术支持人员
            if (f.getSupportMan() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetSupportMan(f, Transfer, Resignation, loginResignation, loginTransfer);
                SupportManRecord = iar.getRecord();
                caseHighSub.setSupportMan(iar.getId());
            }
            //业务中止操作人员
            if (f.getCancelMan() != null) {
                if (f.getCancelMan() == Resignation) {
                    caseHighSub.setCancelMan(Transfer);
                    CancelManRecord = "业务中止操作人员由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }

            listCaseHighSub.add(caseHighSub);
            Record = AssignTechManRecord + TechManRecord + TechManagerRecord + TechAuditManRecord + TechAuditManagerRecord + CommitTechManRecord + AuditTechManRecord + AuditManagerRecord +
                    ClientSetManRecord + TechSBManRecord + SettleManRecord + SettleManagerRecord + CreateManRecord + SupportManRecord + CancelManRecord;
            if (!Record.equals("")) {
                crecord.setCasesId(f.getCasesId());
                crecord.setMode("AddCaseSubMainChange");
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
                    iar.setRecord("指定技术人员由：" + loginResignation + "变更为：" + loginTransfer + "；");
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
                    iar.setRecord("技术支持人员由：" + loginResignation + "变更为：" + loginTransfer + "；");
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
