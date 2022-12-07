package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbCLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface tbCLevelRepository  extends  JpaRepository<tbCLevel,Integer>  {

}
