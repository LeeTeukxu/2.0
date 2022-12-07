package com.zhide.dtsystem.controllers.work;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.NicheNoticeConfigMapper;
import com.zhide.dtsystem.models.NiCheNoticeConfig;
import com.zhide.dtsystem.repositorys.nicheNoticeConfigRepository;
import com.zhide.dtsystem.services.define.INicheNoticeConfigService;
import com.zhide.dtsystem.services.define.ITZSPeriodConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/work/nicheNoticeConfig")
public class nicheNoticeConfigController {
    @Autowired
    ITZSPeriodConfigService tzsService;
    @Autowired
    INicheNoticeConfigService nicheNoticeConfigService;
    @Autowired
    nicheNoticeConfigRepository nicheNoticeConfigRep;
    @Autowired
    NicheNoticeConfigMapper nicheNoticeConfigMapper;

    @RequestMapping("/Config")
    public String Config() {
        return "/work/nicheNotice/nicheNoticeConfig";
    }

    @RequestMapping("/getData")
    @ResponseBody
    public pageObject GetData(int pageIndex, int pageSize, String sortField, String sortOrder) {
        pageObject result = new pageObject();
        try {
            org.springframework.data.domain.Pageable pageable = PageRequest
                    .of(pageIndex, pageSize, Sort.by(sortOrder.equals("asc") ? Sort.Direction
                            .ASC : Sort.Direction.DESC, sortField));
            Page<NiCheNoticeConfig> Rows = nicheNoticeConfigRep.findAll(pageable);
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
        String res = "";
        try {
            List<NiCheNoticeConfig> Datas = JSON.parseArray(Data, NiCheNoticeConfig.class);
            List<String> ListTypeName = nicheNoticeConfigMapper.getNiCheNoticeConfigTypeName();
            for (int i = 0; i < Datas.size(); i++) {
                if (ListTypeName.contains(Datas.get(i).getTypeName())) {
                    res = "通知书类型已监控";
                }
            }
            if (res != "") {
                result.setSuccess(false);
                result.setMessage(res);
            } else {
                nicheNoticeConfigService.Save(Datas);
            }
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
            Optional<NiCheNoticeConfig> Find = nicheNoticeConfigRep.findById(ID);
            if (Find.isPresent()) {
                NiCheNoticeConfig config = Find.get();
                nicheNoticeConfigRep.delete(config);
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/getTZSMC")
    @ResponseBody
    public List<Map<String, Object>> getTZSMC() {
        return nicheNoticeConfigService.getTZSMC();
    }
}
