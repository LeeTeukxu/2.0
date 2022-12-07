package com.zhide.dtsystem.controllers.work;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.mapper.FinanceReportMapper;
import com.zhide.dtsystem.mapper.SellReportMapper;
import com.zhide.dtsystem.models.FinanReportQuery;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SellReportController
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2022年10月10日 10:33
 **/
@Controller
@RequestMapping("/sellReport")
public class SellReportController {
    @Autowired
    SellReportMapper sellMapper;


    @RequestMapping("/index")
    public String Index(){
        return "/work/caseNew/sellReport";
    }

    @RequestMapping("/getData")
    @ResponseBody
    public pageObject getData(HttpServletRequest request){
        pageObject result = new pageObject();
        try {
            Map<String,Object> args=getParameters(request);
            List<Map<String, Object>> datas = sellMapper.getData(args);
            Integer Total=sellMapper.getTotal(args);
            Map<String,Object> Sum= sellMapper.getSum(args);
            result.setTotal(Total);
            result.setSum(Sum);
            result.setData(datas);

        }catch(Exception ax){
            result.raiseException(ax);
        }
        return  result;
    }
    private Map<String,Object> getParameters(HttpServletRequest request) throws Exception{
        String query = request.getParameter("Query");
        if (StringUtils.isNotEmpty(query)) {
            FinanReportQuery obj = JSON.parseObject(query, FinanReportQuery.class);
            Map<String, Object> args = new HashMap<>();
            args.put("timeField", obj.getTimeField());
            args.put("timeValue", obj.getTimeValue());
            args.put("groupField", obj.getGroupField());
            if (obj.getCons() != null) {
                args.put("Items", sqlParameterCreator.convert(obj.getCons()));
            }
            String sortField= request.getParameter("sortField");
            String sortOrder= request.getParameter("sortOrder");
            Integer  pageIndex=Integer.parseInt(request.getParameter("pageIndex"));
            Integer  pageSize=Integer.parseInt(request.getParameter("pageSize"));
            args.put("sortField",sortField);
            args.put("sortOrder",sortOrder);
            args.put("pageIndex",pageIndex);
            args.put("pageSize",pageSize);
            args.put("Begin",pageIndex * pageSize);
            args.put("End",  pageSize);
            return args;
        } else throw new Exception("提交参数异常,Query值为空!");

    }
}
