package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.TreeNode;
import com.zhide.dtsystem.models.managerPermission;
import com.zhide.dtsystem.viewModel.ManagerPermissonResult;

import java.util.List;

public interface IManagerPermissionService {
    List<TreeNode> GetTree();

    List<TreeNode> GetAllUserTree();

    List<managerPermission> GetSavedConfig(Integer UserID);

    void SaveAll(ManagerPermissonResult result);

    List<managerPermission> getAll();
}
