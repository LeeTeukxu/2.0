package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.casesNewAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface casesNewAttachmentRepository  extends  JpaRepository<casesNewAttachment,Integer>  {
    int deleteAllByCasesId(String CasesID);
    List<casesNewAttachment> findAllByCasesId(String CasesID);
}
