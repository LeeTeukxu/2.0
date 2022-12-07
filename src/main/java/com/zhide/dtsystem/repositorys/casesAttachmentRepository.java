package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.casesAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface casesAttachmentRepository  extends  JpaRepository<casesAttachment,Integer>  {
    int deleteAllByCasesId(String CasesID);
    List<casesAttachment> findAllByCasesId(String CasesID);
}
