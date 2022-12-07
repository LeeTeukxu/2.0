package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.v_PantentInfoMemo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface View_PatentInfoMemoRepository extends JpaRepository<v_PantentInfoMemo,Integer> {
    Page<v_PantentInfoMemo>findAllByShenqingh(String SHENQINGH,Pageable page);
}
