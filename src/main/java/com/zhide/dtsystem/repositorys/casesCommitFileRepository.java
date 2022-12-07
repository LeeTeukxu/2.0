package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.casesCommitFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface casesCommitFileRepository  extends  JpaRepository<casesCommitFile,Integer>  {
    List<casesCommitFile> findAllByCasesId(String CasesID);
    List<casesCommitFile> findAllByCasesIdAndCreateMan(String CasesID,Integer CreateMan);
    Optional<casesCommitFile> findFirstByAttId(String AttID);
}
