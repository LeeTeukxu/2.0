package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbSystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface tbSystemConfigRepository  extends  JpaRepository<tbSystemConfig,Integer>  {

}
