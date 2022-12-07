package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.casesOutSourceFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface casesOutSourceFileRepository  extends  JpaRepository<casesOutSourceFile,Integer>  {
    List<casesOutSourceFile> getAllBySubIdInAndType(List<String> SubIDS,String Type);
    List<casesOutSourceFile> getAllBySubIdAndType(String SubID,String Type);
    Optional<casesOutSourceFile> getFirstByAttId(String AttID);
    List<casesOutSourceFile> getAllBySubIdIn(String[] Codes);
}
