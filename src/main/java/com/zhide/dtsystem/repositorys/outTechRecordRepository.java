package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.outTechRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface outTechRecordRepository  extends  JpaRepository<outTechRecord,Integer>  {

}
