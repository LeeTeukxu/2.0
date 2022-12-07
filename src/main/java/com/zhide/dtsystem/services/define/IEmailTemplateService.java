package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.TreeListItem;

import java.io.IOException;
import java.util.List;

public interface IEmailTemplateService {
    boolean AddNew(String Code, String Name) throws IOException;

    List<TreeListItem> getEmailTemplateTypes();
}
