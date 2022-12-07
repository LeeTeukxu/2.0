package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.errorInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorInfoRepository extends JpaRepository<errorInfo, Integer> {

}
