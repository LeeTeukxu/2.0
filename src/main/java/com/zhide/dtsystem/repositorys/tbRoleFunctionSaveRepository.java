package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbRoleFunctionSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface tbRoleFunctionSaveRepository  extends  JpaRepository<tbRoleFunctionSave,Integer>  {
    List<tbRoleFunctionSave> findAllByRoleId(int roleid);
}
