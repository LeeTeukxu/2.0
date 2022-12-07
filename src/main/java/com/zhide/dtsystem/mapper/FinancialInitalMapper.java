package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.models.FinancialInitial;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface FinancialInitalMapper {
    List<Map<String, Object>> getData(Map<String, Object> arguments);

    Optional<FinancialInitial> getAllByFinancialIntitalID(Integer FinancialInitialID);
}
