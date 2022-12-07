package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbLinkersUpdateRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface tbLinkersUpdateRecordRepository  extends  JpaRepository<tbLinkersUpdateRecord,Integer>  {
    Page<tbLinkersUpdateRecord> findAllByClientID(int ClientID,Pageable pageable);
}
