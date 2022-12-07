package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.cases;
import com.zhide.dtsystem.models.casesYwAccept;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.casesRepository;
import com.zhide.dtsystem.repositorys.casesYwAcceptRepository;
import com.zhide.dtsystem.repositorys.casesYwItemsRepository;
import com.zhide.dtsystem.services.define.IShenBaoDetailService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class ShenBaoDetailServiceImpl implements IShenBaoDetailService {

    @Autowired
    casesYwAcceptRepository casesYwRep;
    @Autowired
    casesYwItemsRepository casesYwItemRep;
    @Autowired
    casesRepository casesRep;
    @Autowired
    SysLoginUserMapper loginUserMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean SaveAll(List<casesYwAccept> datas) throws Exception {
        String CasesID = "";
        LoginUserInfo LoginInfo = CompanyContext.get();
        for (int i = 0; i < datas.size(); i++) {
            casesYwAccept item = datas.get(i);
            Integer Id = item.getId();
            if (Id == null) Id = 0;
            if (Id == 0) {

            } else {
                Optional<casesYwAccept> findOnes = casesYwRep.findById(Id);
                if (findOnes.isPresent()) {
                    casesYwAccept accept = findOnes.get();
                    CasesID = accept.getCasesId();
                    accept.setShenBao(item.getShenBao());
                    accept.setShenBaoTime(new Date());
                    accept.setShenBaoMan(Integer.parseInt(LoginInfo.getUserId()));
                    casesYwRep.save(accept);
                }
            }
        }
        changeStates(CasesID);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean RemoveAll(Integer ID) throws Exception {
        Optional<casesYwAccept> findOnes = casesYwRep.findById(ID);
        if (findOnes.isPresent()) {
            casesYwAccept one = findOnes.get();
            casesYwRep.delete(one);
            String casesId = one.getCasesId();
            changeStates(casesId);
        }

        return true;
    }

    private void changeStates(String casesId) {
        Optional<cases> findCases = casesRep.findFirstByCasesid(casesId);
        if (findCases.isPresent()) {
            cases find = findCases.get();
            if (HasShenBaoFinish(casesId)) {
                find.setState(9);
            } else find.setState(10);
            List<casesYwAccept> all = casesYwRep.findAllByCasesId(casesId);
            if (all.size() == 0) find.setState(7);
            List<String> texts = new ArrayList<>();
            for (int i = 0; i < all.size(); i++) {
                casesYwAccept item = all.get(i);
                Integer techMan = item.getTechMan();
                String techName = loginUserMapper.getLoginUserNameById(techMan);
                String X = String.format("%s撰写的%d件%s已完成申报", techName, item.getNum(), item.getYName());
                texts.add(X);
            }
            find.setShenBaoStatus(StringUtils.join(texts, ';'));
            casesRep.save(find);
        }
    }

    /**
     * 这一笔交单是不是都认领完成了。
     *
     * @param CasesID
     * @return
     */
    private boolean HasShenBaoFinish(String CasesID) {
        List<casesYwAccept> findAll = casesYwRep.findAllByCasesId(CasesID);
        List<casesYwAccept> finds = findAll.stream().filter(f -> StringUtils.isEmpty(f
                .getShenBao())).collect(toList());
        return finds.size() == 0;
    }
}
