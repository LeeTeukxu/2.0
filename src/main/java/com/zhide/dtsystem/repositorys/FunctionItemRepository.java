package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.FunctionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FunctionItemRepository extends JpaRepository<FunctionItem, Integer> {
    List<FunctionItem> findAllByCanUseOrderBySn(boolean canUse);
}
