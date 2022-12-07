package com.zhide.dtsystem.repositorys;

import com.zhide.dtsystem.models.emailTemplateParameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface emailTemplateParametersRepository  extends  JpaRepository<emailTemplateParameters,Integer>  {
    List<emailTemplateParameters> findAllByPid(int PID);
}
