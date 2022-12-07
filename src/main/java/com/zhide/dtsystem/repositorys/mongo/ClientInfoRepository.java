package com.zhide.dtsystem.repositorys.mongo;

import com.zhide.dtsystem.models.ClientInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: ClientInfoRepository
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年03月25日 10:33
 **/
@Repository
public interface ClientInfoRepository {
    List<ClientInfo> findAllByOrgCode(String OrgCode);
}
