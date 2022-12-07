package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.contractCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface contractCodeRepository extends JpaRepository<contractCode, Integer> {
}
