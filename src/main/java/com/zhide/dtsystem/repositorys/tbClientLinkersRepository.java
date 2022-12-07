package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbClientLinkers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface tbClientLinkersRepository  extends  JpaRepository<tbClientLinkers,Integer>  {
    Page<tbClientLinkers> findAllByClientIDOrderByPosition(int ClientID,Pageable pageable);
    List<tbClientLinkers> findAllByClientID(int ClientID);
    List<tbClientLinkers> findAllByClientIDAndPosition(int ClientID,int Position);
    Optional<tbClientLinkers> findFirstByClientIDAndLinkPhone(Integer ClientID,String LinkPhone);
    Optional<tbClientLinkers> findFirstByClientIDAndMobile(Integer ClientID,String Mobile);

}
