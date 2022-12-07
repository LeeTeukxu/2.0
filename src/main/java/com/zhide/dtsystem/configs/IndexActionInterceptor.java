package com.zhide.dtsystem.configs;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.zhide.dtsystem.common.DateTimeUtils;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.userGridConfig;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.tbMenuListRepository;
import com.zhide.dtsystem.repositorys.userGridConfigRepository;
import com.zhide.dtsystem.services.define.IRoleFunctionService;
import com.zhide.dtsystem.services.define.IUserGridConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 对Index访问进行拦截，并将前缀与MenuID做一个缓存处理。
 */
@Component
public class IndexActionInterceptor extends HandlerInterceptorAdapter {
    Logger logger = LoggerFactory.getLogger(IndexActionInterceptor.class);
    @Autowired
    StringRedisTemplate redisRep;
    @Autowired
    menuIdCacheObject menuIdCacheObject;
    @Autowired
    IRoleFunctionService roleFunService;
    @Autowired
    IUserGridConfigService gridRep;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {
        if (handler instanceof HandlerMethod) {
            String Url = request.getRequestURI();
            String menuId = request.getParameter("MenuID");
            if (Url.indexOf("/index") > -1 && !Strings.isNullOrEmpty(menuId)) {
                String Tx = Url.replace("/index", "");
                if (Url.equals("/work/notice/index")) {
                    String type = request.getParameter("Type");
                    Url = Tx + "?Type=" + type;
                } else Url = Tx;
                String menuName = menuIdCacheObject.getNameById(menuId);
                request.getSession(true).setAttribute("CurrentMenu", menuName);
                menuIdCacheObject.add(menuId, Url);
                String Key= DateTimeUtils.formatDate(new Date(),"yyyyMM")+"::MenuClickCount";
                redisRep.opsForZSet().incrementScore(Key,menuName,1);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView
            modelAndView) throws Exception {
        if (handler instanceof HandlerMethod) {
            String pageName = request.getParameter("pageName");
            LoginUserInfo Info = CompanyContext.get();
            if (StringUtils.isEmpty(pageName) == false) {
                modelAndView.addObject("PageName", pageName);
                if (Info != null) {
                    String menuId = request.getParameter("MenuID");
                    String roleId = Info.getRoleId();
                    //String Url = request.getRequestURI();
                    //userGridConfig config= gridRep.findOne(Info.getUserIdValue(), Url);
                    String menuName = menuIdCacheObject.getNameById(menuId);
                    List<String> HasFuns = roleFunService.GetAllRoleFunctions(roleId, menuId);
                    modelAndView.addObject("HasFuns", JSON.toJSONString(HasFuns));
                    List<String> AllFuns = roleFunService.GetAllFunctions(menuId);
                    modelAndView.addObject("AllFuns", JSON.toJSONString(AllFuns));
                    modelAndView.addObject("CurrentRoleName", Info.getRoleName());
                    modelAndView.addObject("CurrentMenu",menuName);
                    //modelAndView.addObject("GridConfig",config==null?"{}":JSON.toJSONString(config));
                }
            }
        }
    }
}
