package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.caseHighMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface caseHighMainRepository extends JpaRepository<caseHighMain, Integer> {
    Optional<caseHighMain> findFirstByCasesId(String CasesID);
    List<caseHighMain> findAllByClientId(int clientId);
}
