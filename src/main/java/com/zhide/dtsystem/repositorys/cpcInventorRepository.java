package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.cpcInventor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface cpcInventorRepository  extends  JpaRepository<cpcInventor,Integer>  {
    int deleteAllByMainId(String MainID);
    List<cpcInventor> findAllByMainId(String MainID);
}
