package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.ComboboxItem;
import com.zhide.dtsystem.models.TreeListItem;
import com.zhide.dtsystem.models.tbDictData;
import com.zhide.dtsystem.repositorys.tbDictDataRepository;
import com.zhide.dtsystem.services.define.ItbDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class tbDictDataServiceImpl implements ItbDictDataService {
    @Autowired
    tbDictDataRepository tbDictDataRepository;

    @Override
    @Cacheable(value = "getDictDataAllByDtID")
    public List<TreeListItem> getAllByDtID(int dtId) {
        List<TreeListItem> results = new ArrayList<>();
        List<tbDictData> items = tbDictDataRepository.findAllByDtIdAndCanUseTrueOrderBySn(dtId);
        items.stream().forEach(f -> {
            TreeListItem item = new TreeListItem();
            item.setPid(Integer.toString(f.getPid()));
            item.setId(Integer.toString(f.getFid()));
            item.setText(f.getName());
            results.add(item);
        });
        return results;
    }

    @Cacheable(value = "getByDtId")
    public List<ComboboxItem> getByDtId(int dtId) {
        List<ComboboxItem> results = new ArrayList<>();
        List<tbDictData> items = tbDictDataRepository.findAllByDtIdAndCanUseTrueOrderBySn(dtId);
        items.stream().forEach(f -> {
            ComboboxItem item = new ComboboxItem();
            item.setId(Integer.toString(f.getFid()));
            item.setText(f.getName());
            results.add(item);
        });
        return results;
    }
}
