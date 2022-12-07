package com.zhide.dtsystem.services.instance;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.EntityHelper;
import com.zhide.dtsystem.models.sqwj;
import com.zhide.dtsystem.repositorys.ajRepository;
import com.zhide.dtsystem.repositorys.sqwjRepository;
import com.zhide.dtsystem.services.define.IZlInfoUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Component
public class SQWJInfoUpdater implements IZlInfoUpdater {
    @Autowired
    sqwjRepository sqwjRep;
    @Autowired
    ajRepository ajRep;

    @Override
    public boolean accept(String filePath) {
        File file = new File(filePath);
        return file.getName().toLowerCase().equals("sqwj.txt");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(String fileContent) throws Exception {
        List<sqwj> datas = JSON.parseArray(fileContent, sqwj.class);
        for (int i = 0; i < datas.size(); i++) {
            sqwj s = datas.get(i);
            int wenjianbh = s.getWenjianbh();
            Optional<sqwj> find = sqwjRep.findById(wenjianbh);
            sqwj result = s;
            if (find.isPresent()) {
                result = find.get();
                EntityHelper.copyObject(s, result);
            }
            sqwjRep.save(result);
        }
        return datas.size();
    }
}
