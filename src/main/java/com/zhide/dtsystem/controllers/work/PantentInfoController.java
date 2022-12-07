package com.zhide.dtsystem.controllers.work;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhide.dtsystem.common.ExcelFileBuilder;
import com.zhide.dtsystem.common.WebFileUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.TZSRepository;
import com.zhide.dtsystem.repositorys.casesMainRepository;
import com.zhide.dtsystem.repositorys.casesSubRepository;
import com.zhide.dtsystem.repositorys.pantentInfoRepository;
import com.zhide.dtsystem.services.define.INoticeService;
import com.zhide.dtsystem.services.define.IPatentInfoService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RequestMapping("/work/patentInfo")
@Controller
public class PantentInfoController {
    @Autowired
    IPatentInfoService patentInfoService;
    @Autowired
    pantentInfoRepository pRep;
    @Autowired
    INoticeService noticeService;
    @Autowired
    casesSubRepository caseSubRep;
    @Autowired
    casesMainRepository caseMainRep;

    @RequestMapping("/index")
    public String Index(Map<String, Object> model) {
        model.put("Users", JSON.toJSONString(patentInfoService.GetLoginUserHash()));
        LoginUserInfo Info= CompanyContext.get();
        model.put("RoleName",Info.getRoleName());
        model.put("TZSStatus", JSON.toJSONString(noticeService.getTZSStatus()));
        return "/work/patentInfo/index";
    }

    @RequestMapping("/query")
    public String Query(String Mode, Map<String, Object> model) {
        model.put("Mode", Mode);
        return "/work/patentInfo/query";
    }

    @ResponseBody
    @RequestMapping("/getData")
    public pageObject getData(HttpServletRequest request) {
        pageObject result = new pageObject();
        try {
            Map<String, Object> params = patentInfoService.getParameters(request);
            result = patentInfoService.getData(params);
        } catch (Exception ax) {
            result.raiseException(ax);
            ax.printStackTrace();
        }
        return result;
    }

    @RequestMapping("/changeValue")
    @ResponseBody
    public successResult changeEntityValue(@RequestParam List<String> IDS, String field, String value) {
        successResult result = new successResult();
        try {
            if (field.equals("JIAJI") || field.equals("JIEAN")) {
                boolean OX = Boolean.parseBoolean(value.toString());
                patentInfoService.ChangeValue(IDS, field, OX);
            } else patentInfoService.ChangeValue(IDS, field, value);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        try {
            String VX = request.getParameter("Data");
            List<IExcelExportTemplate> Rows = JSON.parseArray(VX, PatentInfoExcelTemplate.class)
                    .stream().map(f -> (IExcelExportTemplate) f).collect(toList());
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
    public void download(HttpServletResponse response, String Code, String FileName) {
        String[] Codes = Code.split(",");
        File targetFile = null;
        try {
            targetFile = patentInfoService.download(Codes);
            WebFileUtils.download(FileName, targetFile, response);
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

    @RequestMapping("/alldownload")
    public void alldownload(HttpServletResponse response, String Code, String FileName) {
        String[] Codes = Code.split(",");
        File targetFile = null;
        try {
            targetFile = patentInfoService.alldownload(Codes);
            WebFileUtils.download(FileName, targetFile, response);
        } catch (Exception e) {
            try {
                response.getWriter().write(e.getMessage());
            } catch (IOException e1) {
                e.printStackTrace();
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

    @RequestMapping("/importData")
    @ResponseBody
    public successResult ImportData(@RequestParam("Data") String Data, @RequestParam("Cols") String Cols) {
        successResult result = new successResult();
        try {
            List<Map<String, Object>> Datas = JSON.parseObject(Data, new TypeReference<List<Map<String, Object>>>() {
            });
            List<gridColumnInfo> cols = JSON.parseArray(Cols, gridColumnInfo.class);
            for (int i = 0; i < Datas.size(); i++) {
                Map<String, Object> row = Datas.get(i);
                String Result = "导入成功";
                try {
                    patentInfoService.ImportData(row);
                } catch (Exception ax) {
                    result.raiseException(ax);
                    Result ="导入失败:"+ JSON.toJSONString(row)+"失败，具体信息为:\r\n"+ax.getMessage();
                }
                row.put("ImportResult", Result);
            }
            result.setData(Datas);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/updateMemo")
    public successResult UpdateMemo(String SHENQINGH, String Memo) {
        successResult result = new successResult();
        try {
            Optional<pantentInfo> findOne = pRep.findByShenqingh(SHENQINGH);
            if (findOne.isPresent()) {
                pantentInfo One = findOne.get();
                One.setMemo(Memo);
                pRep.save(One);
            } else throw new Exception(SHENQINGH + "指向数据不存在!");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/detail")
    public String Detail(String shenqingh,String BH,String YW,String KH,String JS,String LC,Map<String,Object> models){
        models.put("Shenqingh",shenqingh);
        Optional<pantentInfo> findPantents=pRep.findByShenqingh(shenqingh);
        if(findPantents.isPresent()){
            pantentInfo p=findPantents.get();
            models.put("pantent",p);

            casesSub sub=new casesSub();
            casesMain main=new casesMain();
            if(StringUtils.isEmpty(BH)==false){
                Optional<casesSub> findSubs=caseSubRep.findFirstBySubNo(BH);
                if(findSubs.isPresent()){
                    sub=findSubs.get();
                    sub.setyName(sub.getyName().trim());
                    if(StringUtils.isEmpty(sub.getTechFiles())){
                        sub.setTechFiles(null);
                    }
                    if(StringUtils.isEmpty(sub.getZlFiles())){
                        sub.setZlFiles(null);
                    }
                }
            }
            String CasesID=sub.getCasesId();
            if(StringUtils.isEmpty(CasesID)==false){
                Optional<casesMain>findMains=caseMainRep.findFirstByCasesId(CasesID);
                if(findMains.isPresent()){
                    main=findMains.get();
                }
            }
            models.put("sub",sub);
            models.put("main",main);
            models.put("KH",KH);
            models.put("YW",YW);
            models.put("LC",LC);
            models.put("JS",JS);
        }
        return "/work/patentInfo/detail";
    }
    @Autowired
    TZSRepository tzsRep;
    @RequestMapping("/getTZS")
    @ResponseBody
    public List<TZS> getTZS(String shenqingh){
        List<TZS> ts=tzsRep.findAllByShenqinghIn(new String[]{shenqingh});
        return ts;
    }
    @RequestMapping("/getPantentTotal")
    @ResponseBody
    public successResult getPantentTotal(){
        successResult result=new successResult();
        try
        {
            LoginUserInfo Info=CompanyContext.get();
            List<Map<String,Object>> OO= patentInfoService.getPantentTotal(Info.getDepIdValue(),Info.getUserIdValue(),
                Info.getRoleName());
            result.setData(OO);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
}
