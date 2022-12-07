package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbAgents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface tbAgentsRepository  extends  JpaRepository<tbAgents,Integer>  {
    List<tbAgents> findAllByCompanyName(String CompanyName);
}
