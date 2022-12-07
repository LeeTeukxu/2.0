package com.zhide.dtsystem.services.define;

import com.zhide.dtsystem.models.TreeNode;
import com.zhide.dtsystem.models.tbAttachment;

import java.io.File;
import java.util.List;

public interface ICPCFileService {
    tbAttachment uploadFiveFile(String MainID, File docFile,String fileName) throws  Exception;
    List<File>getFiveFiles(int lx,String filePath,String saveDir) throws Exception;
    List<String> ViewCPCFile(String AttID) throws Exception;
    byte[] getFileContent(String MainID,String FileName) throws Exception;
    List<TreeNode> GetFileTree(String MainID) throws Exception;
    void CopyDocument(String MainID,String Famingmc,Integer Shenqinglx) throws Exception;
}
