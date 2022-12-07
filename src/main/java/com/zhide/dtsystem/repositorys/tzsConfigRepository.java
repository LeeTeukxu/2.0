package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tzsConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface tzsConfigRepository extends JpaRepository<tzsConfig, Integer> {
    @Query(value = "SELECT * FROM tzsConfig WHERE TZSBH=:TZSBH", nativeQuery = true)
    Optional<tzsConfig> getAllByTzsbh(String TZSBH);

    Optional<tzsConfig> getFirstByTzsbhAndType(String Tzsbh, String Type);
}
