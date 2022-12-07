package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbMenuPermissionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface tbMenuPermissionRepository extends JpaRepository<tbMenuPermissionItem,Integer> {
    @Query(value="Select a.ID as id ,a.FunID as funId,a.MenuID as menuId,b.Title as [text],b.Name as [name] from tbPermissionItem a inner join FunctionPermissionList b " +
            "on a.FunID=b.FID Where b.CanUse=1 And a.MenuID=:MenuID Order by b.SN",nativeQuery =true)
    List<Map<String,Object>> findAllMenusByMenID(@Param(value = "MenuID")int menuId);
    void deleteByMenuId(int menuId);
    List<tbMenuPermissionItem> findAllByMenuId(Integer menuId);
}
