package com.zhide.dtsystem.controllers.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhide.dtsystem.common.*;
import com.zhide.dtsystem.mapper.ClientInfoMapper;
import com.zhide.dtsystem.mapper.ClientLinkWindowMapper;
import com.zhide.dtsystem.models.LoginUserInfo;
import com.zhide.dtsystem.models.productItemType;
import com.zhide.dtsystem.models.tbClient;
import com.zhide.dtsystem.models.tbClientLinkers;
import com.zhide.dtsystem.multitenancy.CompanyContext;
import com.zhide.dtsystem.repositorys.productItemTypeRepository;
import com.zhide.dtsystem.repositorys.tbAttachmentRepository;
import com.zhide.dtsystem.repositorys.tbClientRepository;
import com.zhide.dtsystem.services.define.IClientInfoService;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/common/excelImport")
public class ExcelImportController {

    @Autowired
    ClientInfoMapper clientInfoMapper;

    @Autowired
    ClientLinkWindowMapper clientLinkWindowMapper;
    @Autowired
    productItemTypeRepository productItemTypeRepository;

    @Autowired
    tbAttachmentRepository attachmentRep;
    @Autowired
    IClientInfoService clientService;

    Logger logger= LoggerFactory.getLogger(ExcelImportController.class);
    private UploadUtils getUtils() throws Exception {
        LoginUserInfo info = CompanyContext.get();
        if (info == null) throw new Exception("?????????????????????????????????");
        String companyId = info.getCompanyId();
        return UploadUtils.getInstance(companyId);
    }

