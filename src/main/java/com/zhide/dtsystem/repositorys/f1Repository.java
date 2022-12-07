package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.f1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface f1Repository  extends  JpaRepository<f1,Integer>  {
    Optional<f1>  findFirstByExpenseItem(String ExpenseItem);
}
