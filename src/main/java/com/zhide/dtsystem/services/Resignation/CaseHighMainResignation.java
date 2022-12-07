package com.zhide.dtsystem.services.Resignation;

import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.FinanChangeRecordRepository;
import com.zhide.dtsystem.repositorys.caseHighMainRepository;
import com.zhide.dtsystem.repositorys.casesChangeRecordRepository;
import com.zhide.dtsystem.repositorys.tbInvoiceApplicationRepository;
import com.zhide.dtsystem.services.implement.CaseHighAllBrowseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CaseHighMainResignation {
    @Autowired
    SysLoginUserMapper loginUserMapper;
    @Autowired
    caseHighMainRepository highMainRep;
    @Autowired
    casesChangeRecordRepository caseChangeRep;

    public void CaseHighMainIDChange(Integer Transfer, Integer Resignation) throws Exception {
        LoginUserInfo loginUserInfo = CompanyContext.get();
        List<caseHighMain> listCaseHighMan = new ArrayList<>();
        List<casesChangeRecord> listCasesChangeRecord = new ArrayList<>();

        String loginResignation = loginUserMapper.getLoginUserNameById(Resignation);
        String loginTransfer = loginUserMapper.getLoginUserNameById(Transfer);

        highMainRep.findAll().stream().forEach(f -> {
            casesChangeRecord crecord = new casesChangeRecord();
            caseHighMain caseHighMain = new caseHighMain();
            caseHighMain = f;

            String Record = "";
            String CreateManRecord = "";
            String CreateManagerRecord = "";
            String AuditManRecord = "";
            String AuditManagerRecord = "";
            String TechManRecord = "";
            String TechManagerRecord = "";
            String CWManRecord = "";
            String CWManagerRecord = "";
            String CompleteManRecord = "";

            //商务人员
            if (f.getCreateMan() != null) {
                if (f.getCreateMan() == Resignation) {
                    caseHighMain.setCreateMan(Transfer);
                    CreateManRecord = "商务人员由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }
            //商务人员经理
            if (f.getCreateManager() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetCreateManager(f, Transfer, Resignation, loginResignation, loginTransfer);
                CreateManagerRecord = iar.getRecord();
                caseHighMain.setCreateManager(iar.getId());
            }

            //流程审核人员
            if (f.getAuditMan() != null) {
                if (f.getAuditMan() == Resignation) {
                    caseHighMain.setAuditMan(Transfer);
                    AuditManRecord = "流程审核人员由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }
            //流程审核人员经理
            if (f.getAuditManager() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetAuditManager(f, Transfer, Resignation, loginResignation, loginTransfer);
                AuditManagerRecord = iar.getRecord();
                caseHighMain.setAuditManager(iar.getId());
            }

            //接单技术人员
            if (f.getTechMan() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetTechMan(f, Transfer, Resignation, loginResignation, loginTransfer);
                TechManRecord = iar.getRecord();
                caseHighMain.setTechMan(iar.getId());
            }

            //接单技术经理
            if (f.getTechManager() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetTechManager(f, Transfer, Resignation, loginResignation, loginTransfer);
                TechManagerRecord = iar.getRecord();
                caseHighMain.setTechManager(iar.getId());
            }

            //财务人员
            if (f.getCwMan() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetCwMan(f, Transfer, Resignation, loginResignation, loginTransfer);
                CWManRecord = iar.getRecord();
                caseHighMain.setCwMan(iar.getId());
            }

            //财务人员经理
            if (f.getCwManager() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetCwManager(f, Transfer, Resignation, loginResignation, loginTransfer);
                CWManagerRecord = iar.getRecord();
                caseHighMain.setCwManager(iar.getId());
            }

            //完结操作人员
            if (f.getCompleteMan() != null) {
                if (f.getCompleteMan() == Resignation) {
                    caseHighMain.setCompleteMan(Transfer);
                    CompleteManRecord = "完结操作人员由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }

            listCaseHighMan.add(caseHighMain);
            Record = CreateManRecord + CreateManagerRecord + AuditManRecord + AuditManagerRecord + TechManRecord + TechManagerRecord + CWManRecord + CWManagerRecord + CompleteManRecord;
            if (!Record.equals("")) {
                crecord.setCasesId(f.getCasesId());
                crecord.setMode("AddCaseHighManChange");
                crecord.setChangeText(Record);
                crecord.setUserId(loginUserInfo.getUserIdValue());
                crecord.setCreateTime(new Date());
                listCasesChangeRecord.add(crecord);
            }
        });
        if (listCasesChangeRecord.size() > 0) {
            caseChangeRep.saveAll(listCasesChangeRecord);
        }
        if (listCaseHighMan.size() > 0) {
            highMainRep.saveAll(listCaseHighMan);
        }
    }

    private IdAndRecord GetCreateManager(caseHighMain f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String createManager = f.getCreateManager();
        String result = "";
        String[] createManagers = createManager.split(",");
        for (String strCreateManager : createManagers) {
            if (strCreateManager.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("商务人员由：" + loginResignation + "变更为：" + loginTransfer + "；");
                }
            }else result += strCreateManager + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetAuditManager(caseHighMain f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String auditManager = f.getAuditManager();
        String result = "";
        String[] auditManagers = auditManager.split(",");
        for (String strAuditManager : auditManagers) {
            if (strAuditManager.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("流程审核人员经理由：" + loginResignation + "变更为：" + loginTransfer + "；");
                }
            }else result += strAuditManager + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetTechMan(caseHighMain f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String techMan = f.getTechMan();
        String result = "";
        String[] techMans = techMan.split(",");
        for (String strTechMan : techMans) {
            if (strTechMan.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("接单技术人员由：" + loginResignation + "变更为：" + loginTransfer + "；");
                }
            }else result += strTechMan + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetTechManager(caseHighMain f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String techManager = f.getTechManager();
        String result = "";
        String[] techManagers = techManager.split(",");
        for (String strTechManager : techManagers) {
            if (strTechManager.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("接单技术经理由：" + loginResignation + "变更为：" + loginTransfer + "；");
                }
            }else result += strTechManager + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetCwMan(caseHighMain f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String cwMan = f.getCwMan();
        String result = "";
        String[] cwMans = cwMan.split(",");
        for (String strCwMan : cwMans) {
            if (strCwMan.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("财务人员由：" + loginResignation + "便跟为：" + loginTransfer + "；");
                }
            }else result += strCwMan + ",";
        }
        result = result.substring(0,result.length() - 1);
        iar.setId(result);
        return iar;
    }

    private IdAndRecord GetCwManager(caseHighMain f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
        IdAndRecord iar = new IdAndRecord();
        String cwManager = f.getCwManager();
        String result = "";
        String[] cwManagers = cwManager.split(",");
        for (String strCwManager : cwManagers) {
            if (strCwManager.equals(Resignation.toString())) {
                result += Transfer + ",";
                if (iar.getRecord() == null) {
                    iar.setRecord("财务人员经理由：" + loginResignation + "变更为：" + loginTransfer + "；");
                }
            }else result += strCwManager + ",";
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
