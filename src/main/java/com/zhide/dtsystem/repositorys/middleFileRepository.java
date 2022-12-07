package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.middleFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface middleFileRepository extends JpaRepository<middleFile, Integer> {
    List<middleFile> findAllByIdAndType(Integer Id, String Type);

    void deleteAllByAttid(String attId);
}
