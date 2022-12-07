package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.casesMemo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface casesMemoRepository extends JpaRepository<casesMemo, Integer> {
    Optional<casesMemo> findFirstByCasesidAndId(String CasesID, Integer ID);
    int removeAllByCasesid(String casesId);
    Optional<casesMemo> findFirstBySubId(String SubID);
    List<casesMemo> findAllByCasesid(String CasesID);

}
