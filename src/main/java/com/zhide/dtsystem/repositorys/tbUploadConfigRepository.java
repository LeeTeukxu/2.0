package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbUploadConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface tbUploadConfigRepository  extends  JpaRepository<tbUploadConfig,Integer>  {

}
