package com.zhide.dtsystem.configs;

import ch.qos.logback.core.net.server.Client;
import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.RegexUtils;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.ClientInfo;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.services.define.IAllUserListService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

public class CustomShiroRealm extends AuthorizingRealm {

    @Autowired
    IAllUserListService allMapper;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    StringRedisTemplate redisRep;
    @Value("${mongoService.url}")
    String serviceUrl;
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String account = (String) token.getPrincipal();
        SimpleAuthenticationInfo authenticationInfo=null;
        if(RegexUtils.IsEmail(account)){
            String Key="KeHuLoginInfo::Cache";
            if(redisRep.opsForHash().hasKey(Key,account)){
                String X=redisRep.opsForHash().get(Key,account).toString();
                ClientInfo fClient=JSON.parseObject(X, ClientInfo.class);
                authenticationInfo=new SimpleAuthenticationInfo(
                        account,
                        fClient.getPassWord(),
                        getName()
                );
            }else throw new AuthenticationException(account+"暂时不能登录系统，请联系业务人员发送系统邀清邮件激活后再尝试登录系统。");
        } else {
            String CompanyID = allMapper.findCompanyIdByAccount(account);
            LoginUserInfo userInfo = allMapper.findUserInfoByAccountAndCompanyID("Client_" + CompanyID, account);
            authenticationInfo=new SimpleAuthenticationInfo(
                    account,
                    userInfo.getPassword(),
                    getName()
            );
        }
        return authenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
