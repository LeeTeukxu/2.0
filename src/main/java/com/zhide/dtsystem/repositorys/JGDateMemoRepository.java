package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.JGDateMemo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JGDateMemoRepository extends JpaRepository<JGDateMemo, Integer> {

}
