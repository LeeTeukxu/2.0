package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface tbCompanyRepository  extends  JpaRepository<tbCompany,Integer>  {
    Optional<tbCompany> findFirstByCompanyName(String Name);
}
