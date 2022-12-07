package com.zhide.dtsystem.viewModel;

/**
 * @ClassName: ManagerPermissonResult
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年01月06日 10:39
 **/
public class ManagerPermissonResult {
    private String Dep;
    private String Man;
    private Integer Manager;

    public Integer getManager() {
        return Manager;
    }

    public void setManager(Integer manager) {
        Manager = manager;
    }

    public String getDep() {
        return Dep;
    }

    public void setDep(String dep) {
        Dep = dep;
    }

    public String getMan() {
        return Man;
    }

    public void setMan(String man) {
        Man = man;
    }
}
