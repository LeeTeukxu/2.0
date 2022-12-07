package com.zhide.dtsystem.controllers;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.autoTask.ClientCacheBuildTask;
import com.zhide.dtsystem.common.MD5Util;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.AllUserListMapper;
import com.zhide.dtsystem.mapper.ClientInfoMapper;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.ClientInfo;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.tbClient;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.tbClientRepository;
import com.zhide.dtsystem.services.define.IAllUserListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/resetPassword")
public class ResetPasswordController {
    @Autowired
    IAllUserListService allMapper;
    @Autowired
    SysLoginUserMapper loginMapper;
    @Autowired
    tbClientRepository clientRepository;
    Logger logger = LoggerFactory.getLogger(ResetPasswordController.class);

    @Autowired
    RestTemplate restTemplate;

    @Value("${mongoService.url}")
    String serviceUrl;

    @Autowired
    tbClientRepository clientRep;
    @Autowired
    StringRedisTemplate redisUtils;
    @Autowired
    ClientCacheBuildTask task;


    @Autowired
    AllUserListMapper userMapper;
    @Autowired
    ClientInfoMapper clientMapper;
    @Autowired
    StringRedisTemplate redisRep;

    @RequestMapping(value = "/index")
    public String ResetPasswordPage(Integer ClientID, String OrgCode, String Key, Map<String, Object> model) {
        try {
            CheckOne(Key,ClientID,OrgCode);
            model.put("Key",Key);
            model.put("ClientID", Integer.toString(ClientID));
            model.put("OrgCode", OrgCode);
            model.put("Error","");
        } catch (Exception ax) {
            model.put("Error",ax.getMessage());
            model.put("Key","");
            model.put("ClientID","");
            model.put("OrgCode","");
        }
        return "resetPassword";
    }

    private void CheckOne(String Key,Integer ClientID,String OrgCode) throws Exception{
        if(StringUtils.isEmpty(Key))throw new Exception("非法Url，无法进行登录密码重置!");
        String OV = redisUtils.opsForValue().get(Key);
        if(StringUtils.isEmpty(OV)) throw new Exception("当前链接已超过24小时限制或已完成了密码重置工作，请重新发送重置密码邮件！");
        HashMap M=JSON.parseObject(OV,HashMap.class);
        if(M.containsKey("ClientID")==false || M.containsKey("OrgCode")==false){
            throw new Exception("请不要盗用链接进行密码重置工作!");
        }
        int XClientID=Integer.parseInt(M.get("ClientID").toString());
        String XOrgCode=M.get("OrgCode").toString();
        if(XClientID!=ClientID || XOrgCode.equals(OrgCode)==false){
            throw new Exception("请不要盗用链接进行密码重置工作!");
        }

    }
    @RequestMapping("/apply")
    @ResponseBody
    public successResult reset(String Key,Integer ClientID, String OrgCode, String password) {
        successResult result = new successResult();
        try {
            CheckOne(Key,ClientID,OrgCode);
            successResult ssvr = restTemplate.getForObject(URI.create(serviceUrl + "/client/login?userName=" + OrgCode),
                    successResult.class);
            if (ssvr.isSuccess()) {
                String X = JSON.toJSONString(ssvr.getData());
                List<ClientInfo> clients = JSON.parseArray(X, ClientInfo.class);
                if (clients.size() == 0) throw new Exception(OrgCode + "的帐号在系统中不存在!");
                List<ClientInfo> fs =
                        clients.stream().filter(f -> f.getClientID() == ClientID).collect(Collectors.toList());
                if (fs.size() == 0) {
                    throw new Exception("传递数据错误，请重新发送邮件!");
                }
                for (int i = 0; i < clients.size(); i++) {
                    ClientInfo Info = clients.get(i);
                    Integer XID = Info.getClientID();
                    LoginUserInfo info = new LoginUserInfo();
                    info.setUserName("aa");
                    info.setUserId("aa");
                    info.setCompanyId(Info.getCompanyID());
                    info.setCompanyName(Info.getCompanyName());
                    CompanyContext.set(info);

                    tbClient findOne = clientRep.findAllByClientID(XID);
                    if (findOne != null) {
                        findOne.setPassword(MD5Util.EnCode(password.trim()));
                        findOne.settSource(password.trim());
                        findOne.setCanUse(true);
                        findOne.setCanLogin(true);
                        clientRep.save(findOne);
                        logger.info(findOne.getName() + "已重置了登录密码!");


                        String SKey = "KeHuLoginInfo::Cache";
                        Map<String,String> caches = new HashMap<>();
                        List<tbClient> Clients = clientMapper.getCanLoginClient();
                        Clients.forEach(f -> {
                            String orgCode = f.getOrgCode();
                            if (caches.containsKey(orgCode) == false) {
                                ClientInfo cf = new ClientInfo();
                                cf.setClientID(f.getClientID());
                                cf.setName(f.getName());
                                cf.setPassWord(f.getPassword());
                                cf.setCompanyID(Info.getCompanyID());
                                cf.setType(Integer.toString(f.getType()));
                                cf.setOrgCode(f.getOrgCode());
                                caches.put(orgCode, JSON.toJSONString(cf));
                            }
                        });
                        if(caches.size()>0){
                            redisRep.delete(SKey);
                            redisRep.opsForHash().putAll(SKey,caches);
                        }
                    } else throw new Exception(XID + "指向的客户不存在!");
                    CompanyContext.set(null);
                }
                redisUtils.delete(Key);
                logger.info("已删除了重置密码的Key"+Key);
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
