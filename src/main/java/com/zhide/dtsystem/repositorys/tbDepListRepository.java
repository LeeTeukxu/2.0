package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbDepList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface tbDepListRepository extends JpaRepository<tbDepList, Integer> {
    @Query(value = "Select max(isnull(DepID,0))+1 as Num from tbDepList", nativeQuery = true)
    Integer getMax();

    List<tbDepList> getAllByCanUse(boolean canUse);

    @Query(value = "SELECT a.Name,a.PID,a.DepID,a.Sort, (SELECT COUNT(*) FROM v_LoginUser AS b WHERE a.DepID=b.DepID)" +
            " AS Num FROM tbDepList AS a", nativeQuery = true)
    List<Map<String, Object>> getAllByCanUseAndDepNum();

    List<tbDepList> findAllByDepId(int depId);

    @Query(value = "Select FID,PID,Name from v_depEmployee", nativeQuery = true)
    List<Map<String, Object>> getAllUsersInDep();

    @Query(value = "Select CompanyID from DTSystem.dbo.tbCompanyList Where CompanyName=:Name", nativeQuery = true)
    String getCompanyIdbyName(@Param(value = "Name") String depName);

    @Query(value = "Select FID,PID,Name from View_AllLoginUserInDep", nativeQuery = true)
    List<Map<String, Object>> getAllLoginUserInDep();
    @Query(value = "SELECT FID,PID,Name from View_AllLoginUserInDepNotSelf WHERE UserID<>:UserID", nativeQuery = true)
    List<Map<String, Object>> getAllLoginUserInDepNotSelf(@Param(value = "UserID") Integer UserID);
    @Query(value="Select * from dbo.getAllUsersByFunName(:FunName)",nativeQuery = true)
    List<Map<String,Object>> getAllLoginUserByFun(String FunName);
}
