package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbLoginUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface tbLoginUserRepository extends JpaRepository<tbLoginUser, Integer> {
    tbLoginUser findAllByLoginCode(String loginCode);

    tbLoginUser findAllByLoginCodeAndUserIdIsNot(String loginCode, Integer userId);

    List<tbLoginUser> findAllByDepIdIn(List<Integer> deps);

    tbLoginUser findAllByUserId(Integer UserID);
}

