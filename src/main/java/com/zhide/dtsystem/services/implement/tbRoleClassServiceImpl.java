package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.common.RedisUtils;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.TreeListItem;
import com.zhide.dtsystem.models.tbRoleClass;
import com.zhide.dtsystem.repositorys.tbRoleClassRepository;
import com.zhide.dtsystem.services.define.ItbRoleClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class tbRoleClassServiceImpl implements ItbRoleClassService {
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    private SysLoginUserMapper sysMapper;
    @Autowired
    private tbRoleClassRepository tbRoleClassRepository;

    @Override
    @Cacheable(value = "getAllRolesList", keyGenerator = "CompanyKeyGenerator")
    public List<tbRoleClass> getAll() {
        return tbRoleClassRepository.findAll();
    }

    @Override
    @Cacheable(value = "getAllCanUseRoles", keyGenerator = "CompanyKeyGenerator")
    public List<TreeListItem> getAllCanuseItems(boolean canUse) {
        List<tbRoleClass> rs = tbRoleClassRepository.findAllByCanUseTrue();
        List<TreeListItem> items = new ArrayList<>();
        for (int i = 0; i < rs.size(); i++) {
            tbRoleClass c = rs.get(i);
            boolean exists = items.stream().anyMatch(f -> f.getId().equals(Integer.toString(c.getRoleId())));
            if (exists == false) {
                TreeListItem item = new TreeListItem();
                item.setId(Integer.toString(c.getRoleId()));
                item.setPid(Integer.toString(c.getPid()));
                item.setText(c.getRoleName());
                items.add(item);
            }
        }
        return items;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveAll(List<tbRoleClass> items) {
        for (int i = 0; i < items.size(); i++) {
            tbRoleClass item = items.get(i);
            tbRoleClassRepository.save(item);
        }
        redisUtils.clearAll("Role");
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeAll(List<Integer> ids) {
        for (int i = 0; i < ids.size(); i++) {
            Integer id = ids.get(i);
            tbRoleClassRepository.deleteById(id);
        }
        redisUtils.clearAll("Role");
        return true;
    }

    @Override
    @Cacheable(value = "getLoginUserByRole", keyGenerator = "CompanyKeyGenerator")
    public List<Map<String, Object>> getAllUserByRole(Integer RoleID) {
        return sysMapper.getAllUserByRole(RoleID);
    }
}
