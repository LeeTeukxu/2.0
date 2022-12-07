package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.productItemType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface productItemTypeRepository  extends  JpaRepository<productItemType,Integer>  {
    Optional<productItemType> findFirstByFid(String FID);
    Page<productItemType> findAllByNameLike(String Name,Pageable pageable);
}
