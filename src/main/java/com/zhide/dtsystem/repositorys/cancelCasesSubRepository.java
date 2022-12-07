package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.cancelCasesSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface cancelCasesSubRepository  extends  JpaRepository<cancelCasesSub,Integer>  {
    List<cancelCasesSub> findAllByCasesId(String CasesID);
    int deleteAllByMainId(int MainID);
}
