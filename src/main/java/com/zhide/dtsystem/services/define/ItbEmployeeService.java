package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.EmployeeAndUser;
import com.zhide.dtsystem.models.tbEmployee;
import com.zhide.dtsystem.models.tbLoginUser;
import com.zhide.dtsystem.models.v_tbEmployee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItbEmployeeService {
    tbEmployee getById(Integer empId);

    List<tbEmployee> getAllByDepId(Integer depId);

    Page<v_tbEmployee> getPage(Integer depId, Pageable pageable);

    Page<v_tbEmployee> getPage(String empName, Pageable pageable);

    EmployeeAndUser saveAll(tbEmployee employee, tbLoginUser loginUser) throws Exception;

    boolean removeAll(List<Integer> userId) throws Exception;
}
