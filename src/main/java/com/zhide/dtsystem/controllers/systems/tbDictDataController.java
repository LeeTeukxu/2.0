package com.zhide.dtsystem.controllers.systems;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.repositorys.tbDictContRepository;
import com.zhide.dtsystem.repositorys.tbDictDataRepository;
import com.zhide.dtsystem.services.define.ItbDictDataService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/systems/dict")
public class tbDictDataController {
    @Autowired
    ItbDictDataService itbDictDataService;
    @Autowired
    tbDictContRepository contRep;
    @Autowired
    tbDictDataRepository dataRep;
    @RequestMapping("/index")
    public String Index(){
        return "/systems/dict/index";
    }
    @ResponseBody
    @RequestMapping("/getParent")
    public List<tbDictCont> getParent(){
        List<tbDictCont> Cs=contRep.findAll();
        return Cs;
    }
    @RequestMapping("/getData")
    @ResponseBody
    public pageObject getData(int DtID,String DtName, int pageIndex, int pageSize, String sortField,
            String sortOrder){
        pageObject result = new pageObject();
        Sort.Direction direction = Sort.Direction.fromString(sortOrder);
        Sort sort = Sort.by(direction, sortField);
        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
        Page<tbDictData> rows = null;
        if (Strings.isNotEmpty(DtName)==false) {
            rows = dataRep.findAllByDtId(DtID, pageable);
        } else rows = dataRep.findAllByDtIdAndName(DtID,"%"+DtName+"%", pageable);
        result.setTotal(Integer.parseInt(Long.toString(rows.getTotalElements())));
        result.setData(rows.getContent());
        return result;
    }
    @RequestMapping("/saveAll")
    @ResponseBody
    public successResult SaveAll(String Data){
        successResult result=new successResult();
        try {
            List<tbDictData> Ds= JSON.parseArray(Data,tbDictData.class);
            for(int i=0;i<Ds.size();i++){
                tbDictData dd=Ds.get(i);
                if(dd.getFid()==null){
                    dd.setFid(dataRep.getMax());
                }
            }
            dataRep.saveAll(Ds);
        }
        catch(Exception ax){
            result.raiseException(ax);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/getAllByDtId")
    public List<TreeListItem> getAllByDtId(int dtId) {
        return itbDictDataService.getAllByDtID(dtId);
    }

    @RequestMapping("/getByDtId")
    @ResponseBody
    public List<ComboboxItem> getByDtId(int dtId) {
        return itbDictDataService.getByDtId(dtId);
    }
}
