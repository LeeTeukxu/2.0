package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.models.smtpAccount;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SmtpAccountMapper {
    @Select("SELECT * FROM SmtpAccount WHERE UserId=#{UserId}")
    Optional<smtpAccount> getAllByUserId(Integer UserId);
}
