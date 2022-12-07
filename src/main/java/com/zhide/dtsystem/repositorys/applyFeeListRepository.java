package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.applyFeeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface applyFeeListRepository extends JpaRepository<applyFeeList, Integer> {
    int deleteByShenqingh(String shenqingh);
    @Query(value = "Select Distinct Shenqingh from ApplyFeeList Where Shenqingh in ?1", nativeQuery = true)
    List<String> checkEnableWatchByCodes(List<String> Codes);
    List<applyFeeList> findAllByShenqingh(String shenqingh);
    List<applyFeeList> findAllByShenqinghAndFeenameAndJiaofeir(String Shenqingh, String FeeName, Date JiaoFeiR);
    List<applyFeeList> findAllByIdIn(List<Integer>IDS);
}
