package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbBackupRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface tbBackupRecordRepository  extends  JpaRepository<tbBackupRecord,Integer>  {

}
