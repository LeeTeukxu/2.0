package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface expenseMainRepository  extends  JpaRepository<expenseMain,Integer>  {

}
