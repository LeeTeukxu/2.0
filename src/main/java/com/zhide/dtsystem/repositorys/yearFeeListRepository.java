package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.yearFeeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface yearFeeListRepository extends JpaRepository<yearFeeList, Integer> {
    //int deleteByShenqingh(String shenqingh);

    @Query(value = "Select Distinct Shenqingh from YearFeeList Where Shenqingh in ?1", nativeQuery = true)
    List<String> checkEnableWatchByCodes(List<String> Codes);
    List<yearFeeList> findAllByShenqingh(String shenqingh);
    List<yearFeeList> findAllByShenqinghAndFeenameAndJiaofeir(String Shenqingh, String FeeName, Date JiaoFeiR);
    List<yearFeeList> findAllByIdIn(List<Integer>IDS);
    List<yearFeeList> findAllByJiaofeirBetween(Date before,Date end);
    List<yearFeeList> findAllByShenqinghAndFeenameAndJiaofeirBetween(String shenqingh,String feeName,Date begin,
            Date end);
}
