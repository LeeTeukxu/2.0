package com.zhide.dtsystem;

import com.alibaba.fastjson.JSON;
import com.aspose.words.*;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.sun.media.jai.codecimpl.TIFFCodec;
import com.sun.media.jai.codecimpl.TIFFImage;
import com.sun.media.jai.codecimpl.TIFFImageDecoder;
import com.sun.media.jai.codecimpl.TIFFImageEncoder;
import com.sun.media.jai.util.ImageUtil;
import com.zhide.dtsystem.common.*;
import com.zhide.dtsystem.models.*;
import com.zhide.dtsystem.services.instance.CPCPackageParsor;
import com.zhide.dtsystem.services.instance.XmlParsor;
import com.zhide.dtsystem.viewModel.feeItem;
import com.zhide.dtsystem.viewModel.feeObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.multipdf.PDFCloneUtility;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDFormContentStream;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.visible.PDFTemplateCreator;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.*;
import org.apache.tomcat.jni.FileInfo;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import sun.misc.BASE64Decoder;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.*;
import java.util.function.Function;


import java.io.FileOutputStream;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

public class methodTestMain {
    static Logger logger = LoggerFactory.getLogger(methodTestMain.class);
    static List<String> FiveName = Arrays.asList("说明书摘要", "摘要附图", "权利要求书", "说明书", "说明书附图");

