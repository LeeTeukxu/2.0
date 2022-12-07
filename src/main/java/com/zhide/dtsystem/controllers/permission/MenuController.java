package com.zhide.dtsystem.controllers.permission;

import com.zhide.dtsystem.common.IntegerUtils;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.tbMenuList;
import com.zhide.dtsystem.services.define.ItbMenuListService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/permission/menu")
public class MenuController {

    @Autowired
    private ItbMenuListService itbMenuListService;

    @RequestMapping("/index")
    public String index() {
        return "/permission/menu/index";
    }

    @RequestMapping("/getAll")
    @ResponseBody
    public List<tbMenuList> getAll() {
        return itbMenuListService.getAll();
    }

    @RequestMapping("/saveAll")
    @ResponseBody
    public successResult saveAll(@RequestBody List<tbMenuList> Datas) {
        successResult result = new successResult();
        try {
            itbMenuListService.saveAll(Datas);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/removeAll")
    @ResponseBody
    public successResult remove(@RequestBody List<Integer> Ids) {
        successResult result = new successResult();
        try {
            itbMenuListService.removeOne(Ids);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/getAllCanUse")
    @ResponseBody
    public List<tbMenuList> getAllByCanUse() throws Exception {
        List<tbMenuList> Menus = itbMenuListService.getAllByCanUse();
        List<tbMenuList> notExists = new ArrayList<>();
        List<tbMenuList> KS = Menus.stream().filter(f -> f.getPid() > 0).collect(Collectors.toList());
        for (int i = 0; i < KS.size(); i++) {
            tbMenuList M = KS.get(i);
            Optional<tbMenuList> finds = Menus.stream().filter(x -> x.getFid().equals(M.getPid())).findFirst();
            if (finds.isPresent() == false) {
                notExists.add(M);
            }
        }
        Menus.removeAll(notExists);
        return Menus;
    }

    @RequestMapping("/choice")
    public String Choice(Map<String, Object> model) {
        List<String> images = new ArrayList<>();
        try {
            URL u = ResourceUtils.getURL("classpath:static/images");
            File F = ResourceUtils.getFile(u);
            File[] Files = F.listFiles();
            for (int i = 0; i < Files.length; i++) {
                File x = Files[i];
                if (x.isFile()) {
                    String ext = FilenameUtils.getExtension(x.getPath()).toLowerCase();
                    if (ext.equals("png")) {
                        images.add(x.getName());
                    }
                }
            }
            model.put("images", images);
            int Max = IntegerUtils.parseInt(Math.ceil(images.size() / 5)) + 1;
            int nowSize = images.size();
            for (int i = 0; i < Max * 5 - nowSize; i++) {
                images.add("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/permission/menu/choiceImages";
    }
}
