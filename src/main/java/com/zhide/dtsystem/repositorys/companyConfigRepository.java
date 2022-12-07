package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.companyConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface companyConfigRepository  extends  JpaRepository<companyConfig,String>  {
    Optional<companyConfig> findAllByCompanyCode(String CompanyCode);
}
