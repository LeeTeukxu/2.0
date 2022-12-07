package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.casesSubFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface casesSubFilesRepository  extends  JpaRepository<casesSubFiles,Integer>  {
    List<casesSubFiles> getAllBySubIdAndType(String SubID,String Type);
    List<casesSubFiles> getAllBySubIdInAndType(List<String> SubIDS,String Type);
    List<casesSubFiles> getAllBySubId(String SubID);
    List<casesSubFiles> getAllBySubIdIn(String[] SubIDS);
    Optional<casesSubFiles> findFirstByAttId(String AttID);
    int deleteAllByCasesId(String CasesID);
    int deleteAllByCasesIdAndAttId(String CasesID,String AttID);
}
