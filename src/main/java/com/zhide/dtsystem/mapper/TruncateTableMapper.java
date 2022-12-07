package com.zhide.dtsystem.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @ClassName: TruncateTableMapper
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年12月10日 15:12
 **/
@Mapper
public interface TruncateTableMapper {
    @Select(value = "Truncate Table  NotExistTZS")
    void clearNotExistT();

    @Select(value = "Truncate table NotExistCPC")
    void clearNotExistCPC();
}
