package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.versionUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface versionUpdateRepository  extends  JpaRepository<versionUpdate,Integer>  {
    List<versionUpdate> getAllByVerIdGreaterThanAndCanUseIsTrue(Integer verId);
    Optional<versionUpdate> findFirstByAccountAndVerId(String account,Integer verId);
}
