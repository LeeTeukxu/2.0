package com.zhide.dtsystem.controllers.ColumnChart;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.services.define.IColumnChartService;
import com.zhide.dtsystem.services.implement.ContractReceiveServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/ColumnChart")
public class ColumnChartController {
    Logger logger = LoggerFactory.getLogger(ColumnChartController.class);
    @Autowired
    IColumnChartService columnChartService;

    @RequestMapping("/index")
    public String Index(Map<String, Object> model, HttpServletRequest request) {
        String State = request.getParameter("State");
        String Type = request.getParameter("Type");
        String Dates = request.getParameter("Dates");
        if (State == null) {
            State = "51";
        }
        if (Type == null) {
            Type = "Tech";
        }
        List<String> TechNewFields = getDynamicColumn(Type, Integer.parseInt(State));
        List<String> TechListColor = getColor(TechNewFields);
        model.put("TechColor", JSON.toJSONString(TechListColor));
        List<String> TechNames = getName(Type, Integer.parseInt(State), Dates);
        List<List<Integer>> TechNameOfNum = getNameOfNum(Type, Integer.parseInt(State), TechNewFields, TechNames, Dates);
        model.put("TechName", JSON.toJSONString(TechNames));
        model.put("TechNameOfNum", JSON.toJSONString(TechNameOfNum));
        model.put("TechColumn", JSON.toJSONString(TechNewFields));
        model.put("TechColumnLength",TechNewFields.size());
        Integer NumTotals = getNumTotal(TechNameOfNum);
        model.put("Totals", NumTotals);
        Integer Max = getMax(TechNameOfNum);
        model.put("Maxs", Max);

        String BeginTime = request.getParameter("BeginTime");
        String EndTime = request.getParameter("EndTime");
        if (BeginTime == null) {
            BeginTime = "";
        }
        if (EndTime == null) {
            EndTime = "";
        }
        model.put("BeginTime", BeginTime);
        model.put("EndTime", EndTime);

//        List<String> BusNewFields = getDynamicColumn(Type, Integer.parseInt(State));
//        List<String> BusListColor = getColor(TechNewFields);
//        List<String> BusNames = getName(Type, Integer.parseInt(State));
//        List<List<Integer>> BusNameOfNum = getNameOfNum(Type, Integer.parseInt(State), BusNewFields, BusNames);
//        model.put("BusName", JSON.toJSONString(BusNames));
//        model.put("BusNameOfNum", JSON.toJSONString(BusNameOfNum));
//        model.put("BusColumn", JSON.toJSONString(BusNewFields));
//
//        List<String> ClientNewFields = getDynamicColumn(Type, Integer.parseInt(State));
//        List<String> ClientListColor = getColor(TechNewFields);
//        List<String> ClientNames = getName(Type, Integer.parseInt(State));
//        List<List<Integer>> ClientNameOfNum = getNameOfNum(Type, Integer.parseInt(State), ClientNewFields, ClientNames);
//        model.put("ClientName", JSON.toJSONString(ClientNames));
//        model.put("ClientNameOfNum", JSON.toJSONString(ClientNameOfNum));
//        model.put("ClientColumn", JSON.toJSONString(ClientNewFields));

//        model.put("BusColor", JSON.toJSONString(BusListColor));
//        model.put("ClientColor", JSON.toJSONString(ClientListColor));

        model.put("State", Integer.parseInt(State));
        model.put("Type", Type);
        return "/ColumnChart/index";
    }

//    @RequestMapping("/getTechData")
//    @ResponseBody
//    public successResult GetTechData(HttpServletRequest request) {
//        successResult result = new successResult();
//        try {
//            String Type = request.getParameter("Type");
//            String State = request.getParameter("State");
//            List<Map<String, Object>> list = new ArrayList<>();
//            Map<String, Object> map = new HashMap<>();
//            List<String> TechNames = getName(Type, Integer.parseInt(State));
//            List<String> TechNewFields = getDynamicColumn(Type, Integer.parseInt(State));
//            List<List<Integer>> TechNameOfNum = getNameOfNum(Type, Integer.parseInt(State), TechNewFields, TechNames);
//            List<String> TechListColor = getColor(TechNewFields);
//            if (TechNames.size() > 0) {
//                map.put("TechName", JSON.toJSONString(TechNames));
//            }
//            if (TechNewFields.size() > 0) {
//                map.put("TechColumn", JSON.toJSONString(TechNewFields));
//            }
//            if (TechNameOfNum.size() > 0) {
//                map.put("TechNameOfNum", JSON.toJSONString(TechNameOfNum));
//            }
//            if (TechListColor.size() > 0) {
//                map.put("TechColor", JSON.toJSONString(TechListColor));
//            }
//            list.add(map);
//            result.setData(list);
//        }catch (Exception ax) {
//            result.raiseException(ax);
//        }
//        return result;
//    }

