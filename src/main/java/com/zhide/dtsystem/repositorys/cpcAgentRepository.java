package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.cpcAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface cpcAgentRepository  extends  JpaRepository<cpcAgent,Integer>  {
    int deleteAllByMainId(String MainID);
    List<cpcAgent> findAllByMainId(String MainID);
}
