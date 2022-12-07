package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.casesSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface casesSubRepository extends JpaRepository<casesSub, Integer> {
    int deleteAllBySubId(String subId);

    int deleteAllByCasesId(String casesId);

    List<casesSub> findAllByCasesId(String casesId);

    Optional<casesSub> findFirstBySubId(String SubID);

    List<casesSub> getAllBySubIdIn(String[] SubIDS);

    Optional<casesSub> findFirstByProcessTextLike(String Pre);

    Optional<casesSub> findFirstBySubNo(String SubNO);

    int countAllByCLevel(int CLevel);
    List<casesSub> findAllBySubIdIn(List<String> SubIDs);

    Optional<casesSub> getBySubId(String SubID);
}
