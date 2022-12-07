package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.zhide.dtsystem.common.RedisUtils;
import com.zhide.dtsystem.models.ComboboxItem;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.tbCLevel;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.casesSubRepository;
import com.zhide.dtsystem.repositorys.tbCLevelRepository;
import com.zhide.dtsystem.services.define.ICLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName: CLevelServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年10月26日 15:07
 **/
@Service
public class CLevelServiceImpl implements ICLevelService {
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    tbCLevelRepository cLevelRep;
    @Autowired
    casesSubRepository subRep;

    @Override
    @Cacheable(value = "getCLevelItems", keyGenerator = "CompanyKeyGenerator")
    public List<ComboboxItem> getItems() {
        List<ComboboxItem> items = new ArrayList<>();
        List<tbCLevel> Cs = cLevelRep.findAll(Sort.by("sn"));
        for (tbCLevel tb : Cs) {
            ComboboxItem item = new ComboboxItem();
            item.setId(Integer.toString(tb.getId()));
            item.setText(tb.getName());
            items.add(item);
        }
        return items;
    }

    @Override
    @Transactional
    public int RemoveOne(int ID) throws Exception {
        Optional<tbCLevel> findOnes = cLevelRep.findById(ID);
        if (findOnes.isPresent()) {
            tbCLevel level = findOnes.get();
            int Count = subRep.countAllByCLevel(ID);
            if (Count > 0) throw new Exception(level.getName() + "已在交单中被引用。无法删除!");
            cLevelRep.delete(level);
            redisUtils.clearAll("CLevelItems");
        } else throw new Exception("准备删除的项目已不存在!");
        return 0;
    }

    @Override
    public void Save(String Data) {
        LoginUserInfo Info = CompanyContext.get();
        List<tbCLevel> Cs = JSON.parseArray(Data, tbCLevel.class);
        for (tbCLevel level : Cs) {
            if (level.getId() == null) {
                level.setCreateMan(Info.getUserIdValue());
                level.setCreateTime(new Date());
            }
        }
        cLevelRep.saveAll(Cs);
        redisUtils.clearAll("CLevelItems");
    }
}
