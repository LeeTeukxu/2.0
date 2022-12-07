package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbOnlineUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface tbOnlineUserRepository  extends  JpaRepository<tbOnlineUser,Integer>  {

}
