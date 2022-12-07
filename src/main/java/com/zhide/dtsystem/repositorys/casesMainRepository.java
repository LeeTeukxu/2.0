package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.casesMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface casesMainRepository extends JpaRepository<casesMain, Integer> {
    Optional<casesMain> findFirstByCasesId(String casesId);
    List<casesMain> findAllByStateEquals(int State);
    List<casesMain> findAllByClientId(int clientId);
}
