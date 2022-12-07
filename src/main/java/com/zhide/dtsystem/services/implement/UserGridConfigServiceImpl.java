package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.models.userGridConfig;
import com.zhide.dtsystem.repositorys.userGridConfigRepository;
import com.zhide.dtsystem.services.define.IUserGridConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @ClassName: UserGridConfigServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2022年03月29日 15:00
 **/
@Service
public class UserGridConfigServiceImpl implements IUserGridConfigService {
    @Autowired
    userGridConfigRepository gridRep;

    @Override
    @Cacheable(keyGenerator = "CompanyKeyGenerator",value="findUserGridConfig",unless = "#result==null")
    public userGridConfig findOne(Integer UserID, String Url) {
        userGridConfig config=null;
        Optional<userGridConfig> findOne=gridRep.findFirstByUserIdAndUrl(UserID,Url);
        if(findOne.isPresent()) config=findOne.get();
        return config;
    }
}
