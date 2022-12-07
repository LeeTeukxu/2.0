package com.zhide.dtsystem.common;

import com.aspose.words.Document;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.springframework.util.StringUtils;

import java.io.File;

/**
 * @ClassName: PageSizeUtils
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2021年08月18日 16:18
 **/
public class PageSizeUtils {
    public static Integer get(String filePath){
        String ExtName= FilenameUtils.getExtension(filePath).toUpperCase();
        if(ExtName.equals("PDF")) return getPdfCount(filePath);
        if(ExtName.equals("DOC") || ExtName.equals(".DOCX"))return getWordCount(filePath);
        return 1;
    }
    private static Integer getWordCount(String filePath){
        try {
            Document document=new Document(filePath);
            int X= document.getPageCount();
            return X;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    private static  Integer getPdfCount(String filePath){
        try {
            File file=new File(filePath);
            PDDocument pdDocument = PDDocument.load(file);
            int pages = pdDocument.getNumberOfPages();
            pdDocument.close();
            return pages;
        }
        catch(Exception ax){
            ax.printStackTrace();
            return 0;
        }
    }
    /**
     * create by: mmzs
     * description: TODO
     * create time:
     * 
     
     * @return 
     */
    public static Integer getSectionCount(String filePath){
        try {
            File file=new File(filePath);
            PDDocument pdDocument = PDDocument.load(file);
            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);
            PDFTextStripper tStripper = new PDFTextStripper();
            tStripper.setParagraphEnd("\n");
            String pdfFileInText = tStripper.getText(pdDocument);
            String lines[] = pdfFileInText.split("\\r?\\n");
            pdDocument.close();
            int X=0;
            for(String line:lines){
                line=line.trim();
                if(StringUtils.isEmpty(line))continue;
                if(line.length()<10) continue;
                if(line.indexOf(".")==-1) continue;
                String start=line.substring(0,4);
                if(start.endsWith(".")==false){
                    start=line.substring(0,line.indexOf("."));
                }
                start=start.replace(".","");
                X=Integer.parseInt(start);
            }
            return X;
        }
        catch(Exception ax){
            ax.printStackTrace();
            return 0;
        }
    }
}