    public static void main(String[] args) throws IOException {
        try {
            //PdfUtils.fromDirectory("C:\\tmp\\GA000320880778","c:\\tmp\\20221017.pdf");
           System.out.println(Integer.valueOf("2.00"));
        }
        catch(Exception ax){
            ax.printStackTrace();
        }

    }
    public static String decrypt(String Value,String Key) throws Exception{
        try {
            byte[] VV=Key.substring(0,24).getBytes(StandardCharsets.UTF_8);
            // 生成key
            SecretKey secretKey = new SecretKeySpec(VV, 0, 24, "DESede");
            // 加密向量
            byte[] ivv = GetIV("603dd05d-941d-42c9-9c51-dae26acb4533");
            IvParameterSpec iv = new IvParameterSpec(ivv);
            // 通过Chipher执行加密得到的是一个byte的数组
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

            BASE64Decoder base64decoder = new BASE64Decoder();
            byte[] plainTextBytes = base64decoder.decodeBuffer(Value);
            //byte[] plainTextBytes = Value.getBytes(StandardCharsets.UTF_8);
            byte[] cipherText = cipher.doFinal(plainTextBytes);
            // 通过base64,将加密数组转换成字符串
            return new String(cipherText, "utf-8");
        }
        catch (Exception e) {
            System.out.printf("3des解密失败：" + Value, e);
            return null;
        }
    }
    public static String encrypt(String Value, String IV, String Key) {
        try {
            byte[] VV=Key.substring(0,24).getBytes(StandardCharsets.UTF_8);
            // 生成key
            SecretKey secretKey = new SecretKeySpec(VV, 0, 24, "DESede");
            // 加密向量
            byte[] ivv = GetIV(IV);
            IvParameterSpec iv = new IvParameterSpec(ivv);
            // 通过Chipher执行加密得到的是一个byte的数组
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] plainTextBytes = Value.getBytes("utf-8");
            byte[] cipherText = cipher.doFinal(plainTextBytes);
            // 通过base64,将加密数组转换成字符串
            return Base64Utils.decode(cipherText);
        }
        catch (Exception e) {
            System.out.print("3des加密失败：" + Value+e.getMessage());
            return null;
        }
    }

    private static byte[] GetIV(String iv) {
        // 获取距离八位有多长
        int needzeronum = 8 - iv.length();
        if (needzeronum > 0) {
            // 少的补充长度
            iv = iv + String.format("%0" + needzeronum + "d", 0);
        }
        else {
            // 多的截取后八位
            iv = iv.substring(iv.length() - 8);
            // System.out.println(iv);
        }
        return iv.getBytes(StandardCharsets.UTF_8);
    }
    public static void removeBlank(Document document) {
        for (Section section : document.getSections()) {
            for (Paragraph paragraph : section.getBody().getParagraphs()) {
                boolean flag = false;
                if (paragraph.getChildNodes(NodeType.SHAPE, true).getCount() == 0) {
                    flag = true;
                }
                RunCollection runs = paragraph.getRuns();
                if (flag) {
                    //首先去除各个部分的转义字符，如果删除之后run为空则去除
                    for (Run run : runs) {
                        try {
                            run.setText(run.getText().replaceAll("[\f|\r|\n]", ""));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    //删除空白的paragraph，如果有图片则不删除，
                    String content = StringUtils.clean(paragraph.getText());
                    if (StringUtils.isBlank(content)) {
                        section.getBody().getParagraphs().remove(paragraph);
                    }
                }
            }
        }
    }

    private static void bbbb() {
        String tId = "GA000264831656";
        File saveDir = new File("D:\\Upload\\0011\\Notice\\GA000264831656");
        Collection<File> findFiles = FileUtils.listFiles(saveDir, new String[]{"xml"}, true);
        Optional<File> targetFs =
                findFiles.stream().filter(f -> f.getName().startsWith(tId)).findFirst();
        if (targetFs.isPresent()) {
            logger.info("找到了!");
        } else {
            logger.info("没有找到!");
        }

    }

    private static void aaaa() {
        try {
            Integer X = 1 / 0;
        } catch (Exception ax) {
            StringWriter sw = new StringWriter();
            PrintWriter s = new PrintWriter(sw);
            ax.printStackTrace(s);
            System.out.println(sw);
        }

    }

    private static void ConvertToJpg() {
        File file = new File("C:\\TEMP\\a.pdf");
        PDDocument doc = null;
        PDFRenderer renderer = null;
        try {

            doc = PDDocument.load(file);
            renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            for (int i = 0; i < pageCount; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 36);
                ImageIO.write(image, "jpg", new File("C:\\TEMP\\" + i + ".jpg"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (doc != null) {
                try {
                    doc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static void ExcelTest(int Count) {
        FileInputStream fx = null;
        try {
            int startRow = 4;
            fx = new FileInputStream(new File("C:\\TEMP\\OneYear.xls"));
            Workbook book = new HSSFWorkbook(fx);
            Sheet sheet = book.getSheetAt(0);
            sheet.shiftRows(4, sheet.getLastRowNum(), Count, true, false);
            Row sourceRow = sheet.getRow(3);
            for (int i = startRow; i < startRow + Count; i++) {
                Row row = sheet.createRow(i);
                for (int n = 0; n < sourceRow.getLastCellNum(); n++) {
                    Cell cell = row.createCell(n);
                    CellStyle cellStyle = sourceRow.getCell(n).getCellStyle();
                    cell.setCellStyle(cellStyle);
                }
            }

            FileOutputStream fd = new FileOutputStream(new File("C:\\TEMP\\22.xls"));
            book.write(fd);
            fd.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fx.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static void FunTest() {
        Function<String, Integer> Fx = (a) -> Integer.parseInt(a) + 5;
        System.out.println(Fx.apply("999"));

    }

    private static void getAnno() {
        PatentInfoExcelTemplate excelTemplate = new PatentInfoExcelTemplate();
        Class<? extends PatentInfoExcelTemplate> cc = excelTemplate.getClass();
        ExcelTitle title = cc.getDeclaredAnnotation(ExcelTitle.class);
        if (title != null) {
            logger.info(title.value());
        }
        Field[] fields = cc.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String name = field.getName();
            ExcelColumn column = field.getDeclaredAnnotation(ExcelColumn.class);
            if (column != null) {
                logger.info(column.title() + ":" + name);
            }
        }
    }

    private static void doSomeThing() {
        String unZipDir = "D:\\Upload\\0001\\Temp\\10763750-a7f8-4556-a585-e976e24bdab6";
        CPCPackageParsor packageParsor = new CPCPackageParsor(unZipDir);
        if (packageParsor.canWork()) {
            pantentInfo parseObj = packageParsor.getPatentInfo();
        }

    }

    private static void CreateExcel() {
        List<pantentInfo> ps = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            pantentInfo P = new pantentInfo();
            P.setShenqingbh("1111111111111" + i);
            P.setShenqingh("2222222222222" + i);
            P.setFamingmc("sdfdsfdsfdsfds1");
            P.setChuangjianr(new Date());
            P.setFamingrxm("肖新民");
            P.setAnjuanh("123131231");
            P.setDailijgmc("刘丽丹");
            P.setShenqinglx(1);
            P.setShenqingr(new Date());
            P.setShenqingrxm("大大的中国");
            ps.add(P);
        }
        List<IExcelExportTemplate> rows = new ArrayList<>();
        long begin = new Date().getTime();
        for (int i = 0; i < 1000; i++) {
            pantentInfo P = ps.get(i);
            PatentInfoExcelTemplate px = new PatentInfoExcelTemplate();
            try {
                px = EntityHelper.copyObject(P, PatentInfoExcelTemplate.class);
                rows.add(px);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        long end = new Date().getTime();
        logger.info("Use:" + (end - begin));
        ExcelFileBuilder builder = null;
        try {
            builder = new ExcelFileBuilder(rows);
            byte[] datas = builder.export();
            FileOutputStream fx = new FileOutputStream("D:\\aabb.xls");
            fx.write(datas);
            fx.close();
            logger.info("生成文件成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void FtpTest() {

        Integer XX = Integer.parseInt("5689900");
        //System.out.println(XX.getClass().isPrimitive());
        boolean XV = ClassUtils.isPrimitiveOrWrapper(XX.getClass());
        //System.out.println("XX:"+XV);
        /*tbMenuList info=new tbMenuList();
        info.setFid(1111);
        info.setPid(222);
        info.setCanUse(true);
        info.setTitle("5555");
        info.setUrl("55667788");
        try {
          Map<String,Object>  OO= EntityHelper.toMap(info);
          for(int i=0;i<10;i++) {
              OO.replace("title",Integer.toString(i)+"667788");
              long X1 = System.currentTimeMillis();
              tbMenuList X = EntityHelper.parseFrom(OO, tbMenuList.class);
              long X2 = System.currentTimeMillis();

              long X3 = X2 - X1;
              System.out.println("Object Value:"+X.getTitle()+",第"+Integer.toString(i+1)+"次:"+Long.toString(X3));
          }
           // Stream.iterate(5,n->n+55).limit(50).forEach(f->System.out.println(Integer.toString(f)));
        }catch(Exception ax){
            ax.printStackTrace();
        }*/
        FtpPath ftpPath = new FtpPath("0001");
        FTPUtil ftpUtil = new FTPUtil();
        if (ftpUtil.connect() == true) {
            logger.info("FTP Server登录成功!");
            boolean px = ftpUtil.existDirectory("0001");
            logger.info("exist 0001:" + px);
            logger.info("exist 0001/CPC:" + ftpUtil.existDirectory(ftpPath.getCpcPath()));
            ftpUtil.createSubfolder(ftpPath.getCpcPath(), "XXM");
            File f = new File("d:\\aa.zip");
            FileInputStream stream = null;
            try {

                // logger.info("exist file:"+Boolean.toString(f.exists()));
                //stream=new FileInputStream(f.getPath());
                //boolean OK=ftpUtil.upload(stream,"aa.zip",ftpPath.getCpcPath("XXM"));
                //logger.info("上传结果:"+Boolean.toString(OK));
//
                //boolean XO=ftpUtil.deleteFile("0001\\CPC\\XXM","mqserver.jar");
                //logger.info("删除文件结果:"+ Boolean.toString(XO));

                boolean OO = ftpUtil.existFile("\\0001\\Notice\\20130713100434051.zip");
                logger.info("aa.zip exist:" + OO);

                //byte[] Bs=ftpUtil.download("\\0001\\Notice\\20130713100434051.zip");
                // logger.info("Length:"+Integer.toString(Bs.length));

                //boolean OKK=ftpUtil.existFile("\\0001\\CPC\\XXM\\aa.zip");
                //logger.info("aa.zip exist:"+Boolean.toString(OKK));
                //boolean Obb=ftpUtil.existFile("\\0001\\CPC\\XXM\\abb.zip");
                //logger.info("abb.zip exist:"+Boolean.toString(Obb));

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stream != null) stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static void XmlTest() {
        File file = new File("D:\\a.xml");
        SAXReader xmlRead = new SAXReader();
        try {
            //QINGQIUXX p= XmlParsor.getSingleByChildNode(file,"//data-bus/QINGQIUXX",QINGQIUXX.class);
            //if(p!=null)logger.info(p.getNEIBUBH());

            //List<BAONEIWJXX> bs=XmlParsor.getByChildNodes(file,"//data-bus/WENJIANBYSXX/BAONEIWJXX",BAONEIWJXX
            // .class);
            //logger.info("Size:"+Integer.toString(bs.size()));

            File file1 = new File("C:\\TEMP\\bbb.xml");

            //List<FAMINGRXM> Fs=XmlParsor.getByChildNodes(file1,"//实用新型专利请求书/发明人/*",FAMINGRXM.class);
            //logger.info(Integer.toString(Fs.size()));
            //if(Fs.size()>0) logger.info("FAMINGR:"+Fs.get(0).getName());

            //List<SHENQINGRXM> Ft=XmlParsor.getByChildNodes(file1,"//实用新型专利请求书/申请人/*",SHENQINGRXM.class);
            //logger.info(Integer.toString(Fs.size()));
            //if(Fs.size()>0) logger.info("SHENQINGR:"+Fs.get(0).getName());
            //CPCPackageParsor packageParsor=new CPCPackageParsor
            // ("D:\\Upload\\0001\\CPC\\3bbade2a-7428-4dd5-8ce9-1abc1be959b7\\new\\");
            //DLJGMC M= XmlParsor.getSingleByChildNode(file1,"//实用新型专利请求书/专利代理机构",DLJGMC.class);
            //logger.info(M.getName());
            //logger.info(Integer.toString(M.getDLRXM().size()));

            //LXRXM lxrxm=XmlParsor.getSingleByChildNode(file1,"//实用新型专利请求书/联系人",LXRXM.class);
            //logger.info(lxrxm.getName()+lxrxm.getProvince());
            List<feeItem> items = XmlParsor.getByChildNodes(file1, "//cn_notice_info/fee_info_all/fee_info/*"
                    , feeItem
                            .class);
            logger.info(Integer.toString(items.size()));

            feeObject oo = XmlParsor.getSingleByChildNode(file1, "//cn_notice_info/*", feeObject.class);
            logger.info(oo.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = DtsystemApplication.class.getClassLoader().getResourceAsStream("license.xml");

            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void SplitFiveFiles(String FilePath, String SaveDir) throws Exception {
        Document document = new Document(FilePath);
        String docName = "";
        for (int i = 0; i < document.getSections().getCount(); i++) {
            Section section = document.getSections().get(i).deepClone();
            Document newDoc = new Document();
            newDoc.getSections().clear();
            Section newSection = (Section) newDoc.importNode(section, true);
            newSection.getBody().getFirstParagraph().getParagraphFormat().getStyle().getFont().setName("宋体");
            newDoc.getSections().add(newSection);
            docName = FiveName.get(i);
            String savePath = SaveDir + "/" + docName + ".DOC";
            newDoc.save(savePath);
            String pdfPath = SaveDir + "/" + docName + ".PDF";
            WordToPDF(savePath, pdfPath);
            FileUtils.deleteQuietly(new File(savePath));
        }
    }

    public static void WordToPDF(String inputFile, String pdfFile) throws Exception {
        ActiveXComponent app = null;
        System.out.println("开始转换:" + inputFile);
        long start = System.currentTimeMillis();
        try {
            app = new ActiveXComponent("Word.Application");
            Dispatch documents = app.getProperty("Documents").toDispatch();
            Dispatch document = Dispatch.call(documents, "Open", inputFile, false, true).toDispatch();
            File target = new File(pdfFile);
            if (target.exists()) {
                target.delete();
            }
            Dispatch.call(document, "SaveAs", pdfFile, 17);
            Dispatch.call(document, "Close", false);
            System.out.println("转换为: " + pdfFile);
            long end = System.currentTimeMillis();
            System.out.println("转换成功，用时：" + (end - start) + "ms");
        } catch (Exception e) {
            e.getMessage();
            System.out.println("转换失败" + e.getMessage());
        } finally {
            app.invoke("Quit", 0);
        }
    }

    private void aaa() throws Exception {
        try {
            URL u = ResourceUtils.getURL("classpath:static/images");
            File F = ResourceUtils.getFile(u);
            File[] Files = F.listFiles();
            for (int i = 0; i < Files.length; i++) {
                File x = Files[i];
                if (x.isFile()) {
                    String ext = FilenameUtils.getExtension(x.getPath()).toLowerCase();
                    if (ext.equals("png")) System.out.println(x.getName());
                }
            }
            System.out.println(Math.ceil(26 / 3) + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void TiffToPdf() throws Exception{
        Long X1=System.currentTimeMillis();
        BufferedImage image=ImageIO.read(new File("C:\\tmp\\1.tif"));
        PDDocument doc=new PDDocument();

        PDPage pageOne=new PDPage(new PDRectangle(image.getWidth(),image.getHeight()));
        doc.addPage(pageOne);
        PDPageContentStream pageStream = new PDPageContentStream(doc, pageOne);
        PDImageXObject xObject = LosslessFactory.createFromImage(doc,image);
        PDPageContentStream contentStream = new PDPageContentStream(doc, pageOne);
        contentStream.drawImage(xObject, 0, 0, image.getWidth(), image.getHeight());
        contentStream.close();
        doc.save("C:\\tmp\\11.pdf");
        doc.close();
        Long X2=System.currentTimeMillis();
        System.out.println("Used:"+Long.toString(X2-X1));
    }
}
