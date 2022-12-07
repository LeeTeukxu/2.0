package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.casesOutSourceMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface casesOutSourceMainRepository  extends  JpaRepository<casesOutSourceMain,Integer>  {
    Optional<casesOutSourceMain> findFirstBySubId(String SubID);
    List<casesOutSourceMain> findAllBySubIdInAndProcessTextIsNotNull(List<String>SubIDS);
    int deleteAllBySubId(String SubID);
    Optional<casesOutSourceMain> findFirstByProcessTextLike(String Pre);
    Optional<casesOutSourceMain> findFirstByOutId(String OutID);
}
