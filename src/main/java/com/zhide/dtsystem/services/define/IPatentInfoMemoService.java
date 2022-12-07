package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.PantentInfoMemo;

import java.util.List;

public interface IPatentInfoMemoService {
    boolean SaveAll(List<PantentInfoMemo> Infos) throws Exception;
    boolean RemoveAll(List<String> IDS) throws Exception;
    void  SaveImage(PantentInfoMemo memo) throws Exception;
    List<String> getImages(String MID) throws Exception;
}
