package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbRoleClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface tbRoleClassRepository extends JpaRepository<tbRoleClass, Integer> {
    List<tbRoleClass> findAllByCanUseTrue();
}
