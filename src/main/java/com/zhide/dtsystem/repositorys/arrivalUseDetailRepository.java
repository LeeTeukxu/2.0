package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.arrivalUseDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface arrivalUseDetailRepository  extends  JpaRepository<arrivalUseDetail,Integer>  {
    List<arrivalUseDetail> findAllByArrId(int ArrID);
    List<arrivalUseDetail> findAllByArrIdAndIdNot(int ArrID,int ID);
    Page<arrivalUseDetail> findAllByArrId(int ArrID, Pageable pageable);
    List<arrivalUseDetail> findAllByCasesId(String CasesID);
    List<arrivalUseDetail> findAllByCasesIdAndState(String CasesID,int State);
    List<arrivalUseDetail> findAllByCasesIdAndArrIdNot(String CasesID,Integer ID);
    List<arrivalUseDetail> findAllByCasesIdAndCanUse(String CasesID,boolean canUse);
    Optional<arrivalUseDetail> findFirstByArrIdAndCasesId(int ArrID,String CasesID);
    int deleteAllByArrId(int arrId);
    List<arrivalUseDetail> findAllByArrIdAndState(int ArrID,int State);
}
