package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.models.v_PantentInfoMemo;

import java.util.List;

public interface PantentInfoMemoMapper {
    List<v_PantentInfoMemo> getAllByIds(List<String> IDS);
}
