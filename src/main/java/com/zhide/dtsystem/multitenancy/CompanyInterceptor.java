package com.zhide.dtsystem.multitenancy;

import com.google.common.base.Strings;
import com.zhide.dtsystem.common.CompanyTokenUtils;
import com.zhide.dtsystem.models.LoginUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Component
public class CompanyInterceptor extends HandlerInterceptorAdapter {

    List<String> urls= Arrays.asList("/login","/error","/WebAPI","/CPCUpload","/doLogin","/WebAPI/Login",
            "/WebAPI/IsLegalLogin","/resetPassword/index","/resetPassword/apply");
    Logger logger= LoggerFactory.getLogger(CompanyInterceptor.class);

    @Value("${publish.version}")
    String version;
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView
            modelAndView) throws Exception {
        CompanyContext.clear();
        if(version!=null && modelAndView!=null) {
            modelAndView.addObject("version", version);
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {
        String VX=request.getRequestURI();

        if( VX.startsWith("/js") ||
                VX.startsWith("/images") ||
                VX.startsWith("/res") ||
                VX.startsWith("/css") ||
                VX.startsWith("/appImages")
        ) return true;
        //logger.info("URL:"+VX);
        HttpSession session=request.getSession();
        boolean hasLogin=false;
        if(session!=null){
            Object OO=session.getAttribute("loginUser");
            if(OO!=null) {
                LoginUserInfo Info=(LoginUserInfo)OO;
                if(Info!=null){
                    CompanyContext.set(Info);
                    logger.info("[{}]:[{}]->URL:[{}]",Info.getCompanyId(),Info.getUserName(),VX);
                    hasLogin=true;
                }
            } else {
                String token=request.getParameter("webToken");
                if(StringUtils.isEmpty(token)==false){
                    String companyId= CompanyTokenUtils.getCompanyIdByToken(token);
                    if(Strings.isNullOrEmpty(companyId)==false) {
                        LoginUserInfo info = new LoginUserInfo();
                        info.setCompanyId(companyId);
                        CompanyContext.set(info);
                        hasLogin = true;
                        logger.info("{}({}):[{}]",companyId,token,VX);
                        //logger.info(VX + " with token:" + token);
                    }
                }
            }
        }
        if(hasLogin==false){
            String VV= request.getRequestURI();
            if(urls.contains(VV)==false) {
                logger.info(VV+"被访问时Session已过期!");
                response.sendRedirect("/login");
                return false;
            }
        }
        return true;
    }
}
