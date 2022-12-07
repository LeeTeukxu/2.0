package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.models.v_PantentInfoMemo;

import java.util.List;

public interface FeeItemMemoMapper {
    List<v_PantentInfoMemo> getAllByIds(String Type, List<String> IDS);
    List<v_PantentInfoMemo> getAllBySHENQINGHs(String Type, List<String> IDS);
}
