package com.zhide.dtsystem.services.implement;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhide.dtsystem.common.ListUtils;
import com.zhide.dtsystem.common.StringUtilTool;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.TreeNode;
import com.zhide.dtsystem.models.tbDepList;
import com.zhide.dtsystem.models.tbEmployee;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.tbDepListRepository;
import com.zhide.dtsystem.repositorys.tbEmployeeRepository;
import com.zhide.dtsystem.services.define.ItbDepListService;
import freemarker.template.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class tbDepListServiceImpl implements ItbDepListService {
    @Autowired
    StringRedisTemplate redisRep;
    @Autowired
    private tbDepListRepository tbDepListRepository;
    @Autowired
    private tbEmployeeRepository employeeRepository;

    @Override
    public List<tbDepList> getAll() {
        return tbDepListRepository.findAll();
    }

    @Override
    @CacheEvict(value = "getAllCanuDepList", keyGenerator = "CompanyKeyGenerator")
    public List<tbDepList> getAllCanUse() {
        return tbDepListRepository.getAllByCanUse(true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveAll(List<Map<String, Object>> datas) {
        Map<Integer, Integer> KeyHash = new HashMap<>();
        for (int i = 0; i < datas.size(); i++) {
            tbDepList depInfo = new tbDepList();
            Map<String, Object> data = datas.get(i);
            int depId = 0;
            if (data.containsKey("depId") == false) {
                int Id = Integer.parseInt(data.get("_id").toString());
                depId = tbDepListRepository.getMax();
                KeyHash.put(Id, depId);
            } else {
                depId = Integer.parseInt(data.get("depId").toString());
            }
            depInfo.setDepId(depId);
            int pid = 0;
            if (data.containsKey("pid")) {
                pid = Integer.parseInt(data.get("pid").toString());
            } else {
                int ppId = Integer.parseInt(data.get("_pid").toString());
                if (KeyHash.containsKey(ppId)) pid = KeyHash.get(ppId);
            }
            depInfo.setPid(pid);
            depInfo.setCanUse(Boolean.parseBoolean(data.get("canUse").toString()));
            depInfo.setName(StringUtilTool.getValue(data.get("name")));
            depInfo.setMemo(StringUtilTool.getValue(data.get("memo")));
            depInfo.setSn(Integer.parseInt(data.get("sn").toString()));

            String pSort = StringUtil.leftPad(Integer.toString(pid), 6, "0");
            List<tbDepList> parentInfos = tbDepListRepository.findAllByDepId(pid);
            if (parentInfos.size() > 0) {
                tbDepList parent = parentInfos.get(0);
                pSort = parent.getSort();
            }
            String dSort = StringUtil.leftPad(Integer.toString(depId), 6, "0");
            depInfo.setSort(pSort + dSort);

            tbDepListRepository.save(depInfo);
        }
        RemoveAllKeys();
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeAll(List<Integer> ids) {
        for (int i = 0; i < ids.size(); i++) {
            Integer id = ids.get(i);
            List<tbDepList> deps = tbDepListRepository.findAllByDepId(id);
            if (deps.size() == 1) {
                tbDepList depInfo = deps.get(0);
                List<tbEmployee> finds = employeeRepository.findAllByDepId(id);
                if (finds.size() > 0) {
                    throw new IllegalArgumentException("部门:" + depInfo.getName() + "下还有人员，无法删除。");
                }
                tbDepListRepository.delete(depInfo);
            }
        }
        RemoveAllKeys();
        return true;
    }

    @Cacheable(value = "getAllUsersByDep", keyGenerator = "CompanyKeyGenerator")
    @Override
    public List<TreeNode> getAllUsersByDep() {
        List<Map<String, Object>> OO = tbDepListRepository.getAllUsersInDep();
        List<TreeNode> Nodes = JSON.parseArray(JSON.toJSONString(OO), TreeNode.class);
        return Nodes;
    }

    @Cacheable(value = "getAllLoginUserInDep", keyGenerator = "CompanyKeyGenerator")
    @Override
    public List<TreeNode> getAllLoginUserInDep() {
        List<Map<String, Object>> OO = tbDepListRepository.getAllLoginUserInDep();
        List<TreeNode> Nodes = JSON.parseArray(JSON.toJSONString(OO), TreeNode.class);
        return Nodes;
    }
    @Override
    public List<TreeNode> getAllLoginUserInDepNotSelf(Integer UserID) {
        List<Map<String, Object>> OO = tbDepListRepository.getAllLoginUserInDepNotSelf(UserID);
        List<TreeNode> Nodes = JSON.parseArray(JSON.toJSONString(OO), TreeNode.class);
        return Nodes;
    }

    @Override
    public List<Map<String, Object>> getAllByCanUseAndDepNum() {
        return tbDepListRepository.getAllByCanUseAndDepNum();
    }

    private void RemoveAllKeys() {
        List<String> Keys = Arrays.asList("getAllLoginUserInDep", "getAllUsersByDep", "getAllCanuDepList",
                "getAllLoginUserByFun");
        LoginUserInfo Info = CompanyContext.get();
        for (int i = 0; i < Keys.size(); i++) {
            String Key = Keys.get(i);
            String RKey = Key + "::" + Info.getCompanyId();
            redisRep.delete(RKey);
        }
    }

    private Optional<tbDepList> FindNodeByDepID(List<tbDepList> Nodes, Integer ID) {
        return Nodes.stream().filter(f -> f.getDepId() == ID).findFirst();
    }

    public Map<Integer, Integer> GetEmployeeNumbers() throws Exception {
        Map<Integer, Integer> Res = new HashMap<>();
        List<tbDepList> Deps = tbDepListRepository.getAllByCanUse(true);
        List<Map<String, Object>> AllNums = tbDepListRepository.getAllByCanUseAndDepNum();
        List<Map<String, Object>> Nums =
                AllNums.stream().filter(f -> Integer.parseInt(f.get("Num").toString()) > 0).collect(Collectors.toList());
        List<tbDepList> Parents = new ArrayList<>();
        Nums.stream().forEach(f -> {
            Integer DepID = Integer.parseInt(f.get("DepID").toString());
            Optional<tbDepList> findSingle = FindNodeByDepID(Deps, DepID);// Deps.stream().filter(x -> x.getDepId()
            // == DepID).findFirst();
            if (findSingle.isPresent()) {
                tbDepList tb = findSingle.get();
                tb.setNum(Integer.parseInt(f.get("Num").toString()));
                Optional<tbDepList> PNodes = FindNodeByDepID(Deps, tb.getPid());
                if (PNodes.isPresent()) {
                    Optional<tbDepList> findOne = FindNodeByDepID(Parents, tb.getPid());// Parents.stream().filter(y
                    // -> y.getDepId() == ParentID).findFirst();
                    if (findOne.isPresent() == false) Parents.add(PNodes.get());
                }
            }
        });
        List<tbDepList> NS = ListUtils.clone(Parents).stream()
                .sorted((a, b) -> -Integer.compare(a.getSort().length(), b.getSort().length()))
                .collect(Collectors.toList());
        while (true) {
            Parents.clear();
            for (int i = 0; i < NS.size(); i++) {
                tbDepList Parent = NS.get(i);
                Integer DepID = Parent.getDepId();
                tbDepList RealOne = FindNodeByDepID(Deps, DepID).get();
                Integer Total = Deps.stream().filter(f -> f.getPid() == DepID).mapToInt(f -> f.getNum()).sum();
                RealOne.setNum(Total);
                Optional<tbDepList> findOne = FindNodeByDepID(Deps, Parent.getPid()); //Deps.stream().filter(y -> y
                // .getDepId() == Parent.getPid()).findFirst();
                if (findOne.isPresent() == true) {
                    tbDepList PS = findOne.get();
                    if (FindNodeByDepID(Parents, PS.getDepId()).isPresent() == false) Parents.add(PS);
                }
            }
            if (Parents.size() > 0) NS = ListUtils.clone(Parents);
            else break;
        }
        Deps.stream().filter(f -> f.getNum() > 0).forEach(f -> {
            Res.put(f.getDepId(), f.getNum());
        });
        return Res;
    }

    public List<Map<String, Object>> getAllLoginUserByFun(String FunName) {
        List<Map<String, Object>> Data = new ArrayList<>();
        LoginUserInfo Info = CompanyContext.get();
        String Key = "getAllLoginUserByFun::" + Info.getCompanyId()+"::"+FunName;
        if(redisRep.hasKey(Key)){
            String X=redisRep.opsForValue().get(Key);
            Data= JSON.parseObject(X, new TypeReference<List<Map<String, Object>>>(){});
        } else {
           Data= tbDepListRepository.getAllLoginUserByFun(FunName);
           redisRep.opsForValue().set(Key,JSON.toJSONString(Data));
        }
        return Data;
    }
}
