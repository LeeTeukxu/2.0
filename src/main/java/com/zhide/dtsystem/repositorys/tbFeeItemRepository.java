package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbFeeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface tbFeeItemRepository extends JpaRepository<tbFeeItem, Integer> {
    List<tbFeeItem> findAllByShenqingh(String shenqingh);
}
