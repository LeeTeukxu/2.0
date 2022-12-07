package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.caseHighSubFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface caseHighSubFilesRepository  extends  JpaRepository<caseHighSubFiles,Integer>  {
    List<caseHighSubFiles> getAllBySubIdAndType(String SubID,String Type);
    List<caseHighSubFiles> getAllBySubIdInAndType(List<String> SubIDS,String Type);
    List<caseHighSubFiles> getAllBySubId(String SubID);
    List<caseHighSubFiles> getAllBySubIdIn(String[] SubIDS);
    Optional<caseHighSubFiles> findFirstByAttId(String AttID);
    Optional<caseHighSubFiles> findFirstBySubId(String SubID);
    int deleteAllByCasesIdAndAttId(String CasesID,String AttID);
    int deleteAllByCasesId(String CasesID);
}
