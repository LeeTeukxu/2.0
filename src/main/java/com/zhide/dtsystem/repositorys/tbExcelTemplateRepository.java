package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbExcelTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface tbExcelTemplateRepository  extends  JpaRepository<tbExcelTemplate,Integer>  {
    Optional<tbExcelTemplate> findFirstByCode(String Code);
}
