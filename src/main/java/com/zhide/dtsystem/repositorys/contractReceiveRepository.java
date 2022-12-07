package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.contractReceive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface contractReceiveRepository  extends  JpaRepository<contractReceive,Integer>  {
        List<contractReceive> findAllByContractType(Integer contractType);
        contractReceive findAllByContractNo(String ContractNo);
}
