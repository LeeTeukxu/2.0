package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.patentInfoPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface patentInfoPermissionRepository  extends  JpaRepository<patentInfoPermission,Integer>  {
    List<patentInfoPermission> findAllByShenqinghAndUsertypeAndUserid (String shenqingh,String userType,Integer userId);
    List<patentInfoPermission> findAllByShenqinghAndUsertype(String shenqingh,String userType);
    List<patentInfoPermission> findAllByUsertypeAndUserid(String type,int userId);
    int  deleteAllByShenqingh(String shenqingh);
    int deleteAllByShenqinghAndUsertypeAndUserid(String shenqingh,String userType,int userId);
}
