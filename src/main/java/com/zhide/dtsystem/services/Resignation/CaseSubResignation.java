package com.zhide.dtsystem.services.Resignation;

import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.caseHighSub;
import com.zhide.dtsystem.models.casesChangeRecord;
import com.zhide.dtsystem.models.casesSub;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.caseHighSubRepository;
import com.zhide.dtsystem.repositorys.casesChangeRecordRepository;
import com.zhide.dtsystem.repositorys.casesSubRepository;
import com.zhide.dtsystem.services.implement.CasesTechBrowseNewServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CaseSubResignation {
    @Autowired
    SysLoginUserMapper loginUserMapper;
    @Autowired
    casesSubRepository subRep;
    @Autowired
    casesChangeRecordRepository changeRep;

    public void CaseSubIDChange(Integer Transfer, Integer Resignation) throws Exception {
        LoginUserInfo Info = CompanyContext.get();

        List<casesSub> listCasesSub = new ArrayList<>();
        List<casesChangeRecord> listCasesChangeRecord = new ArrayList<>();

        String loginResignation = loginUserMapper.getLoginUserNameById(Resignation);
        String loginTransfer = loginUserMapper.getLoginUserNameById(Transfer);

        subRep.findAll().stream().forEach(f -> {
            casesChangeRecord crecord = new casesChangeRecord();
            casesSub casesSub = new casesSub();
            casesSub = f;

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
            String SetttleManagerRecord = "";
            String CreateManRecord = "";
            String SupportManRecord = "";
            String CancelManRecord = "";
            String StatusChangeManRecord = "";

            //??????????????????
            if (f.getAssignTechMan() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetAssignTechMan(f, Transfer, Resignation, loginResignation, loginTransfer);
                AssignTechManRecord = iar.getRecord();
                casesSub.setAssignTechMan(iar.getId());
            }

            //??????????????????
            if (f.getTechMan() != null) {
                if (f.getTechMan() == Resignation) {
                    casesSub.setTechMan(Transfer);
                    TechManRecord = "????????????????????????" + loginResignation + "????????????" + loginTransfer + "???";
                }
            }

            //????????????????????????
            if (f.getTechManager() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetTechManager(f, Transfer, Resignation, loginResignation, loginTransfer);
                TechManagerRecord = iar.getRecord();
                casesSub.setTechManager(iar.getId());
            }

            //???????????????
            if (f.getTechAuditMan() != null) {
                if (f.getTechAuditMan() == Resignation) {
                    casesSub.setTechAuditMan(Transfer);
                    TechAuditManRecord = "?????????????????????" + loginResignation + "????????????" + loginTransfer + "???";
                }
            }

            //?????????????????????
            if (f.getTechAuditManager() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetTechAuditManager(f, Transfer, Resignation, loginResignation, loginTransfer);
                TechAuditManagerRecord = iar.getRecord();
                casesSub.setTechAuditManager(iar.getId());
            }

            //???????????????
            if (f.getCommitTechMan() != null) {
                if (f.getCommitTechMan() == Resignation) {
                    casesSub.setCommitTechMan(Transfer);
                    CommitTechManRecord = "?????????????????????" + loginResignation + "????????????" + loginTransfer + "???";
                }
            }

            //???????????????
            if (f.getAuditTechMan() != null) {
                if (f.getAuditTechMan() == Resignation) {
                    casesSub.setAuditTechMan(Transfer);
                    AuditTechManRecord = "?????????????????????" + loginResignation + "????????????" + loginTransfer + "???";
                }
            }

            //??????????????????
            if (f.getAuditManager() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetAuditManager(f, Transfer, Resignation, loginResignation, loginTransfer);
                AuditManagerRecord = iar.getRecord();
                casesSub.setAuditManager(iar.getId());
            }

            //????????????
            if (f.getClientSetMan() != null) {
                if (f.getClientSetMan() == Resignation) {
                    casesSub.setClientSetMan(Transfer);
                    ClientSetManRecord = "??????????????????" + loginResignation + "????????????" + loginTransfer + "???";
                }
            }

            //??????????????????
            if (f.getTechSbMan() != null) {
                if (f.getTechSbMan() == Resignation) {
                    casesSub.setTechSbMan(Transfer);
                    TechSBManRecord = "????????????????????????" + loginResignation + "????????????" + loginTransfer + "???";
                }
            }

            //???????????????
            if (f.getSettleMan() != null) {
                if (f.getSettleMan() == Resignation) {
                    casesSub.setSettleMan(Transfer);
                    SettleManRecord = "?????????????????????" + loginResignation + "????????????" + loginTransfer + "???";
                }
            }

            //?????????????????????
            if (f.getSetttleManager() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetSettleManager(f, Transfer, Resignation, loginResignation, loginTransfer);
                SetttleManagerRecord = iar.getRecord();
                casesSub.setSettleMan(Integer.parseInt(iar.getId()));
            }

            //?????????
            if (f.getCreateMan() != null) {
                if (f.getCreateMan() == Resignation) {
                    casesSub.setCreateMan(Transfer);
                    CreateManRecord = "???????????????" + loginResignation + "????????????" + loginTransfer + "???";
                }
            }

            //??????????????????
            if (f.getSupportMan() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetSupportMan(f, Transfer, Resignation, loginResignation, loginTransfer);
                SupportManRecord = iar.getRecord();
                casesSub.setSupportMan(iar.getId());
            }

            //????????????????????????
            if (f.getCancelMan() != null) {
                if (f.getCancelMan() == Resignation) {
                    casesSub.setCancelMan(Transfer);
                    CancelManRecord = "??????????????????????????????" + loginResignation + "????????????" + loginTransfer + "???";
                }
            }

            //??????????????????????????????
            if (f.getStatusChangeMan() != null) {
                if (f.getStatusChangeMan() == Resignation) {
                    casesSub.setStatusChangeMan(Transfer);
                    StatusChangeManRecord = "?????????????????????????????????" + loginResignation + "????????????" + loginTransfer + "???";
                }
            }

            listCasesSub.add(casesSub);
            Record = AssignTechManRecord + TechManRecord + TechManagerRecord + AuditManagerRecord + TechManRecord + TechManagerRecord + TechAuditManRecord + TechAuditManagerRecord +
                    CommitTechManRecord + AuditTechManRecord + AuditManagerRecord + ClientSetManRecord + TechSBManRecord + SettleManRecord + SetttleManagerRecord + CreateManRecord +
                    SupportManRecord + CancelManRecord + StatusChangeManRecord;
            if (!Record.equals("")) {
                crecord.setCasesId(f.getCasesId());
                crecord.setMode("AddCaseSubChange");
                crecord.setChangeText(Record);
                crecord.setUserId(Info.getUserIdValue());
                crecord.setCreateTime(new Date());
                listCasesChangeRecord.add(crecord);
            }
        });

        if (listCasesChangeRecord.size() > 0) {
            changeRep.saveAll(listCasesChangeRecord);
        }

        if (listCasesSub.size() > 0) {
            subRep.saveAll(listCasesSub);
        }
    }

    private IdAndRecord GetAssignTechMan(casesSub f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String assignTechMan = f.getAssignTechMan();
        String result = "";
        String[] assignTechMans = assignTechMan.split(",");
        for (String strAssignTechMan : assignTechMans) {
            if (strAssignTechMan.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("????????????????????????" + loginResignation + "????????????" + loginTransfer + "???");
                }
            }else result += strAssignTechMan + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetTechManager(casesSub f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String techManager = f.getTechManager();
        String result = "";
        String[] techManagers = techManager.split(",");
        for (String strTechManagers : techManagers) {
            if (strTechManagers.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("????????????????????????" + loginResignation + "????????????" + loginTransfer + "???");
                }
            }else result += strTechManagers + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetTechAuditManager(casesSub f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String techAuditManager = f.getTechAuditManager();
        String result = "";
        String[] techAuditManagers = techAuditManager.split(",");
        for (String strTechAuditManager : techAuditManagers) {
            if (strTechAuditManager.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("???????????????????????????" + loginResignation + "????????????" + loginTransfer + "???");
                }
            }else result += strTechAuditManager + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetAuditManager(casesSub f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String auditManager = f.getAuditManager();
        String result = "";
        String[] auditManagers = auditManager.split(",");
        for (String strAuditManager : auditManagers) {
            if (strAuditManager.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("??????????????????????????????" + loginResignation + "????????????" + loginTransfer + "???");
                }
            }else result += strAuditManager + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetSettleManager(casesSub f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String settleManager = f.getSetttleManager();
        String result = "";
        String[] settleManagers = settleManager.split(",");
        for (String strSettleManager : settleManagers) {
            if (strSettleManager.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("???????????????????????????" + loginResignation + "????????????" + loginTransfer + "???");
                }
            }else result += strSettleManager + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetSupportMan(casesSub f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String supportMan = f.getSupportMan();
        String result = "";
        String[] supportMans = supportMan.split(",");
        for (String strSupportMan : supportMans) {
            if (strSupportMan.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("????????????????????????" + loginResignation + "????????????" + loginTransfer + "???");
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
