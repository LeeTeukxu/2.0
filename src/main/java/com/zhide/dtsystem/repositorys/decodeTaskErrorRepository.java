package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.decodeTaskError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface decodeTaskErrorRepository  extends  JpaRepository<decodeTaskError,Integer>  {
    List<decodeTaskError> findAllByShenqingh(String shenqingh);
    Integer countAllByCanUseIsFalse();
    Page<decodeTaskError> findAllByCanUseIsFalse(Pageable pageable);
}
