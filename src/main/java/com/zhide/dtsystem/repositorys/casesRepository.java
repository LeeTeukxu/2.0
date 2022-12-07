package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.cases;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface casesRepository extends JpaRepository<cases, Integer> {
    Optional<cases> findFirstByCasesid(String CasesID);

    List<cases> findAllByContractNo(String ContractNo);
}
