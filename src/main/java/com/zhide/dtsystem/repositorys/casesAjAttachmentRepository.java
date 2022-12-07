package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.casesAjAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface casesAjAttachmentRepository  extends  JpaRepository<casesAjAttachment,Integer>  {
    int deleteAllByAjid(String ajId);
    int deleteAllByCasesId(String casesId);
    int deleteAllByAttId(String attId);
}
