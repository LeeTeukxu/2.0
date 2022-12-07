package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.casesSubUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface casesSubUserRepository extends JpaRepository<casesSubUser, Integer> {
    Optional<casesSubUser> findFirstBySubIdAndUserId(String SubID, Integer UserID);
    List<casesSubUser> findAllByCasesIdAndUserId(String CasesID,Integer UserID);

    List<casesSubUser> findAllByUserId(Integer UserID);

    int deleteAllByCasesId(String CasesID);

    int deleteAllBySubIdAndUserIdIn(String SubID,List<Integer> UserIDS);
}
