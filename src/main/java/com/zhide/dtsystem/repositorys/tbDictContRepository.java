package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbDictCont;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface tbDictContRepository extends JpaRepository<tbDictCont, Integer> {

}
