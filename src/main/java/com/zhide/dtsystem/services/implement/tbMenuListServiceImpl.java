package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.tbMenuList;
import com.zhide.dtsystem.models.tbRoleFunctionSave;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.tbMenuListRepository;
import com.zhide.dtsystem.repositorys.tbRoleFunctionSaveRepository;
import com.zhide.dtsystem.services.CompanyTimeOutCache;
import com.zhide.dtsystem.services.define.ItbMenuListService;
import com.zhide.dtsystem.services.define.ItbMenuPermissionService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class tbMenuListServiceImpl implements ItbMenuListService {
    @Autowired
    private tbMenuListRepository tbMenuListRepository;
    @Autowired
    ItbMenuPermissionService menuPermissionService;
    @Autowired
    tbRoleFunctionSaveRepository tbRoleFunSaveRep;
    @Autowired
    CompanyTimeOutCache menuCache;
    @Autowired
    StringRedisTemplate redisRep;

    @Override
    public List<tbMenuList> getAll() {
        return tbMenuListRepository.findAll(Sort.by("Sn"));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<tbMenuList> saveAll(List<tbMenuList> tbMenuLists) {
        List<tbMenuList> res = new ArrayList<>();
        for (int i = 0; i < tbMenuLists.size(); i++) {
            tbMenuList tb = tbMenuLists.get(i);
            tbMenuList t = tbMenuListRepository.save(tb);
            res.add(t);
        }
        RemoveAllKeys();
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeOne(List<Integer> Ids) {
        for (int i = 0; i < Ids.size(); i++) {
            int Id = Ids.get(i);
            tbMenuListRepository.deleteById(Id);
        }
        RemoveAllKeys();
        return true;
    }

    public List<tbMenuList> getAllByCanUse() throws Exception {
        List<tbMenuList> menus = tbMenuListRepository.findAllByCanUseTrueOrderBySn();
        int count = menus.size();
        for (int i = 0; i < count; i++) {
            tbMenuList item = menus.get(i);
            String url = item.getUrl();
            if (StringUtils.isEmpty(url)) continue;
            if (url.indexOf("?") > -1) {
                String[] texts = url.split("\\?");
                String baseUrl = texts[0];
                String paramters = texts[1];
                String newParamter = urlDecode(paramters);
                item.setUrl(baseUrl + "?" + newParamter);
            }
        }
        return menus;
    }

    @Override
    public List<tbMenuList> getVisibleMenus(int roleId) {
        List<tbMenuList> res = new ArrayList<>();
        String Key = "VisibleMenu:" + Integer.toString(roleId);
        menuCache.setTimeOut(6000000L);
        if (menuCache.hasExpire(Key)) {
            res = getVisible(roleId);
            menuCache.setValue(Key, res);
        } else res = menuCache.getArray(Key, tbMenuList.class);
        return res;
    }

    private List<tbMenuList> getVisible(int roleId) {
        List<tbMenuList> res = new ArrayList<>();
        List<tbMenuList> menus = null;
        try {
            menus = getAllByCanUse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<tbRoleFunctionSave> allFuns = tbRoleFunSaveRep.findAllByRoleId(roleId);
        List<Integer> allMenus = allFuns.stream()
                .filter(f -> f.getFunId() == 3)
                .map(f -> f.getMenuId())
                .distinct().collect(Collectors.toList());
        if (roleId == 2) {
            List<Integer> noPages = menus.stream()
                    .filter(f -> StringUtils.isEmpty(f.getPageName()) && StringUtils.isEmpty(f.getUrl()) == false)
                    .map(x -> x.getFid())
                    .collect(Collectors.toList());
            allMenus.addAll(noPages);
        }
        for (int i = 0; i < allMenus.size(); i++) {
            Integer MenuID = allMenus.get(i);
            Optional<tbMenuList> Fx = menus.stream().filter(f -> f.getFid().equals(MenuID)).findFirst();
            if (Fx.isPresent()) {
                tbMenuList F = Fx.get();
                res.add(F);
                Optional<tbMenuList> FPS = menus.stream().filter(f -> f.getFid().equals(F.getPid())).findFirst();
                if (FPS.isPresent()) {
                    tbMenuList P = FPS.get();
                    List<tbMenuList> ps = res.stream().filter(f -> f.getFid().equals(P.getFid()))
                            .collect(Collectors.toList());
                    if (ps.size() == 0) {
                        res.add(P);
                    }
                }
            }
        }
        res.sort((a, b) -> Integer.compare(Integer.parseInt(a.getSn()), Integer.parseInt(b.getSn())));
        return res;
    }

    private String urlDecode(String paramText) throws UnsupportedEncodingException {
        String[] VS = paramText.split("&");
        List<String> ps = new ArrayList<>();
        for (int i = 0; i < VS.length; i++) {
            String V = VS[i];
            String[] values = V.split("=");
            String newText = values[0] + '=' + URLEncoder.encode(values[1], "utf-8");
            ps.add(newText);
        }
        return Strings.join(ps, '&');
    }

    private void RemoveAllKeys() {
        LoginUserInfo Info = CompanyContext.get();
        if (Info != null) {
            List<String> Keys = Arrays.asList("getAllMenuListInfo", "getAllMenuListByCanUse", "getAllByCanUse");
            for (int i = 0; i < Keys.size(); i++) {
                String Key = Keys.get(i);
                String DeleteKey = Key + "::" + Info.getCompanyId();
                redisRep.delete(DeleteKey);
            }
            List<String> XKeys = redisRep
                    .keys(Info.getCompanyId() + "_VisibleMenu:*")
                    .stream()
                    .collect(Collectors.toList());
            XKeys.stream().forEach(f -> redisRep.delete(f));
        }
    }
}
