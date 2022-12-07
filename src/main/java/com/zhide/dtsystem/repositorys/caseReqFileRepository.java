package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.caseReqFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface caseReqFileRepository extends JpaRepository<caseReqFile, Integer> {

}
