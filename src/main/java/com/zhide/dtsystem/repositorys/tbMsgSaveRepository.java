package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbMsgSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface tbMsgSaveRepository extends JpaRepository<tbMsgSave, Integer> {

}
