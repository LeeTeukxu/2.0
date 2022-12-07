package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.MailConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MailConfigRepository extends  JpaRepository<MailConfig,Integer>  {
    Optional<MailConfig> findAllByCompanyCode(String CompanyCode);
}
