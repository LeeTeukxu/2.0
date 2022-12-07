package com.zhide.dtsystem.services.define;

public interface IStateCounter {
    boolean accept(Integer State);

    Integer getNumber();
}
