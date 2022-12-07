package com.zhide.dtsystem.common;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

/**
 * @ClassName: PdfUtils
 * @Author: 肖新民
 * @*TODO:
 * @CreateTime: 2022年10月17日 9:27
 **/
public class PdfUtils {
    /**
     * create by: mmzs
     * description: TODO
     * create time:
     * 将目录里面的TIF、PDF全部转换并合并为一个PDF文件
     *
     * @return
     */
    public static byte[] toBytes(String dirName,String fileName) throws Exception {
        File TargetFile = toFile(dirName,fileName);
        return FileUtils.readFileToByteArray(TargetFile);
    }

    public static File toFile(String dirName,String fileName) throws Exception {
        File newDirInfo = new File(dirName);
        List<String> pdfFiles =
                FileUtils.listFiles(newDirInfo, new String[]{"pdf"}, true).stream().map(f -> f.getPath()).collect(toList());
        List<String> tifFiles =
                FileUtils.listFiles(newDirInfo, new String[]{"tif"}, true).stream().map(f -> f.getPath()).collect(toList());
        String targetDir = Paths.get(dirName, UUID.randomUUID().toString()).toString();
        File fff = new File(targetDir);
        if (fff.exists() == false) {
            fff.mkdirs();
        }
        List<String> Dirs = new ArrayList<>();
        for (int i = 0; i < tifFiles.size(); i++) {
            String dirPath = new File(tifFiles.get(i)).getParent();
            if (Dirs.contains(dirPath) == false) Dirs.add(dirPath);
        }
        Dirs.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (StringUtils.isEmpty(o1) == true && StringUtils.isEmpty(o2) == false) {
                    return -1;
                }
                if (StringUtils.isEmpty(o1) == false && StringUtils.isEmpty(o2) == true) {
                    return 1;
                }
                if (StringUtils.isEmpty(o1) == true && StringUtils.isEmpty(o2) == true) {
                    return 0;
                }
                if (o1.startsWith("GA")) return 1;
                if (o2.startsWith("GA")) return -1;

                String p1 = FilenameUtils.getBaseName(o1);
                String p2 = FilenameUtils.getBaseName(o2);
                if (p1.length() <= 3) return -1;
                if (p2.length() <= 3) return 1;

                String l1 = p1.substring(p1.length() - 3, p1.length());
                String l2 = p2.substring(p2.length() - 3, p2.length());
                if (l1.indexOf("(") == -1) return -1;
                if (l2.indexOf("(") == -1) return 1;

                String a1 = l1.replace("(", "").replace(")", "");
                String a2 = l2.replace("(", "").replace(")", "");
                return Integer.compare(Integer.parseInt(a1), Integer.parseInt(a2));
            }
        });
        List<String> needFiles = new ArrayList<>();
        for (int i = 0; i < Dirs.size(); i++) {
            String dName = Dirs.get(i);
            List<String> files = tifFiles.stream().filter(f -> f.startsWith(dName)).collect(toList());
            files.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    if (StringUtils.isEmpty(o1) == true && StringUtils.isEmpty(o2) == false) {
                        return -1;
                    }
                    if (StringUtils.isEmpty(o1) == false && StringUtils.isEmpty(o2) == true) {
                        return 1;
                    }
                    if (StringUtils.isEmpty(o1) == true && StringUtils.isEmpty(o2) == true) {
                        return 0;
                    }
                    String n1 = FilenameUtils.getBaseName(o1).replace("[\\w\\s\\W\\S]+", "");
                    String n2 = FilenameUtils.getBaseName(o2).replace("[\\w\\s\\W\\S]+", "");
                    return Integer.compare(Integer.parseInt(n1), Integer.parseInt(n2));
                }
            });
            needFiles.addAll(files);
        }
        String DDFile = Paths.get(targetDir, UUID.randomUUID().toString() + ".pdf").toString();
        ImageUtils.tifToPdf(needFiles, DDFile);
        pdfFiles.add(0, DDFile);
        String ffName=UUID.randomUUID().toString();
        if(StringUtils.isEmpty(fileName)==false)ffName=fileName;
        String target = Paths.get(targetDir, ffName+ ".pdf").toString();
        if (pdfFiles.size() > 1) {
            Long T1 = System.currentTimeMillis();
            PDFMergerUtility M = new PDFMergerUtility();
            MemoryUsageSetting setting = MemoryUsageSetting.setupMainMemoryOnly();
            for (int i = 0; i < pdfFiles.size(); i++) {
                String pdfFile = pdfFiles.get(i);
                M.addSource(pdfFile);
            }
            M.setDestinationFileName(target);
            M.mergeDocuments(setting);
            Long T2 = System.currentTimeMillis();
            System.out.println("合并" + Integer.toString(pdfFiles.size()) + "个PDF完成，用时:" + Long.toString(T2 - T1)+"毫秒。");
        } else {
            FileUtils.copyFile(new File(pdfFiles.get(0)),new File(target));
        }
        return new File(target);
    }
}
