package com.zhide.dtsystem.controllers.Express;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.ExcelFileBuilder;
import com.zhide.dtsystem.common.WebFileUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.OriginalMapper;
import com.zhide.dtsystem.models.IExcelExportTemplate;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.OriginalExpressExcelTemplate;
import com.zhide.dtsystem.models.tbOriginalKd;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.services.define.IOriginalService;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/express/orig")
public class OriginalController {

    Logger logger = LoggerFactory.getLogger(OriginalController.class);

    @Autowired
    IOriginalService iOriginalService;
    @Autowired
    OriginalMapper originalMapper;

    @RequestMapping("/index")
    public String Index(Map<String, Object> model) {
        return "/express/orig/index";
    }

    @RequestMapping("/getData")
    @ResponseBody
    public pageObject GetData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = iOriginalService.getData(request);
        } catch (Exception ax) {
            logger.info(ax.getMessage());
        }
        return result;
    }

    @RequestMapping("/register")
    @ResponseBody
    public successResult Register(HttpServletRequest request) {
        successResult res = new successResult();
        try {
            res = iOriginalService.Register(request);
        } catch (Exception ax) {
            logger.info(ax.getMessage());
        }
        return res;
    }

    @RequestMapping("/registerShouju")
    @ResponseBody
    public successResult RegisterShouju(HttpServletRequest request) {
        successResult res = new successResult();
        try {
            res = iOriginalService.RegisterShouju(request);
        } catch (Exception ax) {
            logger.info(ax.getMessage());
        }
        return res;
    }

    @RequestMapping("/addExpress")
    public String addExpress(String id, String contents, Map<String, Object> model) {
        model.put("id", id);
        model.put("contents", contents);
        return "/express/orig/addExpress";
    }

    @RequestMapping("/saveExpress")
    @ResponseBody
    public successResult saveExpress(HttpServletRequest request) {
        successResult result = new successResult();
        tbOriginalKd res = null;
        try {
            String Data = request.getParameter("Data");
            String dnum = request.getParameter("dnum");
            if (Strings.isEmpty(Data) == false) {
                tbOriginalKd originalKd = JSON.parseObject(Data, tbOriginalKd.class);
                int re = iOriginalService.SaveExpress(originalKd, dnum);
                if (re > 0) {
                    res = originalKd;
                    result.setSuccess(true);
                    result.setData(res);
                }
            } else throw new Exception("数据格式不正确！");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/yjzq")
    @ResponseBody
    public successResult UpdateYJZT(HttpServletRequest request) {
        successResult result = new successResult();
        try {
            String Dnum = request.getParameter("Dnum");
            String PackageNum = request.getParameter("PackageNum");
            Random random = new Random();
            String YJGL = "YJGL-" + random.nextInt(1000000) + "-" + random.nextInt(10000);
            if (Strings.isEmpty(Dnum) == false) {
                int re = iOriginalService.UpdateYJZT(YJGL, Dnum, PackageNum);
                if (re > 0) {
                    result.setSuccess(true);
                }
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        try {
            String VX = request.getParameter("Data");
            List<IExcelExportTemplate> Rows = JSON.parseArray(VX, OriginalExpressExcelTemplate.class)
                    .stream().map(f -> (IExcelExportTemplate) f).collect(Collectors.toList());
            if (Rows.size() > 0) {
                ExcelFileBuilder builder = new ExcelFileBuilder(Rows);
                byte[] datas = builder.export();
                String fileName = builder.getCurrentFileName();
                WebFileUtils.download(fileName, datas, response);
            } else response.getWriter().write("<script>alert('没有数据可以导出。');</script>");
        } catch (Exception ax) {
            try {
                response.getWriter().write("<script>alert('导出Excel失败:" + ax.getMessage() + "');</script>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("/getCoding")
    @ResponseBody
    public  successResult getCoding() {
        successResult result = new successResult();
        try {
            List<String> Coding = originalMapper.getOriginalExpressCoding();
            if (Coding.size() > 0) {
                result.setSuccess(true);
                result.setData(Coding);
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public successResult remove(@RequestBody List<Integer> ids) {
        successResult result = new successResult();
        try {
            iOriginalService.remove(ids);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/refreshTotal")
    public successResult refreshTotal() {
        successResult result = new successResult();
        try {
            LoginUserInfo Info = CompanyContext.get();
            List<Map<String, Object>> OS = originalMapper.getOrigTotal(Info.getDepIdValue(), Info.getUserIdValue(),
                    Info.getRoleName());
            result.setData(OS);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
