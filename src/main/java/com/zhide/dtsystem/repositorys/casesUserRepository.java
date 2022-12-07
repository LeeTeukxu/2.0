package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.casesUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface casesUserRepository extends JpaRepository<casesUser, Integer> {
    List<casesUser> findAllByCasesIdAndUserId(String CasesID, Integer UserID);

    List<casesUser> findAllByCasesId(String casesId);

    List<casesUser> findAllByUserId(Integer UserID);

    int deleteAllByCasesId(String CasesID);
    int deleteAllByCasesIdAndUserIdIn(String CasesID,List<Integer> IDS);
}
