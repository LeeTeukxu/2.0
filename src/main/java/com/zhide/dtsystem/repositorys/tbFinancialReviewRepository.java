package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbFinancialReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface tbFinancialReviewRepository  extends  JpaRepository<tbFinancialReview,Integer>  {

}
