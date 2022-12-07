package com.zhide.dtsystem.controllers.work;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.PantentInfoMemo;
import com.zhide.dtsystem.models.v_PantentInfoMemo;
import com.zhide.dtsystem.repositorys.View_PatentInfoMemoRepository;
import com.zhide.dtsystem.services.define.IPatentInfoMemoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/work/addMemo")
public class AddMemoController {
    @Autowired
    View_PatentInfoMemoRepository viewMemoRep;
    @Autowired
    IPatentInfoMemoService memoService;
    @RequestMapping("/index")
    public String Index(String ID,String MenuName, Map<String, Object> model, HttpServletRequest request) {
        model.put("ID", ID);
        if(StringUtils.isEmpty(MenuName)) {
            Object OO = request.getSession().getAttribute("CurrentMenu");
            if (Objects.isNull(OO) == false) {
                MenuName = OO.toString();
            }
        }
        model.put("BatchMode",ID.indexOf(",")>-1);
        model.put("MenuName", MenuName);
        return "/work/patentInfo/addMemo";
    }

    @ResponseBody
    @RequestMapping("/getData")
    public pageObject GetData(String SHENQINGH, ServletRequest request) {
        pageObject result = new pageObject();
        List<v_PantentInfoMemo> Ps=new ArrayList<>();
        try {
            if(SHENQINGH.indexOf(",")==-1) {
                int pageSize = Integer.parseInt(request.getParameter("pageSize"));
                int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
                String sortOrder = request.getParameter("sortOrder");
                if (sortOrder.isEmpty()) sortOrder = "desc";
                String sortField = request.getParameter("sortField");
                if (sortField.isEmpty()) sortField = "CreateDate";
                Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(sortOrder.equals("asc") ? Sort.Direction
                        .ASC : Sort.Direction.DESC, sortField));
                Page<v_PantentInfoMemo> res = viewMemoRep.findAllByShenqingh(SHENQINGH, pageable);
                Ps=res.getContent();
            }
            result.setData(Ps);
            result.setTotal(Ps.size());
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/save")
    public successResult SaveAll(String Data, HttpServletRequest request) {
        successResult result = new successResult();
        try {
            List<PantentInfoMemo> Infos = JSON.parseArray(Data, PantentInfoMemo.class);
            if(Infos.size()>0) {
                result.setSuccess(memoService.SaveAll(Infos));
            } else throw new Exception("没有可保存的内容!");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/remove")
    public successResult RemoveAll(String ID) {
        successResult result = new successResult();
        try {
            List<String> Infos = Arrays.asList(ID);
            result.setSuccess(memoService.RemoveAll(Infos));
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/saveImage")
    public successResult saveImage(String Data){
        successResult result=new successResult();
        try {
            PantentInfoMemo pObj=JSON.parseObject(Data,PantentInfoMemo.class);
            memoService.SaveImage(pObj);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/getImages")
    public Map<String, Object> getAllImages(String MID) {
        Map<String, Object> res = new HashMap<>();
        try {
            List<Map<String, Object>> OO = new ArrayList<>();
            List<String> Files = memoService.getImages(MID);
            if (Files.size() > 0) {
                res.put("status", 1);
                res.put("start", 0);
                for (int n = 0; n < Files.size(); n++) {
                    String ssText = Files.get(n);
                    String[] ssx=ssText.split("\\|");
                    if(ssx.length>1){
                        String Src=ssx[0];
                        String memo=StringUtils.trim(ssx[1]);
                        if(StringUtils.isEmpty(memo))memo= "第" + (n + 1) + "个图片";
                        Map<String, Object> OX = new HashMap<>();
                        OX.put("src", Src);
                        OX.put("thumb", "");
                        OX.put("alt",memo);
                        OO.add(OX);
                    }
                }
            }
            res.put("data", OO);
            if (OO.size() == 0) throw new Exception("没有可查看的通知书附件。");
        } catch (Exception ax) {
            res.put("status", 0);
            res.put("message", ax.getMessage());
        }
        return res;
    }
}
