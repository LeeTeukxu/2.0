package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.yearFeeBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface yearFeeBaseRepository extends JpaRepository<yearFeeBase, Integer> {
    List<yearFeeBase> findAllByType(int type);
}
