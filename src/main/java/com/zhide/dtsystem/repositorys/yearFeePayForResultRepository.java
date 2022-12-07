package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.allFeePayForResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface yearFeePayForResultRepository  extends  JpaRepository<allFeePayForResult,Integer>  {
        
}
