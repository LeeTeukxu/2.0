package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface tbAttachmentRepository  extends  JpaRepository<tbAttachment,Integer>  {
    List<tbAttachment> findAllByGuidInOrderByUploadTime(List<String>IDS);
    List<tbAttachment> findAllByGuidInAndType(List<String> IDS,Integer Type);
    List<tbAttachment> findAllByGuidIn(List<String>IDS);
    Optional<tbAttachment> findFirstByGuid(String AttID);
    void deleteAllByGuid(String guId);
    Optional<tbAttachment> findAllByGuid(String GUID);
    int deleteAllByGuidIn(List<String> attIds);
}
