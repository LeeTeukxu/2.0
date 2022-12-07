package com.zhide.dtsystem.services.implement;

import com.zhide.dtsystem.common.DepDataHelper;
import com.zhide.dtsystem.common.RedisUtils;
import com.zhide.dtsystem.mapper.SysLoginUserMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.TreeNode;
import com.zhide.dtsystem.models.managerPermission;
import com.zhide.dtsystem.models.tbDepList;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.managerPermissionRepository;
import com.zhide.dtsystem.repositorys.tbDepListRepository;
import com.zhide.dtsystem.services.define.IManagerPermissionService;
import com.zhide.dtsystem.viewModel.ManagerPermissonResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: ManagerPermissionServiceImpl
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年01月05日 13:52
 **/
@Service
public class ManagerPermissionServiceImpl implements IManagerPermissionService {
    @Autowired
    tbDepListRepository depRep;
    @Autowired
    SysLoginUserMapper loginUserMapper;
    @Autowired
    managerPermissionRepository mpRep;
    @Autowired
    RedisUtils redisUtils;

    @Override
    public List<TreeNode> GetTree() {
        List<TreeNode> Res = new ArrayList<>();

        List<Map<String, Object>> AllManager = loginUserMapper.getAllManagers("经理");
        List<Integer> DepIDS =
                AllManager.stream().map(f -> Integer.parseInt(f.get("DepID").toString())).collect(Collectors.toList());
        List<Integer> AllIDS = DepDataHelper.getAllParent(DepIDS);

        List<tbDepList> AllDeps = depRep.findAllById(AllIDS);
        for (int i = 0; i < AllDeps.size(); i++) {
            tbDepList dep = AllDeps.get(i);
            TreeNode PNode = new TreeNode();
            PNode.setFID("D:" + dep.getDepId());
            PNode.setPID("D:" + dep.getPid());
            PNode.setName(dep.getName());


            Integer DepID = dep.getDepId();
            List<Map<String, Object>> Mans = AllManager.stream()
                    .filter(f -> Integer.parseInt(f.get("DepID").toString()) == DepID)
                    .collect(Collectors.toList());
            for (int n = 0; n < Mans.size(); n++) {
                Map<String, Object> Man = Mans.get(n);
                TreeNode Node = new TreeNode();
                Node.setFID(Man.get("UserID").toString());
                Node.setPID("D:" + DepID);
                Node.setName(Man.get("EmpName").toString());
                Res.add(Node);
            }
            Res.add(PNode);
        }
        return Res;
    }

    public List<TreeNode> GetAllUserTree() {
        List<TreeNode> Res = new ArrayList<>();

        List<Map<String, Object>> AllManager = loginUserMapper.getAllUsers();
        List<Integer> DepIDS =
                AllManager.stream().map(f -> Integer.parseInt(f.get("DepID").toString())).collect(Collectors.toList());
        List<Integer> AllIDS = DepDataHelper.getAllParent(DepIDS);

        List<tbDepList> AllDeps = depRep.findAllById(AllIDS);
        for (int i = 0; i < AllDeps.size(); i++) {
            tbDepList dep = AllDeps.get(i);
            TreeNode PNode = new TreeNode();
            PNode.setFID("D:" + dep.getDepId());
            PNode.setPID("D:" + dep.getPid());
            PNode.setName(dep.getName());


            Integer DepID = dep.getDepId();
            List<Map<String, Object>> Mans = AllManager.stream()
                    .filter(f -> Integer.parseInt(f.get("DepID").toString()) == DepID)
                    .collect(Collectors.toList());
            for (int n = 0; n < Mans.size(); n++) {
                Map<String, Object> Man = Mans.get(n);
                TreeNode Node = new TreeNode();
                Node.setFID(Man.get("UserID").toString());
                Node.setPID("D:" + DepID);
                Node.setName(Man.get("EmpName").toString());
                Res.add(Node);
            }
            List<tbDepList> childs = AllDeps.stream().filter(f -> f.getPid() == DepID).collect(Collectors.toList());
            if (Mans.size() == 0) {
                if (childs.size() > 0) Res.add(PNode);
            } else Res.add(PNode);
        }
        return Res;
    }

    @Override
    public List<managerPermission> GetSavedConfig(Integer UserID) {
        return mpRep.findAllByUserId(UserID);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void SaveAll(ManagerPermissonResult result) {
        String XDep = result.getDep();
        LoginUserInfo Info = CompanyContext.get();
        mpRep.deleteAllByUserId(result.getManager());
        if (StringUtils.isEmpty(XDep) == false) {
            String[] Deps = XDep.trim().split(",");
            List<managerPermission> ps = new ArrayList<>();
            for (int i = 0; i < Deps.length; i++) {
                Integer DepID = Integer.parseInt(Deps[i]);
                managerPermission permission = new managerPermission();
                permission.setUserId(result.getManager());
                permission.setValue(DepID);
                permission.setType("Dep");
                permission.setCreateMan(Info.getUserIdValue());
                permission.setCreateTime(new Date());
                ps.add(permission);
            }
            mpRep.saveAll(ps);
        }
        String XMan = result.getMan();
        if (StringUtils.isEmpty(XMan) == false) {
            String[] Mans = XMan.trim().split(",");
            List<managerPermission> ms = new ArrayList<>();
            for (int i = 0; i < Mans.length; i++) {
                Integer ManID = Integer.parseInt(Mans[i]);
                managerPermission permission = new managerPermission();
                permission.setUserId(result.getManager());
                permission.setValue(ManID);
                permission.setType("Man");
                permission.setCreateMan(Info.getUserIdValue());
                permission.setCreateTime(new Date());
                ms.add(permission);
            }
            mpRep.saveAll(ms);
        }
        redisUtils.clearAll("getAllManagerPermisson");
    }

    @Cacheable(value = "getAllManagerPermisson", keyGenerator = "CompanyKeyGenerator")
    public List<managerPermission> getAll() {
        return mpRep.findAll();
    }
}
