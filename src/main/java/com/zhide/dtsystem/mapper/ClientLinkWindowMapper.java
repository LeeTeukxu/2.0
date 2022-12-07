package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.models.tbClientLinkers;

import java.util.List;
import java.util.Map;

public interface ClientLinkWindowMapper {
    List<Map<String, Object>> getDataWindow(Map<String, Object> params);

    int addImportClientLink(tbClientLinkers clientLinkers);
}
