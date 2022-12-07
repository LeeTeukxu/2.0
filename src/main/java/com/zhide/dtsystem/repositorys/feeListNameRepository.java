package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.feeListName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface feeListNameRepository extends JpaRepository<feeListName, Integer> {
    feeListName findFirstByJfqdId(String jfqd);
}
