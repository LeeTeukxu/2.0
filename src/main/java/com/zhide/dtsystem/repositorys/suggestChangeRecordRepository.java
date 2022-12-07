package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface suggestChangeRecordRepository  extends  JpaRepository<suggestChangeRecord,Integer>  {
    List<suggestChangeRecord>  findAllByMainId(Integer MainID);
    int deleteAllByMainId(Integer MainID);
}
