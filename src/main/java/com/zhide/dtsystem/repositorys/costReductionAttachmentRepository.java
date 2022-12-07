package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.costReductionAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface costReductionAttachmentRepository extends  JpaRepository<costReductionAttachment,Integer>  {
    int deleteAllByUUId(String UUID);
    List<costReductionAttachment> findAllByUUId(String UUID);
    List<costReductionAttachment> getAllByUUIdIn(List<String> UUID);
    List<costReductionAttachment> getAllByUUIdIn(String[] UUIDS);
}
