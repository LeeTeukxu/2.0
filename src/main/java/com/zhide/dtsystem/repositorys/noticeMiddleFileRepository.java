package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.noticeMiddleFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface noticeMiddleFileRepository  extends  JpaRepository<noticeMiddleFile,Integer>  {
    List<noticeMiddleFile> findAllByTzsbh(String tzsbh);
    List<noticeMiddleFile> findAllByTzsbhIn(String[] tzsbhs);
    void deleteAllByAttid(String attId);
}
