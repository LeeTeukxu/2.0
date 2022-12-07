package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.caseHighUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface caseHighUserRepository extends JpaRepository<caseHighUser, Integer> {
    List<caseHighUser> findAllByCasesIdAndUserId(String CasesID, int UserID);

    List<caseHighUser> findAllByCasesId(String casesId);

    List<caseHighUser> findAllByUserId(Integer UserID);

    int deleteAllByCasesId(String CasesID);
    int deleteAllByCasesIdAndUserIdIn(String CasesID,List<Integer> IDS);
}
