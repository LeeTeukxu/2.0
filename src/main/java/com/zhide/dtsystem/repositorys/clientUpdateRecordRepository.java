package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.clientUpdateRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface clientUpdateRecordRepository  extends  JpaRepository<clientUpdateRecord,Integer>  {

}
