package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.cpcFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface cpcFilesRepository  extends  JpaRepository<cpcFiles,Integer>  {
    int deleteAllByMainId(String MainID);
    List<cpcFiles> findAllByMainIdOrderByCode(String MainID);
    Optional<cpcFiles> findFirstByAttId(String AttID);
    Optional<cpcFiles> findFirstBySubId(String SubID);
}
