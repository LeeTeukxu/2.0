package com.zhide.dtsystem.controllers.CostReduction;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhide.dtsystem.common.ExcelFileBuilder;
import com.zhide.dtsystem.common.WebFileUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.mapper.CostReductionMapper;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.CostReductionRepository;
import com.zhide.dtsystem.repositorys.costReductionAttachmentRepository;
import com.zhide.dtsystem.repositorys.tbClientRepository;
import com.zhide.dtsystem.services.define.ICostReductionService;
import org.apache.commons.io.FileUtils;
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
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/CostReduction")
public class CostReductionController {

    Logger logger = LoggerFactory.getLogger(CostReductionController.class);

    @Autowired
    ICostReductionService iCostReductionService;

    @Autowired
    CostReductionRepository costReductionRepository;

    @Autowired
    costReductionAttachmentRepository costReductionAttachmentRepository;

    @Autowired
    tbClientRepository tbClientRepository;

    @Autowired
    CostReductionMapper costReductionMapper;

    @RequestMapping("/index")
    public String Index(Map<String, Object> model) {
        LoginUserInfo loginUserInfo = CompanyContext.get();
        return "/CostReduction/index";
    }

    @RequestMapping("/getData")
    @ResponseBody
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            result = iCostReductionService.getData(request);
        } catch (Exception ax) {
            logger.info(ax.getMessage());
        }
        return result;
    }

    @RequestMapping("/add")
    public String Add(Map<String, Object> model) {
        model.put("Mode", "Add");
        model.put("Load", "{}");
        model.put("AttID", "[]");
        return "/CostReduction/edit";
    }

    @ResponseBody
    @RequestMapping("/saveAll")
    public successResult SaveAll(String Data) {
        successResult result = new successResult();
        try {
            Map<String, Object> Datas = JSON.parseObject(Data, new TypeReference<Map<String, Object>>() {
            });
            CostReduction obj = iCostReductionService.SaveAll(Datas);
            result.setData(obj);
        } catch (Exception ax) {

        }
        return result;
    }

    @RequestMapping("/edit")
    public String Edit(Integer ID, String Mode, Map<String, Object> model) {
        Optional<CostReduction> findOne = costReductionRepository.findById(ID);
        if (findOne.isPresent()) {
            CostReduction main = findOne.get();
            Optional<tbClient> findClients = tbClientRepository.findById(main.getCustomerId());
            if (findClients.isPresent()) main.setClientIdName(findClients.get().getName());
            model.put("Load", JSON.toJSONString(main));
            model.put("LoadObj", main);
            List<String> AttIDS =
                    costReductionAttachmentRepository.findAllByUUId(main.getuUId()).stream().map(f -> f.getAttId()).collect(Collectors.toList());
            model.put("AttID", JSON.toJSONString(AttIDS));
        }
        model.put("Mode", Mode);
        LoginUserInfo loginUserInfo = CompanyContext.get();
        model.put("RoleName", loginUserInfo.getRoleName());
        return "/CostReduction/edit";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public successResult remove(@RequestBody List<String> ids) {
        successResult result = new successResult();
        try {
            iCostReductionService.remove(ids);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getAttachmentName")
    public successResult GetAttachmentName(String UUID) {
        successResult res = new successResult();
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            list = costReductionMapper.getAttachmentName(UUID);
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    res.setData(list.get(i).get("Name"));
                }
            }
        } catch (Exception ax) {
            res.setMessage(ax.getMessage());
            res.setSuccess(false);
        }
        return res;
    }

    @ResponseBody
    @RequestMapping("/getAllImages")
    public Map<String, Object> getAllImages(String Path, String GUID) {
        Map<String, Object> res = new HashMap<>();
        try {
            List<String> Files = iCostReductionService.getAttachmentPaths(Path, GUID);
            if (Files.size() > 0) {
                res.put("status", 1);
                res.put("start", 0);
                List<Map<String, Object>> OO = new ArrayList<>();
                for (int i = 0; i < Files.size(); i++) {
                    String Src = Files.get(i);
                    Map<String, Object> OX = new HashMap<>();
                    OX.put("src", Src);
                    OX.put("thumb", "");
                    OX.put("alt", "第" + Integer.toString(i) + "个文件");
                    OO.add(OX);
                }
                res.put("data", OO);
            } else throw new Exception("没有可查看的通知书附件");
        } catch (Exception ax) {
            res.put("status", 0);
            res.put("message", ax.getMessage());
        }
        return res;
    }

    @RequestMapping("/saveNeiShen")
    @ResponseBody
    public successResult saveNeiShen(HttpServletRequest request) {
        successResult result = new successResult();
        CostReduction res = null;
        try {
            String Data = request.getParameter("Data");
            if (Strings.isEmpty(Data) == false) {
                CostReduction costReduction = JSON.parseObject(Data, CostReduction.class);
                res = iCostReductionService.SaveNeiShen(costReduction);
                result.setData(res);
            }
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/NeiShen")
    public String NeiShen(int CostReductionID, Map<String, Object> model) {
        Optional<CostReduction> findOne = costReductionRepository.findById(CostReductionID);
        if (findOne.isPresent()) {
            CostReduction main = findOne.get();
            Optional<tbClient> findClients = tbClientRepository.findById(main.getCustomerId());
            if (findClients.isPresent()) main.setClientIdName(findClients.get().getName());
            model.put("Load", JSON.toJSONString(main));
            model.put("LoadObj", main);
            List<String> AttIDS =
                    costReductionAttachmentRepository.findAllByUUId(main.getuUId()).stream().map(f -> f.getAttId()).collect(Collectors.toList());
            model.put("AttID", JSON.toJSONString(AttIDS));
        }
        model.put("Mode", "NeiShen");
        LoginUserInfo loginUserInfo = CompanyContext.get();
        model.put("RoleName", loginUserInfo.getRoleName());
        return "/CostReduction/saveNeiShen";
    }

    @RequestMapping(value = "/GuoZhiJu", method = RequestMethod.POST)
    @ResponseBody
    public successResult GuoZhiJu(@RequestBody List<Integer> ids) {
        successResult result = new successResult();
        try {
            iCostReductionService.GuoZhiJu(ids);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping(value = "/UnGuoZhiJu", method = RequestMethod.POST)
    @ResponseBody
    public successResult UnGuoZhiJu(@RequestBody List<Integer> ids) {
        successResult result = new successResult();
        try {
            iCostReductionService.UnGuoZhiJu(ids);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/saveFeiJianJieGuo")
    @ResponseBody
    public successResult saveFeiJianJieGuo(HttpServletRequest request) {
        successResult result = new successResult();
        CostReduction res = null;
        try {
            String Data = request.getParameter("Data");
            if (Strings.isEmpty(Data) == false) {
                CostReduction costReduction = JSON.parseObject(Data, CostReduction.class);
                res = iCostReductionService.SaveFeiJianJieGuo(costReduction);
                result.setData(res);
            }
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
            List<Map<String, Object>> OS = costReductionMapper.getCostReductionTotal(Info.getDepIdValue(),
                    Info.getUserIdValue(), Info.getRoleName());
            result.setData(OS);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/FeiJianJieGuo")
    public String FeiJianJieGuo(int CostReductionID, Map<String, Object> model) {
        Optional<CostReduction> findOne = costReductionRepository.findById(CostReductionID);
        if (findOne.isPresent()) {
            CostReduction main = findOne.get();
            Optional<tbClient> findClients = tbClientRepository.findById(main.getCustomerId());
            if (findClients.isPresent()) main.setClientIdName(findClients.get().getName());
            model.put("Load", JSON.toJSONString(main));
            model.put("LoadObj", main);
            List<String> AttIDS =
                    costReductionAttachmentRepository.findAllByUUId(main.getuUId()).stream().map(f -> f.getAttId()).collect(Collectors.toList());
            model.put("AttID", JSON.toJSONString(AttIDS));
        }
        model.put("Mode", "FeiJianJieGuo");
        LoginUserInfo loginUserInfo = CompanyContext.get();
        model.put("RoleName", loginUserInfo.getRoleName());
        return "/CostReduction/saveFeiJianJieGuo";
    }


    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        try {
            String VX = request.getParameter("Data");
            List<IExcelExportTemplate> Rows = JSON.parseArray(VX, CostReductionExcelTemplate.class)
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

    @RequestMapping("/download")
    public void DownLoad(HttpServletResponse response, String UUIDS) {
        File targetFile = null;
        try {
            targetFile = iCostReductionService.DownloadFiles(UUIDS);
            if (targetFile != null) {
                SimpleDateFormat ss = new SimpleDateFormat("yyyyMMddHHmmss");
                WebFileUtils.download("费用减缓办理文件下载_" + ss.format(new Date()) + ".zip", targetFile, response);
            } else throw new Exception("下载文件失败，请稍后重试！");
        } catch (Exception e) {
            try {
                response.getWriter().write(e.getMessage());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (targetFile != null) {
                    FileUtils.forceDelete(targetFile);
                }
            } catch (Exception ax) {
                ax.printStackTrace();
            }
        }
    }
}
