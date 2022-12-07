package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.tbResignationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface tbResignationRepository extends JpaRepository<tbResignationRecord,Integer> {
}
