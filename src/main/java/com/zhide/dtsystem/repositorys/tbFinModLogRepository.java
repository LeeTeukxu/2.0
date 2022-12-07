package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbFinModLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface tbFinModLogRepository extends JpaRepository<tbFinModLog, Integer> {

}
