package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface expenseFilesRepository  extends  JpaRepository<expenseFiles,Integer>  {
    Optional<expenseFiles> findFirstByAttId(String AttID);
    List<expenseFiles> findAllByMainId(Integer MainID);
    int deleteAllByMainId(Integer MainID);
}
