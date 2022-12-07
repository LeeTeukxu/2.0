package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.yearWatchConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface yearWatchConfigRepository  extends  JpaRepository<yearWatchConfig,Integer>  {
    Optional<yearWatchConfig> findAllByShenQingh(String shenqingh);
    int deleteAllByShenQingh(String shenqingh);
}
