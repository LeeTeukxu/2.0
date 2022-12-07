package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface tbEmployeeRepository extends JpaRepository<tbEmployee, Integer> {
    List<tbEmployee> findAllByDepId(int depId);

    List<tbEmployee> findAllByEmpName(String empName);
}
