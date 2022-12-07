package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tradeCases;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface tradeCasesRepository extends JpaRepository<tradeCases, Integer> {
    Optional<tradeCases> findFirstByCasesid(String CasesID);

    List<tradeCases> findAllByContractNo(String ContractNo);
}
