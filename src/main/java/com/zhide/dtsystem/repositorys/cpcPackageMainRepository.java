package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.cpcPackageMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface cpcPackageMainRepository  extends  JpaRepository<cpcPackageMain,Integer>  {
    Optional<cpcPackageMain> findFirstByPid(String PID);

}
