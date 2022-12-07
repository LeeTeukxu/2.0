package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbRoleClass;
import com.zhide.dtsystem.models.tbTradeCaseFollowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface tbTradeCaseFollowRecordRepository extends JpaRepository<tbTradeCaseFollowRecord, Integer> {
}
