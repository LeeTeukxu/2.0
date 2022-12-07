package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.otherOfficeFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface otherOfficeFeeListRepository extends  JpaRepository<otherOfficeFee,Integer>  {
    @Transactional
    int deleteAllByShenqingh(String Shenqingh);
    @Transactional
    @Modifying
    @Query(value = "UPDATE OtherOfficeFee SET OtherOfficeStates=:OtherOfficeStates WHERE id=:id",nativeQuery = true)
    int update(@Param("OtherOfficeStates") Integer OtherOfficeStates,
               @Param("id") Integer id);
    int deleteAllById(Integer ID);
    List<otherOfficeFee> findAllByShenqinghAndExpenseItemAndJiaofeir(String Shenqingh, String ExpenseItem, Date JiaoFeir);
    List<otherOfficeFee> findAllByIdIn(List<Integer> IDS);
}
