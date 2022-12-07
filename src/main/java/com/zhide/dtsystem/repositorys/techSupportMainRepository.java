package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface techSupportMainRepository  extends  JpaRepository<techSupportMain,Integer>  {
    Optional<techSupportMain> findFirstByCasesId(String  CasesID);
    int deleteAllByCasesId(String CasesID);
}
