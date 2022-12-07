package com.zhide.dtsystem.services.instance;

import com.zhide.dtsystem.mapper.CompanyConfigMapper;
import com.zhide.dtsystem.models.companyConfig;
import com.zhide.dtsystem.models.tbLoginUser;
import com.zhide.dtsystem.repositorys.companyConfigRepository;
import com.zhide.dtsystem.repositorys.tbLoginUserRepository;
import com.zhide.dtsystem.services.define.ICompanyConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CompanyConfigServiceImpl implements ICompanyConfigService {
    @Autowired
    companyConfigRepository configRep;
    @Autowired
    tbLoginUserRepository loginRep;
    @Autowired
    CompanyConfigMapper configMapper;

    @Override
    public boolean isExpired(String companyID) throws Exception {
        List<companyConfig> configs = configMapper.getListByCompanyId(companyID);
        if (configs.size() == 0) throw new Exception("公司配置信息缺失，请联系管理员解决!");
        else {
            companyConfig config = configs.get(0);
            if (config.getEndTime().before(new Date())) return true;
            else return false;
        }
    }

    @Override
    public boolean canCreate() throws Exception {
        List<companyConfig> configs = configRep.findAll();
        if (configs.size() == 0) throw new Exception("公司配置信息缺失，请联系管理员解决!");
        else {
            companyConfig config = configs.get(0);
            List<tbLoginUser> users = loginRep.findAll();
            return users.size() < config.getUserNumbers();
        }
    }

    @Override
    public boolean canLogin(String companyID) throws Exception {
        List<companyConfig> configs = configMapper.getListByCompanyId(companyID);
        if (configs.size() == 0) throw new Exception("公司配置信息缺失，请联系管理员解决!");
        else {
            companyConfig config = configs.get(0);
            return config.getCanUse();
        }
    }
}
