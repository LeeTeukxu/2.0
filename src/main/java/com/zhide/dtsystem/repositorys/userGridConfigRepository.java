package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.userGridConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userGridConfigRepository  extends  JpaRepository<userGridConfig,Integer>  {
        Optional<userGridConfig> findFirstByUserIdAndUrl(Integer userId,String Url);
        Optional<userGridConfig> findFirstByShareAndUrl(Integer Share,String Url);
}
