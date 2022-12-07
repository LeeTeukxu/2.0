package com.zhide.dtsystem.models;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class NBBHInfo {
    List<UInfo> LC;
    List<UInfo> YW;
    List<UInfo> DL;
    List<UInfo> JS;
    List<UInfo> KH;
    List<UInfo> BH;

    public List<UInfo> getBH() {
        return BH;
    }

    public void setBH(List<UInfo> BH) {
        this.BH = BH;
    }

    public List<UInfo> getZC() {
        return ZC;
    }

    public void setZC(List<UInfo> ZC) {
        this.ZC = ZC;
    }

    List<UInfo> ZC;

    public boolean isDecodeAll() {
        return decodeAll;
    }

    public void setDecodeAll(boolean decodeAll) {
        this.decodeAll = decodeAll;
    }


    boolean decodeAll;

    Map<String, List<UInfo>> all = new HashMap<>();

    public NBBHInfo() {
        LC = new ArrayList<UInfo>();
        YW = new ArrayList<UInfo>();
        DL = new ArrayList<UInfo>();
        JS = new ArrayList<UInfo>();
        KH = new ArrayList<UInfo>();
        ZC = new ArrayList<UInfo>();
        BH = new ArrayList<UInfo>();
        decodeAll = true;
    }

    public void foreach(BiConsumer<String, List<UInfo>> f) {
        all.put("LC", LC);
        all.put("XS", YW);
        all.put("DL", DL);
        all.put("JS", JS);
        all.put("KH", KH);
        all.put("ZC", ZC);
        all.put("BH", BH);
        all.forEach(f);
    }

    public void SetValue(String Type, List<UInfo> us) {
        if (Type.equals("YW") || Type.equals("XS")) setYW(us);
        else if (Type.equals("LC")) setLC(us);
        else if (Type.equals("DL")) setDL(us);
        else if (Type.equals("KH")) setKH(us);
        else if (Type.equals("JS")) setJS(us);
        else if (Type.equals("ZC")) setZC(us);
        else if (Type.equals("BH")) setBH(us);
    }

    public List<UInfo> getLC() {
        return LC;
    }

    public void setLC(List<UInfo> LC) {
        this.LC = LC;
    }

    public List<UInfo> getYW() {
        return YW;
    }

    public void setYW(List<UInfo> YW) {
        this.YW = YW;
    }

    public List<UInfo> getDL() {
        return DL;
    }

    public void setDL(List<UInfo> DL) {
        this.DL = DL;
    }

    public List<UInfo> getJS() {
        return JS;
    }

    public void setJS(List<UInfo> JS) {
        this.JS = JS;
    }

    public List<UInfo> getKH() {
        return KH;
    }

    public void setKH(List<UInfo> KH) {
        this.KH = KH;
    }

}
