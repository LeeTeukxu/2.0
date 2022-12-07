package com.zhide.dtsystem.common;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipFileUtils {
    private static final Logger log = LoggerFactory.getLogger(ZipFileUtils.class);
    public static void unZip(String zipFilePath, String unZipDir) throws Exception {
        File testFile = new File(zipFilePath);
        if (testFile.exists() == false) throw new Exception("待解压的文件:" + zipFilePath + "不存在!");
        String extName = FilenameUtils.getExtension(zipFilePath).toUpperCase();
        Long begin=System.currentTimeMillis();
        if(extName.equals("ZIP")) {
            unZipFile(zipFilePath, unZipDir);
        }else unRarFile(zipFilePath, unZipDir);
        Long end=System.currentTimeMillis();
        log.info("解压【{}】用时【{}毫秒】",zipFilePath,(end-begin));
    }
    public static void zip(String sourcePath, String zipPath) {
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipPath);
            zos = new ZipOutputStream(fos);
            writeZip(new File(sourcePath), "", zos);
        } catch (FileNotFoundException e) {
            log.error("创建ZIP文件失败", e);
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
            } catch (IOException e) {
                log.error("创建ZIP文件失败", e);
            }

        }
    }
    private static void unZipFile(String zipFilePath, String unZipDir) throws Exception {
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(new File(zipFilePath), Charset.forName("gbk"));
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) entries.nextElement();
                if (zipEntry.isDirectory()) {
                    String dirPath = unZipDir + "/" + zipEntry.getName();
                    File cDir = new File(dirPath);
                    if (cDir.exists() == false) cDir.mkdirs();
                } else {
                    File targetFile = new File(unZipDir + "/" + zipEntry.getName());
                    if (targetFile.getParentFile().exists() == false) {
                        targetFile.getParentFile().mkdirs();
                    }
                    targetFile.createNewFile();
                    InputStream inputStream = zipFile.getInputStream(zipEntry);
                    OutputStream outputStream = new FileOutputStream(targetFile);
                    byte[] buffer = new byte[10240];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, len);
                    }
                    outputStream.flush();
                    outputStream.close();
                    inputStream.close();
                }
            }
        } catch (Exception ax) {
            try {
                if(zipFile!=null){
                    zipFile.close();
                    zipFile=null;
                }
            }
            catch(Exception bx){
                bx.printStackTrace();
                zipFile=null;
            }
            try {
                System.out.println("使用zipStream解压失败:"+ax.getMessage()+"，开始调用Rar.exe解压文件!");
                unRarFile(zipFilePath,unZipDir);
            }catch(Exception cx){
                cx.printStackTrace();
                throw new RuntimeException("解压附件失败，请将本附件删除，尝试重新压缩并重新上传。");
            }
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (Exception ax) {
                    ax.printStackTrace();
                }
            }
        }
    }
    static Object obj=new Object();
    private static void unRarFile(String zipFilePath, String unZipDir) throws  Exception{
        String rarExePath="D:\\rar.exe";
        if(new File(rarExePath).exists()==false) throw new Exception("无法启动解压程序，请检查rar.exe是否存在!");
        String ff="%s X -o+ -y %s %s";
        synchronized (obj) {
            String cmdText = String.format(ff, rarExePath, zipFilePath, unZipDir);
            System.out.println(cmdText);
            Process p = Runtime.getRuntime().exec(cmdText);
            int X = p.waitFor();
            if (X != 0) throw getResult(X);
            p.destroy();
        }
    }
    private static Exception getResult(int Result) {
        Exception ax=null;
        switch (Result) {
            case 1:
                ax=new Exception("有错误但没有发生致命错误！");
                break;
            case 2:
                ax=new Exception("发生一个致命错误！");
                break;
            case 3:
                ax=new Exception("解压缩时发生一个 CRC错误！");
                break;
            case 4:
                ax=new Exception("试图修改先前使用'k'命令锁定的压缩文件！");
                break;
            case 5:
                ax=new Exception("写入磁盘错误！");
                break;
            case 6:
                ax=new Exception("打开文件错误！");
                break;
            case 7:
                ax=new Exception("命令行选项错误！");
                break;
            case 8:
                ax=new Exception("没有足够的内存进行操作！");
                break;
            case 9:
                ax=new Exception("创建文件错误！");
                break;
            case 255:
                ax=new Exception("用户中断操作");
                break;
            default:
                ax=new Exception("未知错误！");
                break;
        }
        return ax;
    }
    public static void zip(String sourcePath, String zipPath, boolean showDirectory) {
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipPath);
            zos = new ZipOutputStream(fos);
            writeZip1(new File(sourcePath), "", zos, showDirectory);
        } catch (FileNotFoundException e) {
            log.error("创建ZIP文件失败", e);
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
            } catch (IOException e) {
                log.error("创建ZIP文件失败", e);
            }

        }
    }
    private static void writeZip(File file, String parentPath, ZipOutputStream zos) {
        if (file.exists()) {
            if (file.isDirectory()) {//处理文件夹
                parentPath += file.getName() + File.separator;
                File[] files = file.listFiles();
                if (files.length != 0) {
                    for (File f : files) {
                        writeZip(f, parentPath, zos);
                    }
                } else { //空目录则创建当前目录
                    try {
                        zos.putNextEntry(new ZipEntry(parentPath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                    parentPath = "";
                    ZipEntry ze = new ZipEntry(parentPath + file.getName());
                    zos.putNextEntry(ze);
                    byte[] content = new byte[1024];
                    int len;
                    while ((len = fis.read(content)) != -1) {
                        zos.write(content, 0, len);
                        zos.flush();
                    }

                } catch (FileNotFoundException e) {
                    log.error("创建ZIP文件失败", e);
                } catch (IOException e) {
                    log.error("创建ZIP文件失败", e);
                } finally {
                    try {
                        if (fis != null) {
                            fis.close();
                        }
                    } catch (IOException e) {
                        log.error("创建ZIP文件失败", e);
                    }
                }
            }
        }
    }
    private static void writeZip1(File file, String parentPath, ZipOutputStream zos, boolean showDirectory) {
        if (file.exists()) {
            if (file.isDirectory()) {//处理文件夹
                parentPath += file.getName() + File.separator;
                File[] files = file.listFiles();
                if (files.length != 0) {
                    for (File f : files) {
                        writeZip1(f, parentPath, zos, showDirectory);
                    }
                } else { //空目录则创建当前目录
                    try {
                        zos.putNextEntry(new ZipEntry(parentPath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                    if (showDirectory == false) parentPath = "";
                    ZipEntry ze = new ZipEntry(parentPath + file.getName());
                    zos.putNextEntry(ze);
                    byte[] content = new byte[1024];
                    int len;
                    while ((len = fis.read(content)) != -1) {
                        zos.write(content, 0, len);
                        zos.flush();
                    }

                } catch (FileNotFoundException e) {
                    log.error("创建ZIP文件失败", e);
                } catch (IOException e) {
                    log.error("创建ZIP文件失败", e);
                } finally {
                    try {
                        if (fis != null) {
                            fis.close();
                        }
                    } catch (IOException e) {
                        log.error("创建ZIP文件失败", e);
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
        将多个文件合并为一个Zip文件。
     * @return
     */
    public static File toFile(File[] files,boolean deleteSource) throws Exception{
        if(files==null || files.length==0)throw new Exception("files参数不能为空!");
        if(files.length==1) return files[0];
        File targetDir=new File(CompanyPathUtils.getTempPath());
        for(int i=0;i<files.length;i++){
            File f=files[i];
            FileUtils.copyFile(f, Paths.get(targetDir.getPath(),f.getName()).toFile(),true);
            if(deleteSource) FileUtils.deleteQuietly(f);
        }
        String targetZip=CompanyPathUtils.getTempPath(UUID.randomUUID().toString()+".zip");
        zip(targetDir.getPath(),targetZip);
        FileUtils.deleteDirectory(targetDir);//中间文件无条件删除
        return new File(targetZip);
    }
    public static byte[] toBytes(File[] files,boolean deleteSource)throws Exception{
        File tt=toFile(files,deleteSource);
        byte[] Bs=FileUtils.readFileToByteArray(tt);
        FileUtils.deleteQuietly(tt);//中间文件无条件删除
        return Bs;
    }

}
