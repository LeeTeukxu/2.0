package com.zhide.dtsystem.services.Resignation;

import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.FinanChangeRecordRepository;
import com.zhide.dtsystem.repositorys.tbArrivalRegistrationRepository;
import com.zhide.dtsystem.repositorys.tbTradeCaseFollowRecordRepository;
import com.zhide.dtsystem.repositorys.tradeCasesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FinanceResignation {
    @Autowired
    SysLoginUserMapper loginUserMapper;
    @Autowired
    tbArrivalRegistrationRepository arrivalRegistrationRepository;
    @Autowired
    FinanChangeRecordRepository finanChangeRecordRepository;

    public void FinanceIDChange(Integer Transfer, Integer Resignation) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        List<tbArrivalRegistration> listArrivalRegistration = new ArrayList<>();
        List<FinanChangeRecord> listFinanChangeRecord = new ArrayList<>();

        String loginResignation = loginUserMapper.getLoginUserNameById(Resignation);
        String loginTransfer = loginUserMapper.getLoginUserNameById(Transfer);

        arrivalRegistrationRepository.findAll().stream().forEach(f -> {
            FinanChangeRecord finanChangeRecord = new FinanChangeRecord();
            tbArrivalRegistration arrivalRegistration = new tbArrivalRegistration();
            arrivalRegistration = f;

            String Record = "";
            String SignManRecord = "";
            String ClaimantRecord = "";
            String ReviewerRecord = "";

            //登记人
            if (f.getSignMan() != null) {
                if (f.getSignMan() == Resignation) {
                    arrivalRegistration.setSignMan(Transfer);
                    SignManRecord = "登记人由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }

            //认领人
            if (f.getClaimant() != null) {
                if (f.getClaimant() == Resignation) {
                    arrivalRegistration.setClaimant(Transfer);
                    ClaimantRecord = "认领人由：" + loginResignation + "变更为：" + loginTransfer + "；";
                }
            }

            //复核人
            if (f.getReviewer() != null) {
                if (f.getReviewer() == Resignation) {
                    arrivalRegistration.setReviewer(Transfer);
                    ReviewerRecord = "复核人由：" + loginResignation + "变更为：" + loginTransfer;
                }
            }
            listArrivalRegistration.add(arrivalRegistration);

            Record = SignManRecord + ClaimantRecord + ReviewerRecord;
            if (!Record.equals("")) {
                finanChangeRecord.setPid(f.getArrivalRegistrationId());
                finanChangeRecord.setMode("Add");
                finanChangeRecord.setModuleName("Arrival");
                finanChangeRecord.setChangeText(Record);
                finanChangeRecord.setUserId(Info.getUserIdValue());
                finanChangeRecord.setCreateTime(new Date());
                listFinanChangeRecord.add(finanChangeRecord);
            }
        });
        if (listFinanChangeRecord.size() > 0) {
            finanChangeRecordRepository.saveAll(listFinanChangeRecord);
        }
        if (listArrivalRegistration.size() > 0) {
            arrivalRegistrationRepository.saveAll(listArrivalRegistration);
        }
    }
}
