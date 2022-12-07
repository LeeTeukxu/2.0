package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.models.tbAttachment;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * @ClassName: ITicketService
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年01月12日 19:59
 **/
public interface ITicketService {
    tbAttachment AddTickFile(MultipartFile file) throws Exception;

    pageObject getData(HttpServletRequest request) throws Exception;

    List<String> GetImages(String FileID) throws Exception;

    File Download(List<String> Codes) throws Exception;

    void Remove(List<String> IDS) throws Exception;
}
