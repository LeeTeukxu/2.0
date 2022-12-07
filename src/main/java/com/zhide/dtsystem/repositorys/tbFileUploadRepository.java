package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface tbFileUploadRepository  extends  JpaRepository<tbFileUpload,Integer>  {
    List<tbFileUpload> findAllByTypeId(Integer TypeID);
    Page<tbFileUpload> getAllByTypeId(Integer TypeID, Pageable pageable);
    Integer countAllByTypeId(Integer ID);
}
