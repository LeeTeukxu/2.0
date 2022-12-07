package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbLoginClientRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface tbLoginClientRecordRepository  extends  JpaRepository<tbLoginClientRecord,Integer>  {

}
