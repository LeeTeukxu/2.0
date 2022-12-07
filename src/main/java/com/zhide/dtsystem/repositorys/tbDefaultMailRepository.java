package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbDefaultMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface tbDefaultMailRepository extends  JpaRepository<tbDefaultMail,Integer>  {
    void deleteAllByClientId(Integer ClientId);
    void deleteAllByLinkersIdIn(List<Integer> LinkersId);
}
