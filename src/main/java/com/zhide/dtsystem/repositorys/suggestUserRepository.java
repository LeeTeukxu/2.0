package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface suggestUserRepository  extends  JpaRepository<suggestUser,Integer>  {
    Optional<suggestUser> findFirstByMainIdAndUserId(Integer MainID,Integer UserID);
    int deleteAllByMainId(Integer MainID);
}
