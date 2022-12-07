package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.caseHighSubUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface caseHighSubUserRepository  extends  JpaRepository<caseHighSubUser,Integer>  {
    Optional<caseHighSubUser> findFirstBySubIdAndUserId(String SubID,Integer UserID);
    List<caseHighSubUser> findAllByCasesIdAndUserId(String casesId,int UserID);
    int deleteAllByCasesId(String CasesID);
    int deleteAllBySubIdAndUserIdIn(String SubID,List<Integer>UserIDS);
    List<caseHighSubUser> findAllByUserId(Integer UserID);
}
