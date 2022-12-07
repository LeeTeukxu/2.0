package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbGovFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface tbGovFeeRepository  extends  JpaRepository<tbGovFee,Integer>  {
    List<tbGovFee> findAllByAid(int AID);
    int deleteAllByAppNo(String AppNo);
    int deleteAllByAppNoIn(List<String> AppNos);

}
