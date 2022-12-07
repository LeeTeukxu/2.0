package com.zhide.dtsystem.services.Resignation;

import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.FinanChangeRecord;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.tbArrivalRegistration;
import com.zhide.dtsystem.models.tbInvoiceApplication;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.FinanChangeRecordRepository;
import com.zhide.dtsystem.repositorys.tbArrivalRegistrationRepository;
import com.zhide.dtsystem.repositorys.tbInvoiceApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class InvoiceApplicationResignation {
    @Autowired
    FinanChangeRecordRepository finanChangeRecordRepository;
    @Autowired
    SysLoginUserMapper loginUserMapper;
    @Autowired
    tbInvoiceApplicationRepository invoiceApplicationRepository;

    public void InvoiceApplicationIDChange(Integer Transfer, Integer Resignation) throws Exception {
        LoginUserInfo Info = CompanyContext.get();

        List<tbInvoiceApplication> listInvoiceApplication = new ArrayList<>();
        List<FinanChangeRecord> listFinanChangeRecord = new ArrayList<>();

        String loginResignation = loginUserMapper.getLoginUserNameById(Resignation);
        String loginTransfer = loginUserMapper.getLoginUserNameById(Transfer);

        invoiceApplicationRepository.findAll().stream().forEach(f -> {
            FinanChangeRecord finanChangeRecord = new FinanChangeRecord();
            tbInvoiceApplication invoiceApplication = new tbInvoiceApplication();
            invoiceApplication = f;

            String Record = "";
            String ApplicantRecord = "";
            String UserIDRecord = "";

            //申请人
            if (f.getApplicant() != null) {
                if (f.getApplicant() == Resignation) {
                    invoiceApplication.setApplicant(Transfer);
                    ApplicantRecord = "申请人由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }
            //创建人
            if (f.getUserId() != null) {
                if (f.getUserId() == Resignation) {
                    invoiceApplication.setUserId(Transfer);
                    UserIDRecord = "创建人由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }

            listInvoiceApplication.add(invoiceApplication);

            Record = ApplicantRecord + UserIDRecord;
            if (!Record.equals("")) {
                finanChangeRecord.setPid(f.getInvoiceApplicationId());
                finanChangeRecord.setMode("Add");
                finanChangeRecord.setModuleName("InvoiceApplication");
                finanChangeRecord.setChangeText(Record);
                finanChangeRecord.setUserId(Info.getUserIdValue());
                finanChangeRecord.setCreateTime(new Date());
                listFinanChangeRecord.add(finanChangeRecord);
            }
        });

        if (listFinanChangeRecord.size() > 0) {
            finanChangeRecordRepository.saveAll(listFinanChangeRecord);
        }
        if (listInvoiceApplication.size() > 0) {
            invoiceApplicationRepository.saveAll(listInvoiceApplication);
        }
    }
}
