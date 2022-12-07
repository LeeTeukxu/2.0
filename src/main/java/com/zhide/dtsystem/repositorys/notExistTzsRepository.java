package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.notExistTzs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface notExistTzsRepository extends JpaRepository<notExistTzs, String> {

}
