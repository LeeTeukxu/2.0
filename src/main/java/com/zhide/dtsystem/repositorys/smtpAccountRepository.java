package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.smtpAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface smtpAccountRepository extends JpaRepository<smtpAccount, Integer> {
    smtpAccount getByUserId(Integer userId);
    Optional<smtpAccount> findFirstByCompanyDefaultIsTrue();
}
