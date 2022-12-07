package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.aj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ajRepository extends JpaRepository<aj, String> {
    String getShenqingbhByAnjuanbh(String anjuanbh);
    List<aj> findAllByShenqingbh(String shenqingbh);
    @Query(value = "Select top 50 * from AJ Where neibubh is not null and shenqingbh in(Select shenqingbh from " +
            "pantentInfo Where Neibubh is null and isnull(NBFixed,0)=0)", nativeQuery = true)
    List<aj> findAllNeedCopyToPantentInfos();
}
