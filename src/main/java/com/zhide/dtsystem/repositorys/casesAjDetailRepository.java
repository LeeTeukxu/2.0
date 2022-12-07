package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.casesAjDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface casesAjDetailRepository  extends  JpaRepository<casesAjDetail,Integer>  {
    int deleteAllByCasesId(String casesId);
    int deleteAllByAjid(String ajId);
    List<casesAjDetail> findAllByCasesId(String casesId);
    Optional<casesAjDetail> findFirstByAjid(String ajId);
}
