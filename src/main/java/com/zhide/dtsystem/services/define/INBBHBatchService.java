package com.zhide.dtsystem.services.define;

import java.util.Map;

public interface INBBHBatchService {
    Map<String, Object> SaveAll(String OldText, String NowText);
}
