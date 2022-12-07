package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.v_tbEmployee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface v_tbEmployeeRepository extends JpaRepository<v_tbEmployee,Integer> {
    Page<v_tbEmployee>  findAllByDepIdIn(List<Integer> depId, Pageable pageable);
    Page<v_tbEmployee>  findAllByEmpNameLike(String empName,Pageable pageable);
    Page<v_tbEmployee>  findAllByEmpNameLikeOrLoginCodeLike(String empName,String loginCode,Pageable pageable);
    @Query(value = "SELECT * FROM v_tbEmployee WHERE EmpID=:EmpID",nativeQuery = true)
    v_tbEmployee findAllByEmpID(@Param("EmpID") Integer empId);
}
