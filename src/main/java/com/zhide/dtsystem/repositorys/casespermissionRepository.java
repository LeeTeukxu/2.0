package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.casespermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface casespermissionRepository  extends  JpaRepository<casespermission,Integer>  {

}
