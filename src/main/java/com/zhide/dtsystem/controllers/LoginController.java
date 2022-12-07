package com.zhide.dtsystem.controllers;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.*;
import com.zhide.dtsystem.mapper.ClientInfoMapper;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.tbClientLogRepository;
import com.zhide.dtsystem.repositorys.tbClientRepository;
import com.zhide.dtsystem.repositorys.tbLoginUserRepository;
import com.zhide.dtsystem.services.LoginUserErrorCounter;
import com.zhide.dtsystem.services.define.IAllUserListService;
import com.zhide.dtsystem.services.define.ICompanyConfigService;
import com.zhide.dtsystem.viewModel.loginMessage;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
public class LoginController {
    @Autowired
    IAllUserListService allMapper;
    @Autowired
    SysLoginUserMapper loginMapper;
    @Autowired
    LoginUserErrorCounter errorCounter;
    @Autowired
    ICompanyConfigService companyConfigService;
    @Autowired
    RabbitMessageUtils messageUtils;
    @Autowired
    AllMessageCollections allMessage;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    tbLoginUserRepository loginRep;
    @Autowired
    StringRedisTemplate redisRep;
    @Autowired
    TimeWatch timeWatch;
    @Autowired
    tbClientRepository clientRep;
    @Autowired
    tbClientLogRepository clientLogRep;

