package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.applyWatchConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface applyWatchConfigRepository  extends  JpaRepository<applyWatchConfig,Integer>  {
    Optional<applyWatchConfig> findAllByShenqingh(String shenqingh);
    int deleteByShenqingh(String shenqingh);
}
