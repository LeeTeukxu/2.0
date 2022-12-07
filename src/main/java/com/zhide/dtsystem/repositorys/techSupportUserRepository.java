package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.techSupportUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface techSupportUserRepository extends JpaRepository<techSupportUser, Integer> {
    List<techSupportUser> findAllByCasesIdAndUserId(String CasesID, Integer UserID);

    int deleteAllByCasesIdAndUserIdIn(String CasesID, List<Integer> IDS);

    int deleteAllByCasesId(String CasesID);
}
