package com.zhide.dtsystem.common;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.mapper.AllUserListMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: LoginUserUtils
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年08月05日 16:10
 **/
@Component
public class LoginUserUtils {
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    AllUserListMapper allUserListMapper;
    String Key="";
    private List<LoginUserInfo> getAll(){
        LoginUserInfo Info= CompanyContext.get();
        List<LoginUserInfo> users=new ArrayList<>();
        Key="getAllLoginUserArrays::"+Info.getCompanyId();
        String X=redisTemplate.opsForValue().get(Key);
        if(StringUtils.isEmpty(X)){
            users=allUserListMapper.getAll();
            redisTemplate.opsForValue().set(Key, JSON.toJSONString(users));
        } else {
            users=JSON.parseArray(X,LoginUserInfo.class);
        }
        return users;
    }
    public List<LoginUserInfo>findAllByRoleName(String roleName){
        List<LoginUserInfo> allUsers=getAll();
        return allUsers.stream().filter(f->f.getRoleName().equals(roleName)).collect(Collectors.toList());
    }
    public List<LoginUserInfo>findAllByRoleNames(List<String> roleNames){
        List<LoginUserInfo> allUsers=getAll();
        List<LoginUserInfo> res=new ArrayList<>();
        allUsers.stream().forEach(f->{
            String roleName=f.getRoleName();
            if(roleNames.contains(roleName)){
                res.add(f);
            }
        });
        return res;
    }

}
