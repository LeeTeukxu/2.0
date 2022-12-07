package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.models.ComboboxItem;
import com.zhide.dtsystem.models.TZS;
import com.zhide.dtsystem.repositorys.TZSRepository;
import com.zhide.dtsystem.services.define.ITZSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TZSServiceImpl implements ITZSService {

    @Autowired
    TZSRepository tzsRep;

    @Override
    public List<ComboboxItem> getItemsByCode(String[] codes) {
        List<ComboboxItem> result = new ArrayList<ComboboxItem>();
        List<TZS> tzsList = tzsRep.findAllByTongzhisbhIn(codes);
        for (int i = 0; i < tzsList.size(); i++) {
            TZS tzs = tzsList.get(i);
            ComboboxItem item = new ComboboxItem();
            item.setId(tzs.getTzspath());
            item.setText(tzs.getFamingmc() + "(" + tzs.getTongzhismc()+")");
            result.add(item);
        }
        return result;
    }
}
