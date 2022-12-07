package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.cancelCasesMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface cancelCasesMainRepository  extends  JpaRepository<cancelCasesMain,Integer>  {

}
