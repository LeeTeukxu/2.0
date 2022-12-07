package com.zhide.dtsystem.common;

import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.JPEGEncodeParam;
import org.apache.pdfbox.multipdf.PDFCloneUtility;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ImageUtils {
    /*
     * tif转Jpg
     * */
    public static void tiftoJpg(String source, String target) throws Exception {
        File tarFile = new File(target);
        if (tarFile.getParentFile().exists() == false) {
            tarFile.getParentFile().mkdirs();
        }
        RenderedOp src2 = null;
        OutputStream os2 = null;
        try {
            src2 = JAI.create("fileload", source);
            os2 = new FileOutputStream(target);
            JPEGEncodeParam param2 = new JPEGEncodeParam();
            ImageEncoder enc2 = ImageCodec.createImageEncoder("JPEG", os2, param2);
            enc2.encode(src2);
            enc2.getOutputStream().flush();
            enc2.getOutputStream().close();
            os2.flush();
        } catch (Exception ax) {
            ax.printStackTrace();
        } finally {
            try {
                os2.close();
                src2.dispose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static byte[] tiftoJpg(String source) throws Exception {
        RenderedOp src2 = null;
        ByteArrayOutputStream os2 = null;
        byte[] Bs = null;
        try {
            src2 = JAI.create("fileload", source);
            os2 = new ByteArrayOutputStream();

            JPEGEncodeParam param2 = new JPEGEncodeParam();
            ImageEncoder enc2 = ImageCodec.createImageEncoder("JPEG", os2, param2);
            enc2.encode(src2);
            enc2.getOutputStream().flush();
            enc2.getOutputStream().close();
            os2.flush();
            Bs = os2.toByteArray();
        } catch (Exception ax) {
            ax.printStackTrace();
        } finally {
            try {
                os2.close();
                src2.dispose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Bs;
    }

    public static void  tifToPdf(String source, String target) throws Exception {
        PDDocument doc = new PDDocument();
        try {
            BufferedImage image =ImageIO.read(new File(source));
            PDPage pageOne = new PDPage(new PDRectangle(image.getWidth(), image.getHeight()));
            doc.addPage(pageOne);
            PDPageContentStream pageStream = new PDPageContentStream(doc, pageOne);
            PDImageXObject xObject = LosslessFactory.createFromImage(doc, image);
            PDPageContentStream contentStream = new PDPageContentStream(doc, pageOne);
            contentStream.drawImage(xObject, 0, 0, image.getWidth(), image.getHeight());
            contentStream.close();
            doc.save(target);
        } catch (Exception ax) {
            throw  new Exception(source+"转换为pdf出错:"+ax.getMessage());
        } finally {
            doc.close();
        }
    }
    public static void tifToPdf(List<String> sources,String target) throws  Exception {
        PDDocument doc = new PDDocument();
        String source="";
        try {

            for(int i=0;i< sources.size();i++) {
                source= sources.get(i);
                Long T1=System.currentTimeMillis();
                BufferedImage image = ImageIO.read(new File(source));
                PDPage pageOne = new PDPage(new PDRectangle(image.getWidth(), image.getHeight()));
                doc.addPage(pageOne);
                PDImageXObject xObject = LosslessFactory.createFromImage(doc, image);
                PDPageContentStream contentStream = new PDPageContentStream(doc, pageOne);
                contentStream.drawImage(xObject, 0, 0, image.getWidth(), image.getHeight());
                contentStream.close();
                Long T2=System.currentTimeMillis();
                System.out.println(source+"转换为PDF完成，用时:"+Long.toString(T2-T1)+"毫秒。");
            }
            doc.save(target);
        } catch (Exception ax) {
            throw  new Exception(source+"转换为pdf出错:"+ax.getMessage());
        } finally {
            doc.close();
        }
    }

    /**
     * Pdf转Jpg
     **/
    public static List<String> pdftoJpg(String source, String targetDir) throws Exception {
        Logger logger = LoggerFactory.getLogger(ImageUtils.class);
        List<String> files = new ArrayList<>();
        File file = new File(source);
        if (file.exists() == false) throw new Exception("要转换的PDF源文件不存在!");
        PDDocument doc = null;
        PDFRenderer renderer = null;
        try {
            String pureName = file.getName().toLowerCase().replace(".pdf", "");
            doc = PDDocument.load(file);
            renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            for (int i = 0; i < pageCount; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 192);
                File Fx = new File(targetDir);
                if (Fx.exists() == false) {
                    Fx.mkdirs();
                }
                String tempName = Paths.get(targetDir, pureName + "_" + Integer.toString(i + 1) + ".jpg").toString();
                ImageIO.write(image, "jpg", new File(tempName));
                files.add(tempName);
            }
            logger.info(source + "转换PDF为Jpg成功！");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (doc != null) doc.close();
        }
        return files;
    }

    /**
     * create by: mmzs
     * description: TODO
     * create time:
     * 本地图片转base64
     *
     * @return
     */
    public static String toBase64(String filePath) throws Exception {
        String x = "";
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(new File(filePath));
            byte[] bx = new byte[stream.available()];
            stream.read(bx, 0, stream.available());
            x = Base64Utils.encodeToString(bx);
        } catch (Exception ax) {
            ax.printStackTrace();
            x = "";
        } finally {
            if (stream != null) stream.close();
        }
        return x;
    }
}
