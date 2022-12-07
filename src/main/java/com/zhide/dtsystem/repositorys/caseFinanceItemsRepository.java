package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.caseFinanceItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface caseFinanceItemsRepository  extends  JpaRepository<caseFinanceItems,Integer>  {

}
