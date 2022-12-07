package com.zhide.dtsystem.services.define;

import java.util.List;

/**
 * @ClassName: IChangeRecordService
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年11月23日 13:42
 **/
public interface IChangeRecordService {
    void CreateAndSave(List<String> fields, String ConfigName, IChangeEntity obj);
}
