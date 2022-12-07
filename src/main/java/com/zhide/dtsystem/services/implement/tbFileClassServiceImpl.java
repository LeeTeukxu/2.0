package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.tbFileClassRepository;
import com.zhide.dtsystem.repositorys.tbFileUploadRepository;
import com.zhide.dtsystem.repositorys.tbRoleClassRepository;
import com.zhide.dtsystem.services.define.ItbFileClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName: tbFileClassServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年11月15日 13:41
 **/
@Service
public class tbFileClassServiceImpl implements ItbFileClassService {
    @Autowired
    private tbFileClassRepository fileClassRep;
    @Autowired
    private tbFileUploadRepository fileUploadRep;

    @Override
    public List<tbFileClass> getAll() {
        return fileClassRep.findAll();
    }

    @Override
    public List<TreeListItem> getAllCanuseItems(boolean canUse) {
        List<tbFileClass> rs = fileClassRep.findAllByCanUseIsTrue();
        List<TreeListItem> items = new ArrayList<>();
        for (int i = 0; i < rs.size(); i++) {
            tbFileClass c = rs.get(i);
            boolean exists = items.stream().anyMatch(f -> f.getId().equals(Integer.toString(c.getId())));
            if (exists == false) {
                TreeListItem item = new TreeListItem();
                item.setId(Integer.toString(c.getId()));
                item.setPid(Integer.toString(c.getPid()));
                item.setText(c.getTypeName());
                item.setName(Integer.toString(fileUploadRep.countAllByTypeId(c.getId())));
                items.add(item);
            }
        }
        return items;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveAll(List<tbFileClass> items) {
        LoginUserInfo Info= CompanyContext.get();
        for (int i = 0; i < items.size(); i++) {
            tbFileClass item = items.get(i);
            if(item.getCreateMan()==null){
                item.setCreateMan(Info.getUserIdValue());
                item.setCreateTime(new Date());
                item.setCanUse(true);
            }
            else {
                Optional<tbFileClass> findClasss=fileClassRep.findById(item.getId());
                if(findClasss.isPresent()){
                    tbFileClass cc= findClasss.get();
                    cc.setTypeName(item.getTypeName());
                    cc.setCanUse(item.getCanUse());
                    cc.setSn(item.getSn());
                    cc.setMemo(item.getMemo());

                    items.set(i,cc);
                }
            }
        }
        fileClassRep.saveAll(items);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeAll(List<Integer> ids) throws Exception {
        List<tbFileClass> cs=new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            Integer id = ids.get(i);
            Optional<tbFileClass> findFiles=fileClassRep.findById(id);
            if(findFiles.isPresent()){
                tbFileClass f= findFiles.get();;
               List<tbFileUpload> Us= fileUploadRep.findAllByTypeId(id);
               if(Us.size()>0){
                   throw new Exception(f.getTypeName()+"已被引用。无法删除!");
               }
               cs.add(f);
            }
        }
        fileClassRep.deleteAll(cs);
        return true;
    }

}
