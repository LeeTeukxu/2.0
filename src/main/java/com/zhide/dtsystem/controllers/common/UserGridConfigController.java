package com.zhide.dtsystem.controllers.common;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.RedisUtils;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.userGridConfig;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.userGridConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Optional;

/**
 * @ClassName: UserGridConfigController
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2020年09月21日 20:46
 **/
@Controller
@RequestMapping("/gridConfig")
public class UserGridConfigController {

    @Autowired
    userGridConfigRepository userConfigRep;
    @Autowired
    RedisUtils redisUtils;

    @RequestMapping("/save")
    @ResponseBody
    public successResult Save(int Share, String Data) {
        successResult result = new successResult();
        try {
            Integer UserID = CompanyContext.get().getUserIdValue();
            userGridConfig getConfig = JSON.parseObject(Data, userGridConfig.class);
            Optional<userGridConfig> findOnes = userConfigRep.findFirstByUserIdAndUrl(UserID, getConfig.getUrl());
            if (findOnes.isPresent()) {
                userGridConfig findOne = findOnes.get();
                findOne.setColumnIndex(getConfig.getColumnIndex());
                findOne.setColumnTitle(getConfig.getColumnTitle());
                findOne.setColumnVisible(getConfig.getColumnVisible());
                findOne.setColumnWidth(getConfig.getColumnWidth());
                findOne.setUpdateTime(new Date());
                findOne.setShare(Share);
                userConfigRep.save(findOne);
            } else {
                getConfig.setUserId(UserID);
                getConfig.setShare(Share);
                userConfigRep.save(getConfig);
            }
            redisUtils.clearAll("findUserGridConfig");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
