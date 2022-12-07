package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.shenqingxx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface shenqingxxRepository extends JpaRepository<shenqingxx, String> {
    Optional<shenqingxx> findFirstByShenqingbh(String shenqingbh);
}
