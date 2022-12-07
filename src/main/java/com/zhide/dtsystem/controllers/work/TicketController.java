package com.zhide.dtsystem.controllers.work;

import com.zhide.dtsystem.common.WebFileUtils;
import com.zhide.dtsystem.common.pageObject;
import com.zhide.dtsystem.common.successResult;
import com.zhide.dtsystem.models.ComboboxItem;
import com.zhide.dtsystem.models.tbAttachment;
import com.zhide.dtsystem.models.tickets;
import com.zhide.dtsystem.repositorys.tbAttachmentRepository;
import com.zhide.dtsystem.repositorys.ticketsRepository;
import com.zhide.dtsystem.services.define.ITicketService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: TicketController
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年01月12日 9:22
 **/
@Controller
@RequestMapping("/work/ticket")
public class TicketController {
    @Autowired
    ITicketService ticketService;
    @Autowired
    ticketsRepository ticketRep;
    @Autowired
    tbAttachmentRepository attRep;

    @RequestMapping("/index")
    public String Index() {
        return "/work/ticket/index";
    }

    @ResponseBody
    @RequestMapping("/upload")
    public successResult upload(MultipartFile file) {
        successResult result = new successResult();
        try {
            if (file == null) {
                throw new Exception("票据文件格式异常。");
            }
            String fileName = file.getOriginalFilename();
            if (fileName.toLowerCase().indexOf("zip") == -1) {
                throw new Exception("票据文件只能是以'zip'结尾的压缩文件!");
            }
            tbAttachment obj = ticketService.AddTickFile(file);
            result.setData(obj);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/getData")
    @ResponseBody
    public pageObject GetData(HttpServletRequest request) {
        pageObject obj = new pageObject();
        try {
            obj = ticketService.getData(request);
        } catch (Exception ex) {
            obj.raiseException(ex);
        }
        return obj;
    }

    @ResponseBody
    @RequestMapping("/getImages")
    public Map<String, Object> GetImages(String ID) {
        Map<String, Object> res = new HashMap<>();
        try {
            List<Map<String, Object>> OO = new ArrayList<>();
            res.put("status", 1);
            res.put("start", 0);
            List<String> IDS = Arrays.stream(ID.split(",")).collect(Collectors.toList());
            for (int i = 0; i < IDS.size(); i++) {
                Integer XID = Integer.parseInt(IDS.get(i));
                Optional<tickets> finds = ticketRep.findById(XID);
                if (finds.isPresent()) {
                    tickets One = finds.get();
                    List<String> Files = ticketService.GetImages(One.getPdf());
                    if (Files.size() > 0) {
                        for (int n = 0; n < Files.size(); n++) {
                            String Src = Files.get(n);
                            Map<String, Object> OX = new HashMap<>();
                            OX.put("src", Src);
                            OX.put("thumb", "");
                            OX.put("alt", "第" + Integer.toString(OO.size() + 1) + "个文件");
                            OO.add(OX);
                        }
                    }
                }
            }
            res.put("data", OO);
            if (OO.size() == 0) throw new Exception("没有可查看的通知书附件。");
        } catch (Exception ax) {
            res.put("status", 0);
            res.put("message", ax.getMessage());
        }
        return res;
    }

    @ResponseBody
    @RequestMapping("/getFilesByID")
    public successResult GetFilesByID(String IDS) {
        successResult result = new successResult();

        try {
            List<ComboboxItem> items = new ArrayList<>();
            List<String> IDD = Arrays.stream(IDS.split(",")).collect(Collectors.toList());
            for (int i = 0; i < IDD.size(); i++) {
                Integer ID = Integer.parseInt(IDD.get(i));
                Optional<tickets> findTickets = ticketRep.findById(ID);
                if (findTickets.isPresent()) {
                    tickets ticket = findTickets.get();
                    Optional<tbAttachment> tbs = attRep.findAllByGuid(ticket.getPdf());
                    if (tbs.isPresent()) {
                        tbAttachment tb = tbs.get();
                        ComboboxItem item = new ComboboxItem();
                        item.setText(tb.getName());
                        item.setId(tb.getPath());
                        items.add(item);
                    }
                }
            }
            result.setData(items);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public successResult Delete(String IDS) {
        successResult result = new successResult();
        try {
            ticketService.Remove(Arrays.stream(IDS.split(",")).collect(Collectors.toList()));
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/download")
    public void Download(HttpServletResponse response, String Code, String FileName) {
        String[] Codes = Code.split(",");
        File targetFile = null;
        try {
            List<String> Atts = new ArrayList<>();
            for (int i = 0; i < Codes.length; i++) {
                String xCode = Codes[i];
                Optional<tickets> tp = ticketRep.findById(Integer.parseInt(xCode));
                tickets find = tp.get();
                Atts.add(find.getPdf());
            }
            targetFile = ticketService.Download(Atts);
            WebFileUtils.download(FileName, targetFile, response);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.getWriter().write(e.getMessage());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (targetFile != null) {
                    FileUtils.forceDelete(targetFile);
                }
            } catch (Exception ax) {

            }

        }
    }
}
