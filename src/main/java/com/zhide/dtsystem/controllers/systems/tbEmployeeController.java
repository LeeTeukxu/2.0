package com.zhide.dtsystem.controllers.systems;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.EmployeeAndUser;
import com.zhide.dtsystem.models.tbEmployee;
import com.zhide.dtsystem.models.tbLoginUser;
import com.zhide.dtsystem.models.v_tbEmployee;
import com.zhide.dtsystem.services.define.ItbDepListService;
import com.zhide.dtsystem.services.define.ItbEmployeeService;
import com.zhide.dtsystem.services.define.ItbLoginUserService;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/systems/employee")
public class tbEmployeeController {

    @Autowired
    ItbEmployeeService itbEmployeeService;

    @Autowired
    ItbLoginUserService loginUserService;
    @Autowired
    private ItbDepListService itbDepListService;

    Logger logger = LoggerFactory.getLogger(tbEmployeeController.class);

    @RequestMapping("/index")
    public String Index(Map<String, Object> model) {
        List<Map<String, Object>> listNum = itbDepListService.getAllByCanUseAndDepNum();
        model.put("LoginUserNum", JSON.toJSONString(listNum));
        return "/systems/employee/index";
    }

    @RequestMapping("/add")
    public String Add(int depId, Map<String, Object> model) {
        model.put("depId", depId);
        model.put("mode", "add");
        model.put("result", "{}");
        return "/systems/employee/add";
    }

    @RequestMapping("/edit")
    public String Edit(int userId, Map<String, Object> model) throws JsonProcessingException {
        EmployeeAndUser result = new EmployeeAndUser();
        tbLoginUser user = loginUserService.getById(userId);
        if (user != null) {
            int empId = user.getEmpId();
            tbEmployee employee = itbEmployeeService.getById(empId);
            result.setEmployee(employee);
        }
        result.setLoginUser(user);
        model.put("mode", "edit");
        model.put("depId", user.getDepId());
        String X = JSON.toJSONString(result);
        model.put("result", X);
//        model.put("pwd",user.getPassword());
        return "/systems/employee/add";
    }

    @RequestMapping("/getPage")
    @ResponseBody
    public pageObject getPage(Integer depId, String empName, int pageIndex, int pageSize, String sortField,
            String sortOrder) {
        pageObject result = new pageObject();
        Sort.Direction direction = Sort.Direction.fromString(sortOrder);
        Sort sort = Sort.by(direction, sortField);
        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
        Page<v_tbEmployee> rows = null;
        if (Strings.isNotEmpty(empName)) {
            rows = itbEmployeeService.getPage("%" + empName + "%", pageable);
        } else rows = itbEmployeeService.getPage(depId, pageable);
        result.setTotal(Integer.parseInt(Long.toString(rows.getTotalElements())));
        result.setData(rows.getContent());

        return result;
    }

    @ResponseBody
    @RequestMapping("/saveAll")
    public successResult saveAll(String Data) {
        successResult result = new successResult();
        try {
            logger.info("保存Employee和tbLoginUser:" + Data);
            EmployeeAndUser employeeAndUser = JSON.parseObject(Data, EmployeeAndUser.class);
            tbEmployee employee = employeeAndUser.getEmployee();
            tbLoginUser loginUser = employeeAndUser.getLoginUser();
            result.setData(itbEmployeeService.saveAll(employee, loginUser));
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping(value = "/removeAll", method = RequestMethod.POST)
    @ResponseBody
    public successResult remove(@RequestBody List<Integer> userId) {
        successResult res = new successResult();
        try {
            itbEmployeeService.removeAll(userId);
        } catch (Exception ax) {
            res.raiseException(ax);
        }
        return res;
    }
    @ResponseBody
    @RequestMapping("/getNumbers")
    public Map<Integer,Integer> GetEmployeeNumbers(){
        Map<Integer,Integer> Res=new HashMap<>();
        try {
            Res= itbDepListService.GetEmployeeNumbers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Res;
    }
}
