package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.attachmentMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface attachmentMainRepository  extends  JpaRepository<attachmentMain,Integer>  {

}