    public List<String> getName(String Type, int State, String Dates) {
        List<String> res = new ArrayList<>();
        try {
            res = columnChartService.getName(Type, State, Dates);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public List<List<Integer>> getNameOfNum(String Type, int State, List<String> NewFields, List<String> Names, String Dates) {
        List<List<Integer>> NameOfNum = new ArrayList<>();
        try {
            NameOfNum = columnChartService.getNameOfNum(Type, State, NewFields, Names, Dates);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return NameOfNum;
    }

    public List<String> getDynamicColumn(String Type, int State) {
        List<String> res = new ArrayList<>();
        try {
            res = columnChartService.getDynamicColumns(Type, State);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    private List<String> getColor(List<String> NewFields) {
        List<String> listColor = new ArrayList<>();
        Map<String, Object> map = Colors();
        NewFields.stream().forEach(f -> {
            listColor.add(map.get(f).toString());
        });
        return listColor;
    }

    private Integer getNumTotal(List<List<Integer>> TechNameOfNum) {
        Integer Total = 0;
        if (TechNameOfNum.size() <= 0) return 0;
        for (List<Integer> Nums : TechNameOfNum) {
            if (Nums.size() <= 0) return 0;
            for (Integer Num : Nums) {
                Total += Num;
            }
        }
        return Total;
    }

    private Integer getMax(List<List<Integer>> TechNameOfNum) {
        Integer Max = 0;
        if (TechNameOfNum.size() <= 0) return 240;
        for (List<Integer> Nums : TechNameOfNum) {
            if (Nums.size() <= 0) return 0;
            Integer Num = Collections.max(Nums);
            if (Num > Max) {
                Max = Num;
            }
        }
        return Max;
    }

    private Map<String, Object> Colors() {
        Map<String, Object> map = new HashMap<>();
        map.put("PCT国家阶段", "red");
        map.put("发明专利", "orange");
        map.put("发明专利风险代理", "yellow");
        map.put("非常规业务", "green");
        map.put("实用新型", "cyan");
        map.put("外观设计", "blue");
        map.put("预先审查", "purple");
        map.put("专利驳回复审", "darkgreen");
        map.put("专利优先审查", "lightgreen");
        map.put("专利著录项目信息变更", "darkorange");
        map.put("同日申请", "darkblue");
        map.put("PCT国际阶段", "lightblue");
        map.put("PCT-国际阶段", "lightblue");
        map.put("巴黎公约国际专利", "skyblue");
        map.put("版权登记", "crimson");
        map.put("科学技术局专利查新检索报告", "lightred");
        map.put("软件著作权登记", "lightgrey");
        map.put("同日申请C类", "navy");
        map.put("知识产权贯标", "darkcyan");
        map.put("专利代理机构变更", "pink");
        map.put("专利登记薄副本办理", "black");
        map.put("专利流程监管", "brown");
        map.put("专利买卖", "gold");
        map.put("专利权利恢复", "gray");
        map.put("专利权评价报告", "plum");
        return map;
    }
}
