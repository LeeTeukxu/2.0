package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface techSupportChangeRecordRepository  extends  JpaRepository<techSupportChangeRecord,Integer>  {
    List<techSupportChangeRecord> getAllByCasesId(String CasesID);
    int deleteAllByCasesId(String CasesID);
}
