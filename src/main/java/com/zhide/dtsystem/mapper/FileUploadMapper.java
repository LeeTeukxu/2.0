package com.zhide.dtsystem.mapper;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: FileUploadMapper
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年11月16日 16:21
 **/
public interface FileUploadMapper {
    List<Map<String,Object>> getData(Map<String,Object> params);
}
