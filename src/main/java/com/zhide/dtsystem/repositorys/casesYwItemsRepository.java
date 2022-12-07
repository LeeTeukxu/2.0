package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.casesYwItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface casesYwItemsRepository extends JpaRepository<casesYwItems, Integer> {
    int deleteAllByCasesId(String CasesID);

    List<casesYwItems> findAllByCasesId(String CasesID);

    Optional<casesYwItems> findFirstByCasesIdAndYName(String CasesID, String YName);

    Optional<casesYwItems> findFirstBySubId(String subId);

    int deleteAllBySubId(String subId);

    List<casesYwItems> getAllBySubIdIn(String[] SubIDS);
}
