package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.feeMemo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface feeMemoRepository extends JpaRepository<feeMemo, Integer> {
    List<feeMemo> getAllByFeeIdAndType(int ID, String Type);
}
