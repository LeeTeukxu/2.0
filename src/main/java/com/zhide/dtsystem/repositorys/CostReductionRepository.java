package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.CostReduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface CostReductionRepository extends  JpaRepository<CostReduction,Integer>  {
    @Transactional
    @Modifying
    @Query(value = "UPDATE CostReduction SET InternalPeople=:InternalPeople," +
            "InternalResult=:InternalResult," +
            "InternalDate=:InternalDate WHERE CostReductionID=:CostReductionID",nativeQuery = true)
    int updateNeiShen(@Param("InternalPeople") Integer InternalPeople,
                     @Param("InternalResult") Integer InternalResult,
                     @Param("InternalDate") Date InternalDate,
                      @Param("CostReductionID") Integer CostReductionID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE CostReduction SET Transactor=:Transactor," +
            "DealTime=:DealTime,GzjZt=1 WHERE CostReductionID=:CostReductionID",nativeQuery = true)
    int updateGuoZhiJu(@Param("Transactor") Integer Transactor,
                      @Param("DealTime") Date DealTime,
                      @Param("CostReductionID") Integer CostReductionID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE CostReduction SET Transactor=''," +
            "DealTime=NULL,GzjZt=0 WHERE CostReductionID=:CostReductionID",nativeQuery = true)
    int updateUnGuoZhiJu(@Param("CostReductionID") Integer CostReductionID);

    @Transactional
    @Modifying
    @Query(value = "UPDATE CostReduction SET TheSuccess=:TheSuccess," +
            "ReductionTheYear=:ReductionTheYear WHERE CostReductionID=:CostReductionID",nativeQuery = true)
    int updateFeiJianJieGuo(@Param("TheSuccess") Integer TheSuccess,
                      @Param("ReductionTheYear") String ReductionTheYear,
                      @Param("CostReductionID") Integer CostReductionID);

    List<CostReduction> getAllByUUIdIn(String[] UUIDS);
}
