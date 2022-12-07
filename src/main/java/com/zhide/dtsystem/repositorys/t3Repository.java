package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.t3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface t3Repository extends JpaRepository<t3, Integer> {
    List<t3> findAllByIdIn(List<Integer> IDS);
}
