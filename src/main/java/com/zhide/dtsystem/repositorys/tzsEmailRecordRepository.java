package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tzsEmailRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface tzsEmailRecordRepository  extends  JpaRepository<tzsEmailRecord,Integer>  {
    List<tzsEmailRecord> findAllByTongzhisbhOrderBySendTimeDesc(String tongzhisbh);
}
