package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.allFeePayForResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface allFeePayForResultRepository  extends  JpaRepository<allFeePayForResult,Integer>  {
    Optional<allFeePayForResult> findFirstByTypeAndShenQinghLike (String Type,String shenqingh);
    List<allFeePayForResult> findAllByFeeItemIdLikeAndPayStateEquals(String  FeeItemID,Integer PayState);
}
