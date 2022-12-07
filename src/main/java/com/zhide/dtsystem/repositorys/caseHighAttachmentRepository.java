package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.caseHighAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface caseHighAttachmentRepository  extends  JpaRepository<caseHighAttachment,Integer>  {
    int deleteAllByCasesId(String CasesID);
    List<caseHighAttachment> findAllByCasesId(String CasesID);
}
