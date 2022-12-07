package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface tbProductFilesRepository  extends  JpaRepository<tbProductFiles,Integer>  {
        List<tbProductFiles> findAllByMainIdAndType(String MainID,String Type);
        List<tbProductFiles> findAllByMainIdIn(List<String>IDS);
}
