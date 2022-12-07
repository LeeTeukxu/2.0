package com.zhide.dtsystem.services.Resignation;

import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.patentInfoPermissionRepository;
import com.zhide.dtsystem.repositorys.tbTradeCaseFollowRecordRepository;
import com.zhide.dtsystem.repositorys.tradeCasesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class TradeCaseResignation {
    @Autowired
    SysLoginUserMapper loginUserMapper;
    @Autowired
    tradeCasesRepository tradeCasesRep;
    @Autowired
    tbTradeCaseFollowRecordRepository tradeCaseFollowRecordRepository;

    public void TradeCaseUserIDChange(Integer Transfer, Integer Resignation) throws Exception {
        LoginUserInfo loginUserInfo = CompanyContext.get();
        List<tradeCases> listTradeCase = new ArrayList<>();
        List<tbTradeCaseFollowRecord> listTradeCaseFollowRecord = new ArrayList<>();

        String loginResignation = loginUserMapper.getLoginUserNameById(Resignation);
        String loginTransfer = loginUserMapper.getLoginUserNameById(Transfer);

        tradeCasesRep.findAll().stream().forEach(f -> {
            tbTradeCaseFollowRecord tradeCaseFollowRecord = new tbTradeCaseFollowRecord();
            tradeCases tradeCases = new tradeCases();
            tradeCases = f;

            String Record = "";
            String CreateManRecord = "";
            String CreateManagerRecord = "";
            String AuditManRecord = "";
            String AuditManagerRecord = "";
            String TechManRecord = "";
            String TechManagerRecord = "";

            //商务人员
            if (f.getCreateMan() != null) {
                if (f.getCreateMan() == Resignation) {
                    tradeCases.setCreateMan(Transfer);
                    CreateManRecord = "商务人员由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }
            //商务人员经理
            if (f.getCreateManager() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetCreateManager(f, Transfer, Resignation, loginResignation, loginTransfer);
                CreateManagerRecord = iar.getRecord();
                tradeCases.setCreateManager(iar.getId());
            }

            //流程审核人员
            if (f.getAuditMan() != null) {
                if (f.getAuditMan() == Resignation) {
                    tradeCases.setAuditMan(Transfer);
                    AuditManRecord = "流程审核人员由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }
            //流程审核人员经理
            if (f.getAuditManager() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetAuditManager(f, Transfer, Resignation, loginResignation, loginTransfer);
                AuditManagerRecord = iar.getRecord();
                tradeCases.setAuditManager(iar.getId());
            }

            //接单技术人员
            if (f.getTechMan() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetTechMan(f, Transfer, Resignation, loginResignation, loginTransfer);
                TechManRecord = iar.getRecord();
                tradeCases.setTechMan(iar.getId());
            }
            //接单技术经理
            if(f.getTechManager() != null) {
                IdAndRecord iar = new IdAndRecord();
                iar = GetTechManager(f, Transfer, Resignation, loginResignation, loginTransfer);
                TechManagerRecord = iar.getRecord();
                tradeCases.setTechManager(iar.getId());
            }
            listTradeCase.add(tradeCases);
            Record = CreateManRecord + CreateManagerRecord + AuditManRecord + AuditManagerRecord + TechManRecord + TechManagerRecord;
            if (!Record.equals("")) {
                tradeCaseFollowRecord.setRecord(Record);
                tradeCaseFollowRecord.setTradeCaseId(f.getId());
                tradeCaseFollowRecord.setCreateMan(loginUserInfo.getUserIdValue());
                tradeCaseFollowRecord.setCreateTime(new Date());
                listTradeCaseFollowRecord.add(tradeCaseFollowRecord);
            }
        });
        if (listTradeCaseFollowRecord.size() > 0) {
            tradeCaseFollowRecordRepository.saveAll(listTradeCaseFollowRecord);
        }
        if (listTradeCase.size() > 0) {
            tradeCasesRep.saveAll(listTradeCase);
        }
    }

    private IdAndRecord GetCreateManager(tradeCases f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
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

    private IdAndRecord GetAuditManager(tradeCases f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
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

    private IdAndRecord GetTechMan(tradeCases f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
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

    private IdAndRecord GetTechManager(tradeCases f, Integer Transfer, Integer Resignation, String loginResignation, String loginTransfer) {
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
