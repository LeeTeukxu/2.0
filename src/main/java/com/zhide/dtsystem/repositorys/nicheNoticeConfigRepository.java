package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.NiCheNoticeConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface nicheNoticeConfigRepository extends  JpaRepository<NiCheNoticeConfig,Integer>  {
}
