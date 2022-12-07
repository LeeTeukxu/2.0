package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.PantentInfoMemo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface pantentInfoMemoRepository  extends  JpaRepository<PantentInfoMemo,String>  {
    Optional<PantentInfoMemo> findFirstByMid(String MID);
}
