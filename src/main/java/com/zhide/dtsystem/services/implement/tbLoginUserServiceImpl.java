package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.common.EntityHelper;
import com.zhide.dtsystem.common.MD5Util;
import com.zhide.dtsystem.mapper.AllUsersListMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.allUsersList;
import com.zhide.dtsystem.models.tbLoginUser;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.tbLoginUserRepository;
import com.zhide.dtsystem.services.define.ICompanyConfigService;
import com.zhide.dtsystem.services.define.ItbLoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class tbLoginUserServiceImpl implements ItbLoginUserService {
    @Autowired
    tbLoginUserRepository loginUserRepository;

    @Autowired
    AllUsersListMapper allUsersListMapper;
    @Autowired
    ICompanyConfigService companyConfigService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public tbLoginUser save(tbLoginUser user) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        MD5Util md5Util = new MD5Util(Info.getCompanyId());
        tbLoginUser existUser = null;
        boolean addNew = false;
        if (user.getUserId() == null) {
            if (companyConfigService.canCreate() == false) {
                throw new Exception("已超过了最大允许创建用户数量!");
            }
            user.settSource(user.getPassword());
            user.setPassword(md5Util.EnCode(user.getPassword()));
            user.setCreateTime(new Date());
            existUser = loginUserRepository.findAllByLoginCode(user.getLoginCode());
            int Num = allUsersListMapper.getNumByAccount(user.getLoginCode());
            if (Num > 0) {
                throw new Exception(user.getLoginCode() + "已被使用，请改用其它标识。");
            }
            addNew = true;
        } else {
            Optional<tbLoginUser> ftbLoginUser = loginUserRepository.findById(user.getUserId());
            if (ftbLoginUser.isPresent()) {
                tbLoginUser nowOne = ftbLoginUser.get();
                if (nowOne.getPassword().equals(user.getPassword()) == false) {
                    user.settSource(user.getPassword());
                    user.setPassword(md5Util.EnCode(user.getPassword()));
                }
                user.setLastLoginTime(new Date());
                EntityHelper.copyObject(user, nowOne);
                if (user.getLoginCode().equals(nowOne.getLoginCode()) == false) {
                    //当前用户变更了登录帐号。要去allUsersList表去把老的删除了。
                    allUsersListMapper.deleteLoginUser(user.getLoginCode());
                }
            }
            existUser = loginUserRepository.findAllByLoginCodeAndUserIdIsNot(user.getLoginCode(), user.getUserId());
        }
        if (existUser != null) throw new Exception(existUser.getLoginCode() + "已存在，不能重复使用。");
        tbLoginUser result = loginUserRepository.save(user);
        if (addNew == true) {
            if (result != null) {
                allUsersList add = new allUsersList();
                add.setAccount(user.getLoginCode());
                add.setCompanyId(Info.getCompanyId());
                add.setCreateTime(new Date());
                allUsersListMapper.addLoginUserInfo(add);
            } else throw new Exception(user.getLoginCode() + "保存失败。");
        }
        return result;
    }

    @Override
    public tbLoginUser getById(int userId) {
        return loginUserRepository.findById(userId).get();
    }

    @Cacheable(value = "getAllLoginUser", keyGenerator = "CompanyKeyGenerator")
    public List<tbLoginUser> getAll() {
        return loginUserRepository.findAll();
    }
}
