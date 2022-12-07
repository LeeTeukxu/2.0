package com.zhide.dtsystem.models;

public class UInfo {
    public UInfo(String N, int D) {
        setName(N);
        setID(D);
    }

    String Name;
    int ID;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
