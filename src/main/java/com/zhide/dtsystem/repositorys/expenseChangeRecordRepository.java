package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface expenseChangeRecordRepository  extends  JpaRepository<expenseChangeRecord,Integer>  {
    int deleteAllByMainId(Integer MainID);
    List<expenseChangeRecord> findAllByMainId(Integer MainID);
}