    @RequestMapping("/selectXls")
    public @ResponseBody
    successResult selectXls(MultipartFile file) {
        successResult result = new successResult();
        FileInputStream fileInputStream;
        try {
            String fileName = file.getOriginalFilename();
            String[] exts = fileName.split("\\.");
            String extName = exts[exts.length - 1];
            String uuId = UUID.randomUUID().toString();
            String uploadFileName = uuId + "." + extName;
            String targetFile = CompanyPathUtils.getFullPath("Temp", uploadFileName);
            File fx = new File(targetFile);
            FileUtils.writeByteArrayToFile(fx, file.getBytes());
            if (fx.exists()) {
                try {
                    fileInputStream = new FileInputStream(fx);
                    UploadUtils uploadUtils = getUtils();
                    uploadFileResult rr = uploadUtils.uploadAttachment(uploadFileName, fileInputStream);
                    fileInputStream.close();
                    result.setData(jxXLS(targetFile));
                    result.setSuccess(true);
                } catch (Exception ax) {
                    result.raiseException(ax);
                } finally {
                    FileUtils.forceDeleteOnExit(fx);
                }
            } else throw new Exception("????????????????????????!");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/selectProductItemTypeXls")
    public @ResponseBody
    successResult selectProductItemTypeXls(MultipartFile file) {
        successResult result = new successResult();
        FileInputStream fileInputStream;
        try {
            String fileName = file.getOriginalFilename();
            String[] exts = fileName.split("\\.");
            String extName = exts[exts.length - 1];
            String uuId = UUID.randomUUID().toString();
            String uploadFileName = uuId + "." + extName;
            String targetFile = CompanyPathUtils.getFullPath("Temp", uploadFileName);
            File fx = new File(targetFile);
            FileUtils.writeByteArrayToFile(fx, file.getBytes());
            if (fx.exists()) {
                try {
                    fileInputStream = new FileInputStream(fx);
                    UploadUtils uploadUtils = getUtils();
                    uploadFileResult rr = uploadUtils.uploadAttachment(uploadFileName, fileInputStream);
                    fileInputStream.close();
                    result.setData(jxProductItemTypeXLS(targetFile));
                    result.setSuccess(true);
                } catch (Exception ax) {
                    result.raiseException(ax);
                } finally {
                    FileUtils.forceDeleteOnExit(fx);
                }
            } else throw new Exception("????????????????????????!");
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    private List<Map<String, String>> jxXLS(String filePath) {
        List<Map<String, String>> list = null;
        try {
            Workbook wb = null;
            Sheet sheet = null;
            Row row = null;
            String cellData = null;
            String columns[] = {"????????????", "????????????", "???????????????", "???????????????", "????????????", "????????????", "????????????", "??????", "????????????"};
            //String columns[] = {"Name","Type","CType","LinkMan","Mobile","Address","LinkPhone","Memo","Email"};
            wb = readExcel(filePath);
            if (wb != null) {
                //????????????????????????
                list = new ArrayList<Map<String, String>>();
                //???????????????sheet
                sheet = wb.getSheetAt(0);
                //??????????????????
                int rownum = sheet.getPhysicalNumberOfRows();
                //???????????????
                row = sheet.getRow(0);
                //??????????????????????????????
                int forRows = 2;
                //??????????????????
                int colnum = row.getPhysicalNumberOfCells();
                for (int i = forRows; i <= rownum; i++) {
                    Map<String, String> map = new LinkedHashMap<String, String>();
                    row = sheet.getRow(i);
                    if (row != null) {
                        for (int j = 0; j < colnum; j++) {
                            cellData = (String) getCellFormatValue(row.getCell(j));
                            String rowNameEng = getRowNameEng(columns[j]);
                            //?????????????????????????????????????????????????????????
                            if (rowNameEng.equals("Mobile")) {
                                if (StringUtils.isEmpty(cellData)==false) {
                                    cellData=cellData.trim()
                                            .replaceAll(" ","")
                                            .replaceAll("\\p{C}","")
                                            .replaceAll("\\u00A0+", "")
                                            .replace("-","");
                                    logger.info(cellData);
                                    BigDecimal bigDecimal = new BigDecimal(StringUtils.trimAllWhitespace(cellData));
                                    cellData = bigDecimal.toPlainString();
                                }
                            }
                            if (rowNameEng.equals("LinkPhone")) {
                                cellData = cellData.split("\\.")[0];
                            }
                            map.put(rowNameEng, cellData);
                        }
                    } else {
                        break;
                    }
                    list.add(map);
                }
            }
        } catch (Exception ax) {
            ax.printStackTrace();
        }
        return list;
    }

    private List<Map<String, String>> jxProductItemTypeXLS(String filePath) {
        List<Map<String, String>> list = null;
        try {
            Workbook wb = null;
            Sheet sheet = null;
            Row row = null;
            String cellData = null;
            String columns[] = {"????????????", "????????????", "????????????", "????????????", "??????????????????", "??????"};
            wb = readExcel(filePath);
            if (wb != null) {
                //????????????????????????
                list = new ArrayList<Map<String, String>>();
                //???????????????sheet
                sheet = wb.getSheetAt(0);
                //??????????????????
                int rownum = sheet.getPhysicalNumberOfRows();
                //???????????????
                row = sheet.getRow(0);
                //??????????????????????????????
                int forRows = 2;
                //??????????????????
                int colnum = row.getPhysicalNumberOfCells();
                for (int i = forRows; i <= rownum; i++) {
                    Map<String, String> map = new LinkedHashMap<String, String>();
                    row = sheet.getRow(i);
                    if (row != null) {
                        for (int j = 0; j < colnum; j++) {
                            cellData = (String) getCellFormatValue(row.getCell(j));
                            String rowNameEng = getProductItemTypeRowNameEng(columns[j]);
                            map.put(rowNameEng, cellData);
                        }
                    } else {
                        break;
                    }
                    list.add(map);
                }
            }
        } catch (Exception ax) {
        }
        return list;
    }

    //??????excel
    public static Workbook readExcel(String filePath) {
        Workbook wb = null;
        if (filePath == null) {
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if (".xls".equals(extString)) {
                return wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {
                return wb = new XSSFWorkbook(is);
            } else {
                return wb = null;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    public static Object getCellFormatValue(Cell cell) {
        Object cellValue = null;
        if (cell != null) {
            //??????cell??????
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC: {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                }
                case Cell.CELL_TYPE_FORMULA: {
                    //??????cell?????????????????????
                    if (DateUtil.isCellDateFormatted(cell)) {
                        //?????????????????????YYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    } else {
                        //??????
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING: {
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }

    public String getRowNameEng(String rowNameChn) {
        String rowNameEng = "";
        switch (rowNameChn) {
            case "????????????":
                rowNameEng = "Name";
                break;
            case "????????????":
                rowNameEng = "Type";
                break;
            case "???????????????":
                rowNameEng = "CType";
                break;
            case "???????????????":
                rowNameEng = "LinkMan";
                break;
            case "????????????":
                rowNameEng = "Mobile";
                break;
            case "????????????":
                rowNameEng = "Address";
                break;
            case "????????????":
                rowNameEng = "LinkPhone";
                break;
            case "??????":
                rowNameEng = "Memo";
                break;
            case "????????????":
                rowNameEng = "Email";
                break;
            default:
                rowNameEng = "";
                break;
        }
        return rowNameEng;
    }

    public String getProductItemTypeRowNameEng(String rowNameChn) {
        String rowNameEng = "";
        switch (rowNameChn) {
            case "????????????":
                rowNameEng = "Name";
                break;
            case "????????????":
                rowNameEng = "Type";
                break;
            case "????????????":
                rowNameEng = "Cost";
                break;
            case "????????????":
                rowNameEng = "Price";
                break;
            case "??????????????????":
                rowNameEng = "Required";
                break;
            case "??????":
                rowNameEng = "Memo";
                break;
            default:
                rowNameEng = "";
                break;
        }
        return rowNameEng;
    }

    @RequestMapping("/downLoadClientTemplate")
    public void downLoadClientTemplate(String Code, String File, HttpServletResponse response) {
//        try{
//            String SavePath=CompanyPathUtils.getTempPath("ClientTemplate","client.xls");
//            FTPUtil ftpUtil=new FTPUtil();
//            if (ftpUtil.connect()==true){
//                ftpUtil.download("ClientTemplate/client.xls",SavePath);
//                WebFileUtils.download("????????????.xls",new File(SavePath),response);
//            }else response.getWriter().write("FTP????????????");
//        }catch (Exception ax){
//            try {
//                response.getWriter().write(ax.getMessage());
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }
        try {
            File fx = ResourceUtils.getFile("classpath:static/template/" + Code + ".xls");
            WebFileUtils.download(File, fx, response);
        } catch (Exception ax) {
            try {
                response.getWriter().write("<script type='text/javascript'>alert('?????????????????????" + ax.getMessage() + "');" +
                        "</script>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping("/save")
    public @ResponseBody
    successResult save(HttpServletRequest request) {
        String Type = request.getParameter("Type");
        successResult result = new successResult();
        LoginUserInfo loginUserInfo = CompanyContext.get();
        Date nowTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String Data = request.getParameter("Data");
            Data = Data.substring(0, Data.length() - 1);
            Data = Data.substring(1, Data.length());
            //for (int i=0;i<saveData.length;i++){
            JSONArray jsonArray = (JSONArray) JSONArray.parse("[" + Data + "]");
            Map<String,Object> OO= clientService.SaveAll(Type,jsonArray);
            result.setData(OO);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }

    @RequestMapping("/saveProductItemType")
    public @ResponseBody
    successResult saveProductItemType(HttpServletRequest request) {
        successResult result = new successResult();
        LoginUserInfo loginUserInfo = CompanyContext.get();
        Date nowTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String Data = request.getParameter("Data");
            Data = Data.substring(0, Data.length() - 1);
            Data = Data.substring(1, Data.length());
            JSONArray jsonArray = (JSONArray) JSONArray.parse("[" + Data + "]");
            for (Object object : jsonArray) {
                JSONObject jo = (JSONObject) object;
                if (jo.getString("IsErrorResult").equals("????????????")) {
                    productItemType productItemType = new productItemType();
                    productItemType.setFid(UUID.randomUUID().toString());
                    productItemType.setName(jo.getString("Name"));
                    productItemType.setType(jo.getString("Type"));
                    productItemType.setCost(Double.parseDouble(jo.getString("Cost")));
                    productItemType.setPrice(Double.parseDouble(jo.getString("Price")));
                    productItemType.setRequired(jo.getString("Required"));
                    productItemType.setMemo(jo.getString("Memo"));
                    productItemType.setCreateTime(new Date());
                    productItemType.setCreateMan(Integer.parseInt(loginUserInfo.getUserId()));
                    productItemTypeRepository.save(productItemType);
                }
            }
            result.setData(Data);
        } catch (Exception ax) {
            result.raiseException(ax);
        }
        return result;
    }
}
