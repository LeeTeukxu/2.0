package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface tbClaimRepository extends JpaRepository<tbClaim, Integer> {

}
