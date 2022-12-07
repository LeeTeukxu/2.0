package com.zhide.dtsystem.controllers;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.*;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.tbClientRepository;
import com.zhide.dtsystem.repositorys.tbLoginUserRepository;
import com.zhide.dtsystem.services.connectionParsor;
import com.zhide.dtsystem.services.define.ItbMenuListService;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private final Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    ItbMenuListService itbMenuListService;
    @Autowired
    connectionParsor conParsor;
    @Autowired
    tbLoginUserRepository loginUserRep;
    @Autowired
    tbClientRepository clientRep;
    @Autowired
    RabbitMessageUtils messageUtils;
    @Autowired
    AllMessageCollections allMessage;
    @Autowired
    StringRedisTemplate redisUtils;

    @RequestMapping("/")
    public String FirstPage() {
        return "login";
    }

    @RequestMapping("/index")
    public String Index(Map<String, Object> model, HttpServletRequest request) {
        Long T1 = System.currentTimeMillis();
        Subject user = SecurityUtils.getSubject();
        if (user.isAuthenticated()) {
            List<tbMenuList> allMenus = null;
            LoginUserInfo Info = CompanyContext.get();

            allMenus = itbMenuListService.getVisibleMenus(Integer.parseInt(Info.getRoleId()));
            List<tbMenuList> childs = allMenus.stream().filter(f -> f.getPid() > 0).collect(Collectors.toList());
            for (int i = 0; i < childs.size(); i++) {
                tbMenuList child = childs.get(i);
                String url = child.getUrl();
                String pageName = child.getPageName();
                if (StringUtils.isEmpty(pageName) == false) {
                    if (url.indexOf("?") > -1) {
                        url += "&pageName=" + pageName;
                    } else url += "?pageName=" + pageName;
                }
                child.setUrl(url);
            }
            List<tbMenuList> roots = allMenus.stream()
                    .filter(f -> f.getPid() == 0)
                    .collect(Collectors.toList());
            model.put("roots", roots);
            List<tbMenuList> firstMenus = allMenus.stream()
                    .filter(f -> f.getPid() == roots.get(0).getFid())
                    .collect(Collectors.toList());
            firstMenus.stream()
                    .forEach(f -> {
                        String url = f.getUrl();
                        if (StringUtils.isEmpty(url) == false) {
                            if (url.indexOf("?") == -1) f.setUrl(url + "?MenuID=" + f.getFid());
                            else f.setUrl(url + "&MenuID=" + f.getFid());
                        }
                    });
            model.put("firsts", firstMenus);
            model.put("loginUser", CompanyContext.get());
            model.put("childMenus", JSON.toJSONString(childs));

            List<ClientInfo> clientInfos = null;
            Object OX = request.getSession().getAttribute("Companys");
            if (OX != null) clientInfos = (List<ClientInfo>) OX;
            else clientInfos = new ArrayList<>();
            clientInfos.stream().forEach(f -> {
                f.setPassWord(null);
            });
            model.put("Companys", JSON.toJSONString(clientInfos));
            model.put("Account", Info.getUserName());
            model.put("LoginCode", Info.getAccount());

            Long T2 = System.currentTimeMillis();
            logger.info("加载首页菜单及人员信息数据用时:" + Long.toString(T2 - T1) + "毫秒.");
            return "index";
        } else {
            model.put("roots", new ArrayList<tbMenuList>());
            model.put("firsts", new ArrayList<tbMenuList>());
            return "login";
        }
    }

    @RequestMapping("getAllMenuList")
    @ResponseBody
    public List<tbMenuList> getAllMenuList() {
        List<tbMenuList> menus = null;
        try {
            menus = itbMenuListService.getAllByCanUse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < menus.size(); i++) {
            tbMenuList menu = menus.get(i);
            String menuId = Integer.toString(menu.getFid());

            String Url = menu.getUrl();
            if (Strings.isEmpty(Url)) continue;
            if (Url.indexOf("?") > -1) {
                Url += "&MenuID=" + menuId;
            } else Url += "?MenuID=" + menuId;
            String pageName = menu.getPageName();
            if (StringUtils.isEmpty(pageName) == false) {
                Url += "&PageName=" + pageName;
            }
            menu.setUrl(Url);
        }
        return menus;
    }

    @ResponseBody
    @RequestMapping("/changePassword")
    public successResult changePassword(String data) {
        successResult result = new successResult();
        try {
            LoginUserInfo Info = CompanyContext.get();
            Map<String, Object> OO = (Map<String, Object>) JSON.parseObject(data, HashMap.class);
            String oldPassword = OO.get("oldPassword").toString().trim();
            String newPassword = OO.get("newPassword").toString().trim();
            String confirmPassword = OO.get("confirmPassword").toString().trim();
            if (newPassword.equals(confirmPassword) == false) {
                throw new Exception("新的密码两次输入不一致!");
            }
            String Dword = MD5Util.EnCode(oldPassword);
            if (Dword.equals(Info.getPassword()) == false) {
                throw new Exception("原始密码输入不正确!");
            }
            String Account = Info.getAccount();
            if (RegexUtils.IsEmail(Account) == false) {
                Optional<tbLoginUser> tbUsers = loginUserRep.findById(Info.getUserIdValue());
                if (tbUsers.isPresent()) {
                    tbLoginUser one = tbUsers.get();
                    one.setPassword(MD5Util.EnCode(confirmPassword));
                    loginUserRep.save(one);
                }
            } else {
                Optional<tbClient> tbClients = clientRep.findById(Info.getUserIdValue());
                if (tbClients.isPresent()) {
                    tbClient tb = tbClients.get();
                    tb.setPassword(MD5Util.EnCode(newPassword));
                    tb.settSource(newPassword);
                    clientRep.save(tb);
                }
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
