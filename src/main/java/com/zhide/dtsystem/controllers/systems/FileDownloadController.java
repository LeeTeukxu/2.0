package com.zhide.dtsystem.controllers.systems;

import com.zhide.dtsystem.common.ListUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.tbAttachment;
import com.zhide.dtsystem.repositorys.tbAttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: FileDownloadController
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2022年06月20日 9:23
 **/
@Controller
@RequestMapping("/fileDownload")
public class FileDownloadController {

    @Autowired
    tbAttachmentRepository attRep;

    @RequestMapping("/index")
    public String Index(String AttIDS,String ZipFileName, Map<String,Object> model){
        model.put("AttIDS",AttIDS);
        model.put("ZipFileName",ZipFileName);
        return "/systems/download/index";
    }
    @RequestMapping("/getData")
    @ResponseBody
    public pageObject getData(String AttIDS){
        pageObject result=new pageObject();
        try {
            List<String> IDS= ListUtils.parse(AttIDS,String.class);
            List<tbAttachment> atts=attRep.findAllByGuidIn(IDS);
            result.setData(atts);
            result.setTotal(atts.size());
        }
        catch(Exception ax){
            result.raiseException(ax);

        }
        return result;
    }
}
