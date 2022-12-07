package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.caseHighSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface caseHighSubRepository extends JpaRepository<caseHighSub, Integer> {
    List<caseHighSub> findAllByCasesId(String CasesID);

    Optional<caseHighSub> findBySubId(String SubID);

    Optional<caseHighSub> findFirstBySubId(String SubID);

    int deleteBySubId(String SubID);

    int deleteAllByCasesId(String CasesID);
    List<caseHighSub> findAllBySubIdIn(List<String>SubIDS);
}
