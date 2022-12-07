package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.casesChangeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface casesChangeRecordRepository  extends  JpaRepository<casesChangeRecord,Integer>  {
    List<casesChangeRecord> findAllByCasesIdOrderByCreateTimeDesc(String CasesID);
}
