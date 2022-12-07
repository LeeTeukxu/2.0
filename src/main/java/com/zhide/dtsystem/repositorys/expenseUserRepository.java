package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface expenseUserRepository  extends  JpaRepository<expenseUser,Integer>  {
    int deleteAllByMainId(Integer MainID);
}
