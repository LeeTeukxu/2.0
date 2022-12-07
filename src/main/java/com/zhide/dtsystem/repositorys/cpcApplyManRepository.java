package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.cpcApplyMan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface cpcApplyManRepository  extends  JpaRepository<cpcApplyMan,Integer>  {
    int deleteAllByMainId(String MainID);
    List<cpcApplyMan> findAllByMainId(String MainID);
    Optional<cpcApplyMan> findFirstBySubId(String SubID);
}
