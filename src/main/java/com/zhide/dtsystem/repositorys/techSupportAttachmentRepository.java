package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface techSupportAttachmentRepository  extends  JpaRepository<techSupportAttachment,Integer>  {
    int deleteAllByCasesId(String CasesID);
    List<techSupportAttachment> findAllByCasesId(String CasesID);
    Optional<techSupportAttachment> findFirstByAttId(String AttID);
    Optional<techSupportAttachment> findFirstByCasesIdAndAttId(String CasesID,String AttID);
    Optional<techSupportAttachment> findFirstByRefId(String RefID);
}
