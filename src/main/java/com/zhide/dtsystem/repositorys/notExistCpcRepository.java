package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.notExistCpc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface notExistCpcRepository extends JpaRepository<notExistCpc, String> {

}
