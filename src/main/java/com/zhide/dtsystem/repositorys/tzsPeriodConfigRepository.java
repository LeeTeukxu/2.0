package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tzsPeriodConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface tzsPeriodConfigRepository  extends  JpaRepository<tzsPeriodConfig,Integer>  {
    @Query(value = "SELECT a.ID AS id,a.TZSPeriodID AS tzsPeriodId,b.TypeName as [text] FROM tbTZSSendType AS a INNER JOIN TZSPeriodConfig AS b ON a.tzsPeriodId=b.ID",nativeQuery = true)
    List<Map<String, Object>> findTIDAndSID();

    @Query(value = "SELECT ID AS tzsPeriodId,TypeName as [text] FROM TZSPeriodConfig",nativeQuery = true)
    List<Map<String, Object>> findTSID();
}