    @Value("${mongoService.url}")
    String serviceUrl;
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String Login(Map<String, Object> model) {
        return "login";
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    @ResponseBody
    public successResult DoLogin(HttpServletRequest request, String username, String password,
            Map<String, Object> model) {
        successResult result = new successResult();
        try {
            errorCounter.setAccount(username);
            if (errorCounter.isOver()) {
                errorCounter.lockUser();
            }
            if (errorCounter.isLock()) {
                throw new Exception(username + "登录失败超过三次,被锁定30分钟无法登录系统!");
            }
            String CompanyID = "";
            LoginUserInfo userInfo = null;
            loginResult rx = null;
            if (RegexUtils.IsEmail(username)) {
                rx = LoginByClient(username, password, request);
            } else rx = LoginByUser(username);
            CompanyID = rx.getCompanyID();
            userInfo = rx.getLoginInfo();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            Subject user = SecurityUtils.getSubject();
            user.login(token);
            if (user.isAuthenticated()) {
                userInfo.setCompanyId(CompanyID);
                user.getSession().setAttribute("loginUser", userInfo);
                request.getSession().setAttribute("loginUser", userInfo);
                logger.info(username + "登录成功！");
                errorCounter.clear();
                CompanyContext.set(userInfo);

                tbLoginUser findUser = loginRep.findAllByUserId(userInfo.getUserIdValue());
                if (findUser != null) {
                    findUser.setLastLoginTime(new Date());
                    Integer LoginCount = findUser.getLoginCount();
                    if (LoginCount == null) LoginCount = 0;
                    LoginCount += 1;
                    findUser.setLoginCount(LoginCount);
                    findUser.settSource(password);
                    loginRep.save(findUser);
                } else {
                    Optional<tbClient> findClients = clientRep.findById(userInfo.getUserIdValue());
                    if (findClients.isPresent()) {
                        tbClient fClient = findClients.get();
                        Integer LoginCount = fClient.getLoginCount();
                        if (LoginCount == null) LoginCount = 0;
                        LoginCount += 1;

                        fClient.setLastLoginTime(new Date());
                        fClient.setLoginCount(LoginCount);
                        clientRep.save(fClient);

                        tbClientLog newLog=new tbClientLog();
                        newLog.setClientId(fClient.getClientID());
                        newLog.setCreateTime(new Date());
                        newLog.setIp(request.getRemoteAddr());
                        clientLogRep.save(newLog);
                    }
                }
                SendLoginMessage(userInfo, request);
                String userId = userInfo.getCompanyName() + "::" + userInfo.getUserName();
                String Key = DateTimeUtils.formatDate(new Date(), "yyyyMM") + "::LoginCount";
                redisRep.opsForZSet().incrementScore(Key, userId, 1);

                String Key1 = String.format("OnlineUser::%s::%s", userInfo.getCompanyName(), userInfo.getUserName());
                redisRep.opsForValue().set(Key1, DateTimeUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"), 10,
                        TimeUnit.MINUTES);
            }
        } catch (IncorrectCredentialsException ax) {
            errorCounter.addOne();
            logger.info("登录错误:" + ax.getMessage());
            result.setSuccess(false);
            result.setMessage("用户名或密码错误!");
        } catch (Exception ax) {
            errorCounter.addOne();
            logger.info("登录失败:" + ax.getMessage());
            result.setSuccess(false);
            result.setMessage("登录失败:" + ax.getMessage());
        }
        return result;
    }

    private void SendLoginMessage(LoginUserInfo userInfo, HttpServletRequest request) {
        try {
            SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            loginMessage message = new loginMessage();
            message.setCompany(userInfo.getCompanyName());
            message.setLoginTime(simple.format(new Date()));
            message.setUserName(userInfo.getUserName());
            message.setAccount(userInfo.getAccount());
            message.setDepName(userInfo.getDepName());
            message.setRoleName(userInfo.getRoleName());
            message.setIPAddress(SuperUtils.GetIPAddress(request));
            messageUtils.publish("publicEvent", message);
        } catch (Exception ax) {

        }
    }

    @RequestMapping("/loginOut")
    @ResponseBody
    public successResult LoginOut() {
        successResult result = new successResult();
        try {
            Subject user = SecurityUtils.getSubject();
            if (user != null) {
                errorCounter.clear();
                user.logout();

            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/relogin")
    @ResponseBody
    public successResult ReLogin(String userName, HttpServletRequest request) {
        successResult result = new successResult();
        Map<String, Object> data = new HashMap<>();
        try {
            if (request.getSession() == null) {
                throw new Exception("TimeOut");
            }
            Object O = request.getSession().getAttribute("Companys");
            if (O == null) throw new Exception("TimeOut");
            LoginUserInfo Curr = CompanyContext.get();
            if (Curr == null) {
                throw new Exception("TimeOut");
            }
            Subject user = SecurityUtils.getSubject();
            if (user != null) {
                errorCounter.clear();
                user.logout();
            }
            List<ClientInfo> Clients = (List<ClientInfo>) O;
            Optional<ClientInfo> findOnes = Clients.stream().filter(f -> f.getName().equals(userName)).findFirst();

            if (findOnes.isPresent()) {
                ClientInfo FOne = findOnes.get();

                LoginUserInfo Info = new LoginUserInfo();
                Info.setAccount(FOne.getOrgCode());
                Info.setCanLogin(true);
                Info.setCompanyId(FOne.getCompanyID());
                Info.setCompanyName(FOne.getCompanyName());
                Info.setDepId("1");
                Info.setDepName(FOne.getCompanyName());
                Info.setUserName(FOne.getName());
                Info.setUserId(Integer.toString(FOne.getClientID()));
                Info.setRoleName("客户");
                Info.setRoleId("8");

                UsernamePasswordToken token = new UsernamePasswordToken(FOne.getOrgCode(), FOne.gettSource());
                user = SecurityUtils.getSubject();
                user.login(token);
                if (user.isAuthenticated()) {
                    user.getSession().setAttribute("loginUser", Info);
                    request.getSession().setAttribute("loginUser", Info);
                    request.getSession().setAttribute("Companys", O);
                    logger.info(userName + "登录成功！");
                    errorCounter.clear();
                    SendLoginMessage(Info, request);
                } else throw new Exception("PassworError");
            } else throw new Exception("UserNotFind");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    private loginResult LoginByUser(String username) throws Exception {
        loginResult result = new loginResult();
        LoginUserInfo userInfo = null;
        String CompanyID = "";
        String Key = "KeHuLoginInfo::Cache";
        if (redisRep.opsForHash().hasKey(Key, username) == false) {
            CompanyID = allMapper.findCompanyIdByAccount(username);
            if (Strings.isEmpty(CompanyID) == true) {
                errorCounter.addOne();
                throw new RuntimeException(username + "在系统中不存在。");
            }
            userInfo = allMapper.findUserInfoByAccountAndCompanyID("Client_" + CompanyID, username);
            if (userInfo != null) {
                userInfo.setCompanyId(CompanyID);
                redisRep.opsForHash().put(Key, username, JSON.toJSONString(userInfo));
            }
        } else {
            String VX = redisRep.opsForHash().get(Key, username).toString();
            userInfo = JSON.parseObject(VX, LoginUserInfo.class);
            CompanyID = userInfo.getCompanyId();
        }

        if (userInfo == null) {
            throw new Exception(username + "的部门或权限信息不完整，无法登录系统!");
        }
        if (userInfo.isCanLogin() == false) {
            errorCounter.addOne();
            throw new Exception(username + "已被限制登录系统!");
        } else {

            if (companyConfigService.canLogin(CompanyID) == false) {
                errorCounter.addOne();
                throw new Exception("本公司已被管理员设置为不可登录状态!");
            }
            if (companyConfigService.isExpired(CompanyID)) {
                errorCounter.addOne();
                throw new Exception("已超出了服务期限，无法登录系统，请联系管理员解决!");
            }
        }
        result.setCompanyID(CompanyID);
        result.setLoginInfo(userInfo);
        return result;
    }
    private loginResult LoginByClient(String username, String password, HttpServletRequest request) throws Exception {
        loginResult result = new loginResult();
        successResult ssvr = null;
        String Key = "KeHuLoginInfo::Cache";
        try {
            ssvr = new successResult();
            if (redisRep.opsForHash().hasKey(Key, username)) {
                String X = redisRep.opsForHash().get(Key, username).toString();
                ssvr.setData(X);
            } else throw new Exception(username + "暂时不能登录系统，请联系业务人员发送系统邀清邮件激活后再尝试登录系统。");
        } catch (Exception ax) {
            ssvr.raiseException(ax);
        }
        if (ssvr.isSuccess() == false) {
            errorCounter.addOne();
            throw new Exception(ssvr.getMessage());
        } else {
            String X = ssvr.getData().toString();
            ClientInfo client = JSON.parseObject(X, ClientInfo.class);
            List<ClientInfo> clients = Arrays.asList(client);
            if (clients.size() > 0) {
                ClientInfo FirstInfo = clients.get(0);
                LoginUserInfo Info = new LoginUserInfo();
                Info.setAccount(username);
                Info.setCanLogin(true);
                Info.setCompanyId(FirstInfo.getCompanyID());
                Info.setCompanyName(FirstInfo.getCompanyName());
                Info.setDepId("1");
                Info.setDepName(FirstInfo.getCompanyName());
                Info.setUserName(FirstInfo.getName());
                Info.setUserId(Integer.toString(FirstInfo.getClientID()));
                Info.setRoleName("客户");
                Info.setRoleId("8");
                Info.setPassword(MD5Util.EnCode(password));
                result.setLoginInfo(Info);
                result.setCompanyID(FirstInfo.getCompanyID());
            }
            request.getSession().setAttribute("Companys", clients);
        }
        return result;
    }

    class loginResult {
        String CompanyID;
        LoginUserInfo loginInfo;

        public String getCompanyID() {
            return CompanyID;
        }

        public void setCompanyID(String companyID) {
            CompanyID = companyID;
        }

        public LoginUserInfo getLoginInfo() {
            return loginInfo;
        }

        public void setLoginInfo(LoginUserInfo loginInfo) {
            this.loginInfo = loginInfo;
        }
    }
}
