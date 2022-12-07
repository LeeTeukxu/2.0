package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbMenuList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface tbMenuListRepository extends JpaRepository<tbMenuList, Integer> {
    List<tbMenuList> findAllByCanUseTrueOrderBySn();

    @Query(value = "Select Title from tbMenuList  Where FID=:Fid", nativeQuery = true)
    String getTitleByFid(Integer Fid);
}
