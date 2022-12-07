package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbPermissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface tbPermissionTypeRepository  extends  JpaRepository<tbPermissionType,Integer>  {

}
