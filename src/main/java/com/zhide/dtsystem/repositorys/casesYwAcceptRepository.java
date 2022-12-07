package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.casesYwAccept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface casesYwAcceptRepository  extends  JpaRepository<casesYwAccept,Integer>  {
    List<casesYwAccept> findAllByCasesIdAndYid(String CasesID,String YID);
    List<casesYwAccept> findAllByCasesId(String CasesID);
    List<casesYwAccept> findAllByCasesIdAndTechMan(String CasesID,Integer TechMan);
    List<casesYwAccept> findAllByYid(String yId);
}
