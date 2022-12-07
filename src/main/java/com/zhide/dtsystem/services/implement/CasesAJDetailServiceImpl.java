package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.models.casesAjDetail;
import com.zhide.dtsystem.repositorys.casesAjAttachmentRepository;
import com.zhide.dtsystem.repositorys.casesAjDetailRepository;
import com.zhide.dtsystem.services.define.ICasesAJDetailService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CasesAJDetailServiceImpl implements ICasesAJDetailService {
    @Autowired
    casesAjDetailRepository casesAjRep;
    @Autowired
    casesAjAttachmentRepository ajAttRep;

    @Override
    @Transactional
    public boolean Remove(String AJID) {
        ajAttRep.deleteAllByAjid(AJID);
        casesAjRep.deleteAllByAjid(AJID);
        return true;
    }

    @Override
    @Transactional
    public boolean RemoveAjAttachment(String AJID, String AttID) {
        Optional<casesAjDetail> finds = casesAjRep.findFirstByAjid(AJID);
        if (finds.isPresent()) {
            casesAjDetail findOne = finds.get();
            String AttIDS = findOne.getAttIds();
            if (StringUtils.isEmpty(AttIDS) == false) {
                List<String> Fs = Arrays.stream(AttIDS.split(",")).collect(Collectors.toList());
                Fs.remove(AttID);
                findOne.setAttIds(StringUtils.join(Fs, ","));
                casesAjRep.save(findOne);
            }
        }
        ajAttRep.deleteAllByAttId(AttID);
        return true;
    }
}
