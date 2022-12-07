package com.zhide.dtsystem.services.Resignation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AllResgination {
    @Autowired
    ClientFollowResignation clientFollowResignation;
    @Autowired
    ClientInfoResignation clientInfoResignation;
    @Autowired
    PatentInfoResignation patentInfoResignation;
    @Autowired
    TradeCaseResignation tradeCaseResignation;
    @Autowired
    FinanceResignation financeResignation;
    @Autowired
    InvoiceApplicationResignation invoiceApplicationResignation;
    @Autowired
    CaseHighMainResignation caseHighMainResignation;
    @Autowired
    CaseHighSubResignation caseHighSubResignation;
    @Autowired
    CaseMainResignation caseMainResignation;
    @Autowired
    CaseSubResignation caseSubResignation;

    @Transactional(rollbackFor = Exception.class)
    public void AllResgination(Integer Transfer, Integer Resgination) {
        try {
            clientFollowResignation.FollowRecordUserIDChange(Transfer, Resgination);
            clientFollowResignation.FollowRecordAddInChange(Transfer, Resgination);
            clientInfoResignation.ChangeSignMan(Transfer, Resgination);
            patentInfoResignation.PatentInfoPermissionChangeUserID(Transfer, Resgination);
            tradeCaseResignation.TradeCaseUserIDChange(Transfer, Resgination);
            financeResignation.FinanceIDChange(Transfer, Resgination);
            invoiceApplicationResignation.InvoiceApplicationIDChange(Transfer, Resgination);
            caseHighMainResignation.CaseHighMainIDChange(Transfer, Resgination);
            caseHighSubResignation.CaseHighSubIDChange(Transfer, Resgination);
            caseMainResignation.CaseMainIDChange(Transfer, Resgination);
            caseSubResignation.CaseSubIDChange(Transfer, Resgination);
        }catch (Exception ax) {
            ax.printStackTrace();
        }
    }
}
