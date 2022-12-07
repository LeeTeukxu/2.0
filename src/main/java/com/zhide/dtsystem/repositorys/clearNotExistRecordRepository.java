package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.clearNotExistRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface clearNotExistRecordRepository  extends  JpaRepository<clearNotExistRecord,Integer>  {
    List<clearNotExistRecord> findAllByMark(String Mark);
}
