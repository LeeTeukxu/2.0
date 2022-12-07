package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbPermissionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface tbPermissionItemRepository  extends  JpaRepository<tbPermissionItem,Integer>  {

}
