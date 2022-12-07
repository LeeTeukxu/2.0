package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.casesYwAccept;

import java.util.List;

public interface IShenBaoDetailService {
    boolean SaveAll(List<casesYwAccept> datas) throws Exception;

    boolean RemoveAll(Integer IDS) throws Exception;
}
