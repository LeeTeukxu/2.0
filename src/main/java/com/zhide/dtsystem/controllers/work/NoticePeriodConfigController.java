package com.zhide.dtsystem.controllers.work;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.tzsPeriodConfig;
import com.zhide.dtsystem.repositorys.tzsPeriodConfigRepository;
import com.zhide.dtsystem.services.define.ITZSPeriodConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/work/noticePeriodConfig")
public class NoticePeriodConfigController {
    @Autowired
    ITZSPeriodConfigService tzsService;
    @Autowired
    tzsPeriodConfigRepository tzsPeriodRep;

    @RequestMapping("/index")
    public String Index() {
        return "/work/notice/noticePeriodConfig";
    }

    @RequestMapping("/getData")
    @ResponseBody
    public pageObject GetData(int pageIndex, int pageSize, String sortField, String sortOrder) {
        pageObject result = new pageObject();
        try {
            org.springframework.data.domain.Pageable pageable = PageRequest
                    .of(pageIndex, pageSize, Sort.by(sortOrder.equals("asc") ? Sort.Direction
                            .ASC : Sort.Direction.DESC, sortField));
            Page<tzsPeriodConfig> Rows = tzsPeriodRep.findAll(pageable);
            result.setData(Rows.getContent());
            result.setTotal(Rows.getTotalElements());
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/save")
    public successResult SaveAll(String Data) {
        successResult result = new successResult();
        try {
            List<tzsPeriodConfig> Datas = JSON.parseArray(Data, tzsPeriodConfig.class);
            tzsService.Save(Datas);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/remove")
    public successResult Remove(Integer ID) {
        successResult result = new successResult();
        try {
            Optional<tzsPeriodConfig> Find = tzsPeriodRep.findById(ID);
            if (Find.isPresent()) {
                tzsPeriodConfig config = Find.get();
                tzsPeriodRep.delete(config);
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
