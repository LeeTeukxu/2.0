package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.managerPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface managerPermissionRepository  extends  JpaRepository<managerPermission,Integer>  {
    List<managerPermission> findAllByUserId(Integer UserID);
    int deleteAllByUserId(Integer UserID);
}
