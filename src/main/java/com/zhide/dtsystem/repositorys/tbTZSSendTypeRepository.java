package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbTZSSendType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface tbTZSSendTypeRepository extends JpaRepository<tbTZSSendType, Integer> {
}
