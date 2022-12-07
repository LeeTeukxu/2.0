package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhide.dtsystem.mapper.ColumnChartMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.PantentInfoMemo;
import com.zhide.dtsystem.models.sqlParameter;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.services.define.IColumnChartService;
import com.zhide.dtsystem.services.sqlParameterCreator;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.ss.formula.functions.Na;
import org.aspectj.weaver.ast.And;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ColumnChartServiceImpl implements IColumnChartService {
    Logger logger = LoggerFactory.getLogger(ColumnChartServiceImpl.class);

    @Autowired
    ColumnChartMapper columnChartMapper;

    @Override
    public List<String> getName(String Type, int State, String Dates) throws Exception {
        Map<String, Object> params = getParameter(Type, State, Dates);

        List<Map<String, Object>> Datas = columnChartMapper.getName(params);
        List<String> listName = new ArrayList<>();
        if (Datas.size() > 0) {
            Datas.stream().forEach(f -> {
                listName.add(f.get("name").toString());
            });
        }
        return listName;
    }

    @Override
    public List<List<Integer>> getNameOfNum(String Type, int State, List<String> NewFields, List<String> Names, String Dats) throws Exception {
        Map<String, Object> params = getParameter(Type, State, Dats);
        List<String> Fields = getOldDynamicColumns(Type, State);

        List<Map<String, Object>> Datas = columnChartMapper.getName(params);
        List<Map<String, Object>> Ds = columnChartMapper.getWaitDetail(params);
        TreeMap<String, Object> AllNumMap = new TreeMap<>();
        for (Map<String, Object> data : Datas) {
            Map<String, Object> map = new HashMap<>();
            String Name = data.get("name").toString();
            List<Map<String, Object>> Details = Ds.stream().filter(f -> f.get("name").toString().equals(Name)).collect(Collectors.toList());
            Integer FMNum = 0;
            Integer SYNum = 0;
            Integer WGNum = 0;
            Integer FXNum = 0;
            Integer NewNum = 0;
            for (Map<String, Object> detail : Details) {
                String YName = detail.get("YName").toString().trim();
                Double dNum = Double.parseDouble(detail.get("Num").toString());
                Integer Num = dNum.intValue();

                Optional<String> findCol = Fields.stream().filter(f -> f.equals(YName)).findFirst();
                if (findCol.isPresent()) {
                    if (findCol.get().indexOf("发明") > -1) {
                        if (findCol.get().equals("发明专利风险代理")){
                            if (map.containsKey("发明专利风险代理")) {
                                FXNum += Num;
                                map.put("发明专利风险代理", FXNum);
                            }else {
                                FXNum += Num;
                                map.put("发明专利风险代理", FXNum);
                            }
                        }else {
                            if (map.containsKey("发明专利")) {
                                FMNum += Num;
                                map.put("发明专利", FMNum);
                            } else {
                                FMNum += Num;
                                map.put("发明专利", Num);
                            }
                        }
                    }else if (findCol.get().indexOf("实用") > -1) {
                        if (map.containsKey("实用新型")) {
                            SYNum += Num;
                            map.put("实用新型", SYNum);
                        }else {
                            SYNum += Num;
                            map.put("实用新型", Num);
                        }
                    }else if (findCol.get().indexOf("外观") > -1) {
                        if (map.containsKey("外观设计")) {
                            WGNum += Num;
                            map.put("外观设计", WGNum);
                        }else {
                            WGNum += Num;
                            map.put("外观设计", Num);
                        }
                    }else {
                        if (map.containsKey(findCol.get())) {
                            NewNum = Integer.parseInt(map.get(findCol.get()).toString());
                            NewNum = NewNum + Num;
                            map.put(findCol.get(), NewNum);
                        } else {
                            map.put(findCol.get(), Num);
                        }

                    }
                }
            }
            NewFields.stream().forEach(f -> {
                if (map.get(f) != null) {
                    AllNumMap.put(f + "?" + Name, map.get(f));
                }else AllNumMap.put(f + "?" + Name, 0);
            });
        }

        List<List<Integer>> listAllNum = new ArrayList<>();
        NewFields.stream().forEach(f -> {
            List<Integer> listSingleNum = new ArrayList<>();
            Names.stream().forEach(x -> {
                String NameAndCol = f + "?" + x;
                listSingleNum.add(Integer.parseInt(AllNumMap.get(NameAndCol).toString()));
            });
            listAllNum.add(listSingleNum);
        });
        //去除json的key的双引号
//        int features = SerializerFeature.config(JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.QuoteFieldNames, false);
        return listAllNum;
    }

    private List<String> getOldDynamicColumns(String Type, int State) {
        List<String> res = new ArrayList<>();
        LoginUserInfo Info = CompanyContext.get();
        Map<String, Object> params = new HashMap<>();
        params.put("RoleName", Info.getRoleName());
        params.put("UserID", Info.getUserIdValue());
        params.put("Type", Type);
        params.put("State", State);
        List<String> Cs = columnChartMapper.getReportColumns(params);
        if (Cs.size() > 0) {
            for (String cs : Cs) {
                Map<String, Object> O = new HashMap<>();
                O.put("field", cs);
                res.add(cs);
            }
        }
        res = res.stream().distinct().collect(Collectors.toList());
        return res;
    }

    @Override
    public List<String> getDynamicColumns(String Type, int State) {
        List<String> res = new ArrayList<>();
        LoginUserInfo Info = CompanyContext.get();
        Map<String, Object> params = new HashMap<>();
        params.put("RoleName", Info.getRoleName());
        params.put("UserID", Info.getUserIdValue());
        params.put("Type", Type);
        params.put("State", State);
        List<String> Cs = columnChartMapper.getReportColumns(params);
        if (Cs.size() > 0) {
            for (String cs : Cs) {
                Map<String, Object> O = new HashMap<>();
                O.put("field", cs);
                if (cs.indexOf("发明") > -1) {
                    if (cs.equals("发明专利风险代理")) {
                        cs = "发明专利风险代理";
                    }else cs = "发明专利";
                }else if (cs.indexOf("实用") > -1) {
                    cs = "实用新型";
                }else if (cs.indexOf("外观") > -1) {
                    cs = "外观设计";
                }
                res.add(cs);
            }
        }
        res = res.stream().distinct().collect(Collectors.toList());
        return res;
    }

    private Map<String, Object> getParameter(String Type, int State, String Dates) throws Exception {
        LoginUserInfo Info = CompanyContext.get();
        Map<String, Object> params = new HashMap<>();
        params.put("sortOrder", "Desc");
        params.put("sortField", "Name");
        params.put("UserID", Info.getUserIdValue());
        params.put("RoleName", Info.getRoleName());
        params.put("Type", Type);
        params.put("State", State);

        if (Strings.isNotEmpty(Dates)) {
            List<sqlParameter> Ps = JSON.parseArray(Dates, sqlParameter.class);
            List<sqlParameter> AndItems = sqlParameterCreator.convert(Ps);
            params.put("andItems", AndItems);
        }else params.put("andItems", new ArrayList<>());
        return params;
    }
}
