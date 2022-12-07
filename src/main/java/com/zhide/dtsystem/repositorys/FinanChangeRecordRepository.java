package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.FinanChangeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinanChangeRecordRepository extends  JpaRepository<FinanChangeRecord,Integer>  {
    List<FinanChangeRecord> findAllByPidAndModuleNameOrderByCreateTimeDesc(Integer PID,String ModuleName);
    int deleteAllByPidAndModuleName(Integer PID,String ModuleName);
}
