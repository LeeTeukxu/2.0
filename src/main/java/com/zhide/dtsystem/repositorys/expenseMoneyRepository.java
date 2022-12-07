package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface expenseMoneyRepository  extends  JpaRepository<expenseMoney,Integer>  {
        List<expenseMoney> findAllByYearAndFid(Integer Year,Integer FID);
        List<expenseMoney> findAllByYear(Integer Year);
}
