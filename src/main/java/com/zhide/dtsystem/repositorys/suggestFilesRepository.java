package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface suggestFilesRepository  extends  JpaRepository<suggestFiles,Integer>  {
    Optional<suggestFiles> findFirstByAttId(String AttID);
    List<suggestFiles> findAllByMainId(Integer MainID);
    int deleteAllByMainId(Integer MainID);
}
