package com.zhide.dtsystem.controllers.systems;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.viewModel.ExceptionMessage;
import com.zhide.dtsystem.viewModel.ProcessInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: AdminController
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年11月03日 16:49
 **/
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Value("${mongoService.url}")
    String serviceUrl;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    StringRedisTemplate redisRep;

    @RequestMapping("/index")
    public String Index() {
        return "/adminPages";
    }

    @ResponseBody
    @RequestMapping("/getException")
    public pageObject getException(HttpServletRequest request) {
        pageObject obj = new pageObject();
        try {
            String pageIndex = request.getParameter("pageIndex");
            String pageSize = request.getParameter("pageSize");
            String sortField = request.getParameter("sortField");
            String sortOrder = request.getParameter("sortOrder");
            String url = String.format(serviceUrl + "/exception/getAll?pageIndex=%s&pageSize=%s&sortField=%s" +
                    "&sortOrder=%s", pageIndex, pageSize, sortField, sortOrder);
            obj = restTemplate.getForObject(URI.create(url), pageObject.class);
            String VV = JSON.toJSONString(obj.getData());
            List<ExceptionMessage> Ms = JSON.parseArray(VV, ExceptionMessage.class);
            obj.setData(Ms);
        } catch (Exception ax) {
            obj.raiseException(ax);
        }
        return obj;
    }

    @ResponseBody
    @RequestMapping("/getUsedTime")
    public pageObject getUsedTime(HttpServletRequest request) {
        pageObject obj = new pageObject();
        try {
            String pageIndex = request.getParameter("pageIndex");
            String pageSize = request.getParameter("pageSize");
            String sortField = request.getParameter("sortField");
            String sortOrder = request.getParameter("sortOrder");
            String url = String.format(serviceUrl + "/usedTime/getAll?pageIndex=%s&pageSize=%s&sortField=%s&sortOrder" +
                            "=%s",
                    pageIndex, pageSize, sortField, sortOrder);
            obj = restTemplate.getForObject(URI.create(url), pageObject.class);
            String VV = JSON.toJSONString(obj.getData());
            List<ProcessInfo> Ms = JSON.parseArray(VV, ProcessInfo.class);
            // Ms.stream().forEach(f->f.setCurTime(DateUtils.addHours(f.getCurTime(),8)));
            obj.setData(Ms);
        } catch (Exception ax) {
            obj.raiseException(ax);
        }
        return obj;
    }

    @ResponseBody
    @RequestMapping("/getOnlineUsers")
    public List<Map<String, Object>> getOnlineUsers() {
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            Set<String> Keys = redisRep.keys("OnlineUser*");
            for (String Key : Keys) {
                Map<String, Object> OO = new HashMap<>();
                String Value = redisRep.opsForValue().get(Key);
                String[] KK = Key.split("::");
                OO.put("Company", KK[1]);
                OO.put("Name", KK[2]);
                OO.put("LoginTime", Value);
                OO.put("Expire", redisRep.getExpire(Key, TimeUnit.SECONDS));
                result.add(OO);
            }
        } catch (Exception ax) {
            ax.printStackTrace();
        }
        return result;
    }
}
