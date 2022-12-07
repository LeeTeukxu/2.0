package com.zhide.dtsystem.services.define;

public interface ICasesAJDetailService {
    boolean Remove(String AJID);

    boolean RemoveAjAttachment(String AJID, String AttID);
}
