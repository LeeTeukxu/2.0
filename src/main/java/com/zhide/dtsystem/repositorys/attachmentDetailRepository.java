package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.attachmentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface attachmentDetailRepository  extends  JpaRepository<attachmentDetail,Integer>  {

}
