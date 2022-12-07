package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.models.JGDateMemo;
import com.zhide.dtsystem.models.v_PantentInfoMemo;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface JGDateMemoMapper {
    List<JGDateMemo> getAllByIds(List<String> ids);
}
