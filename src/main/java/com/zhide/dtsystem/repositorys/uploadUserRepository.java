package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.uploadUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface uploadUserRepository extends JpaRepository<uploadUser, Integer> {
    Optional<uploadUser> findFirstByAccount(String account);
}
