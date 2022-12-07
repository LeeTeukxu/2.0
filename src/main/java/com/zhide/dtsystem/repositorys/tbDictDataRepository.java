package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbDictData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface tbDictDataRepository extends JpaRepository<tbDictData, Integer> {
    List<tbDictData> findAllByDtIdAndCanUseTrueOrderBySn(Integer dtId);

    Optional<tbDictData> findAllByFid(int FID);

    Page<tbDictData> findAllBydtId(Integer dtId, Pageable page);
    Page<tbDictData> findAllByDtId(Integer dtId,Pageable page);
    Page<tbDictData> findAllByDtIdAndName(Integer dtId,String name,Pageable page);
    @Query(value = "Select Max(FID) from tbDictData",nativeQuery = true)
    int getMax();

}
