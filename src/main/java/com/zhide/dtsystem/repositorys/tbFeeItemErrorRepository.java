package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbFeeItemError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface tbFeeItemErrorRepository  extends  JpaRepository<tbFeeItemError,Integer>  {

}
