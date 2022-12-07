package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.models.productItemType;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface ProductItemTypeMapper {
    List<Map<String, String>> getProductItemTypeName();

    @Select(value = "SELECT DISTINCT Type FROM ProductItemType")
    List<productItemType> findDistinctByType();
}
