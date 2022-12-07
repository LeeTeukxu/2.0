package com.zhide.dtsystem.services.instance;

import com.zhide.dtsystem.common.CompanyPathUtils;
import com.zhide.dtsystem.common.FTPUtil;
import com.zhide.dtsystem.common.PdfUtils;
import com.zhide.dtsystem.common.ZipFileUtils;
import com.zhide.dtsystem.models.TextAndValue;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeUtility;
import javax.validation.constraints.Email;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

/**
 * @ClassName: EmailAttachment
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2022年10月18日 11:02
 **/
public class emailAttachment {
    MimeMessageHelper message;
    public emailAttachment(MimeMessageHelper helper){
        this.message=helper;
    }
    public void groupAndSave(List<TextAndValue> atts) throws Exception{
        if (atts.size() > 0) {
            FTPUtil F = new FTPUtil();
            if (F.connect() == true) {
                for (int i = 0; i < atts.size(); i++) {
                    TextAndValue value = atts.get(i);
                    String fileName = value.getText();
                    String filePath = value.getValue();

                    if (F.existFile(filePath) == true) {
                        String newFileName = CompanyPathUtils.getFullPath("Temp", UUID.randomUUID().toString()+".zip");
                        F.download(filePath, newFileName);
                        File fx = new File(newFileName);
                        if (fx.exists() == true) {
                            String extName = FilenameUtils.getExtension(newFileName).toLowerCase();
                            if (extName.equals("zip")) {
                                try {
                                    String pName = fileName.split("\\.")[0];
                                    processSingleZip(fx, pName);
                                }catch(Exception ax){
                                    String AName = MimeUtility.encodeText(StringUtils.trim(fileName), "utf-8", "B");
                                    message.addAttachment(AName, fx);
                                }
                            } else {
                                String AName = MimeUtility.encodeText(StringUtils.trim(fileName), "utf-8", "B");
                                message.addAttachment(AName, fx);
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     * create by: mmzs
     * description: TODO
     * create time:
     *
     一个ZIP文件，解压出来看看，有TIF和PDF文件不。
     有的话就做合成。
     * @return
     */
    private void processSingleZip(File zipFile,String fileName) throws Exception{
        String upZipDir = Paths.get(CompanyPathUtils.getImages(), UUID.randomUUID().toString()).toString();
        ZipFileUtils.unZip(zipFile.getPath(), upZipDir);
        File upFile=new File(upZipDir);
        List<File> tifFiles = FileUtils.listFiles(upFile, new String[]{"tif"}, true).stream().collect(toList());
        List<File> pdfFiles=FileUtils.listFiles(upFile, new String[]{"pdf"}, true).stream().collect(toList());
        int totalSize= tifFiles.size()+ pdfFiles.size();
        if(totalSize>=1) {
           File lastFile= PdfUtils.toFile(upZipDir,fileName);
            String AName = MimeUtility.encodeText(StringUtils.trim(lastFile.getName()), "utf-8", "B");
            message.addAttachment(AName, lastFile);
        } else {
            //不知道是些什么垃圾。不搞了。照着发。
            String AName = MimeUtility.encodeText(StringUtils.trim(fileName+".zip"), "utf-8", "B");
            message.addAttachment(AName, zipFile);
        }
    }
}
